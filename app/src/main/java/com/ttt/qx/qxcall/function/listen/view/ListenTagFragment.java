package com.ttt.qx.qxcall.function.listen.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.ListenCategoryAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.listen.model.entity.StealListenList;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 王亚东 on 2017/10/1.
 */

public class ListenTagFragment extends Fragment {
    private SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.listen_category_recycler_view)
    RecyclerView listen_category_recycler_view;
    private Context context;
    private ListenCategoryAdapter listenCategoryAdapter;
    private LinearLayoutManager mLayoutManager;
    //是否存在屏幕顶部标记
    private boolean top = true;
    private int current_page;
    private int totalPage;
    private String category;
    private String mAuthorization = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_listen_ctegory, null, false);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setColorSchemeColors(Color.RED, Color.BLUE);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(true);
                //下拉刷新操作
                initData();
            }
        });
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    /**
     * 初始化view
     */
    private void initView() {
        if (listenCategoryAdapter != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            listen_category_recycler_view.setLayoutManager(linearLayoutManager);
            listen_category_recycler_view.setAdapter(listenCategoryAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
            listen_category_recycler_view.addItemDecoration(dividerItemDecoration);
            listen_category_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                int lastVisibleItem = 0;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (listenCategoryAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItem + 1 == listenCategoryAdapter.getItemCount()) {
                        //滚动到底部了，可以进行数据加载等操作
//                    if (!lading) {
                        loadMore(category);
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
    }

    /**
     * 加载更多
     *
     * @param id
     */
    public void loadMore(String id) {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (current_page < totalPage) {
            current_page++;
            StealListenModel.getStealListenModel().getStealListenList(new ProgressSubscribe<>(new SubScribeOnNextListener<StealListenList>() {
                @Override
                public void onNext(StealListenList stealListenList) {
                    List<StealListenList.DataBean.ListBean> listBeen = stealListenList.getData().getList();
                    listenCategoryAdapter.addList(listBeen);
                    listenCategoryAdapter.notifyDataSetChanged();
                }
            }, context), id, String.valueOf(current_page), mAuthorization);
        } else {
            onToast("没有更多了！");
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            mAuthorization = "Bearer " + userBean.getToken();
        }
        category = getArguments().getString("id");
        StealListenModel.getStealListenModel().getStealListenList(new ProgressSubscribe<>(new SubScribeOnNextListener<StealListenList>() {
            @Override
            public void onNext(StealListenList stealListenList) {
                if (stealListenList.getStatus_code() == 200) {
                    swiperefreshlayout.setRefreshing(false);
                    StealListenList.DataBean data = stealListenList.getData();
                    List<StealListenList.DataBean.ListBean> listBeen = stealListenList.getData().getList();
                    listenCategoryAdapter = new ListenCategoryAdapter(context, category, listBeen);
                    //初始化分页数据
                    current_page = data.getCurrent_page();
                    totalPage = data.getLast_page();
                    initView();
                } else {
                    onToast("加载失败！");
                }

            }
        }, context), category, "1", mAuthorization);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(context, message, Toast.LENGTH_SHORT);
    }

}
