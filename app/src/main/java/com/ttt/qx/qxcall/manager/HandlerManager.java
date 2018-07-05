package com.ttt.qx.qxcall.manager;

import android.os.Handler;
import android.os.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 王亚东 on 2017/11/12.
 */

public class HandlerManager {
    private static HandlerManager instance;
    private static Map<String, Handler> handlerMap = new ConcurrentHashMap<>();

    private HandlerManager() {
    }

    public static synchronized HandlerManager getInstance() {
        if (instance == null) {
            instance = new HandlerManager();
        }
        return instance;
    }

    public void registerHandler(Class cls, Handler handler) {
        handlerMap.put(cls.getSimpleName(), handler);
    }

    public void sendMessage(Class cls, int what, Object object) {
        Message message = Message.obtain();
        message.obj = object;
        message.what = what;
        handlerMap.get(cls.getSimpleName()).sendMessage(message);
    }
}
