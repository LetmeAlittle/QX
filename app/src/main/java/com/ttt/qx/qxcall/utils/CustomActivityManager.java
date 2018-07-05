package com.ttt.qx.qxcall.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by 王亚东 on 2017/11/17.
 */

public class CustomActivityManager {
    private static CustomActivityManager customActivityManager = new CustomActivityManager();
    private WeakReference<Activity> topActivity;

    private CustomActivityManager() {

    }

    public static CustomActivityManager getInstance() {
        return customActivityManager;
    }

    public Activity getTopActivity() {
        if (topActivity != null) {
            return topActivity.get();
        }
        return null;
    }

    public void setTopActivity(Activity topActivity) {
        this.topActivity = new WeakReference<>(topActivity);
    }

}
