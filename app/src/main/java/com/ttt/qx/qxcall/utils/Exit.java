package com.ttt.qx.qxcall.utils;

import android.os.Handler;
import android.os.HandlerThread;


/**
 * 应用程序退出工具
 * Created by wyd on 2016/2/26
 */
public class Exit {
    //应用程序退出时延
    public static final int EXIT_DELAY = 3000;
    private boolean isExit = false;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            /**
             * 超过给定延迟之后自动将退出标记置为false
             */
            isExit = false;
        }
    };

    /**
     * 退出
     */
    public void doExitInSeconds() {
        isExit = true;
        HandlerThread thread = new HandlerThread("doTask");
        thread.start();
        new Handler(thread.getLooper()).postDelayed(task, EXIT_DELAY);
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }
}
