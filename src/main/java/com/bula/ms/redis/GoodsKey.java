package com.bula.ms.redis;

import com.bula.ms.vo.GoodsVo;

public class GoodsKey extends BasePrefix {

    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getMSGoodsStock = new GoodsKey(0,"gs");

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");

    public static GoodsKey getGoodsVo = new GoodsKey(60, "gv");
}
