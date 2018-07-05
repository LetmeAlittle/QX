package com.ttt.qx.qxcall.function.home.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.HomeCategoryAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.SexFilter;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.pager.HomePager;
import com.ttt.qx.qxcall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.ttt.qx.qxcall.pager.HomePager.sex;

/**
 * 首页不同用户分类容器Fragment
 * Created by 王亚东 on 2017/10/1.
 */

public class HomeCategoryFragment extends Fragment {
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.home_category_recycler_view)
    RecyclerView home_category_recycler_view;
    private Context context;
    private HomeCategoryAdapter homeCategoryAdapter;
    private LinearLayoutManager mLayoutManager;
    //是否存在屏幕顶部标记
    private boolean top = true;
    //标签
    private String tag;
    private int current_page;
    private int totalPage;
    private boolean loading;
    //性别是否进行了 筛选标记
    private String authorization = "";
    private String currentSex = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_home_ctegory, null, false);
        ButterKnife.bind(this, view);
        swiperefreshlayout.setColorSchemeColors(Color.RED, Color.BLUE);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(true);
                //下拉刷新操作
                initData();
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) {//好用、难读、效率低、不易维护。
            EventBus.getDefault().register(this);
        }
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            authorization = "Bearer " + userBean.getToken();
        }
        tag = getArguments().getString("id");
        initData();
//        initView();
        return view;
    }

    /**
     * 初始化view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        home_category_recycler_view.setLayoutManager(gridLayoutManager);
        home_category_recycler_view.setAdapter(homeCategoryAdapter);
        home_category_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (homeCategoryAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == homeCategoryAdapter.getItemCount()) {
                    //滚动到底部了，可以进行数据加载等操作
//                    if (!loading) {
                    loadMore();
//                        loading = true;
//                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void loadMore() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (current_page < totalPage) {
            current_page++;
            HomeModel.getHomeModel().getUserList(new ProgressSubscribe<>(new SubScribeOnNextListener<UserListInfo>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onNext(UserListInfo info) {
                    if (info.getStatus_code() == 200) {
                        UserListInfo.DataBean data = info.getData();
                        List<UserListInfo.DataBean.ListBean> listBeanList = data.getList();
                        if (listBeanList == null) {
                            listBeanList = new ArrayList<UserListInfo.DataBean.ListBean>();
                        }
                        homeCategoryAdapter.addList(listBeanList);
                        homeCategoryAdapter.notifyDataSetChanged();
//                        loading = false;
                    }
                }
            }, context), tag, sex, String.valueOf(current_page), authorization);
        } else {
//            loading = false;
            onToast("没有更多了！");
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        HomeModel.getHomeModel().getUserList(new Subscriber<UserListInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onNext(UserListInfo userListInfo) {
                if (userListInfo.getStatus_code() == 200) {
                    swiperefreshlayout.setRefreshing(false);
                    createAdapter(userListInfo);
                    initView();
                }
            }
        }, tag, sex, "1", authorization);
//        HomeModel.getHomeModel().getUserList(new ProgressSubscribe<>(new SubScribeOnNextListener<UserListInfo>() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onNext(UserListInfo info) {
//                if (info.getStatus_code() == 200) {
//                    swiperefreshlayout.setRefreshing(false);
//                    createAdapter(info);
//                    initView();
//                }
//            }
//        }, context), tag, sex, "1", authorization);
    }

    /**
     * 根据用户列表数据对象 创建 adapter
     *
     * @param info
     */
    private void createAdapter(UserListInfo info) {
        UserListInfo.DataBean data = info.getData();
        List<UserListInfo.DataBean.ListBean> listBeanList = data.getList();
        if (listBeanList == null) {
            onToast("暂无数据！");
            listBeanList = new ArrayList<UserListInfo.DataBean.ListBean>();
        }
        //初始化分页数据
        current_page = data.getPage().getCurrent_page();
        totalPage = data.getPage().getLast_page();
        homeCategoryAdapter = new HomeCategoryAdapter(context, listBeanList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        initData();
    }

    private void onToast(String message) {
        ToastUtil.show(context, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventSexFilter(SexFilter sexFilter) {
        if (isMenuVisible()) {//刷新当前可见的Fragment
            if (!currentSex.equals(HomePager.sex)) {
                currentSex = HomePager.sex;
                initData();
            }
        }
    }
}
