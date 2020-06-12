package com.bula.ms.controller;

import com.alibaba.fastjson.JSON;
import com.bula.ms.entity.User;
import com.bula.ms.exception.GlobalException;
import com.bula.ms.result.CodeMsg;
import com.bula.ms.result.Result;
import com.bula.ms.service.GoodsService;
import com.bula.ms.vo.GoodsDetailVo;
import com.bula.ms.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private GoodsService goodsService;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result<List<GoodsVo>> list(User user) {
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        if (goodsVoList == null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        logger.info("商品列表数据 - " + System.currentTimeMillis() + " : " + JSON.toJSON(goodsVoList));
        return Result.success(goodsVoList);
    }

    @GetMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(@PathVariable("goodsId") long goodsId, User user) {
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo == null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int msStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {
            //秒杀还没开始
            msStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            //秒杀已经结束
            msStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀正在进行中
            msStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setMsStatus(msStatus);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setGoodsVo(goodsVo);
        return Result.success(goodsDetailVo);
    }

}
