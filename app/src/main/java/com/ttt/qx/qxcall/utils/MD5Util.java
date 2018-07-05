package com.ttt.qx.qxcall.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 王亚东 2015/7/12
 */
public class MD5Util {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    /**
     * 根据字符数组获取加密字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    public static String encode(String string) {
        StringBuilder hex = null;
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hex.toString();
    }
    // MD5加码。32位
    public   static String MD5(String inStr) {
        MessageDigest md5 = null ;
        try  {
            md5 = MessageDigest.getInstance("MD5" );
        } catch  (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return   "" ;
        }
        char [] charArray = inStr.toCharArray();
        byte [] byteArray =  new   byte [charArray.length];

        for  ( int  i =  0 ; i < charArray.length; i++)
            byteArray[i] = (byte ) charArray[i];

        byte [] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for  ( int  i =  0 ; i < md5Bytes.length; i++) {
            int  val = (( int ) md5Bytes[i]) &  0xff ;
            if  (val <  16 )
                hexValue.append("0" );
            hexValue.append(Integer.toHexString(val));
        }

        return  hexValue.toString();
    }

    // 可逆的加密算法
    public   static String KL(String inStr) {
        // String s = new String(inStr);
        char [] a = inStr.toCharArray();
        for  ( int  i =  0 ; i < a.length; i++) {
            a[i] = (char ) (a[i] ^  't' );
        }
        String s = new String(a);
        return  s;
    }

    // 加密后解密
    public   static String JM(String inStr) {
        char [] a = inStr.toCharArray();
        for  ( int  i =  0 ; i < a.length; i++) {
            a[i] = (char ) (a[i] ^  't' );
        }
        String k = new String(a);
        return  k;
    }

    /**
     * Created by zhangpan on 2016/7/2.
     */
    //  读取assets中的json文件
    public static class JsonFileReader {
        public static String getJson(Context context, String fileName) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open(fileName);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = bufferedInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baos.toString();
        }
    }
}
