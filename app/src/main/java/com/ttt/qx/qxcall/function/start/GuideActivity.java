package com.ttt.qx.qxcall.function.start;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.AppPagerAdapter;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.widget.viewhelper.ImmerseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 应用引导页
 * Created by wyd on 2017/7/19.
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //引导ViewPager
    @BindView(R.id.guide_view_pager)
    ViewPager guide_view_pager;
    //开启按钮
    @BindView(R.id.btn)
    Button btn;
    //本地初始化数据资源
    private int[] images = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initview();
        initData();
    }

    /**
     * 初始化view
     */
    private void initview() {
        //沉浸设置
        ImmerseHelper.immerse2(this);
        guide_view_pager.setOnPageChangeListener(this);
    }

    /**
     * 控件点击事件监听方法
     *
     * @param v
     */
    @OnClick({R.id.btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                IntentUtil.jumpIntent(GuideActivity.this, MainActivity.class);
                finish();
                //更新数据isFirst=false;
                sp.edit().putBoolean(CommonConstant.FIRST_ENETR, false).commit();
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        guide_view_pager.setAdapter(new AppPagerAdapter(this, images));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        //当前选中的页面
        if (i == (images.length - 1)) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //当页面发生改变时
    }

}
