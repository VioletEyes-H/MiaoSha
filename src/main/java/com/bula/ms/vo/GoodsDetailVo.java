package com.bula.ms.vo;

import com.bula.ms.entity.User;

import java.io.Serializable;

public class GoodsDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int msStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goodsVo;
    private User user;

    public int getMsStatus() {
        return msStatus;
    }

    public void setMsStatus(int msStatus) {
        this.msStatus = msStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
