package com.ttt.qx.qxcall.pager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;

import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.utils.AppActivityManager;
import com.ysxsoft.qxerkai.utils.SystemUtils;

/**
 * 基础Pager
 * Created by wyd on 2017/7/19.
 */
public abstract class BasePager {
    //应用缓存对象
    public SharedPreferences sp;
    //应用视图管理
    public AppActivityManager mActivityManager;
    /**
     * 上下文对象
     */
    public Context ctx;

    /**
     * view对象
     */
    private View view;

    /**
     * Activity 对象
     */
    public Activity activity;

    /*
     * application 对象
     */
    private QXCallApplication mQXCallApplication;

    public BasePager(Context ctx) {
        this.ctx = ctx;
        activity = (Activity) ctx;
        sp = ctx.getSharedPreferences(CommonConstant.APP_SP_CONFIG, Context.MODE_PRIVATE);
        mActivityManager = AppActivityManager.getInstance();
        view = initView();

    }

    /**
     * 初始化view
     */
    public abstract View initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 创建外界获取本view的方法
     */
    public View getView() {
        return view;
    }

    /**
     * 属性方法
     *
     * @return application对象
     */
    public QXCallApplication getMyapplication() {
        return mQXCallApplication;
    }

    public void setMyapplication(QXCallApplication mQXCallApplication) {
        this.mQXCallApplication = mQXCallApplication;
    }

    public void initStatusBar(View mStatusBar){
        if(SystemUtils.checkDeviceHasNavigationBar(ctx)){
            mStatusBar.setVisibility(View.GONE);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(activity);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    public void initStatusBar2(View mStatusBar){
        if(SystemUtils.checkDeviceHasNavigationBar(ctx)){
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(activity);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(activity);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

}
