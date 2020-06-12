package com.bula.ms.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (OrderInfo)实体类
 *
 * @author makejava
 * @since 2020-05-16 23:12:15
 */
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = -61359110120475825L;
    
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 商品id
    */
    private Long goodsId;
    /**
    * 收货地址id
    */
    private Long deliveryAddrId;
    /**
    * 冗余过来的商品名称
    */
    private String goodsName;
    /**
    * 商品数量
    */
    private Integer goodsCount;
    /**
    * 商品单价
    */
    private Double goodsPrice;
    /**
    * 1pc，2android，3ios
    */
    private int orderChannel;
    /**
    * 订单状态，0新建支付，1已支付，2已发货，3已收货，4已退款，5已完成
    */
    private int status;
    /**
    * 订单创建时间
    */
    private Date createDate;
    /**
    * 支付时间
    */
    private Date payDate;


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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(int orderChannel) {
        this.orderChannel = orderChannel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

}