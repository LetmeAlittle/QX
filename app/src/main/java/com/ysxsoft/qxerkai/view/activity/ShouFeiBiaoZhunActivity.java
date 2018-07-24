package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.view.SetCallPriceActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouFeiBiaoZhunActivity extends NBaseActivity {

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
    @BindView(R.id.tv_biaozhun)
    TextView tvBiaozhun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_fei_biao_zhun);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
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
        tvPublicTitlebarCenter.setText("收费标准");
    }

    private void initView() {
        tvBiaozhun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.jumpIntent(ShouFeiBiaoZhunActivity.this, SetCallPriceActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //首先获取 应用配置的通话价格数组
        String[] prices = getResources().getStringArray(R.array.audio_call_price);
        //查询当前用户所设置的价格
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        String Authorization = "Bearer " + userBean.getToken();
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    UserDetailInfo.DataBean userDetailInfoData = userDetailInfo.getData();
                    String currentSelPrice = String.valueOf(userDetailInfoData.getMember_price());
                    tvBiaozhun.setText(currentSelPrice+ "砰砰豆/分钟");
                }
            }
        }, this), "",Authorization);
    }

}
