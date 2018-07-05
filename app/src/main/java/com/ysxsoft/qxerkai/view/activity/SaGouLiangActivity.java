package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.view.adapter.MainPageAdapter;
import com.ysxsoft.qxerkai.view.fragment.SGLOneFragment;
import com.ysxsoft.qxerkai.view.fragment.SGLTwoFragment;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaGouLiangActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.vp_activity_sagouliang)
    ViewPager vpActivitySagouliang;

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<RadioButton> radioButtons = new ArrayList<>();
    @BindView(R.id.rv_radioButton1)
    RadioButton rvRadioButton1;
    @BindView(R.id.rv_radioButton2)
    RadioButton rvRadioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_gou_liang);
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
        tvPublicTitlebarCenter.setText("撒狗粮");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("规则");
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        radioButtons.add(rvRadioButton1);
        radioButtons.add(rvRadioButton2);
        fragments.add(new SGLOneFragment());
        fragments.add(new SGLTwoFragment());
        vpActivitySagouliang.setOffscreenPageLimit(fragments.size());
        vpActivitySagouliang.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
        vpActivitySagouliang.setOnPageChangeListener(new PageChangeListener());
        initIndicator();
    }

    private void initData() {

    }

    /**
     * 初始化navigation按钮
     */
    private void initIndicator() {
        for (int i = 0; i < radioButtons.size(); i++) {
            if (i == 0) { // 初始化第一个为选中状态
                radioButtons.get(i).setChecked(true);
            } else {
                radioButtons.get(i).setChecked(false);
            }
            radioButtons.get(i).setOnCheckedChangeListener(new PageCheckListener());
        }
    }

    /**
     * navigation按钮改变监听
     */
    private class PageCheckListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                int tag = Integer.valueOf(compoundButton.getTag().toString());
                vpActivitySagouliang.setCurrentItem(tag, false);
            }
        }
    }

    /**
     * viewpager页面切换监听
     */
    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < radioButtons.size(); i++) {
                radioButtons.get(i).setChecked(false);
            }
            radioButtons.get(arg0).setChecked(true);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}
