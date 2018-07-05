package com.ttt.qx.qxcall.function.listen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.ListenCategoryAdapter;
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

public class ListenCategoryFragment extends Fragment {
    @BindView(R.id.listen_category_recycler_view)
    RecyclerView listen_category_recycler_view;
    private Context context;
    private ListenCategoryAdapter listenCategoryAdapter;
    private LinearLayoutManager mLayoutManager;
    private EndLessOnScrollListener mListener;
    //是否存在屏幕顶部标记
    private boolean top = true;
    private int current_page;
    private int totalPage;
    private String category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_listen_ctegory, null, false);
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
            listen_category_recycler_view.setOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {

                    //判断当前 页与后台数据返回过来的总页数 大小
                    if (current_page < totalPage) {
                        current_page++;
                        StealListenModel.getStealListenModel().getStealListenList(new ProgressSubscribe<>(new SubScribeOnNextListener<StealListenList>() {
                            @Override
                            public void onNext(StealListenList stealListenList) {
                                if (stealListenList.getStatus_code() == 200) {
                                    List<StealListenList.DataBean.ListBean> listBeen = stealListenList.getData().getList();
                                    listenCategoryAdapter.addList(listBeen);
                                    listenCategoryAdapter.notifyDataSetChanged();
                                } else {
                                    onToast("加载失败！");
                                }

                            }
                        }, context), category,String.valueOf(current_page),"");
                    } else {
                        onToast("没有更多了！");
                    }
                }
            });
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        category = getArguments().getString("category");
        StealListenModel.getStealListenModel().getStealListenList(new ProgressSubscribe<>(new SubScribeOnNextListener<StealListenList>() {
            @Override
            public void onNext(StealListenList stealListenList) {
                if (stealListenList.getStatus_code() == 200) {
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
        }, context), category,String.valueOf(current_page),"");
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

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(context, message, Toast.LENGTH_SHORT);
    }

}
