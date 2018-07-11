package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XuanZheShouHuActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    public static final String TYPE_JUMP = "TYPE_JUMP";

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

    private XuanZheShouHuAdapter adapter;
    private int type = 0, pageIndex = 1, pageTotal = 1;
    private String user_id = "";
    private HashMap<String, String> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_zhe_shou_hu);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
        setListener();
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
        tvPublicTitlebarCenter.setText("");
    }

    private void initView() {
        user_id = DBUtils.getUserId();

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new XuanZheShouHuAdapter(R.layout.activity_xuan_zhe_shou_hu_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        swipeTarget.setAdapter(adapter);


        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE_JUMP, 0);

    }

    private void initData() {
        pageIndex = 1;
        getHyData();
    }

    private void setListener() {
        adapter.setOnLoadMoreListener(this, swipeTarget);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HaoYouListResponse.DataBeanX.DataBean dataBean= (HaoYouListResponse.DataBeanX.DataBean) adapter.getItem(position);
                dataBean.getIcon();
                dataBean.getId();
                dataBean.getNick_name();
            }
        });
    }


    //获取好友列表数据
    private void getHyData() {
        map.clear();
        map.put("user_id", user_id);
        map.put("page", pageIndex + "");

        RetrofitTools.getHaoYouList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
            @Override
            public void onSuccess(HaoYouListResponse response, int code, String msg) {
                multipleStatusView.hideLoading();
                if (code == 200) {
                    List<HaoYouListResponse.DataBeanX.DataBean> data = response.getData().getData();
                    if (pageIndex == 1) {
                        pageTotal = response.getData().getLast_page();
                        if (data != null) {
                            adapter.setNewData(data);
                        }
                    } else {
                        adapter.addData(data);
                    }
                }
            }

            @Override
            public void onFailed(Throwable e) {
                multipleStatusView.hideLoading();

            }

            @Override
            public void onStart() {
                super.onStart();
                multipleStatusView.showLoading();
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            getHyData();
        } else {
            adapter.loadMoreEnd();
        }
    }

    private class XuanZheShouHuAdapter extends BaseQuickAdapter<HaoYouListResponse.DataBeanX.DataBean, BaseViewHolder> {

        public XuanZheShouHuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, HaoYouListResponse.DataBeanX.DataBean item) {
            TextView tvStatus = helper.getView(R.id.tv_status);
            TextView tvChoose = helper.getView(R.id.tv_choose);

            int isOnLine = item.getIs_online();//0:离线 1：在线
            if (isOnLine == 0) {
                tvStatus.setTextColor(Color.parseColor("#30FFFFFF"));
                tvStatus.setText("离线");
                tvChoose.setVisibility(View.INVISIBLE);
            } else {
                tvStatus.setTextColor(Color.parseColor("#fd3d5c"));
                tvStatus.setText("在线");
                tvChoose.setVisibility(View.VISIBLE);
            }
            Glide.with(mContext).load(item.getIcon())
                    .into((ImageView) helper.getView(R.id.iv_touxiang));

            helper.setText(R.id.tv_nickname, item.getNick_name());
        }
    }

}
