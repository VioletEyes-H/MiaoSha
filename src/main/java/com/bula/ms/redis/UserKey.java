package com.bula.ms.redis;

public class UserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    public static UserKey getById = new UserKey(0,"id");

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE, "token");

}
