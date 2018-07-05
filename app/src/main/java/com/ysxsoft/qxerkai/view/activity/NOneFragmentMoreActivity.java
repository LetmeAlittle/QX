package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.fragment.OnePage;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaozhipeng
 *         首页二级页面---查看更多
 */
public class NOneFragmentMoreActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.rv_more)
    RecyclerView rvMore;

    private YongHuAdapter adapter;
    private ArrayList<Integer> temp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_none_fragment_more);
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
        tvPublicTitlebarCenter.setText(getIntent().getStringExtra("title"));
    }

    private void initView() {
        adapter = new YongHuAdapter(R.layout.fragment_one_item);
        rvMore.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvMore.setAdapter(adapter);
    }

    private void initData() {
        temp.add(R.mipmap.image1);
        temp.add(R.mipmap.image2);
        temp.add(R.mipmap.image3);
        temp.add(R.mipmap.image4);
        temp.add(R.mipmap.image5);
        ArrayList<String> temp = new ArrayList<>();
        for(int i=0;i<20;i++){
            temp.add("");
        }
        adapter.setNewData(temp);
    }

    private class YongHuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public YongHuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            FrameLayout flBg = helper.getView(R.id.fl_bg);
            int bgWidth = (int) SystemUtils.getScreenWidth(NOneFragmentMoreActivity.this) / 2 - DimenUtils.dp2px(NOneFragmentMoreActivity.this, 17.5f);
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth / 34 * 45));
            ImageView ivView = helper.getView(R.id.iv_image);
            Random rand = new Random();
            int i = rand.nextInt(5);
            ivView.setImageResource(temp.get(i));
        }
    }

}
