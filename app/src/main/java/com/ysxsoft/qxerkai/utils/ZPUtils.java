package com.ysxsoft.qxerkai.utils;

/**
 * Created by zhaozhipeng on 18/5/11.
 */

public class ZPUtils {

    /**
     * 截取字符串，超过长度显示...
     *
     * @param temp
     * @param len
     * @return
     */
    public static String subString(String temp, int len) {
        if (temp.length() > len) {
            return temp.substring(0, len) + "...";
        }
        return temp;
    }
}
