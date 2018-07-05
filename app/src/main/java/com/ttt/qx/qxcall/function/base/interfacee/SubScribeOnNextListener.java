package com.ttt.qx.qxcall.function.base.interfacee;

import java.io.IOException;

/**
 * onNext方法监听接口类
 *  Created by wyd on 2017/7/19.
 */

public interface SubScribeOnNextListener<T> {
    void onNext(T t) throws IOException;
}
