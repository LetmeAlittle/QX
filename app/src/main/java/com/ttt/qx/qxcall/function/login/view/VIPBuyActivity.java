package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.VIPBuyListAdapter;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.VIPBuyList;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * VIP购买
 * Created by wyd on 2017/7/19.
 */
public class VIPBuyActivity extends BaseActivity {
    @BindView(R.id.alipay_ll)
    LinearLayout alipay_ll;
    @BindView(R.id.alipay_tv)
    TextView alipay_tv;
    @BindView(R.id.alipay_v)
    View alipay_v;
    @BindView(R.id.wx_pay_ll)
    LinearLayout wx_pay_ll;
    @BindView(R.id.wx_pay_tv)
    TextView wx_pay_tv;
    @BindView(R.id.wx_pay_v)
    View wx_pay_v;
    @BindView(R.id.vip_buy_recycler_view)
    RecyclerView vip_buy_recycler_view;
    private Context mContext;
    private String mMark;
    private VIPBuyListAdapter vipBuyListAdapter;
    //支付方式 默认是支付宝支付
    public static PayType payType = PayType.ALIPAY;
    private int ffb141;
    private int bbb7d3;
    private int _0000;

    public enum PayType {
        ALIPAY,
        WEIXIN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_buy);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.alipay_ll, R.id.wx_pay_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.alipay_ll:
                payType = PayType.ALIPAY;
                //改变顶部显示样式
                alipay_tv.setTextColor(ffb141);
                alipay_v.setBackgroundColor(ffb141);
                wx_pay_tv.setTextColor(bbb7d3);
                wx_pay_v.setBackgroundColor(_0000);
                break;
            case R.id.wx_pay_ll:
                payType = PayType.WEIXIN;
                wx_pay_tv.setTextColor(ffb141);
                wx_pay_v.setBackgroundColor(ffb141);
                alipay_tv.setTextColor(bbb7d3);
                alipay_v.setBackgroundColor(_0000);
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        ffb141 = getResources().getColor(R.color._ffb141);
        bbb7d3 = getResources().getColor(R.color._bbb7d3);
        _0000 = getResources().getColor(R.color._0000);
        LoginModel.getLoginModel().buyVipList(new ProgressSubscribe<>(new SubScribeOnNextListener<VIPBuyList>() {
            @Override
            public void onNext(VIPBuyList vipBuyList) {
                List<VIPBuyList.DataBean> data = vipBuyList.getData();
                if (data==null) {
                    data=new ArrayList<VIPBuyList.DataBean>();
                }
                vipBuyListAdapter = new VIPBuyListAdapter(VIPBuyActivity.this,data);
                initView();
            }
        }, this));
    }


    /**
     * 初始化view
     */
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vip_buy_recycler_view.setLayoutManager(linearLayoutManager);
        vip_buy_recycler_view.setAdapter(vipBuyListAdapter);
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
