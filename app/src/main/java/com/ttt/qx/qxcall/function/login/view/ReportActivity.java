package com.ttt.qx.qxcall.function.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 发表动态
 * Created by wyd on 2017/7/30.
 */

public class ReportActivity extends BaseActivity {
    @BindView(R.id.public_content_et)
    EditText public_content_et;
    @BindView(R.id.importability_text_tv)
    TextView importability_text_tv;
    private UserDao userDao;
    private UserBean userBean;
    private String authorization;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        public_content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //改变之后 文本内容统计
                String content = public_content_et.getText().toString();
                if (content.length() <= 200) {
                    importability_text_tv.setText(String.valueOf(content.length()) + "/200");
                } else {
                    public_content_et.setText(content.substring(0, 200));
                    public_content_et.setSelection(200);
                    onToast("超出文本限制长度！");
                }
            }
        });
    }

    /**
     * 初始数据
     */
    private void initData() {
        id = getIntent().getIntExtra("id", -1);
        userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
    }

    @OnClick({R.id.top_back_rl, R.id.publish_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                onBackPressed();
                break;
            case R.id.publish_ll:
                String content = public_content_et.getText().toString().trim();
                if (!content.equals("")) {
                    LoginModel.getLoginModel().userReport(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) throws IOException {
                            onToast(standardResponse.getMessage());
                            if (standardResponse.getStatus_code() == 200) {
                                finish();
                            }
                        }
                    }, this), String.valueOf(id), content, authorization);
                } else {
                    onToast("举报描述不能为空！");
                }
                break;
        }
    }

    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
