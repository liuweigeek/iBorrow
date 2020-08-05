package com.zhinang.iborrow.util;

import java.util.UUID;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return (null == str) || ("".equals(str.trim()));
    }

    public static boolean isNotEmpty(String str) {
        return (null != str) && (!("".equals(str.trim())));
    }

    public static String getParamFromUrl(String url) {
        String afterQuestion = url.substring(url.indexOf("?") + 1);
        return afterQuestion.substring(afterQuestion.indexOf("=") + 1);
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static String getRandomString(char c) {
        return UUID.randomUUID().toString().replace("-", Character.toString(c));
    }

    public static String getRandomStringVipId() {
        String vipId = UUID.randomUUID().toString().substring(0, 7);
        vipId = vipId.replace("4", "6");
        vipId = vipId.replace("7", "8");
        return vipId;
    }

}
