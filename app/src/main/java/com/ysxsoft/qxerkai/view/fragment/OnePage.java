package com.ysxsoft.qxerkai.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.NOneFragmentMoreActivity;
import com.ysxsoft.qxerkai.view.activity.NPaoHaTiActivity;
import com.ysxsoft.qxerkai.view.activity.NPengYouQuanActivity;
import com.ysxsoft.qxerkai.view.activity.NTouTingActivity;
import com.ysxsoft.qxerkai.view.activity.NYiJianPiPeiActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.SaGouLiangActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.MyScrollView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class OnePage extends BasePager implements View.OnClickListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.rv_xiaoxiannv)
    RecyclerView rvXiaoxiannv;
    @BindView(R.id.rv_xiaogege)
    RecyclerView rvXiaogege;
    @BindView(R.id.rv_yujie)
    RecyclerView rvYujie;
    @BindView(R.id.rv_dashu)
    RecyclerView rvDashu;
    @BindView(R.id.rv_shengyou)
    RecyclerView rvShengyou;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_paohuati)
    LinearLayout llPaohuati;
    @BindView(R.id.ll_pipei)
    LinearLayout llPipei;
    @BindView(R.id.ll_xiaoxiannv)
    LinearLayout llXiaoxiannv;
    @BindView(R.id.ll_xiaogege)
    LinearLayout llXiaogege;
    @BindView(R.id.ll_yujie)
    LinearLayout llYujie;
    @BindView(R.id.ll_dashu)
    LinearLayout llDashu;
    @BindView(R.id.ll_shengyou)
    LinearLayout llShengyou;
    @BindView(R.id.ll_pengyouquan)
    LinearLayout llPengyouquan;
    @BindView(R.id.ll_pengyouquan2)
    LinearLayout llPengyouquan2;
    @BindView(R.id.ll_shagouliang)
    LinearLayout llShagouliang;
    @BindView(R.id.ll_shagouliang2)
    LinearLayout llShagouliang2;
    @BindView(R.id.ll_toutingyou)
    LinearLayout llToutingyou;
    @BindView(R.id.ll_toutingyo2)
    LinearLayout llToutingyo2;

    private View rootView;
    private YongHuAdapter adapter1;
    private YongHuAdapter adapter2;
    private YongHuAdapter adapter3;
    private YongHuAdapter adapter4;
    private YongHuAdapter adapter5;

    private ArrayList<Integer> temp = new ArrayList<>();

    public OnePage(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_one, null);
        ButterKnife.bind(this, rootView);
        initStatusBar(statusBar);
        initTitleBar();
        initRv();
        llPaohuati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NPaoHaTiActivity.class));
            }
        });
        llPipei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NYiJianPiPeiActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        });
        llXiaoxiannv.setOnClickListener(this);
        llXiaogege.setOnClickListener(this);
        llYujie.setOnClickListener(this);
        llDashu.setOnClickListener(this);
        llShengyou.setOnClickListener(this);
        llPengyouquan.setOnClickListener(new ItemClickListener());
        llPengyouquan2.setOnClickListener(new ItemClickListener());
        llShagouliang.setOnClickListener(new ItemClickListener());
        llShagouliang2.setOnClickListener(new ItemClickListener());
        llToutingyou.setOnClickListener(new ItemClickListener());
        llToutingyo2.setOnClickListener(new ItemClickListener());
        return rootView;
    }

    private void initRv() {
        adapter1 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter2 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter3 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter4 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter5 = new YongHuAdapter(R.layout.fragment_one_item);
        rvXiaoxiannv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvXiaogege.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvYujie.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvDashu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvShengyou.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvXiaoxiannv.setAdapter(adapter1);
        rvXiaogege.setAdapter(adapter2);
        rvYujie.setAdapter(adapter3);
        rvDashu.setAdapter(adapter4);
        rvShengyou.setAdapter(adapter5);
        ArrayList<String> temp = new ArrayList<>();
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        adapter1.setNewData(temp);
        adapter2.setNewData(temp);
        adapter3.setNewData(temp);
        adapter4.setNewData(temp);
        adapter5.setNewData(temp);
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class).putExtra("title", "用户昵称"));
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class).putExtra("title", "用户昵称"));
            }
        });
        adapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class).putExtra("title", "用户昵称"));
            }
        });
        adapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class).putExtra("title", "用户昵称"));
            }
        });
        adapter5.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class).putExtra("title", "用户昵称"));
            }
        });
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("砰砰");
    }

    @Override
    public void initData() {
        tvGonggao.setSelected(true);
        temp.clear();
        temp.add(R.mipmap.image1);
        temp.add(R.mipmap.image2);
        temp.add(R.mipmap.image3);
        temp.add(R.mipmap.image4);
        temp.add(R.mipmap.image5);
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY > llCenter.getY()) {
                    llTop.setVisibility(View.VISIBLE);
                } else {
                    llTop.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        String title = "";
        switch (view.getId()) {
            case R.id.ll_xiaoxiannv:
                title = "小仙女";
                break;
            case R.id.ll_xiaogege:
                title = "小哥哥";
                break;
            case R.id.ll_yujie:
                title = "御姐";
                break;
            case R.id.ll_dashu:
                title = "大叔";
                break;
            case R.id.ll_shengyou:
                title = "声优";
                break;
        }
        ctx.startActivity(new Intent(ctx, NOneFragmentMoreActivity.class).putExtra("title", title));
    }

    private class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_pengyouquan:
                case R.id.ll_pengyouquan2:
                    ctx.startActivity(new Intent(ctx, NPengYouQuanActivity.class));
                    break;
                case R.id.ll_shagouliang:
                case R.id.ll_shagouliang2:
                    ctx.startActivity(new Intent(ctx, SaGouLiangActivity.class));
                    break;
                case R.id.ll_toutingyou:
                case R.id.ll_toutingyo2:
                    ctx.startActivity(new Intent(ctx, NTouTingActivity.class));
                    break;
            }
        }
    }

    private class YongHuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public YongHuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            FrameLayout flBg = helper.getView(R.id.fl_bg);
            int bgWidth = (int) SystemUtils.getScreenWidth(activity) / 2 - DimenUtils.dp2px(ctx, 17.5f);
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth / 34 * 45));
            ImageView ivView = helper.getView(R.id.iv_image);
            Random rand = new Random();
            int i = rand.nextInt(5);
            ivView.setImageResource(temp.get(i));
            ImageView ivCall=helper.getView(R.id.iv_call);
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctx.startActivity(new Intent(ctx, NHuaLiaoActivity.class));
                }
            });
        }
    }

}
