package com.ttt.qx.qxcall.function.home.presenter;

import android.content.Context;

import com.ttt.qx.qxcall.function.home.view.IMainView;

/**
 * 主界面业务逻辑实现类
 * Created by wyd on 2017/7/19.
 */

public class MainPresenterImpl implements IMainPresenter {
    private final Context context;
    private final IMainView iMainView;

    public MainPresenterImpl(Context context, IMainView iMainView) {
        this.context = context;
        this.iMainView = iMainView;
    }
}
