package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.ExitLogin;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NSettingActivity extends NBaseActivity implements View.OnClickListener{

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
    @BindView(R.id.ll_zhanghuanquan)
    LinearLayout llZhanghuanquan;
    @BindView(R.id.ll_yijianfankui)
    LinearLayout llYijianfankui;
    @BindView(R.id.ll_jianchagengxin)
    LinearLayout llJianchagengxin;
    @BindView(R.id.ll_qinglihuancun)
    LinearLayout llQinglihuancun;
    @BindView(R.id.ll_shiyongshuoming)
    LinearLayout llShiyongshuoming;
    @BindView(R.id.ll_guanyuwomen)
    LinearLayout llGuanyuwomen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsetting);
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
        tvPublicTitlebarCenter.setText("设置");
    }

    private void initView() {
        llZhanghuanquan.setOnClickListener(this);
        llYijianfankui.setOnClickListener(this);
        llJianchagengxin.setOnClickListener(this);
        llQinglihuancun.setOnClickListener(this);
        llShiyongshuoming.setOnClickListener(this);
        llGuanyuwomen.setOnClickListener(this);
    }

    public void onExit(View view) {
        EventBus.getDefault().post(new ExitLogin());
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //账户安全
            case R.id.ll_zhanghuanquan:
                break;
            //意见反馈
            case R.id.ll_yijianfankui:
                startActivity(new Intent(this,NFeedBackActivity.class));
                break;
            //检查更新
            case R.id.ll_jianchagengxin:
                break;
            //清理缓存
            case R.id.ll_qinglihuancun:
                break;
            //使用说明
            case R.id.ll_shiyongshuoming:
                break;
            //关于我们
            case R.id.ll_guanyuwomen:
                break;
        }
    }
}
