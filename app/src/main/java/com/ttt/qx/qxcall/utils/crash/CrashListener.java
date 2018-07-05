package com.ttt.qx.qxcall.utils.crash;

import java.io.File;

/**
 *  Created by wyd on 2017/7/19.
 */
public interface CrashListener {
    /**
     * 保存异常的日志。
     *
     * @param file
     */
    public void afterSaveCrash(File file);
}
