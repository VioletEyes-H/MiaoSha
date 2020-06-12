package com.bula.ms.vo;

import com.bula.ms.entity.OrderInfo;
import com.bula.ms.entity.User;

import java.io.Serializable;
import java.util.Date;

public class OrderInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private GoodsVo goodsVo;
    private OrderInfo orderInfo;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
