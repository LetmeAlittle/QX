package com.ysxsoft.qxerkai.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.ttt.qx.qxcall.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.layoutback.ParallaxActivityBase;


/**
 * Created by zhaozhipeng on 17/6/5.
 */

public abstract class NBaseActivity extends ParallaxActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void initStatusBar() {
        if (!SystemUtils.checkDeviceHasNavigationBar(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                /**
                 * Android 5.0以上，全透明状态栏
                 */
                SystemUtils.setTranslucentStatus(this);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                /**
                 * Android 4.4以上，半透明状态栏
                 */
                SystemUtils.setTranslucentStatus(this, true);
            }
        }
    }

    /**
     * 初始化自定义的状态栏
     * 4.4以下，系统自带状态栏，隐藏自定义状态栏
     * 4.4以上，设置系统状态栏透明，显示自定义状态栏，并设置状态栏高度
     *
     * @param mStatusBar
     */
    public void initStatusBar(View mStatusBar) {
        if (SystemUtils.checkDeviceHasNavigationBar(this)) {
            mStatusBar.setVisibility(View.GONE);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    public void initStatusBar2(View mStatusBar) {
        if (SystemUtils.checkDeviceHasNavigationBar(this)) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(this);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

}
