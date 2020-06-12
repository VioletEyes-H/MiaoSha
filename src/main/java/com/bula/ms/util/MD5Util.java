package com.bula.ms.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "violet";

    /**
     * 一次加密
     *
     * @param inputPass 用户输入的明文密码
     * @return
     */
    public static String inputPassFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 二次加密，把用户传过来已加密过的密码再加入salt进行二次md5加密
     *
     * @param formPass 用户传过来一次加密的密码
     * @param salt     随机的salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 整合以上两个方法，把用户传过来的明文密码进行一次加密再加入saltDB进行二次加密
     *
     * @param input 明文密码
     * @param saltDB 数据库salt
     * @return
     */
    public static String inputPassToDBPass(String input, String saltDB) {
        String formPass = inputPassFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassFormPass("123456"));
        System.out.println(inputPassToDBPass("123456","abc123"));
    }

}
