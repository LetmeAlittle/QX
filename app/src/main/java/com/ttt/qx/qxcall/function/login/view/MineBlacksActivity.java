package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.BlackListAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.BlacksList;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyd on 2017/7/28.
 */

public class MineBlacksActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private String mAuthorization;
    private Context mContext;
    private int mCurrentPage;
    private int mLastPage;
    private BlackListAdapter mBlackListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        mAuthorization = "Bearer " + userBean.getToken();
        MineModel.getMineModel().getBlackLists(new ProgressSubscribe<>(new SubScribeOnNextListener<BlacksList>() {
            @Override
            public void onNext(BlacksList list) throws IOException {
                if (list.getStatus_code() == 200) {
                    BlacksList.DataBeanX data = list.getData();
                    List<BlacksList.DataBeanX.DataBean> dataBeanList = data.getData();
                    if (dataBeanList == null) {
                        dataBeanList = new ArrayList<BlacksList.DataBeanX.DataBean>();
                    }
                    mCurrentPage = data.getCurrent_page();
                    mLastPage = data.getLast_page();
                    mBlackListAdapter = new BlackListAdapter(mContext, dataBeanList);
                    recycler_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mBlackListAdapter);
                    recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        int lastVisibleItem = 0;

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (mBlackListAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                                    && lastVisibleItem + 1 == mBlackListAdapter.getItemCount()) {
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
        }, mContext), "1", mAuthorization);
    }

    private void loadMore() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (mCurrentPage < mLastPage) {
            mCurrentPage++;
            MineModel.getMineModel().getBlackLists(new ProgressSubscribe<>(new SubScribeOnNextListener<BlacksList>() {
                @Override
                public void onNext(BlacksList list) throws IOException {
                    if (list.getStatus_code() == 200) {
                        BlacksList.DataBeanX data = list.getData();
                        List<BlacksList.DataBeanX.DataBean> dataBeanList = data.getData();
                        if (dataBeanList == null) {
                            dataBeanList = new ArrayList<BlacksList.DataBeanX.DataBean>();
                        }
                        mBlackListAdapter.addList(dataBeanList);
                        mBlackListAdapter.notifyDataSetChanged();
                    }
                }
            }, mContext), String.valueOf(mCurrentPage), mAuthorization);
        } else {
//            loading = false;
            onToast("没有更多了！");
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
    }

    //
    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                onFinish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }

    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

}
