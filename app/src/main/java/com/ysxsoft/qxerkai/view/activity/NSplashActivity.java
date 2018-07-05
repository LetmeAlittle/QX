package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.view.MainActivity;

/**
 * 启动页
 * @author zhaozhipeng
 */
public class NSplashActivity extends NBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsplash);
        setBackEnable(false);
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(NSplashActivity.this, MainActivity.class));
                finish();
            }
        }.sendEmptyMessageDelayed(0,3000);
    }


    @Override
    public void onBackPressed() {
    }
}
