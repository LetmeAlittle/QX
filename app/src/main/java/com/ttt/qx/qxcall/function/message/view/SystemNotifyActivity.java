package com.ttt.qx.qxcall.function.message.view;

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
import com.ttt.qx.qxcall.adapter.SySNotifyAdapter;
import com.ttt.qx.qxcall.database.NotifyDao;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.NotifyBean;
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

public class SystemNotifyActivity extends BaseActivity {
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
        setContentView(R.layout.activity_sys_notify);
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
        NotifyDao notifyDao = new NotifyDao();
        List<NotifyBean> notifyBeanList = notifyDao.selectAll();
        if (notifyBeanList == null) {
            notifyBeanList = new ArrayList<>();
        }
        SySNotifyAdapter sySNotifyAdapter = new SySNotifyAdapter(this, notifyBeanList);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(sySNotifyAdapter);
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
