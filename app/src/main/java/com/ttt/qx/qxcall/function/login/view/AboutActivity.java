package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * 关于我们、隐私条款、风险说明
 * Created by wyd on 2017/9/6.
 */

public class AboutActivity extends BaseActivity implements ISettingView {

    //关于url
    private String mAboutUrl;
    private String mark;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAboutUrl = getIntent().getStringExtra("aboutUrl");
        mark = getIntent().getStringExtra("mark");
    }

    /**
     * 初始化view
     */
    private void initView() {
    }


    @Override
    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onFinish() {
        //销毁当前
        finish();
    }
}
