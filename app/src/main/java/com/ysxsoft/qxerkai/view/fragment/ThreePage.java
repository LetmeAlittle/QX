package com.ysxsoft.qxerkai.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ysxsoft.qxerkai.view.activity.BanYanActivity;
import com.ysxsoft.qxerkai.view.activity.NTopBangActivity;
import com.ysxsoft.qxerkai.view.activity.NZhuanShuPiPeiActivity;
import com.ysxsoft.qxerkai.view.activity.XuanZheShouHuActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ResizableImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class ThreePage extends BasePager {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.circle_waiwei)
    ImageView circleWaiwei;
    @BindView(R.id.iv_zhuanshupipei)
    ImageView ivZhuanshupipei;

    private View rootView;

    public ThreePage(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_three, null);
        ButterKnife.bind(this, rootView);
        initStatusBar(statusBar);
        initTitleBar();
        ivZhuanshupipei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NZhuanShuPiPeiActivity.class));
            }
        });
        return rootView;
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("私人订制");
        tvPublicTitlebarRight.setText("TOP榜");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NTopBangActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);//动画的重复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        animation.setInterpolator(new LinearInterpolator());
        circleWaiwei.startAnimation(animation);//开始动画
    }

    @OnClick({R.id.tv_kaitong1,R.id.tv_kaitong2,R.id.tv_kaitong3})
    public void onKaitong(View view){
        ctx.startActivity(new Intent(ctx, XuanZheShouHuActivity.class));
    }

    @OnClick({R.id.riv_banyan1,R.id.riv_banyan2,R.id.riv_banyan3,R.id.riv_banyan4,R.id.riv_banyan5,R.id.riv_banyan6})
    public void onBanYan(View view){
        ctx.startActivity(new Intent(ctx, BanYanActivity.class));
    }

}
