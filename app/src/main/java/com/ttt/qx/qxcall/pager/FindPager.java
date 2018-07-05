package com.ttt.qx.qxcall.pager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.TabsAdapter2;
import com.ttt.qx.qxcall.function.find.view.AllFriendsNewsFragment;
import com.ttt.qx.qxcall.function.find.view.GiftListFragment;
import com.ttt.qx.qxcall.function.find.view.PublishDynamicsActivity;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.widget.viewhelper.TabLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ttt.qx.qxcall.QXCallApplication.login;

/**
 * 交易 pager
 * Created by wyd on 2017/8/15.
 */
public class FindPager extends BasePager {
    private MainActivity mMainActivity;
    private View mView;
    private Dialog mDialog;
    @BindView(R.id.find_tab)
    TabLayout find_tab;
    @BindView(R.id.find_view_pager)
    ViewPager find_view_pager;
    @BindView(R.id.publiv_camera_iv)
    ImageView publiv_camera_iv;
    private List<String> tabIndicators;
    private List<Fragment> categoryFragments;

    //保存当前
    public FindPager(Context ctx) {
        super(ctx);
        mMainActivity = (MainActivity) ctx;
    }

    @Override
    public View initView() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        //注意initView只调用执行一次
        mView = View.inflate(ctx, R.layout.find_pager, null);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.publiv_camera_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.publiv_camera_iv:
                if (login) {
                    IntentUtil.jumpIntent(ctx, PublishDynamicsActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
        }
    }

    /**
     * 页签初始化
     */
    private void init() {
        initTab();
        initContent();
    }

    /***
     * 初始化tab
     */
    private void initTab() {
        find_tab.setTabMode(TabLayout.MODE_FIXED);
//        find_tab.setForegroundGravity(TabLayout.);
        find_tab.setTabTextColors(ContextCompat.getColor(ctx, R.color._787878), ContextCompat.getColor(ctx, R.color._f669ca));
        find_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(ctx, R.color._f669ca));
        //设置indicator的高度
        find_tab.setSelectedTabIndicatorHeight(ctx.getResources().getInteger(R.integer.i10));
        ViewCompat.setElevation(find_tab, 10);
        //关联viewpager
        find_tab.setupWithViewPager(find_view_pager);
        find_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals("礼物榜")) {
                    publiv_camera_iv.setVisibility(View.GONE);
                } else {
                    publiv_camera_iv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始tab对应的fragment
     */
    private void initContent() {
        tabIndicators = new ArrayList<>();
        String[] tab_arrays = ctx.getResources().getStringArray(R.array.find_tab_item_arrays);
        for (int i = 0; i < tab_arrays.length; i++) {
            tabIndicators.add(tab_arrays[i]);
        }
        categoryFragments = new ArrayList<>();
        for (String tab : tabIndicators) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putString("category", tab);//
            switch (tab) {
                case "全部":
                case "好友动态":
                    fragment = new AllFriendsNewsFragment();
                    break;
                case "礼物榜":
                    fragment = new GiftListFragment();
                    break;
            }
            fragment.setArguments(bundle);
            categoryFragments.add(fragment);
        }
        MainActivity mainActivity = (MainActivity) ctx;
        TabsAdapter2 adapter = new TabsAdapter2(mainActivity.getSupportFragmentManager(), tabIndicators, categoryFragments);
        find_view_pager.setAdapter(adapter);
        //设置修整indicator的宽度
        TabLayoutHelper.reflex(find_tab);
    }


    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(ctx, message, Toast.LENGTH_SHORT);
    }

}
