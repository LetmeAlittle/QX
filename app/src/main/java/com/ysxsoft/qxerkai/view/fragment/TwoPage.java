package com.ysxsoft.qxerkai.view.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
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
import com.ysxsoft.qxerkai.view.activity.LiaoHanQuActivity;
import com.ysxsoft.qxerkai.view.activity.LiaoMeiQuActivity;
import com.ysxsoft.qxerkai.view.activity.NFaTieActivity;
import com.ysxsoft.qxerkai.view.activity.NQingQuListActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ResizableImageView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class TwoPage extends BasePager implements View.OnClickListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.top_bar_bg_view)
    LinearLayout topBarBgView;
    @BindView(R.id.tv_top_bar_item1)
    TextView tvTopBarItem1;
    @BindView(R.id.tv_top_bar_item2)
    TextView tvTopBarItem2;
    @BindView(R.id.tv_top_bar_item3)
    TextView tvTopBarItem3;
    @BindView(R.id.tv_top_bar_item4)
    TextView tvTopBarItem4;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.rv_quanzi)
    RecyclerView rvQuanzi;
    @BindView(R.id.rv_tuijianyonghu)
    RecyclerView rvTuijianyonghu;
    @BindView(R.id.riv_loop)
    ResizableImageView rivLoop;
    @BindView(R.id.iv_liaomei)
    ImageView ivLiaomei;
    @BindView(R.id.iv_liaohan)
    ImageView ivLiaohan;

    private View rootView;

    private int offset = 0;// 动画图片偏移量
    private int currentTabIndex = 0;

    private QuanZiAdapter quanZiAdapter;
    private YongHuAdapter yongHuAdapter;

    public TwoPage(Context ctx) {
        super(ctx);
    }

    private ArrayList<Integer> loops = new ArrayList<>();
    private ArrayList<Integer> temp = new ArrayList<>();

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_two, null);
        ButterKnife.bind(this, rootView);
        initStatusBar(statusBar);
        initTitleBar();
        initRv();
        return rootView;
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("小情趣");
    }

    private void initRv() {
        rvQuanzi.setLayoutManager(new LinearLayoutManager(ctx));
        quanZiAdapter = new QuanZiAdapter(R.layout.fragment_two_content_item);
        rvQuanzi.setAdapter(quanZiAdapter);
        rvTuijianyonghu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        yongHuAdapter = new YongHuAdapter(R.layout.fragment_one_item);
        rvTuijianyonghu.setAdapter(yongHuAdapter);
    }

    @Override
    public void initData() {
        initCurrView();
        initHttpData();
        loops.clear();
        loops.add(R.mipmap.fragment_two_laoshiji);
        loops.add(R.mipmap.fragment_two_guimi);
        loops.add(R.mipmap.fragment_two_liangxing);
        loops.add(R.mipmap.fragment_two_juben);
        temp.clear();
        temp.add(R.mipmap.image1);
        temp.add(R.mipmap.image2);
        temp.add(R.mipmap.image3);
        temp.add(R.mipmap.image4);
        temp.add(R.mipmap.image5);
    }

    private void initHttpData() {
        ArrayList<String> temp1 = new ArrayList<>();
        switch (currentTabIndex) {
            case 0:
                temp1.clear();
                temp1.add("");
                temp1.add("");
                temp1.add("");
                quanZiAdapter.setNewData(temp1);
                break;
            case 1:
                temp1.clear();
                temp1.add("");
                temp1.add("");
                quanZiAdapter.setNewData(temp1);
                break;
            case 2:
                temp1.clear();
                temp1.add("");
                quanZiAdapter.setNewData(temp1);
                break;
            case 3:
                temp1.clear();
                temp1.add("");
                temp1.add("");
                temp1.add("");
                quanZiAdapter.setNewData(temp1);
                break;
        }
        ArrayList<String> temp2 = new ArrayList<>();
        temp2.add("");
        temp2.add("");
        temp2.add("");
        temp2.add("");
        quanZiAdapter.setNewData(temp1);
        yongHuAdapter.setNewData(temp2);
    }

    private void initCurrView() {
        // 动画
        offset = (int) ((SystemUtils.getScreenWidth(activity) - DimenUtils.dp2px(ctx, 30)) / 4);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(offset, ViewGroup.LayoutParams.MATCH_PARENT);
        topBarBgView.setLayoutParams(params);
        tvTopBarItem1.setOnClickListener(this);
        tvTopBarItem2.setOnClickListener(this);
        tvTopBarItem3.setOnClickListener(this);
        tvTopBarItem4.setOnClickListener(this);
        tvGonggao.setSelected(true);
        ivLiaomei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, LiaoMeiQuActivity.class));
            }
        });
        ivLiaohan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, LiaoHanQuActivity.class));
            }
        });
    }

    @OnClick({R.id.tv_mores})
    public void onMores(View view) {
        ctx.startActivity(new Intent(ctx, NQingQuListActivity.class));
    }

    /**
     * 发布
     *
     * @param view
     */
    @OnClick({R.id.iv_fabu})
    public void onFabu(View view) {
        ctx.startActivity(new Intent(ctx, NFaTieActivity.class));
    }

    @Override
    public void onClick(View view) {
        int tag = Integer.valueOf(view.getTag().toString());
        if (tag == currentTabIndex) {
            return;
        }
        //必须用属性动画，否则回到这个页面会初始化位置
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topBarBgView, "translationX", offset * currentTabIndex, offset * tag);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        objectAnimator.start();
        rivLoop.setImageResource(loops.get(tag));
        currentTabIndex = tag;
        initHttpData();
    }

    private class QuanZiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public QuanZiAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

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
        }
    }

}
