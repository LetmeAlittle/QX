package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的服务
 * Created by wyd on 2017/7/28.
 */

public class MyServerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_server);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化view
     */
    private void initView() {
    }

    //
    @OnClick({R.id.top_back_rl, R.id.tag_select_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                onFinish();
                break;
            case R.id.tag_select_rl:
                IntentUtil.jumpIntent(this, SetUserTagActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }

    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

}
