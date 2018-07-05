package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 提现视图
 * Created by wyd on 2017/7/19.
 */
public class MentionActivity extends BaseActivity {
    @BindView(R.id.alipay_num_et)
    EditText alipay_num_et;
    @BindView(R.id.real_name_et)
    EditText real_name_et;
    @BindView(R.id.mention_money_et)
    EditText mention_money_et;//提现额度
    @BindView(R.id.submit_tv)
    TextView submit_tv;
    @BindView(R.id.remain_tv)
    TextView remain_tv;
    private Context mContext;
    private UserBean userBean;
    private String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mention);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.submit_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.submit_tv:
                String alipayNum = alipay_num_et.getText().toString().trim();
                if (!alipayNum.equals("")) {
                    String realName = real_name_et.getText().toString().trim();
                    if (!realName.equals("")) {
                        String mentionMoney = mention_money_et.getText().toString().trim();
                        if (!mentionMoney.equals("")) {
                            MineModel.getMineModel().mentionMoney(new ProgressSubscribe<>(
                                    new SubScribeOnNextListener<StandardResponse>() {
                                        @Override
                                        public void onNext(StandardResponse standardResponse) {
                                            onToast(standardResponse.getMessage());
                                            if (standardResponse.getStatus_code() == 200) {
                                                onFinish();
                                            }
                                        }
                                    }
                                    , mContext), mentionMoney, alipayNum, realName, authorization);
                        } else {
                            onToast("提现金额不能为空！");
                        }
                    } else {
                        onToast("真实姓名不能为空！");
                    }

                } else {
                    onToast("提现账号不能为空！");
                }
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        MineModel.getMineModel().crashAllow(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
            @Override
            public void onNext(StandardResponse standardResponse) {
                if (standardResponse.getStatus_code() == 200) {
                    remain_tv.setText(String.valueOf(standardResponse.getData().getAmount()));
                }
            }
        }, mContext), authorization);
    }

    /**
     * 初始化view
     */
    private void initView() {
        //根据当前可提额度 设置 提交按钮是否可点击
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
