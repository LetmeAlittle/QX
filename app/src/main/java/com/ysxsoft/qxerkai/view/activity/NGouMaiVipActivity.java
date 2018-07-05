package com.ysxsoft.qxerkai.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.eventbus.PaySuccess;
import com.ttt.qx.qxcall.function.alipay.PayResult;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.GetPayInfoResponse;
import com.ttt.qx.qxcall.function.login.view.VIPBuyActivity;
import com.ttt.qx.qxcall.function.wxpay.WXPay;
import com.ttt.qx.qxcall.function.wxpay.WXPayData;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ttt.qx.qxcall.QXCallApplication.onToast;

public class NGouMaiVipActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.ll_vip_item1)
    LinearLayout llVipItem1;
    @BindView(R.id.ll_vip_item2)
    LinearLayout llVipItem2;
    @BindView(R.id.ll_vip_item3)
    LinearLayout llVipItem3;
    @BindView(R.id.ll_vip_item4)
    LinearLayout llVipItem4;
    @BindView(R.id.ll_vip_item5)
    LinearLayout llVipItem5;
    @BindView(R.id.ll_vip_item6)
    LinearLayout llVipItem6;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;

    private ArrayList<LinearLayout> llVipItems = new ArrayList<>();
    private int currItem = 0;
    private int currPay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngou_mai_vip);
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
        tvPublicTitlebarCenter.setText("购买VIP");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("VIP特权");
    }

    private void initView() {
        authorization = "Bearer " + new UserDao().queryFirstData().getToken();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        llVipItems.add(llVipItem1);
        llVipItems.add(llVipItem2);
        llVipItems.add(llVipItem3);
        llVipItems.add(llVipItem4);
        llVipItems.add(llVipItem5);
        llVipItems.add(llVipItem6);
    }

    private void initData() {

    }

    @OnClick({R.id.ll_vip_item1, R.id.ll_vip_item2, R.id.ll_vip_item3, R.id.ll_vip_item4, R.id.ll_vip_item5, R.id.ll_vip_item6})
    public void click(View view) {
        int tag = Integer.valueOf(view.getTag().toString());
        if (tag == currItem) {
            return;
        }
        llVipItems.get(currItem).setBackgroundResource(R.drawable.activity_goumai_vip_bg_unselect);
        llVipItems.get(tag).setBackgroundResource(R.drawable.activity_goumai_vip_bg_select);
        currItem = tag;
    }

    @OnClick({R.id.ll_zhifubao, R.id.ll_weixin})
    public void zfclick(View view) {
        switch (view.getId()) {
            case R.id.ll_zhifubao:
                currPay = 0;
                ivZhifubao.setVisibility(View.VISIBLE);
                ivWeixin.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_weixin:
                currPay = 1;
                ivWeixin.setVisibility(View.VISIBLE);
                ivZhifubao.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private String authorization;
    public void onPay(View view) {
        if (currPay == 0) {
            MineModel.getMineModel().getVipPayOrderInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<GetPayInfoResponse>() {
                @Override
                public void onNext(GetPayInfoResponse getPayInfoResponse) {
                    if (getPayInfoResponse.getStatus_code() == 200) {
                        String orderInfo = getPayInfoResponse.getData().getPaystr();
                        if (VIPBuyActivity.payType == VIPBuyActivity.PayType.ALIPAY) {
                            alipay(orderInfo);
                        }
                    } else {
                        onToast("获取订单信息失败！");
                    }
                }
            }, NGouMaiVipActivity.this), "alipay", String.valueOf(""), authorization);
        } else {
            MineModel.getMineModel().getVipWXPayOrderInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<WXPayData>() {
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
            }, NGouMaiVipActivity.this), "wxpay", String.valueOf(""), authorization);
        }
    }

    /**
     * 支付宝 支付成功！
     *
     * @param orderInfo
     */
    private void alipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(NGouMaiVipActivity.this);
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
    }

    private static final int SDK_PAY_FLAG = 1;
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
                        Toast.makeText(NGouMaiVipActivity.this, "购买vip成功,可以到会员中心查看！", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(NGouMaiVipActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    @Subscribe
    public void onEventPaySuccess(PaySuccess paySuccess) {
        Toast.makeText(this, "购买vip成功,可以到会员中心查看！", Toast.LENGTH_SHORT).show();
    }


}
