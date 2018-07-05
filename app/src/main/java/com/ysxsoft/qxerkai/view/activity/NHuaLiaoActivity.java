package com.ysxsoft.qxerkai.view.activity;

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
 * 语音聊天页面
 */
public class NHuaLiaoActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.iv_public_titlebar_right_2)
    ImageView ivPublicTitlebarRight2;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhua_liao);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.activity_hualiao_exit);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("小仙女");
        ivPublicTitlebarRight2.setVisibility(View.VISIBLE);
        ivPublicTitlebarRight2.setImageResource(R.mipmap.activity_hualiao_right);
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        tvGonggao.setSelected(true);
    }

    private void initData() {

    }
}
