package com.ttt.qx.qxcall.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * 控制整个项目的任务栈操作
 * 对activity操作的工具类
 * created by wyd 2016/3/1
 */

public class AppActivityManager {
    private static Stack<Activity> mActivityStack;
    private static AppActivityManager mAppManager;

    private AppActivityManager() {
    }

    /**
     * 单一实例
     */
    public static synchronized AppActivityManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppActivityManager();
        }
        return mAppManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }

    /**
     * 将指定Activity从任务栈中移除，不进行finish
     *
     * @param cls 完整类名
     */
    public void removeActivityNotFinish(Class<?> cls) {
        if (mActivityStack != null) {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    removeActivityNotFinish(activity);
                }
            }
        }
    }

    /**
     * 从任务栈中移除指定的Activity，不需要finish
     *
     * @param activity
     */
    public void removeActivityNotFinish(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls 完整类名
     */
    public void killActivity(Class<?> cls) {
        if (mActivityStack != null) {
            for (Activity activity : mActivityStack) {//注意当任务栈中包含了多的相同的Activity时，系统会报错
                if (activity.getClass().equals(cls)) {
                    killActivity(activity);
                }
            }
        }

    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
