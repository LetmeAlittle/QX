package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.VIPListAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.DealTimeUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;


/**
 * 我的VIP
 * Created by wyd on 2017/7/19.
 */
public class MyVIPActivity extends BaseActivity {
    @BindView(R.id.vip_ll)
    LinearLayout vip_ll;
    @BindView(R.id.user_default_icon)
    CircleImageView user_default_icon;
    @BindView(R.id.detail_time)
    TextView detail_time;
    @BindView(R.id.vip_recycler_view)
    RecyclerView vip_recycler_view;
    @BindView(R.id.vip_bg_iv)
    ImageView vip_bg_iv;
    private Context mContext;
    private String mMark;
    private VIPListAdapter vipListAdapter;
    private String mAuthorization;
    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.now_buy_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.now_buy_ll:
                IntentUtil.jumpIntent(this, VIPBuyActivity.class);
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        vipListAdapter = new VIPListAdapter(this);
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            mAuthorization = "Bearer " + userBean.getToken();
            setVipVisible();
        }
    }

    /**
     * 设置会员等级显隐
     */
    private void setVipVisible() {
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo info) throws IOException {
                if (info.getStatus_code() == 200) {
                    UserDetailInfo.DataBean data = info.getData();
                    if (data.getLevel() == 1) {
//                        ImageUtil.scaleImage((MyVIPActivity) mContext, vip_bg_iv, R.mipmap.vip_bg_iv);
                        vip_bg_iv.setBackgroundResource(R.mipmap.vip_bg_iv);
                        vip_ll.setVisibility(View.VISIBLE);
                        Glide.clear(user_default_icon);
                        Glide.with(mContext).load(data.getMember_avatar())
                                .skipMemoryCache(true)//跳过内部缓存
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(user_default_icon);
                        //获取会员剩余时间
                        MineModel.getMineModel().vipEndTime(new Subscriber<StandardResponse>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(StandardResponse response) {
                                if (response.getStatus_code() == 200) {
                                    Long endTime = response.getData().getVip_end_time();
                                    Log.i("endTime==", String.valueOf(endTime));
                                    detail_time.setText("VIP剩余" + DealTimeUtil.handleStr(endTime * 1000 - System.currentTimeMillis()) + "到期");
                                }
                            }
                        }, mAuthorization);
                    } else {
//                        ImageUtil.scaleImage((MyVIPActivity) mContext, vip_bg_iv, R.mipmap.no_vip_iv);
                        vip_bg_iv.setBackgroundResource(R.mipmap.no_vip_iv);
                    }
                }
            }
        }, mContext), "", mAuthorization);
    }

    /**
     * 初始化view
     */
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vip_recycler_view.setLayoutManager(linearLayoutManager);
        vip_recycler_view.setAdapter(vipListAdapter);
    }


    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!first) {
            setVipVisible();
        }
        first = false;
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
