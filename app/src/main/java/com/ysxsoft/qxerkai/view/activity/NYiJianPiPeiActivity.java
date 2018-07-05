package com.ysxsoft.qxerkai.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.SystemUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NYiJianPiPeiActivity extends AppCompatActivity {

    @BindView(R.id.iv_zhuanquan)
    ImageView ivZhuanquan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_jian_pi_pei);
        ButterKnife.bind(this);
        initStatusBar();
        initView();
    }

    private void initView() {
        Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(-1);//动画的重复次数
        animation.setInterpolator(new LinearInterpolator());
        ivZhuanquan.startAnimation(animation);//开始动画
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
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

}
