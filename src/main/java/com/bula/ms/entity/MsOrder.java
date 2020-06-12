package com.bula.ms.entity;

import java.io.Serializable;

/**
 * (MsOrder)实体类
 *
 * @author makejava
 * @since 2020-05-16 23:11:14
 */
public class MsOrder implements Serializable {
    private static final long serialVersionUID = 354767454831914229L;
    
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 订单id
    */
    private Long orderId;
    /**
    * 商品id
    */
    private Long goodsId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

}