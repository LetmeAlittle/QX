package com.ttt.qx.qxcall.function.find.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.AllFriendsNewsAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.NotifyFindDataRefresh;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ttt.qx.qxcall.QXCallApplication.login;
import static com.ttt.qx.qxcall.QXCallApplication.onToast;


/**
 * 全部以及好友动态fragment
 * Created by 王亚东 on 2017/10/1.
 */

public class AllFriendsNewsFragment extends Fragment {
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.all_friends_news_recycler_view)
    RecyclerView all_friends_news_recycler_view;
    private Context context;
    private AllFriendsNewsAdapter allFriendsNewsAdapter;
    private LinearLayoutManager mLayoutManager;
    //是否存在屏幕顶部标记
    private boolean top = true;
    private String category;
    private String authorization;
    private int current_page;
    private int totalPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_all_friends_news, null, false);
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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initData();
        initView();
        return view;
    }

    /**
     * 初始化view
     */
    private void initView() {

    }
    @Subscribe
    public void onEventNotifyFindDataRefresh(NotifyFindDataRefresh notifyFindDataRefresh) {
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        category = getArguments().getString("category");
        if (category.equals("全部")) {//如果是全部
            FindModel.getFindModel().getAllDynamic(new ProgressSubscribe<>(new SubScribeOnNextListener<DynamicResponse>() {
                @Override
                public void onNext(DynamicResponse dynamicResponse) {
                    if (dynamicResponse.getStatus_code() == 200) {
                        swiperefreshlayout.setRefreshing(false);
                        DynamicResponse.DataBean data = dynamicResponse.getData();
                        List<DynamicResponse.DataBean.ListBean> listBeen = data.getList();
                        if (listBeen == null) {
                            listBeen = new ArrayList<DynamicResponse.DataBean.ListBean>();
                        }
                        //初始化分页数据
                        current_page = data.getCurrent_page();
                        totalPage = data.getLast_page();
                        allFriendsNewsAdapter = new AllFriendsNewsAdapter(context, listBeen);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        all_friends_news_recycler_view.setLayoutManager(linearLayoutManager);
                        all_friends_news_recycler_view.setAdapter(allFriendsNewsAdapter);
                        all_friends_news_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            int lastVisibleItem = 0;

                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (allFriendsNewsAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                                        && lastVisibleItem + 1 == allFriendsNewsAdapter.getItemCount()) {
                                    loadMore();
                                }
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                            }
                        });
                    } else {
                    }
                }
            }, context), authorization, "1","");
        } else {//好友动态
            if (login) {
                UserDao userDao = new UserDao();
                UserBean userBean = userDao.queryFirstData();
                String token = userBean.getToken();
                authorization = "Bearer" + token;
                FindModel.getFindModel().getFriendDynamic(new ProgressSubscribe<>(new SubScribeOnNextListener<DynamicResponse>() {
                    @Override
                    public void onNext(DynamicResponse dynamicResponse) {
                        swiperefreshlayout.setRefreshing(false);
                        if (dynamicResponse.getStatus_code() == 200) {
                            DynamicResponse.DataBean data = dynamicResponse.getData();
                            List<DynamicResponse.DataBean.ListBean> listBeen = data.getList();
                            if (listBeen == null) {
                                listBeen = new ArrayList<DynamicResponse.DataBean.ListBean>();
                            }
                            //初始化分页数据
                            current_page = data.getCurrent_page();
                            totalPage = data.getLast_page();
                            allFriendsNewsAdapter = new AllFriendsNewsAdapter(context, listBeen);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            all_friends_news_recycler_view.setLayoutManager(linearLayoutManager);
                            all_friends_news_recycler_view.setAdapter(allFriendsNewsAdapter);
                            all_friends_news_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                int lastVisibleItem = 0;

                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if (allFriendsNewsAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                                            && lastVisibleItem + 1 == allFriendsNewsAdapter.getItemCount()) {
                                        loadMoreFriends();
                                    }
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                                }
                            });
                        } else {
                        }
                    }
                }, context), authorization, "1","");
            }
        }
    }

    /**
     * 加载好友动态
     */
    private void loadMoreFriends() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (current_page < totalPage) {
            current_page++;
            FindModel.getFindModel().getFriendDynamic(new ProgressSubscribe<>(new SubScribeOnNextListener<DynamicResponse>() {
                @Override
                public void onNext(DynamicResponse dynamicResponse) {
                    if (dynamicResponse.getStatus_code() == 200) {
                        DynamicResponse.DataBean data = dynamicResponse.getData();
                        List<DynamicResponse.DataBean.ListBean> listBeen = data.getList();
                        if (listBeen == null) {
                            onToast("暂无数据！");
                            listBeen = new ArrayList<DynamicResponse.DataBean.ListBean>();
                        }
                        allFriendsNewsAdapter.addList(listBeen);
                        allFriendsNewsAdapter.notifyDataSetChanged();
                    }
                }
            }, context), authorization, String.valueOf(current_page),"");
        } else {
            onToast("没有更多了！");
        }
    }

    private void loadMore() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (current_page < totalPage) {
            current_page++;
            FindModel.getFindModel().getAllDynamic(new ProgressSubscribe<>(new SubScribeOnNextListener<DynamicResponse>() {
                @Override
                public void onNext(DynamicResponse dynamicResponse) {
                    if (dynamicResponse.getStatus_code() == 200) {
                        DynamicResponse.DataBean data = dynamicResponse.getData();
                        List<DynamicResponse.DataBean.ListBean> listBeen = data.getList();
                        if (listBeen == null) {
                            onToast("暂无数据！");
                            listBeen = new ArrayList<DynamicResponse.DataBean.ListBean>();
                        }
                        allFriendsNewsAdapter.addList(listBeen);
                        allFriendsNewsAdapter.notifyDataSetChanged();
                    }
                }
            }, context), authorization, String.valueOf(current_page),"");
        } else {
            onToast("没有更多了！");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
