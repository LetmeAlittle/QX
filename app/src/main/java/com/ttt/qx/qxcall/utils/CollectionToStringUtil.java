package com.ttt.qx.qxcall.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合与字符串之间的转化
 * Created by wyd on 2016/3/8.
 */
public class CollectionToStringUtil {
    /**
     * 将字符串集合，以逗号分隔转化为字符串！
     *
     * @param arrayList 转化集合
     * @return 结果
     */
    public static String collToString(ArrayList arrayList) {
        String str = "";
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == arrayList.size() - 1) {
                str += arrayList.get(i);
            } else {
                str += arrayList.get(i) + ",";
            }
        }
        System.out.println(str);
        return str;
    }

    /**
     * 将字符串集合，以逗号分隔转化为字符串！
     *
     * @param list 转化集合
     * @return 结果
     */
    public static String collToString(List list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str += list.get(i);
            } else {
                str += list.get(i) + ",";
            }
        }
        System.out.println(str);
        return str;
    }

    /**
     * 将字符串集合，以指定分隔符 分隔转化为字符串！
     *
     * @param arrayList    转化集合
     * @param separateChar 分隔符
     * @return 结果
     */
    public static String collToString(ArrayList arrayList, String separateChar) {
        String str = "";
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == arrayList.size() - 1) {
                str += arrayList.get(i);
            } else {
                str += arrayList.get(i) + separateChar;
            }
        }
        System.out.println(str);
        return str;
    }

    /**
     * 将字符串，以逗号分隔的元素，转化为字符串集合！
     *
     * @param str 转化字符串
     * @return 结果
     */
    public static ArrayList<String> stringToColl(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (str != null) {
            if (!str.contains(",") && !"".equals(str)) {
                arrayList.add(str);//如果不包含逗号则说明只有一张图片
            } else {
                String[] split = str.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (!"".equals(split[i])) {
                        arrayList.add(split[i]);
                    }
                }
            }
        }
        return arrayList;
    }

    /**
     * 将字符串，以逗号分隔的元素，转化为字符串集合！
     *
     * @param str 转化字符串
     * @param str separateChar
     * @return 结果
     */
    public static ArrayList<String> stringToColl(String str, String separateChar) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (str != null) {
            if (!str.contains(separateChar) && !"".equals(str)) {
                arrayList.add(str);//如果不包含逗号则说明只有一张图片
            } else {
                String[] split = str.split(separateChar);
                for (int i = 0; i < split.length; i++) {
                    if (!"".equals(split[i])) {
                        arrayList.add(split[i]);
                    }
                }
            }
        }
        return arrayList;
    }
}
