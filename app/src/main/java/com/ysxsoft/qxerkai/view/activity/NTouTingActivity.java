package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.view.adapter.MainPageAdapter;
import com.ysxsoft.qxerkai.view.fragment.SGLOneFragment;
import com.ysxsoft.qxerkai.view.fragment.SGLTwoFragment;
import com.ysxsoft.qxerkai.view.fragment.TouTingOneFragment;
import com.ysxsoft.qxerkai.view.fragment.TouTingTwoFragment;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NTouTingActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.view_topline1)
    View viewTopline1;
    @BindView(R.id.view_topline2)
    View viewTopline2;
    @BindView(R.id.vp_activity_touxing)
    ViewPager vpActivityTouTing;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<View> lineViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntou_ting);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
        setListener();
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
        tvPublicTitlebarCenter.setText("");
    }

    private void initView() {
        lineViews.add(viewTopline1);
        lineViews.add(viewTopline2);
        fragments.add(new TouTingOneFragment());
        fragments.add(new TouTingTwoFragment());
        vpActivityTouTing.setOffscreenPageLimit(fragments.size());
        vpActivityTouTing.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragments));
        vpActivityTouTing.setOnPageChangeListener(new PageChangeListener());
        initIndicator();
    }

    private void initData() {

    }


    private void setListener() {

    }

    public void onCheck(View view){
        int tag=Integer.valueOf(view.getTag().toString());
        vpActivityTouTing.setCurrentItem(tag, false);
    }

    /**
     * 初始化navigation按钮
     */
    private void initIndicator() {
        for (int i = 0; i < lineViews.size(); i++) {
            if (i == 0) { // 初始化第一个为选中状态
                lineViews.get(i).setVisibility(View.VISIBLE);
            } else {
                lineViews.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * viewpager页面切换监听
     */
    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < lineViews.size(); i++) {
                lineViews.get(i).setVisibility(View.INVISIBLE);
            }
            lineViews.get(arg0).setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}
