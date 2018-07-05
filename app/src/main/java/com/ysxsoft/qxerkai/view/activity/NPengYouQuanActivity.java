package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NPengYouQuanActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.status_bar2)
    View statusBar2;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.ll_public_titlebar_left2)
    LinearLayout llPublicTitlebarLeft2;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.ll_public_titlebar)
    LinearLayout llPublicTitlebar;
    @BindView(R.id.rg_radioGroup)
    RadioGroup rgRadioGroup;

    private int pageIndex = 1;
    private int pageTotal = 1;
    private PengYouQuanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npeng_you_quan);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initStatusBar(statusBar2);
        initTieleBar();
        initView();
        initData();
    }

    private void initTieleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llPublicTitlebarLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("");
    }

    private void initView() {
        ((RadioButton)rgRadioGroup.getChildAt(0)).setChecked(true);
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                LogUtils.i("--->" + totalDy);
                if (totalDy > 200) {
                    llPublicTitlebar.setVisibility(View.VISIBLE);
                } else {
                    llPublicTitlebar.setVisibility(View.INVISIBLE);
                }
            }
        });
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PengYouQuanAdapter(R.layout.activity_peng_you_quan_item, new ArrayList<String>());
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getLayoutInflater().inflate(R.layout.activity_peng_you_quan_top, (ViewGroup) swipeTarget.getParent(), false);
        headView.findViewById(R.id.tv_fabu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NPengYouQuanActivity.this,NPengYouQuanFaBuActivity.class));
            }
        });
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(NPengYouQuanActivity.this,PengYouQuanDetailActivity.class));
            }
        });
    }

    private void initData() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        adapter.setNewData(temp);
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
