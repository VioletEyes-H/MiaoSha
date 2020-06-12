package com.bula.ms;

import com.bula.ms.entity.User;
import com.bula.ms.rabbitmq.MQSender;
import com.bula.ms.rabbitmq.MsMessage;
import com.bula.ms.service.GoodsService;
import com.bula.ms.service.MiaoshaService;
import com.bula.ms.service.OrderService;
import com.bula.ms.vo.GoodsVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MQSender mqSender;

    @Test
    void createOrder() {
        User user = new User();
        user.setId(Long.parseLong("13500035815"));
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setMsPrice(Double.valueOf("0.1"));
        goodsVo.setId(Long.parseLong("1"));
        goodsVo.setGoodsName("手机1");
//        miaoshaService.miaosha(user, goodsVo);
        goodsService.reduceStock(goodsVo);
    }

    @Test
    void rabbitMqTestSend() {
        MsMessage msMessage = new MsMessage();
        msMessage.setGoodsId(1);
        User user = new User();
        user.setNickname("abc");
        msMessage.setUser(user);
        mqSender.sendMiaoshaMessage(msMessage);
    }

    @Test
    void reducesStockTest() {
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setId(1L);
        boolean result = goodsService.reduceStock(goodsVo);
        System.out.println(result);
    }
}
