package com.ttt.qx.qxcall.function.start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.widget.viewhelper.ImmerseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 应用启动页
 * Created by wyd on 2017/7/19.
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.sprain_iv)
    ImageView sprain_iv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //第二个参数表示闪屏 时间ms
        handler.sendEmptyMessageDelayed(100, 3000);
    }

    /**
     * 启动处理
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isFirst = sp.getBoolean(CommonConstant.FIRST_ENETR, true);
            if (isFirst) {
                //如果是第一次,进入引导页
                IntentUtil.jumpIntent(SplashActivity.this, GuideActivity.class);
                finish();
            } else {
                //如果不是第一次则直接进入首页
                IntentUtil.jumpIntent(SplashActivity.this, MainActivity.class);
                finish();
            }
        }
    };
}
