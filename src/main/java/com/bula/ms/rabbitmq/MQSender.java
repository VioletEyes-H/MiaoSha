package com.bula.ms.rabbitmq;

import com.bula.ms.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送者
 */
@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);
//    四种常用的交换机Exchange模式
//    public void send(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("Send Message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE_NAME, msg);
//    }
//
//    public void sendTopic(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("Send Topic Message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.ROUTING_KEY1, msg + "1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.ROUTING_KEY2, msg + "2");
//    }
//
//    public void sendFanout(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("Fanout Message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
//    }
//
//    public void sendHeaders(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("Headers Message:" + msg);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("header1", "value1");
//        messageProperties.setHeader("header2", "value2");
//        Message obj = new Message(msg.getBytes(), messageProperties);
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
//    }

    public void sendMiaoshaMessage(MsMessage msMessage) {
        String msg = RedisService.beanToString(msMessage);
        logger.info("Send Message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.MS_QUEUE, msg);
    }
}
