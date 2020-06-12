package com.bula.ms.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列配置
 * 以下是4种Exchange交换机配置
 */
@Configuration
public class MQConfig {

    public static final String QUEUE_NAME = "queue";
    public static final String MS_QUEUE = "msQueue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";
    public static final String HEADER_QUEUE = "header.queue";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String HEADERS_EXCHANGE = "headersExchange";

//    /**
//     * Direct模式，按照QUEUE_NAME分发到指定队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME, true);
//    }
//
//    /**
//     * Topic模式，多关键字匹配
//     */
//    @Bean
//    public Queue topicQueue1() {
//        return new Queue(TOPIC_QUEUE1, true);
//    }
//
//    @Bean
//    public Queue topicQueue2() {
//        return new Queue(TOPIC_QUEUE2, true);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//
//
//    @Bean
//    public Binding topicBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
//    }
//
//    /**
//     * ROUTING_KEY使用通配符#的意思是发送给匹配字母的所有接收者
//     *
//     * @return
//     */
//    @Bean
//    public Binding topicBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
//    }
//
//    /**
//     * Fanout模式 广播模式
//     */
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(FANOUT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding FanoutBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
//    }
//
//    @Bean
//    public Binding FanoutBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
//    }
//
//    /**
//     * Header模式，通过添加属性key-value匹配
//     */
//
//    @Bean
//    public HeadersExchange headersExchange() {
//        return new HeadersExchange(HEADERS_EXCHANGE);
//    }
//
//    @Bean
//    public Queue headerQueue() {
//        return new Queue(HEADER_QUEUE, true);
//    }
//
//    @Bean
//    public Binding headerBinding() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("header1", "value1");
//        map.put("header2", "value2");
//        return BindingBuilder.bind(headerQueue()).to(headersExchange()).whereAll(map).match();
//    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue ms_queue() {
        return new Queue(MS_QUEUE, true);
    }
}
