package com.ttt.qx.qxcall.pager;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.ListenCategoryAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.ListenFragmentTransfer;
import com.ttt.qx.qxcall.eventbus.NotifyListenDataRefresh;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.listen.model.entity.RandomStealListen;
import com.ttt.qx.qxcall.function.listen.view.StealListenDetailActivity;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.RechargeActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.widget.viewhelper.TabLayoutHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.onToast;


/**
 * Created by wyd on 2017/7/19.
 */
public class ListenPager extends BasePager {
    @BindView(R.id.listen_steal_tab)
    TabLayout listen_steal_tab;
    @BindView(R.id.random_listen_iv)
    CircleImageView random_listen_iv;
    //    @BindView(R.id.listen_category_recycler_view)
//    RecyclerView listen_category_recycler_view;
    //    @BindView(R.id.listen_view_pager)
//    ViewPager listen_view_pager;
//    private List<String> tabIndicators;
    private List<CommonTagList.DataBean> listenTagListData;
    //    private List<Fragment> categoryFragments;
    private ListenCategoryAdapter listenCategoryAdapter;
    //标签名称对应的id Map集合
    private Map<String, String> tagIdMap = new HashMap<>();
    private boolean top = true;
    private int current_page;
    private int totalPage;
    private LinearLayoutManager linearLayoutManager;
    private String id = "0";
    private boolean firstRefresh = true;

    public ListenPager(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        View view = null;
        view = View.inflate(ctx, R.layout.listen_pager, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void initData() {
//        ListenFragmentTransfer listenFragmentTransfer = new ListenFragmentTransfer();
//        listenFragmentTransfer.id = id;
//        EventBus.getDefault().post(listenFragmentTransfer);
        if (QXCallApplication.login) {//登录状态下
            if (firstRefresh) {
                EventBus.getDefault().post(new NotifyListenDataRefresh());
                firstRefresh = false;
            }
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
        listen_steal_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        listen_steal_tab.setTabTextColors(ContextCompat.getColor(ctx, R.color._bbb7d3), ContextCompat.getColor(ctx, R.color._f669ca));
        listen_steal_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(ctx, R.color._f669ca));
        //设置indicator的高度
        listen_steal_tab.setSelectedTabIndicatorHeight(ctx.getResources().getInteger(R.integer.i10));
        ViewCompat.setElevation(listen_steal_tab, 10);
        //关联viewpager
//        listen_steal_tab.setupWithViewPager(listen_view_pager);
        listen_steal_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击事件处理
                String tag = tab.getText().toString();
                id = tagIdMap.get(tag);
                ListenFragmentTransfer listenFragmentTransfer = new ListenFragmentTransfer();
                listenFragmentTransfer.id = id;
                EventBus.getDefault().post(listenFragmentTransfer);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.random_listen_iv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.random_listen_iv:
                UserDao userDao = new UserDao();
                UserBean userBean = userDao.queryFirstData();
                if (userBean != null) {
                    String Authorization = "Bearer " + userBean.getToken();
                    //随机偷听处理
                    StealListenModel.getStealListenModel().getRandomStealListenList(new ProgressSubscribe<>(new SubScribeOnNextListener<RandomStealListen>() {
                        @Override
                        public void onNext(RandomStealListen stealListenList) throws IOException {
                            if (stealListenList.getStatus_code() == 200) {
                                RandomStealListen.DataBean data = stealListenList.getData();
                                if (data != null) {
                                    int id = data.getId();
                                    //进行偷听
                                    //首先判断用户是否可以偷听 偷听费用是否满足
                                    StealListenModel.getStealListenModel().stealListenDeduction(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                        @Override
                                        public void onNext(StandardResponse standardResponse) {
                                            if (standardResponse.getStatus_code() == 200) {
                                                StandardResponse.DataBean data = standardResponse.getData();
                                                if (data.getIs_allow() == 1) {
                                                    Intent intent = new Intent(ctx, StealListenDetailActivity.class);
                                                    //偷听id
                                                    intent.putExtra("id", id);
                                                    intent.putExtra("memberAccount", data.getMember_account());
                                                    ctx.startActivity(intent);
                                                } else {
                                                    //偷听费用不足 弹出是否充值对话框
                                                    TipDialog.showCenterTipDialog(ctx, "当前剩余钻石" + data.getMember_account() + ",偷听钻石不足,是否前去充值？", new TipDialog.OnComponentClickListener() {
                                                        @Override
                                                        public void onCancle() {
                                                            //用户取消操作
                                                        }

                                                        @Override
                                                        public void onConfirm() {
                                                            //用户点击确定执行 相关逻辑
                                                            IntentUtil.jumpIntent(ctx, RechargeActivity.class);
                                                        }
                                                    }, true);
                                                }

                                            } else {

                                            }
                                        }
                                    }, ctx), String.valueOf(id), "Bearer " + userBean.getToken());
                                }
                            }
                        }
                    }, ctx), Authorization);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
        }

    }

    /**
     * 初始tab对应的fragment
     */
    private void initContent() {
        //获取首页标签分类列表
        StealListenModel.getStealListenModel().getStealListenTags(new Subscriber<CommonTagList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CommonTagList commonTagList) {
                if (commonTagList.getStatus_code() == 200) {
                    listenTagListData = commonTagList.getData();
                    for (CommonTagList.DataBean dataBean : listenTagListData) {
                        tagIdMap.put(dataBean.getName(), dataBean.getId());
                        listen_steal_tab.addTab(listen_steal_tab.newTab().setText(dataBean.getName()));
                    }
                    TabLayoutHelper.reflex(listen_steal_tab);
                    //初始化 当前选择的标签 id
                    if (listenTagListData.size() > 0) {
                        id = listenTagListData.get(0).getId();
                    }
                } else {
                    onToast("标签分类获取失败！");
                }
            }
        });
//        tabIndicators = new ArrayList<>();
//        String[] tab_arrays = ctx.getResources().getStringArray(R.array.home_tab_item_arrays);
//        for (int i = 0; i < tab_arrays.length; i++) {
//            tabIndicators.add(tab_arrays[i]);
//            listen_steal_tab.addTab(listen_steal_tab.newTab().setText(tab_arrays[i]));
//        }
//        categoryFragments = new ArrayList<>();
//        for (String tab : tabIndicators) {
//            Fragment fragment = new ListenCategoryFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("category", tab);
//            fragment.setArguments(bundle);
//            categoryFragments.add(fragment);
//        }
//        MainActivity mainActivity = (MainActivity) ctx;
//        TabsAdapter adapter = new TabsAdapter(mainActivity.getSupportFragmentManager(), tabIndicators, categoryFragments);
//        listen_view_pager.setAdapter(adapter);
        //设置修整indicator的宽度
    }

}
