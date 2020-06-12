package com.bula.ms.redis;

public class OrderKey extends BasePrefix {
    public static OrderKey getOrderInfoVo = new OrderKey(0, "ov");
    public static OrderKey getMsOrderByUserId = new OrderKey(0, "msoUid");

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getOrderInfo = new OrderKey(60, "oi");
}
