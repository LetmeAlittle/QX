package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 赚取宝典
 * Created by wyd on 2017/7/19.
 */
public class EarnBookActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_book);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
        }
    }


    /**
     * 初始化view
     */
    private void initView() {
    }


    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }
}
