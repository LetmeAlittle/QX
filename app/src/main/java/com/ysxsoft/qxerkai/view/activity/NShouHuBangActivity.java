package com.ysxsoft.qxerkai.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.LiaoRenQuAdapter;
import com.ysxsoft.qxerkai.view.adapter.ShouHuBangAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NShouHuBangActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private int pageIndex = 1;
    private int pageTotal = 1;
    private ShouHuBangAdapter adapter;

    private String uid;
    private String type;    //1：这个用户的守护列表，2：这个用户守护的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nshou_hu_bang);
        ButterKnife.bind(this);
        uid=getIntent().getStringExtra("uid");
        type=getIntent().getStringExtra("type");
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
        tvPublicTitlebarCenter.setText("守护榜");
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShouHuBangAdapter(R.layout.activity_shouhubang_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this,swipeTarget);
        View headView = getLayoutInflater().inflate(R.layout.activity_shouhubang_top, (ViewGroup) swipeTarget.getParent(), false);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {
       multipleStatusView.showLoading();
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",uid);
        hashMap.put("type",type);
        RetrofitTools.getGuardsingList(hashMap)
                .subscribe(new ResponseSubscriber<GuardsListResponse>(){

                    @Override
                    public void onSuccess(GuardsListResponse guardsListResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        adapter.setNewData(guardsListResponse.getData());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                        ToastUtils.showToast(NShouHuBangActivity.this,e.getMessage(),0);
                    }
                });
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
