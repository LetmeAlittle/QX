package com.ttt.qx.qxcall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.ttt.qx.qxcall.function.login.model.entity.VIPBuyList;
import com.ttt.qx.qxcall.function.login.view.VIPBuyActivity;
import com.ttt.qx.qxcall.function.wxpay.WXPay;
import com.ttt.qx.qxcall.function.wxpay.WXPayData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ttt.qx.qxcall.QXCallApplication.onToast;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class VIPBuyListAdapter extends RecyclerView.Adapter<VIPBuyListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<VIPBuyList.DataBean> data = new ArrayList<>();
    private final String authorization;

    public VIPBuyListAdapter(Context context, List<VIPBuyList.DataBean> data) {
        this.context = context;
        this.data.addAll(data);
        inflater = LayoutInflater.from(context);
        authorization = "Bearer " + new UserDao().queryFirstData().getToken();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_vip_buy_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VIPBuyList.DataBean dataBean = data.get(position);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pay_type = "alipay";
                if (VIPBuyActivity.payType == VIPBuyActivity.PayType.ALIPAY) {
                    pay_type = "alipay";
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
                    }, context), pay_type, String.valueOf(dataBean.getId()), authorization);
                } else {
                    pay_type = "wxpay";
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
                    }, context), pay_type, String.valueOf(dataBean.getId()), authorization);

                }
            }
        });
        holder.vip_title.setText(dataBean.getText());
        holder.vip_money_tv.setText(dataBean.getPrice_text());

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
                PayTask alipay = new PayTask((VIPBuyActivity) context);
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
                        Toast.makeText(context, "购买vip成功,可以到会员中心查看！", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Subscribe
    public void onEventPaySuccess(PaySuccess paySuccess) {
        Toast.makeText(context, "购买vip成功,可以到会员中心查看！", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.vip_title)
        TextView vip_title;
        @BindView(R.id.vip_money_tv)
        TextView vip_money_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
