package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.XCDanmuView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NPaoHaTiActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_right_2)
    ImageView ivPublicTitlebarRight2;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.iv_xin)
    ImageView ivXin;
    @BindView(R.id.xcDanmuView)
    XCDanmuView xcDanmuView;

    private String[] mStrItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npao_ha_ti);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initDanMu();
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
        ivPublicTitlebarRight2.setVisibility(View.VISIBLE);
        ivPublicTitlebarRight2.setImageResource(R.mipmap.activity_paohuati_fabu);
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvPublicTitlebarCenter.setText("抛话题");
    }

    private void initView() {
        ScaleAnimation animation = new ScaleAnimation(
                1f, 1.2f, 1f, 1.2f
                , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(800);
        animation.setRepeatCount(-1);//动画的重复次数
        animation.setInterpolator(new AnticipateInterpolator());
        ivXin.startAnimation(animation);//开始动画
    }

    private void initDanMu() {
        mStrItems=new String[20];
        for(int i=0;i<20;i++){
            mStrItems[i]="话题名称话题名称话题"+i;
        }
        xcDanmuView.initDanmuItemViews(mStrItems);
        xcDanmuView.start();
    }

    private void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xcDanmuView.stop();
    }
}
