package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/28.
 */

public class AboutQxcallActivity extends BaseActivity {
    @BindView(R.id.concat_tv)
    TextView concat_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        LoginModel.getLoginModel().androidOnline(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
            @Override
            public void onNext(StandardResponse response) throws IOException {
                if (response.getStatus_code() == 200) {
                    if (response.getData().getState().equals("1")) {
                        concat_tv.setText(CommonConstant.PHONE);
                    } else {
                        concat_tv.setText(CommonConstant.QQ);
                    }
                }
            }
        }, this));
    }

    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
        }
    }
}
