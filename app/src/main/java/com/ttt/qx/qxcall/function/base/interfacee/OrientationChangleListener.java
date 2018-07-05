package com.ttt.qx.qxcall.function.base.interfacee;

/**
 * 方向改变监听
 * Created by wyd on 2017/8/11.
 */

public interface OrientationChangleListener {
    /**
     * 方向标记
     */
    Oritation ORITATION = Oritation.PORTRAIT;

    enum Oritation {
        PORTRAIT,
        LANDSCAPE
    }

    void onChange(Oritation oritation);
}
