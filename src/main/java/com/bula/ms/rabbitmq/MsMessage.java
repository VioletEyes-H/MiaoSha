package com.bula.ms.rabbitmq;

import com.bula.ms.entity.User;

import java.io.Serializable;

public class MsMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
