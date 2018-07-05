package com.ttt.qx.qxcall.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 转化时间戳为标准时间字符串或将字符串转化为时间戳
 * unix下的时间戳单位为秒
 *
 * @author wyd
 */
public class DealTimeUtil {
    /**
     * yyyy-MM-dd 格式
     * 将时间戳转化为字符串
     *
     * @param time
     * @return
     */
    public static String dealTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }

    /**
     * MM-dd 格式
     * 将时间戳转化为字符串
     *
     * @param time
     * @return
     */
    public static String dealTime5(long time) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
        return format.substring(format.indexOf("-") + 1, format.length());
    }

    /**
     * 根据任意给定的时间戳值 转化为 秒、分、小时、天、周
     *
     * @param time
     * @return
     */
    public static String handleStr(long time) {
        if (time >= (1000 * 60 * 60 * 24)) {//如果大于等于一天
            long l = time / (1000 * 60 * 60 * 24);
            return String.valueOf(time%(1000 * 60 * 60 * 24)==0?l:l)+"天";
        } else if (time >= (1000 * 60 * 60 )){//如果大于等于一小时
            long l = time /(1000 * 60 * 60 );
            return String.valueOf(time%(1000 * 60 * 60)==0?l:l)+"小时";
        } else if (time >= (1000 * 60)) {//如果大于等于一分钟
            long l = time / (1000 * 60);
            return String.valueOf(time % (1000 * 60) == 0 ? l : l) + "分钟";
        } else {
            return "已到期";
        }
    }
    /**
     * yyyy.MM.dd 格式
     * 将时间戳转化为字符串
     *
     * @param time
     * @return
     */
    public static String dealTime3(long time) {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date(time));
    }

    /**
     * yyyyMMdd 格式
     * 将时间戳转化为字符串
     *
     * @param time
     * @return
     */
    public static String dealTime4(long time) {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date(time)).replace(".", "");
    }

    /**
     * 由时间戳，返回2017.08.02格式
     *
     * @param time
     * @return
     */
    public static String dealTime6(long time) {
        String time4 = dealTime4(time);
        return time4.length() == 8 ? time4.substring(0, 4) + "." + time4.substring(4, 6) + "." + time4.substring(6, 8) : time4;
    }

    /**
     * 获取当前系统时间字符串
     *
     * @return
     */
    public static String getCurrentSystemTimeStr() {
        String createTime = "";
        Calendar c = Calendar.getInstance();
        // 取得系统日期:
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        createTime = year + "-" + ((month + 1) < 10 ? ("0" + (month + 1)) : (month + 1)) + "-" + (day < 10 ? ("0" + day) : day);
        return createTime;
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式
     * 将时间戳转化为字符串
     *
     * @param time
     * @return
     */
    public static String dealTime2(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
                time));
    }

    /**
     * 判断某一个年份是否是润年
     *
     * @param years
     */
    public static boolean isLeapYear(int years) {
        Calendar cal = Calendar.getInstance();
        cal.set(years, Calendar.DECEMBER, 31);
        if (cal.get(Calendar.DAY_OF_YEAR) == 366) {
            return true;
        }
        return false;
    }

    /**
     * 将给定的整数日期20160723 转化为2016-07-23
     *
     * @param time 日期数值
     * @return
     */
    public static String timeToFormatTime(int time) {
        return timeToFormatTime(String.valueOf(time));
    }

    /**
     * 将给定的整数日期20160723 转化为2016-07-23
     *
     * @param time 日期数值
     * @return
     */
    public static String timeToFormatTime(String time) {
        String result = "";
        result += (time.substring(0, 4) + "-");
        result += (time.substring(4, 6) + "-");
        result += (time.substring(6, 8));
        return result;
    }

    /**
     * 将字符串格式yyyy-MM-dd转化为时间戳
     *
     * @param standardTime 字符串格式
     * @return
     */
    public static long standardTimeToTimeStamp(String standardTime) {
        long time = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(standardTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将字符串格式yyyy-MM-dd HH:mm:ss转化为时间戳
     *
     * @param standardTime 字符串格式
     * @return
     */
    public static long standardTimeToTimeStamp4(String standardTime) {
        long time = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(standardTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将字符串格式yyyy.MM.dd转化为时间戳
     *
     * @param standardTime 字符串格式
     * @return
     */
    public static long standardTimeToTimeStamp3(String standardTime) {
        long time = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = format.parse(standardTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将字符串格式yyyy-MM-dd HH:mm:ss转化为时间戳
     *
     * @param standardTime 字符串格式
     * @return
     */
    public static long standardTimeToTimeStamp2(String standardTime) {
        long time = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(standardTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取yyyyMMdd日期格式
     *
     * @param year  2017
     * @param month 1-12
     * @param day   1-31||1-18||1-30
     * @return
     */
    public static String getYYYYmmDD(int year, int month, String day) {
        String yearStr = String.valueOf(year);
        String monthTemp = String.valueOf(month);
        String monthStr = month < 10 ? "0" + monthTemp : monthTemp;
        String dayStr = Integer.parseInt(day) < 10 ? "0" + day : day;
        return yearStr + monthStr + dayStr;
    }

    /**
     * 获取yyyyMMdd日期格式
     *
     * @param year  2017
     * @param month 1-12
     * @param day   1-31||1-18||1-30
     * @return
     */
    public static String getYYYYmmDD(int year, int month, int day) {
        return getYYYYmmDD(year, month, String.valueOf(day));
    }
}
