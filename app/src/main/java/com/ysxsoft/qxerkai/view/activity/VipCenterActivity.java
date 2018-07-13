package com.ysxsoft.qxerkai.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.UserXiaoFeiNum;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class VipCenterActivity extends NBaseActivity {

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
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.civ_img11)
    CircleImageView civImg11;
    @BindView(R.id.civ_img1)
    CircleImageView civImg1;
    @BindView(R.id.iv_img1)
    View ivImg1;
    @BindView(R.id.civ_22)
    CircleImageView civ22;
    @BindView(R.id.civ_img2)
    CircleImageView civImg2;
    @BindView(R.id.iv_img2)
    View ivImg2;
    @BindView(R.id.civ_img33)
    CircleImageView civImg33;
    @BindView(R.id.civ_img3)
    CircleImageView civImg3;
    @BindView(R.id.iv_img3)
    View ivImg3;
    @BindView(R.id.civ_img44)
    CircleImageView civImg44;
    @BindView(R.id.civ_img4)
    CircleImageView civImg4;
    @BindView(R.id.iv_img4)
    View ivImg4;
    @BindView(R.id.civ_img55)
    CircleImageView civImg55;
    @BindView(R.id.civ_img5)
    CircleImageView civImg5;
    @BindView(R.id.top_beijing)
    View topBeijing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_center);
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
        tvPublicTitlebarCenter.setText("会员中心");
    }

    private void initView() {
        Glide.with(this).load(getIntent().getStringExtra("url")).into(civHead);
    }

    private void initData() {
        multipleStatusView.showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", getIntent().getStringExtra("uid"));
        RetrofitTools.getUserXiaoFei(hashMap).subscribe(new ResponseSubscriber<UserXiaoFeiNum>() {
            @Override
            public void onSuccess(UserXiaoFeiNum baseResponse, int code, String msg) {
                multipleStatusView.hideLoading();
                tvName.setText("消费的砰砰豆：" + baseResponse.getData());
                setShow(baseResponse.getData());
                topBeijing.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(Throwable e) {
                multipleStatusView.hideLoading();
                ToastUtils.showToast(VipCenterActivity.this, e.getMessage(), 0);
            }
        });
    }

    @SuppressLint("ResourceType")
    private void setShow(int ppdNum) {
        if (ppdNum < 10000) {
            ivImg4.setVisibility(View.VISIBLE);
            civImg5.setImageResource(R.color._666666);
            civImg55.setImageResource(R.color._88666666);
        }
        if (ppdNum < 5000) {
            ivImg3.setVisibility(View.VISIBLE);
            civImg4.setImageResource(R.color._666666);
            civImg44.setImageResource(R.color._88666666);
        }
        if (ppdNum < 1000) {
            ivImg2.setVisibility(View.VISIBLE);
            civImg3.setImageResource(R.color._666666);
            civImg33.setImageResource(R.color._88666666);
        }
        if (ppdNum < 500) {
            ivImg1.setVisibility(View.VISIBLE);
            civImg2.setImageResource(R.color._666666);
            civ22.setImageResource(R.color._88666666);
        }
        if (ppdNum < 100) {
            civImg1.setImageResource(R.color._666666);
            civImg11.setImageResource(R.color._88666666);
        }
    }

}
