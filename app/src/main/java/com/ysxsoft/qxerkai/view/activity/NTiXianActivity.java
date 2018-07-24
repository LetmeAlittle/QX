package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NTiXianActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.tv_ketijine)
    TextView tvKetijine;
    @BindView(R.id.tv_quanbutixian)
    TextView tvQuanbutixian;
    @BindView(R.id.tv_tixian_btn)
    TextView tvTixianBtn;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_zhifubao)
    EditText etZhifubao;
    @BindView(R.id.et_xingming)
    EditText etXingming;
    private UserBean userBean;
    private String authorization;
    private String amount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nti_xian);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("提现");
    }

    private void initView() {
        tvQuanbutixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount != null) {
                    etMoney.setText(amount);
                }
            }
        });
        tvTixianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alipayNum = etZhifubao.getText().toString().trim();
                if (!alipayNum.equals("")) {
                    String realName = etXingming.getText().toString().trim();
                    if (!realName.equals("")) {
                        String mentionMoney = etMoney.getText().toString().trim();
                        if (!mentionMoney.equals("")) {
                            MineModel.getMineModel().mentionMoney(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                @Override
                                public void onNext(StandardResponse standardResponse) {
                                    if (standardResponse.getStatus_code() == 200) {
                                        ToastUtils.showToast(NTiXianActivity.this, "提现申请已提交！", 0);
                                        finish();
                                    } else {
                                        ToastUtils.showToast(NTiXianActivity.this, standardResponse.getMessage(), 0);
                                    }
                                }
                            }
                                    , NTiXianActivity.this), mentionMoney, alipayNum, realName, authorization);
                        } else {
                            ToastUtils.showToast(NTiXianActivity.this, "提现金额不能为空！", 0);
                        }
                    } else {
                        ToastUtils.showToast(NTiXianActivity.this, "真实姓名不能为空！", 0);
                    }
                } else {
                    ToastUtils.showToast(NTiXianActivity.this, "提现账号不能为空！", 0);
                }
            }
        });
    }

    private void initData() {
        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        MineModel.getMineModel().crashAllow(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
            @Override
            public void onNext(StandardResponse standardResponse) {
                if (standardResponse.getStatus_code() == 200) {
                    amount = String.valueOf(standardResponse.getData().getAmount());
                    tvKetijine.setText("当前可提现金额为：" + String.valueOf(standardResponse.getData().getAmount()));
                }
            }
        }, this), authorization);
    }
}
