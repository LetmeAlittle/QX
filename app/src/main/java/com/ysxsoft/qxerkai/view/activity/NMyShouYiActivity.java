package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.view.MentionActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NMyShouYiActivity extends NBaseActivity {

    @BindView(R.id.status_bar2)
    View statusBar2;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_pengpengdou)
    TextView tvPengpengdou;
    @BindView(R.id.tv_ketixian)
    TextView tvKetixian;
    @BindView(R.id.ll_tixian)
    LinearLayout llTixian;
    @BindView(R.id.webView)
    WebView webView;
    private UserBean userBean;
    private String authorization;
    private double amount = 0;
    private DecimalFormat mformat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmy_shou_yi);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initStatusBar2(statusBar2);
        initTitleBar();
        initView();
        parseRule("2");
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
        tvPublicTitlebarCenter.setText("我的收益");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("收支明细");
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.jumpIntent(NMyShouYiActivity.this, NShouZhiMingXiActivity.class);
            }
        });
    }

    private void initView() {
        llTixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
                    @Override
                    public void onNext(UserDetailInfo userDetailInfo) {
                        if (userDetailInfo.getStatus_code() == 200) {
                            UserDetailInfo.DataBean data = userDetailInfo.getData();
                            if (data.getIs_true() == 1) {
                                //首先判断用户是否 已经进行了实名认证
                                //判断当前可提现的额度 如果小于 指定值 0 不可提现
                                if (amount >= 100) {
                                    IntentUtil.jumpIntent(NMyShouYiActivity.this, NTiXianActivity.class);
                                } else {
                                    onToast("可提现额度为" +
                                            CommonConstant.REN_MIN_COIN_SING + mformat.format(amount) + "不满足提现要求");
                                }
                            } else {
                                onToast("请您首先进行身份认证！");
                            }
                        } else {
                            onToast(userDetailInfo.getMessage());
                        }
                    }
                }, NMyShouYiActivity.this), "", authorization);
            }
        });
        webView.setBackgroundColor(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        //获取当前用户账户余额
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //初始化用户余额
                    amount = userDetailInfo.getData().getMember_account() / 10.00;
                    tvKetixian.setText("可提现金：" + mformat.format(amount) + "元");
                    tvPengpengdou.setText(String.valueOf(userDetailInfo.getData().getMember_account()));
                }
            }
        }, this), "", authorization);
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this, message, Toast.LENGTH_SHORT);
    }

    /**
     * @param aid
     */
    private void parseRule(String aid) {
        Map<String, String> map = new HashMap<>();
        map.put("aid", aid);

        RetrofitTools.getRule(map)
                .subscribe(new ResponseSubscriber<RuleResponse>() {
                    @Override
                    public void onSuccess(RuleResponse ruleResponse, int code, String msg) {
                        if (code == 200) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(ruleResponse.getData());
                            webView.loadData(stringBuilder.toString(), "text/html;charset=UTF-8", null);
                        } else {
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {

                    }
                });
    }

}
