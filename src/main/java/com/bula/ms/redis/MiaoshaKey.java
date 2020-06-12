package com.bula.ms.redis;

public class MiaoshaKey extends BasePrefix {
    public static MiaoshaKey getMsPath = new MiaoshaKey(60, "msp");

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");

    public static MiaoshaKey getVerifyCode = new MiaoshaKey(300, "vc");
}
