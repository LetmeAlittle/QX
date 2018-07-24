package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.PayDetailAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.PaymentDetail;
import com.ysxsoft.qxerkai.view.adapter.ShouZhiMingXiAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NShouZhiMingXiActivity extends NBaseActivity  implements BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private int pageIndex=1;
    private int pageTotal=1;
    private ShouZhiMingXiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nshou_zhi_ming_xi);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("收支明细");
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShouZhiMingXiAdapter(R.layout.activity_shouzhimingxi_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this,recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private UserBean userBean;
    private String authorization;

    private void initData() {
        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        MineModel.getMineModel().payDetail(new ProgressSubscribe<>(new SubScribeOnNextListener<PaymentDetail>() {
            @Override
            public void onNext(PaymentDetail paymentDetail) {
                adapter.loadMoreComplete();
                if (paymentDetail.getStatus_code() == 200) {
                    PaymentDetail.DataBeanX data = paymentDetail.getData();
                    List<PaymentDetail.DataBeanX.DataBean> dataBeanList = data.getData();
                    if (dataBeanList == null) {
                        dataBeanList = new ArrayList<PaymentDetail.DataBeanX.DataBean>();
                    }
                    pageTotal = data.getLast_page();
                    if(pageIndex==1){
                        adapter.setNewData(dataBeanList);
                    }else {
                        adapter.addData(dataBeanList);
                    }
                }
            }
        }, NShouZhiMingXiActivity.this), ""+pageIndex, authorization);
    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            initData();
        } else {
            adapter.loadMoreEnd();
        }
    }
}
