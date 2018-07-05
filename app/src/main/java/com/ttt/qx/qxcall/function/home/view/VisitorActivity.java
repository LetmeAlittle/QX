package com.ttt.qx.qxcall.function.home.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.FollowVisitorInfoAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.FlowVisitorInfoList;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyd on 2017/11/4.
 */

public class VisitorActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    //当前用户id
    private int userId;
    private String mAuthorization;
    private int mCurrent_page;
    private int mTotalPage;
    private Context context;
    private FollowVisitorInfoAdapter mFollowVisitorInfoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_fans_visitor);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userId = getIntent().getIntExtra("id", -1);
        UserDao dao = new UserDao();
        UserBean userBean = dao.queryFirstData();
        mAuthorization = "Bearer " + userBean.getToken();
        if (userBean.getUserId() == userId) {//如果是用户自己 查看
            title_tv.setText("我看过的");
            userId = -1;//如果是自己本人 置为-1,member_id值为""
        } else {
            title_tv.setText("谁看过他");
        }
        context = this;
        HomeModel.getHomeModel().getUserVisitorList(new ProgressSubscribe<>(new SubScribeOnNextListener<FlowVisitorInfoList>() {
            @Override
            public void onNext(FlowVisitorInfoList list) {
                if (list.getStatus_code() == 200) {
                    FlowVisitorInfoList.DataBeanX data = list.getData();
                    mCurrent_page = data.getCurrent_page();
                    mTotalPage = data.getLast_page();
                    List<FlowVisitorInfoList.DataBeanX.DataBean> dataBeanList = data.getData();
                    if (dataBeanList == null) {
                        dataBeanList = new ArrayList<FlowVisitorInfoList.DataBeanX.DataBean>();
                    }
                    mFollowVisitorInfoAdapter = new FollowVisitorInfoAdapter(context, dataBeanList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    recycler_view.setAdapter(mFollowVisitorInfoAdapter);
                    recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        int lastVisibleItem = 0;

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (mFollowVisitorInfoAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                                    && lastVisibleItem + 1 == mFollowVisitorInfoAdapter.getItemCount()) {
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
                    onToast(list.getMessage());
                }
            }
        }, this), userId == -1 ? "" : String.valueOf(userId), "1", mAuthorization);
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (mCurrent_page < mTotalPage) {
            mCurrent_page++;
            HomeModel.getHomeModel().getUserVisitorList(new ProgressSubscribe<>(new SubScribeOnNextListener<FlowVisitorInfoList>() {
                @Override
                public void onNext(FlowVisitorInfoList list) {
                    if (list.getStatus_code() == 200) {
                        FlowVisitorInfoList.DataBeanX data = list.getData();
                        List<FlowVisitorInfoList.DataBeanX.DataBean> dataBeanList = data.getData();
                        if (dataBeanList == null) {
                            dataBeanList = new ArrayList<FlowVisitorInfoList.DataBeanX.DataBean>();
                        }
                        mFollowVisitorInfoAdapter.addList(dataBeanList);
                        mFollowVisitorInfoAdapter.notifyDataSetChanged();
                    }
                }
            }, context), userId == -1 ? "" : String.valueOf(userId), String.valueOf(mCurrent_page), mAuthorization);
        } else {
            onToast("没有更多了！");
        }
    }

    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
        }
    }

    private void onToast(String message) {
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }
}
