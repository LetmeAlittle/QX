package com.ysxsoft.qxerkai.utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ObserverMap {
    private static HashMap<String, IPageDataChangeObserver> pageHashMap;

    public static void reg(String clazzName, IPageDataChangeObserver target) {
        if (pageHashMap == null) {
            pageHashMap = new HashMap<>();
        }
        if (pageHashMap.containsValue(target)) {
            pageHashMap.remove(clazzName);
        }
        pageHashMap.put(clazzName, target);
    }

    public static void notifyAllPage() {
        if (pageHashMap == null) return;
        Set<String> set = pageHashMap.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            pageHashMap.get(key).change();
        }
    }

    public static void notify(String clazzName) {
        if (pageHashMap == null) return;
        if (pageHashMap.containsKey(clazzName)) {
            pageHashMap.get(clazzName).change();
        }
    }

    /**
     * 更新数据
     *
     * @param clazzName 目标名称
     * @param likeNum   变更后的点赞数量
     * @param commonNum 变更后的评论数量
     * @param isChanged 是否发生变化
     * @param position  变更的position
     */
    public static void notify(String clazzName, int likeNum, int commonNum, boolean isChanged, int position, int readNum) {
        if (pageHashMap == null) return;
        if (pageHashMap.containsKey(clazzName)) {
            pageHashMap.get(clazzName).change(likeNum, commonNum, isChanged, position, readNum);
        }
    }

    public static void remove(String clazzName) {
        if (pageHashMap == null) {
            pageHashMap = new HashMap<>();
        }
        pageHashMap.remove(clazzName);
    }

    public interface IPageDataChangeObserver {
        void change();

        void change(int likeNum, int commonNum, boolean isChanged, int position, int readNum);
    }
}
