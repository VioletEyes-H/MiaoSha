package com.bula.ms.controller;

import com.bula.ms.access.AccessLimit;
import com.bula.ms.entity.MsOrder;
import com.bula.ms.entity.User;
import com.bula.ms.rabbitmq.MQSender;
import com.bula.ms.rabbitmq.MsMessage;
import com.bula.ms.redis.AccessKey;
import com.bula.ms.redis.GoodsKey;
import com.bula.ms.redis.RedisService;
import com.bula.ms.result.CodeMsg;
import com.bula.ms.result.Result;
import com.bula.ms.service.GoodsService;
import com.bula.ms.service.MiaoshaService;
import com.bula.ms.service.OrderService;
import com.bula.ms.vo.GoodsVo;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QPS：604
 * 5000 * 10并发
 * <p>
 * QPS：1300
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(MiaoshaController.class);
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();


    @PostMapping(value = "/{path}/ms")
    @ResponseBody
    public Result<Integer> ms(User user,
                              @PathVariable("path") String path,
                              @RequestParam("goodsId") long goodsId) {
        logger.info("秒杀请求：" + (user != null ? "用户" + user.getNickname() + "秒杀了商品" + goodsId : "没有用户") + "");
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //使用内存标记，减少redis的访问
        boolean over = localOverMap.get(goodsId);
        if (over) {//如果标记为true，就是商品秒杀完了
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMSGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到
        MsOrder msOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (msOrder != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MsMessage msMessage = new MsMessage();
        msMessage.setUser(user);
        msMessage.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(msMessage);
        return Result.success(0);//排队中
        /*
            //判断商品库存
            GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
            int stock = goodsVo.getStockCount();
            if (stock <= 0) {
                return Result.error(CodeMsg.MIAO_SHA_COUNT_OVER);
            }
            if (new Date().before(goodsVo.getStartDate())) {
                return Result.error(CodeMsg.MIAO_SHA_NOT_START);
            }
            if (new Date().after(goodsVo.getEndDate())) {
                return Result.error(CodeMsg.MIAO_SHA_OVER);
            }
            //判断是否已经秒杀到
            MsOrder msOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
            if (msOrder != null) {
                return Result.error(CodeMsg.REPEATE_MIAOSHA);
            }
            //减库存 下订单 写入秒杀订单
            OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
            return Result.success(orderInfo);
        */

    }

    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        if (goodsVoList == null) {
            return;
        }
        for (GoodsVo goodsVo : goodsVoList) {
            if (redisService.exists(GoodsKey.getMSGoodsStock, "" + goodsVo.getId())) {
                redisService.delete(GoodsKey.getMSGoodsStock, "" + goodsVo.getId());
            }
            localOverMap.put(goodsVo.getId(), false);
            redisService.set(GoodsKey.getMSGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
        }
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0：排队中
     *
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/result")
    @ResponseBody
    public Result<Long> MsResult(User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            logger.info("没有登录");
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        long result = miaoshaService.getMsResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @AccessLimit(seconds=5,maxCount=10,needLogin=true)
    @GetMapping(value = "/path")
    @ResponseBody
    public Result<String> GetMsPath(HttpServletRequest request, User user, @RequestParam("goodsId") long goodsId,
                                    @RequestParam(value = "verifyCode", defaultValue = "0") String verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        }

        //验证验证码
        boolean CheckVerifyCode = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
        if (!CheckVerifyCode) {
            return Result.error(CodeMsg.MIAO_SHA_VERIFY_CODE_FAIL);
        }
        String path = miaoshaService.createMiaoshaPath(user, goodsId);
        return Result.success(path);
    }

    @GetMapping(value = "/verifyCode")
    @ResponseBody
    public Result<String> getMsVerifyCode(HttpServletResponse response, User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        BufferedImage bufferedImage = miaoshaService.createVerifyCode(user, goodsId);
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "JPEG", outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.VERIFY_CODE_FAIL);
        }
    }

}
