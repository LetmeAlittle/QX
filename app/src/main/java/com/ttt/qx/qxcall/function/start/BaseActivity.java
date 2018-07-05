package com.ttt.qx.qxcall.function.start;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.utils.AppActivityManager;
import com.umeng.analytics.MobclickAgent;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.layoutback.ParallaxActivityBase;

/**
 * 应用所有Activity基础类
 * Created by wyd on 2017/7/19.
 */

public class BaseActivity extends ParallaxActivityBase {
    //应用缓存对象
    public SharedPreferences sp;
    public AppActivityManager mActivityManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(CommonConstant.APP_SP_CONFIG, Context.MODE_PRIVATE);
        mActivityManager = AppActivityManager.getInstance();
        //输入法弹出的时候不顶起布局
        //如果我们不设置"adjust..."的属性，对于没有滚动控件的布局来说，采用的是adjustPan方式，
        // 而对于有滚动控件的布局，则是采用的adjustResize方式。
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void initStatusBar(){
        if(!SystemUtils.checkDeviceHasNavigationBar(this)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                /**
                 * Android 5.0以上，全透明状态栏
                 */
                SystemUtils.setTranslucentStatus(this);

            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                /**
                 * Android 4.4以上，半透明状态栏
                 */
                SystemUtils.setTranslucentStatus(this,true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
