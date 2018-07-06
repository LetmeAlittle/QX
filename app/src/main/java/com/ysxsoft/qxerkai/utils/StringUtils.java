package com.ysxsoft.qxerkai.utils;

public class StringUtils {
    public static String convert(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return "";
        }
        return str;
    }
}
