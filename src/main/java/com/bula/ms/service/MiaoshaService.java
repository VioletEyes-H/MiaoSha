package com.bula.ms.service;

import com.bula.ms.entity.MsOrder;
import com.bula.ms.entity.OrderInfo;
import com.bula.ms.entity.User;
import com.bula.ms.redis.MiaoshaKey;
import com.bula.ms.redis.RedisService;
import com.bula.ms.util.MD5Util;
import com.bula.ms.util.UUIDUtil;
import com.bula.ms.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    /**
     * 秒杀商品
     *
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo) {
        //减少库存
        boolean success = goodsService.reduceStock(goodsVo);
        if (success) {
            //生成order
            return orderService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }


    /**
     * 获取秒杀结果
     *
     * @param userId
     * @param goodsId
     * @return
     */
    public long getMsResult(Long userId, long goodsId) {
        MsOrder msOrder = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (msOrder != null) {//秒杀成功
            return msOrder.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);//判断是否卖完
            if (isOver) {
                return -1;//秒杀失败
            } else {
                return 0;//排队中
            }
        }
    }

    /**
     * 在redis设置标记，商品已卖完
     *
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    /**
     * 获取redis设置的标记，查看商品是否卖完
     *
     * @param goodsId
     * @return
     */
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }

    public boolean checkPath(User user, long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String pathOld = redisService.get(MiaoshaKey.getMsPath, "" + user.getId() + "_" + goodsId, String.class);
        return path.equals(pathOld);
    }

    public String createMiaoshaPath(User user, long goodsId) {
        if (user == null || goodsId <= 0)
            return null;
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisService.set(MiaoshaKey.getMsPath, "" + user.getId() + "_" + goodsId, str);
        return str;
    }

    public BufferedImage createVerifyCode(User user, long goodsId) {
        if (user == null || goodsId <= 0)
            return null;
        int width = 80;
        int height = 32;
        //Create Image
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        //Set The Background Color
        graphics.setColor(new Color(0xDCDCDC));
        graphics.fillRect(0, 0, width, height);
        //Draw The Border
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width - 1, height - 1);
        //Create A Random
        Random random = new Random();
        //生成50个干扰小点
        for (int i = 1; i < 500; i++) {
            int x = random.nextInt(80);
            int y = random.nextInt(50);
            graphics.drawOval(x, y, 1, 1);
        }
        String verifyCode = generateVerifyCode(random);
        graphics.setColor(new Color(0, 100, 0));
        graphics.setFont(new Font("Console", Font.BOLD, 20));
        graphics.drawString(verifyCode, 8, 24);
        graphics.dispose();
        //存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getVerifyCode, user.getId() + "_" + goodsId, rnd);
        return bufferedImage;
    }

    private int calc(String exp) {
        try {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
            return (Integer) scriptEngine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[]{'+', '-'};

    private static String generateVerifyCode(Random random) {
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        char op1 = ops[random.nextInt(2)];
        char op2 = ops[random.nextInt(2)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    public boolean checkVerifyCode(User user, long goodsId, String verifyCode) {
        if (user == null || goodsId <= 0 || verifyCode == null) {
            return false;
        }
        String OldVerifyCode = redisService.get(MiaoshaKey.getVerifyCode, user.getId() + "_" + goodsId, String.class);
        redisService.delete(MiaoshaKey.getVerifyCode, user.getId() + "_" + goodsId);
        return verifyCode.equals(OldVerifyCode);
    }
}
