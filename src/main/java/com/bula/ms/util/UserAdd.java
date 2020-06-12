package com.bula.ms.util;

public class UserAdd {


    private static String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";

    private static String charNum = "0123456789";

    public static String getNumber() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("135");
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * charNum.length());
            stringBuffer.append(charNum.charAt(index));
        }
        return stringBuffer.toString();
    }

    public static String getRandom(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * charStr.length());
            stringBuffer.append(charStr.charAt(index));
        }
        return stringBuffer.toString();
    }

}
