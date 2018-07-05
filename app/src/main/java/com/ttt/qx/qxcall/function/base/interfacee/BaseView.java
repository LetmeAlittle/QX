package com.ttt.qx.qxcall.function.base.interfacee;

/**
 * MVP中所有视图逻辑基础接口
 *  Created by wyd on 2017/7/19.
 */
public interface BaseView {
    /**
     * Toast弹出
     *
     * @param message
     */
    void onToast(String message);

    /**
     * finish
     */
    void onFinish();
}
