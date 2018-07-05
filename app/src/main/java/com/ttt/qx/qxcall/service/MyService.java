package com.ttt.qx.qxcall.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 后台服务类
 *  Created by wyd on 2017/7/19.
 */

public class MyService extends Service {
    // TODO: 2017/1/13 配置文件中配置
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
