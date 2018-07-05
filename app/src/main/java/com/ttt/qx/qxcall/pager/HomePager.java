package com.ttt.qx.qxcall.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.TabsAdapter;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.RefreshHomeCurrentCategory;
import com.ttt.qx.qxcall.eventbus.SexFilter;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.home.view.HomeCategoryFragment;
import com.ttt.qx.qxcall.function.home.view.IDSearchActivity;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.widget.viewhelper.TabLayoutHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


/**
 * pager
 * Created by wyd on 2017/7/19.
 */
public class HomePager extends BasePager {
    @BindView(R.id.home_tab)
    TabLayout home_tab;
    @BindView(R.id.content_view_pager)
    ViewPager content_view_pager;
    private List<Fragment> categoryFragments;
    private List<CommonTagList.DataBean> homeTagListData;
    public static String sex = "0";

    public HomePager(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        View view = null;
        view = View.inflate(ctx, R.layout.home_pager, null);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        init();
        return view;
    }

    @Override
    public void initData() {
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
        home_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        home_tab.setTabTextColors(ContextCompat.getColor(ctx, R.color._bbb7d3), ContextCompat.getColor(ctx, R.color._f669ca));
        home_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(ctx, R.color._f669ca));
        //设置indicator的高度
        home_tab.setSelectedTabIndicatorHeight(ctx.getResources().getInteger(R.integer.i10));
        ViewCompat.setElevation(home_tab, 10);
        //关联viewpager
        home_tab.setupWithViewPager(content_view_pager);
        home_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((HomeCategoryFragment) categoryFragments.get(tab.getPosition())).refreshData();
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
        //获取首页标签分类列表
        HomeModel.getHomeModel().getHomeTagList(new Subscriber<CommonTagList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CommonTagList commonTagList) {
                if (commonTagList.getStatus_code() == 200) {
                    homeTagListData = commonTagList.getData();
                    categoryFragments = new ArrayList<>();
                    for (CommonTagList.DataBean dataBean : homeTagListData) {
                        Fragment fragment = new HomeCategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", dataBean.getId());
                        fragment.setArguments(bundle);
                        categoryFragments.add(fragment);
                    }
                    MainActivity mainActivity = (MainActivity) ctx;
                    TabsAdapter adapter = new TabsAdapter(mainActivity.getSupportFragmentManager(), homeTagListData, categoryFragments);
                    content_view_pager.setAdapter(adapter);
                    //设置修整indicator的宽度
                    TabLayoutHelper.reflex(home_tab);
                } else {
                    onToast("标签分类获取失败！");
                }
            }
        });
    }

    @OnClick({R.id.sex_select_rl, R.id.id_select_rl})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.sex_select_rl://根据性别筛选
                TipDialog.showBottomDialog((MainActivity) ctx, "请选择性别", "男", "女", new TipDialog.OnBottomDialogClickListener() {
                    @Override
                    public void onTopClick() {
                        sex = "1";
                        //筛选 男性
//                        HomeCategoryFragment homeCategoryFragment = ((HomeCategoryFragment) categoryFragments
//                                .get(home_tab.getSelectedTabPosition()));
//                        homeCategoryFragment.filterSexData("1");
                        EventBus.getDefault().post(new SexFilter());
                    }

                    @Override
                    public void onBottomClick() {
                        sex = "2";
                        //筛选 女性
//                        ((HomeCategoryFragment) categoryFragments
//                                .get(home_tab.getSelectedTabPosition())).filterSexData("2");
                        EventBus.getDefault().post(new SexFilter());
                    }

                    @Override
                    public void onCancle() {
                        //取消
                        HomeCategoryFragment homeCategoryFragment = ((HomeCategoryFragment) categoryFragments
                                .get(home_tab.getSelectedTabPosition()));
                        if (!sex.equals("0")) {//如果进行了性别筛选
                            sex = "0";
//                            homeCategoryFragment.refreshData();
                            EventBus.getDefault().post(new SexFilter());
                        }
                    }

                    @Override
                    public void onConfirm() {

                    }
                });
                break;
            case R.id.id_select_rl://根据用户id筛选
                IntentUtil.jumpIntent(ctx, IDSearchActivity.class);
                break;
        }
    }

    @Subscribe
    public void onEventRefreshHomeCurrentCategory(RefreshHomeCurrentCategory refreshHomeCurrentCategory) {
        ((HomeCategoryFragment) categoryFragments.get(home_tab.getSelectedTabPosition())).refreshData();
    }

    private void onToast(String message) {
        ToastUtil.show(ctx, message, Toast.LENGTH_SHORT);
    }
}
