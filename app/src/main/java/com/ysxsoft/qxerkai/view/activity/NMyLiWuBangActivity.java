package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.MyLiWuResponse;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.LiWuBangAdapter;
import com.ysxsoft.qxerkai.view.adapter.MyLiWuBangAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NMyLiWuBangActivity extends NBaseActivity{

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.rv_putongliwu)
    RecyclerView rvPutongliwu;
    @BindView(R.id.rv_guizhuliwu)
    RecyclerView rvGuizhuliwu;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private MyLiWuBangAdapter adapter1;
    private MyLiWuBangAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmy_li_wu_bang);
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
        tvPublicTitlebarCenter.setText("礼物榜");
    }

    private void initView() {
        rvPutongliwu.setLayoutManager(new StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.VERTICAL));
        adapter1 = new MyLiWuBangAdapter(R.layout.activity_nmy_liwubang_item);
        adapter1.openLoadAnimation(com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN);
        adapter1.isFirstOnly(true);
        rvPutongliwu.setAdapter(adapter1);

//        贵族礼物
//        rvGuizhuliwu.setLayoutManager(new StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.VERTICAL));
//        adapter2 = new MyLiWuBangAdapter(R.layout.activity_nmy_liwubang_item);
//        adapter2.openLoadAnimation(com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN);
//        adapter2.isFirstOnly(true);
//        rvGuizhuliwu.setAdapter(adapter2);
    }

    private void initData() {
        multipleStatusView.showLoading();
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",getIntent().getStringExtra("uid"));
        RetrofitTools.getMyLiWuList(hashMap)
                .subscribe(new ResponseSubscriber<MyLiWuResponse>(){
                    @Override
                    public void onSuccess(MyLiWuResponse myLiWuResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                       adapter1.setNewData(myLiWuResponse.getData().getData());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                        ToastUtils.showToast(NMyLiWuBangActivity.this,e.getMessage(),0);
                    }
                });
    }



}
