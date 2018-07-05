package com.ttt.qx.qxcall.utils;

/**
 * 应用开发中 常用正则串
 * Created by wyd on 2017/10/16.
 */

public class CommonRegexUtil {
    //日期格式正则 2015-06-17
    public static final String dateReg = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])" +
            "|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|" +
            "(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    //一般昵称 正则
    public static final String NICKREG = "^[A-Za-z0-9\\u4E00-\\u9FA5]+$";
}
