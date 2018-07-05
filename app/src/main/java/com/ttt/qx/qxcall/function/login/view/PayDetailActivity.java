package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.PayDetailAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.PaymentDetail;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by wyd on 2017/7/19.
 */
public class PayDetailActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private Context mContext;
    private UserBean userBean;
    private String authorization;
    private int mCurrentPage;
    private int mLastPage;
    private PayDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        MineModel.getMineModel().payDetail(new ProgressSubscribe<>(new SubScribeOnNextListener<PaymentDetail>() {
            @Override
            public void onNext(PaymentDetail paymentDetail) {
                if (paymentDetail.getStatus_code() == 200) {
                    PaymentDetail.DataBeanX data = paymentDetail.getData();
                    List<PaymentDetail.DataBeanX.DataBean> dataBeanList = data.getData();
                    if (dataBeanList == null) {
                        dataBeanList = new ArrayList<PaymentDetail.DataBeanX.DataBean>();
                    }
                    mAdapter = new PayDetailAdapter(mContext, dataBeanList);
                    mCurrentPage = data.getCurrent_page();
                    mLastPage = data.getLast_page();
                    recycler_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                    recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        int lastVisibleItem = 0;

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE
                                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
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
        }, mContext), "1", authorization);
    }

    private void loadMore() {
        //判断当前 页与后台数据返回过来的总页数 大小
        if (mCurrentPage < mLastPage) {
            mCurrentPage++;
            MineModel.getMineModel().payDetail(new ProgressSubscribe<>(new SubScribeOnNextListener<PaymentDetail>() {
                @Override
                public void onNext(PaymentDetail paymentDetail) throws IOException {
                    if (paymentDetail.getStatus_code() == 200) {
                        PaymentDetail.DataBeanX data = paymentDetail.getData();
                        List<PaymentDetail.DataBeanX.DataBean> dataBeanList = data.getData();
                        if (dataBeanList == null) {
                            dataBeanList = new ArrayList<>();
                        }
                        mAdapter.addList(dataBeanList);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }, mContext), String.valueOf(mCurrentPage), authorization);
        } else {
//            loading = false;
            onToast("没有更多了！");
        }
    }

    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }
}
