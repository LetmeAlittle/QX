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
import com.ttt.qx.qxcall.adapter.GiftListAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.NotifyFindDataRefresh;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.GiftRankList;

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

public class GiftListFragment extends Fragment {
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.gift_list_recycler_view)
    RecyclerView gift_list_recycler_view;
    private Context context;
    private GiftListAdapter giftListAdapter;
    private LinearLayoutManager mLayoutManager;
    private EndLessOnScrollListener mListener;
    //是否存在屏幕顶部标记
    private boolean top = true;
    private int current_page;
    private int totalPage;
    private UserDao userDao;
    private UserBean userBean;
    private String authorization;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_gift_list, null, false);
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

    /**
     * 初始化数据
     */
    private void initData() {
        if (login) {
            userDao = new UserDao();
            userBean = userDao.queryFirstData();
            authorization = "Bearer " + userBean.getToken();
            FindModel.getFindModel().getGiftRankList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftRankList>() {
                @Override
                public void onNext(GiftRankList giftRankList) {
                    if (giftRankList.getStatus_code() == 200) {
                        swiperefreshlayout.setRefreshing(false);
                        GiftRankList.DataBeanX data = giftRankList.getData();
                        List<GiftRankList.DataBeanX.DataBean> list = data.getData();
                        if (list == null) {
                            list = new ArrayList<GiftRankList.DataBeanX.DataBean>();
                        }
                        //初始化分页数据
                        current_page = data.getCurrent_page();
                        totalPage = data.getLast_page();
                        giftListAdapter = new GiftListAdapter(context, list);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        gift_list_recycler_view.setLayoutManager(linearLayoutManager);
                        gift_list_recycler_view.setAdapter(giftListAdapter);
//                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//                    dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
//                    gift_list_recycler_view.addItemDecoration(dividerItemDecoration);
                        gift_list_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            int lastVisibleItem = 0;

                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (giftListAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                                        && lastVisibleItem + 1 == giftListAdapter.getItemCount()) {
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
                    }
                }
            }, context), "1","" ,authorization);
        }
    }

    private void loadMore() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (current_page < totalPage) {
            current_page++;
            FindModel.getFindModel().getGiftRankList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftRankList>() {
                @Override
                public void onNext(GiftRankList giftRankList) {
                    if (giftRankList.getStatus_code() == 200) {
                        GiftRankList.DataBeanX data = giftRankList.getData();
                        List<GiftRankList.DataBeanX.DataBean> list = data.getData();
                        if (list == null) {
                            list = new ArrayList<GiftRankList.DataBeanX.DataBean>();
                        }
                        giftListAdapter.addGiftList(list);
                        giftListAdapter.notifyDataSetChanged();
                    }
                }
            }, context), String.valueOf(current_page),"", authorization);
        } else {
            onToast("没有更多了！");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * 上拉加载更多监听
     */
    abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {
        //声明一个LinearLayoutManager
        private LinearLayoutManager mLinearLayoutManager;
        // 当前页，从0开始
        private int currentPage = 0;
        // 已经加载出来的Item的数量
        private int totalItemCount;
        // 主要用来存储上一个totalItemCount
        private int previousTotal = 0;
        // 在屏幕上可见的item数量
        private int visibleItemCount;
        // 在屏幕可见的Item中的第一个
        private int firstVisibleItem;
        // 是否正在上拉数据
        private boolean loading = true;

        //
        public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            if (firstVisibleItem > 0) {
                top = false;
            } else {
                top = true;
            }
            if (loading) {
                if (totalItemCount > previousTotal) {
                    //说明数据已经加载结束
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            //这里需要好好理解
            if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
                currentPage++;
                onLoadMore(currentPage);
                loading = true;
            }
        }

        /**
         * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
         * 并且实现这个方法
         */
        public abstract void onLoadMore(int currentPage);
    }

    @Subscribe
    public void onEventNotifyFindDataRefresh(NotifyFindDataRefresh notifyFindDataRefresh) {
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
