package com.ttt.qx.qxcall.utils.crash;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *  Created by wyd on 2017/7/19.
 * 接下来是用于处理崩溃异常的类，它要实现UncaughtExceptionHandler接口。
 * 实现它之后，将它设为默认的线程异常的处理者，这样程序崩溃之后，就会调用它了。
 * 但是在调用它之前，还需要先获取保存之前默认的handler，用于在我们收集了异常之后对程序进行处理，
 * 比如默认的弹出“程序已停止运行”的对话框（当然你也可以自己实现一个），终止程序，打印LOG。
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler sHandler = null;
    private static final Thread.UncaughtExceptionHandler sDefaultHandler = Thread
            .getDefaultUncaughtExceptionHandler();
    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private Future<?> future;
    private CrashListener mListener;
    private File mLogFile;

    /**
     * 单例模式运行
     * @return
     */
    public static CrashHandler getInstance() {
        if (sHandler == null) {
            sHandler = new CrashHandler();
        }
        return sHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        CrashLogUtil.writeLog(mLogFile, "CrashHandler", ex.getMessage(), ex);
        future = THREAD_POOL.submit(new Runnable() {
            public void run() {
                if (mListener != null) {
                    mListener.afterSaveCrash(mLogFile);
                }
            }
        });
        if (!future.isDone()) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sDefaultHandler.uncaughtException(thread, ex);
    }

    public void init(File logFile, CrashListener listener) {
        mLogFile = logFile;
        mListener = listener;
        //ljj 设置该异常处理为默认异常处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
