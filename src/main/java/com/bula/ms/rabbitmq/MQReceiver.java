package com.bula.ms.rabbitmq;


import com.bula.ms.entity.MsOrder;
import com.bula.ms.entity.User;
import com.bula.ms.redis.RedisService;
import com.bula.ms.service.GoodsService;
import com.bula.ms.service.MiaoshaService;
import com.bula.ms.service.OrderService;
import com.bula.ms.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接收者
 */
@Service
public class MQReceiver {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);


//    @RabbitListener(queues = MQConfig.QUEUE_NAME)
//    public void receive(String message) {
//        logger.info("receive message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message) {
//        logger.info("receive topic queue1 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message) {
//        logger.info("receive topic queue2 message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
//    public void receiveHeaderQueue(byte[] message) {
//        logger.info("receive headers message:" + new String(message));
//    }

    @RabbitListener(queues = MQConfig.MS_QUEUE)
    public void receiveMs(String message) {
        logger.info("Receive Ms:" + message);
        MsMessage msMessage = RedisService.stringToBean(message, MsMessage.class);
        User user = msMessage.getUser();
        long goodsId = msMessage.getGoodsId();
        //判断商品库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否秒杀到了
        MsOrder msOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (msOrder != null) {

            return;
        }
        //生成秒杀订单 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goodsVo);
    }
}
