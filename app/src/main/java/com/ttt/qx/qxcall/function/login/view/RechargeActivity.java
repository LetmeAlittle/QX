package com.ttt.qx.qxcall.function.login.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.MoneyTypeAdapter;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.NotifyMoneyTypeModify;
import com.ttt.qx.qxcall.eventbus.PaySuccess;
import com.ttt.qx.qxcall.function.alipay.PayResult;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.GetPayInfoResponse;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.VirtualCoinRatio;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.function.wxpay.WXPay;
import com.ttt.qx.qxcall.function.wxpay.WXPayData;
import com.ttt.qx.qxcall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


/**
 * 充值
 * Created by wyd on 2017/7/19.
 */
public class RechargeActivity extends BaseActivity {
    private Context mContext;
    //充值比例
    private int ratio = 10;
    @BindView(R.id.recharge_tv)
    TextView recharge_tv;
    @BindView(R.id.enable_excharge_num_tv)
    TextView enable_excharge_num_tv;
    @BindView(R.id.balance_tv)
    TextView balance_tv;
    @BindView(R.id.hand_money_et)
    EditText hand_money_et;
    @BindView(R.id.money_type_rv)
    RecyclerView money_type_rv;
    @BindView(R.id.alipay_bg)
    ImageView alipay_bg;
    @BindView(R.id.wxpay_bg)
    ImageView wxpay_bg;
    @BindView(R.id.txje_bg_iv)
    ImageView txje_bg_iv;
    //默认支付金额类别
    String[] moneyTyps = new String[]{"30元", "50元", "100元", "200元", "500元", "1000元"};
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private String type = ALIPAY;
    public static final String ALIPAY = "alipay";
    public static final String WXPAY = "wxpay";
    //充值金额
    private String amount = "100";
    private MoneyTypeAdapter moneyTypeAdapter;
    private String authorization;
    private String direction = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.alipay_rl, R.id.wxpay_rl, R.id.recharge_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.alipay_rl:
                type = ALIPAY;
                alipay_bg.setBackgroundResource(R.mipmap.pay_selected_iv);
                wxpay_bg.setBackgroundResource(R.mipmap.pay_unselect_iv);
                break;
            case R.id.wxpay_rl:
                type = WXPAY;
                wxpay_bg.setBackgroundResource(R.mipmap.pay_selected_iv);
                alipay_bg.setBackgroundResource(R.mipmap.pay_unselect_iv);
                break;
            case R.id.recharge_tv:
                if (type.equals(ALIPAY)) {//获取支付宝支付订单信息
                    MineModel.getMineModel().getPayOrderInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<GetPayInfoResponse>() {
                        @Override
                        public void onNext(GetPayInfoResponse getPayInfoResponse) {
                            if (getPayInfoResponse.getStatus_code() == 200) {
                                String orderInfo = getPayInfoResponse.getData().getPaystr();
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(RechargeActivity.this);
                                        Map<String, String> result = alipay.payV2(orderInfo, true);
                                        Log.i("msp", result.toString());
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {
                                onToast("获取订单信息失败！");
                            }
                        }
                    }, this), type, amount, authorization);
                } else {//微信支付订单信息
                    MineModel.getMineModel().getWXPayOrderInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<WXPayData>() {
                        @Override
                        public void onNext(WXPayData wxPayData) {
                            if (wxPayData.getStatus_code() == 200) {
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        WXPay.wxpay(wxPayData.getData().getPaystr());
                                    }
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {
                                onToast("获取订单信息失败！");
                            }
                        }
                    }, this), type, amount, authorization);
                }
                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int WEI_XIN_PAY = 3;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //支付成功之后重新刷新余额
                        paySuccessHandle();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case WEI_XIN_PAY://微信支付

                    break;
            }
        }
    };

    /**
     * 支付成功后处理
     */
    private void paySuccessHandle() {
        HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //初始化用户余额
                    balance_tv.setText(CommonConstant.REN_MIN_COIN_SING
                            + decimalFormat.format(userDetailInfo.getData().getMember_account() / 10.00));
                }
            }
        }, "", authorization);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        direction = getIntent().getStringExtra("direction");
        mContext = this;
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        //首先获取 充值比例
        MineModel.getMineModel().getVirtualCoinRatio(new Subscriber<VirtualCoinRatio>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(VirtualCoinRatio virtualCoinRatio) {
                if (virtualCoinRatio.getStatus_code() == 200) {
                    ratio = virtualCoinRatio.getData().getRatio();
                }
            }
        });
        //获取当前用户账户余额
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //初始化用户余额
                    balance_tv.setText(CommonConstant.REN_MIN_COIN_SING
                            + decimalFormat.format(userDetailInfo.getData().getMember_account() / 10.00));
                }
            }
        }, this), "", authorization);
        //初始化 金额类别
        moneyTypeAdapter = new MoneyTypeAdapter(this, moneyTyps);
    }

    /**
     * 初始化view
     */
    private void initView() {
//        ImageUtil.scaleImage(this, txje_bg_iv, R.mipmap.txje_bg_iv);
        txje_bg_iv.setBackgroundResource(R.mipmap.txje_bg_iv);
        alipay_bg.setBackgroundResource(R.mipmap.pay_selected_iv);
        wxpay_bg.setBackgroundResource(R.mipmap.pay_unselect_iv);
        setPayVirtualNum("100元");
        hand_money_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String money = hand_money_et.getText().toString().trim();
                //首先判断 输入的金额是否符合 数值要求
                if (true) {
                    setPayVirtualNum(money + "元");
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        money_type_rv.setLayoutManager(gridLayoutManager);
        money_type_rv.setAdapter(moneyTypeAdapter);
    }

    /**
     * 根据当前选择的支付金额计算  所付 以及可以兑换的虚拟币
     */
    public void setPayVirtualNum(String moneyType) {
        String num = moneyType.replace("元", "");
        if (!num.equals("")) {
            amount = num;
            recharge_tv.setText("支付" + moneyType);
            enable_excharge_num_tv.setText(String.valueOf((int) (ratio * Double.parseDouble(num))));
        }
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

    @Subscribe
    public void onEventNotifyMoneyTypeModify(NotifyMoneyTypeModify notifyMoneyTypeModify) {
        setPayVirtualNum(notifyMoneyTypeModify.money);
    }

    @Subscribe
    public void onEventPaySuccess(PaySuccess paySuccess) {
        paySuccessHandle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
