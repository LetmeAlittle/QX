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

    public static void notify(String clazzName){
        if (pageHashMap == null) return;
        if (pageHashMap.containsKey(clazzName)) {
            pageHashMap.get(clazzName).change();
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
    }
}
