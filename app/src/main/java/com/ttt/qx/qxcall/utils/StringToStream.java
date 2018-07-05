package com.ttt.qx.qxcall.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 字符串与流之间的相互转化
 * Created by wyd on 2016/5/11.
 */
public class StringToStream {
    /**
     * 字符串转化为输入流
     *
     * @param sInputString 输入字符串
     * @return
     */
    public static InputStream getStringStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
