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
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.ttt.qx.qxcall.R;
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
import com.ttt.qx.qxcall.function.login.model.entity.VirtualCoinRatio;
import com.ttt.qx.qxcall.function.login.view.RechargeActivity;
import com.ttt.qx.qxcall.function.wxpay.WXPay;
import com.ttt.qx.qxcall.function.wxpay.WXPayData;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class NChongZhiActivity extends NBaseActivity {

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
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.tv_chongzhi_item_douzi_1)
    TextView tvChongzhiItemDouzi1;
    @BindView(R.id.tv_chongzhi_item_money_1)
    TextView tvChongzhiItemMoney1;
    @BindView(R.id.ll_chongzhi_item_1)
    LinearLayout llChongzhiItem1;
    @BindView(R.id.tv_chongzhi_item_douzi_2)
    TextView tvChongzhiItemDouzi2;
    @BindView(R.id.tv_chongzhi_item_money_2)
    TextView tvChongzhiItemMoney2;
    @BindView(R.id.ll_chongzhi_item_2)
    LinearLayout llChongzhiItem2;
    @BindView(R.id.tv_chongzhi_item_douzi_3)
    TextView tvChongzhiItemDouzi3;
    @BindView(R.id.tv_chongzhi_item_money_3)
    TextView tvChongzhiItemMoney3;
    @BindView(R.id.ll_chongzhi_item_3)
    LinearLayout llChongzhiItem3;
    @BindView(R.id.tv_chongzhi_item_douzi_4)
    TextView tvChongzhiItemDouzi4;
    @BindView(R.id.tv_chongzhi_item_money_4)
    TextView tvChongzhiItemMoney4;
    @BindView(R.id.ll_chongzhi_item_4)
    LinearLayout llChongzhiItem4;
    @BindView(R.id.tv_chongzhi_item_douzi_5)
    TextView tvChongzhiItemDouzi5;
    @BindView(R.id.tv_chongzhi_item_money_5)
    TextView tvChongzhiItemMoney5;
    @BindView(R.id.ll_chongzhi_item_5)
    LinearLayout llChongzhiItem5;
    @BindView(R.id.tv_chongzhi_item_douzi_6)
    TextView tvChongzhiItemDouzi6;
    @BindView(R.id.ll_chongzhi_item_6)
    LinearLayout llChongzhiItem6;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.ll_zhifubao)
    LinearLayout llZhifubao;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.tv_chongzhi_item_money_6)
    TextView tvChongzhiItemMoney6;

    private ArrayList<LinearLayout> llChongZhiItems = new ArrayList<>();
    private ArrayList<TextView> tvChongZhiItemDous = new ArrayList<>();
    private ArrayList<TextView> tvChongZhiItemMoneys = new ArrayList<>();

    private int currItem=0;
    private int currPay=0;
    private String amount = "30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nchong_zhi);
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
        tvPublicTitlebarCenter.setText("充值");
    }

    private void initView() {
        llChongZhiItems.add(llChongzhiItem1);
        llChongZhiItems.add(llChongzhiItem2);
        llChongZhiItems.add(llChongzhiItem3);
        llChongZhiItems.add(llChongzhiItem4);
        llChongZhiItems.add(llChongzhiItem5);
        llChongZhiItems.add(llChongzhiItem6);
        tvChongZhiItemDous.add(tvChongzhiItemDouzi1);
        tvChongZhiItemDous.add(tvChongzhiItemDouzi2);
        tvChongZhiItemDous.add(tvChongzhiItemDouzi3);
        tvChongZhiItemDous.add(tvChongzhiItemDouzi4);
        tvChongZhiItemDous.add(tvChongzhiItemDouzi5);
        tvChongZhiItemDous.add(tvChongzhiItemDouzi6);
        tvChongZhiItemMoneys.add(tvChongzhiItemMoney1);
        tvChongZhiItemMoneys.add(tvChongzhiItemMoney2);
        tvChongZhiItemMoneys.add(tvChongzhiItemMoney3);
        tvChongZhiItemMoneys.add(tvChongzhiItemMoney4);
        tvChongZhiItemMoneys.add(tvChongzhiItemMoney5);
        tvChongZhiItemMoneys.add(tvChongzhiItemMoney6);
    }

    @OnClick({R.id.ll_chongzhi_item_1, R.id.ll_chongzhi_item_2, R.id.ll_chongzhi_item_3, R.id.ll_chongzhi_item_4, R.id.ll_chongzhi_item_5, R.id.ll_chongzhi_item_6, R.id.ll_zhifubao, R.id.ll_weixin})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_chongzhi_item_1:
            case R.id.ll_chongzhi_item_2:
            case R.id.ll_chongzhi_item_3:
            case R.id.ll_chongzhi_item_4:
            case R.id.ll_chongzhi_item_5:
            case R.id.ll_chongzhi_item_6:
                int tag=Integer.valueOf(view.getTag().toString());
                switch (tag){
                    case 0:amount="30";break;
                    case 1:amount="50";break;
                    case 2:amount="100";break;
                    case 3:amount="200";break;
                    case 4:amount="500";break;
                    case 5:amount="1000";break;
                }
                if(currItem==tag){
                    return;
                }
                llChongZhiItems.get(currItem).setBackgroundResource(R.drawable.activity_chongzhi_bg_unselect);
                llChongZhiItems.get(tag).setBackgroundResource(R.drawable.activity_chongzhi_bg_select);
                tvChongZhiItemDous.get(currItem).setTextColor(getResources().getColor(R.color.white));
                tvChongZhiItemDous.get(tag).setTextColor(getResources().getColor(R.color.colorAccent));
                tvChongZhiItemMoneys.get(currItem).setTextColor(getResources().getColor(R.color.loginUsernameColor));
                tvChongZhiItemMoneys.get(tag).setTextColor(getResources().getColor(R.color.colorAccent));
                currItem=tag;
                LogUtils.e(amount);
                break;
            case R.id.ll_zhifubao:
                currPay=0;
                ivZhifubao.setVisibility(View.VISIBLE);
                ivWeixin.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_weixin:
                currPay=1;
                ivWeixin.setVisibility(View.VISIBLE);
                ivZhifubao.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private String authorization;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    //充值比例
    private int ratio = 10;

    private void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
                    tvYue.setText(CommonConstant.REN_MIN_COIN_SING
                            + decimalFormat.format(userDetailInfo.getData().getMember_account() / 10.00));
                }
            }
        }, this), "", authorization);
    }

    @Subscribe
    public void onEventPaySuccess(PaySuccess paySuccess) {
        paySuccessHandle();
    }

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
                    tvYue.setText(CommonConstant.REN_MIN_COIN_SING
                            + decimalFormat.format(userDetailInfo.getData().getMember_account() / 10.00));
                }
            }
        }, "", authorization);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onChongZhi(View view){
        if (currPay==0) {//获取支付宝支付订单信息
            MineModel.getMineModel().getPayOrderInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<GetPayInfoResponse>() {
                @Override
                public void onNext(GetPayInfoResponse getPayInfoResponse) {
                    if (getPayInfoResponse.getStatus_code() == 200) {
                        String orderInfo = getPayInfoResponse.getData().getPaystr();
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(NChongZhiActivity.this);
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
            }, this), "alipay", amount, authorization);
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
            }, this), "wxpay", amount, authorization);
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this,message,Toast.LENGTH_SHORT);
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
                        Toast.makeText(NChongZhiActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case WEI_XIN_PAY://微信支付

                    break;
            }
        }
    };

}
