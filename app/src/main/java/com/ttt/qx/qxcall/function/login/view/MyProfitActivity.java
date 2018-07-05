package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的收益
 * Created by wyd on 2017/7/19.
 */
public class MyProfitActivity extends BaseActivity {
    @BindView(R.id.txje_bg_iv)
    ImageView txje_bg_iv;
    @BindView(R.id.enable_mention_money_tv)
    TextView enable_mention_money_tv;
    @BindView(R.id.diamon_num_tv)
    TextView diamon_num_tv;
    private Context mContext;
    private UserBean userBean;
    private String authorization;
    private MineModel mineModel;
    private double amount = 0;
    private DecimalFormat mformat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profit);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.mention_tv, R.id.mention_record_rl, R.id.make_money_secret_rl, R.id.incom_expend_detail_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.mention_record_rl:
                IntentUtil.jumpIntent(mContext, MentionRecordActivity.class);
                break;
            case R.id.make_money_secret_rl:
                IntentUtil.jumpIntent(mContext, EarnBookActivity.class);
                break;
            case R.id.incom_expend_detail_rl:
                IntentUtil.jumpIntent(mContext, PayDetailActivity.class);
                break;
            case R.id.mention_tv:
                HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
                    @Override
                    public void onNext(UserDetailInfo userDetailInfo) {
                        if (userDetailInfo.getStatus_code() == 200) {
                            UserDetailInfo.DataBean data = userDetailInfo.getData();
                            if (data.getIs_true() == 1) {
                                //首先判断用户是否 已经进行了实名认证
                                //判断当前可提现的额度 如果小于 指定值 0 不可提现
                                if (amount >= 100) {
                                    IntentUtil.jumpIntent(mContext, MentionActivity.class);
                                } else {
                                    onToast("可提现额度为" +
                                            CommonConstant.REN_MIN_COIN_SING + mformat.format(amount)+ "不满足提现要求");
                                }
                            } else {
                                onToast("请您首先进行身份认证！");
                            }
                        } else {
                            onToast(userDetailInfo.getMessage());
                        }
                    }
                }, mContext), "", authorization);
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
        mineModel = MineModel.getMineModel();
        //获取当前用户账户余额
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //初始化用户余额
                    amount = userDetailInfo.getData().getMember_account()/10.00;
                    enable_mention_money_tv.setText(CommonConstant.REN_MIN_COIN_SING + mformat.format(amount));
                    diamon_num_tv.setText(String.valueOf(userDetailInfo.getData().getMember_account()));
                }
            }
        }, this), "", authorization);
//        mineModel.crashAllow(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
//            @Override
//            public void onNext(StandardResponse standardResponse) {
//                if (standardResponse.getStatus_code() == 200) {
//                    amount = standardResponse.getData().getAmount();
//                    enable_mention_money_tv.setText(CommonConstant.REN_MIN_COIN_SING + mformat.format(amount));
//                    diamon_num_tv.setText(String.valueOf((int) (amount * 10)));
//                }
//            }
//        }, mContext), authorization);
    }

    /**
     * 初始化view
     */
    private void initView() {
//        ImageUtil.scaleImage(this, txje_bg_iv, R.mipmap.txje_bg_iv);
        txje_bg_iv.setBackgroundResource(R.mipmap.txje_bg_iv);
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
