package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 获取用户资料
 */
public class NZhiLiaoActivity extends NBaseActivity implements View.OnClickListener {

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
    @BindView(R.id.ll_liwubang)
    LinearLayout llLiwubang;
    @BindView(R.id.ll_liaorenqu)
    LinearLayout llLiaorenqu;
    @BindView(R.id.ll_shouhu)
    LinearLayout llShouhu;
    @BindView(R.id.tv_xiangche_more)
    TextView tvXiangcheMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nzhi_liao);
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
        tvPublicTitlebarCenter.setText(getIntent().getStringExtra("title"));
    }

    private void initView() {
        llLiwubang.setOnClickListener(this);
        llLiaorenqu.setOnClickListener(this);
        llShouhu.setOnClickListener(this);
        tvXiangcheMore.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_liwubang:
                startActivity(new Intent(this, LiWuBangActivity.class));
                break;
            case R.id.ll_liaorenqu:
                startActivity(new Intent(this, NLiaoRenQuActivity.class));
                break;
            case R.id.ll_shouhu:
                startActivity(new Intent(this, NShouHuBangActivity.class));
                break;
            case R.id.tv_xiangche_more:
                startActivity(new Intent(this, NXiangCheActivity.class));
                break;
        }
    }
}
