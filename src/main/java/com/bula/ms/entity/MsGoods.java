package com.bula.ms.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (MsGoods)实体类
 *
 * @author makejava
 * @since 2020-05-16 23:11:01
 */
public class MsGoods implements Serializable {
    private static final long serialVersionUID = -54544172558689306L;
    /**
    * 秒杀的商品表id
    */
    private Long id;
    /**
    * 商品id
    */
    private Long goodsId;
    /**
    * 秒杀价
    */
    private Double msPrice;
    /**
    * 库存数量
    */
    private Integer stockCount;
    /**
    * 秒杀开始时间
    */
    private Date startDate;
    /**
    * 秒杀结束时间
    */
    private Date endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Double getMsPrice() {
        return msPrice;
    }

    public void setMsPrice(Double msPrice) {
        this.msPrice = msPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}