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
import com.ttt.qx.qxcall.adapter.GiftListAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.GiftRankList;
import com.ysxsoft.qxerkai.view.adapter.LiWuBangAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ttt.qx.qxcall.QXCallApplication.login;

public class LiWuBangActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{

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

    private int pageIndex=1;
    private int pageTotal=1;
    private LiWuBangAdapter adapter;
    private UserDao userDao;
    private UserBean userBean;
    private String authorization;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li_wu_bang);
        uid=getIntent().getStringExtra("uid");
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
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LiWuBangAdapter(R.layout.activity_liwubang_item);
        adapter.openLoadAnimation(com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        swipeTarget.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (login) {
            userDao = new UserDao();
            userBean = userDao.queryFirstData();
            authorization = "Bearer " + userBean.getToken();
            FindModel.getFindModel().getGiftRankList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftRankList>() {
                @Override
                public void onNext(GiftRankList giftRankList) {
                    if (giftRankList.getStatus_code() == 200) {
                        GiftRankList.DataBeanX data = giftRankList.getData();
                        List<GiftRankList.DataBeanX.DataBean> list = data.getData();
                        if (list == null) {
                            list = new ArrayList<GiftRankList.DataBeanX.DataBean>();
                        }
                        //初始化分页数据
                        pageTotal = data.getLast_page();
                        if(pageIndex==1){
                            adapter.setNewData(list);
                        }else {
                            adapter.addData(list);
                            adapter.loadMoreComplete();
                        }
                    }else {
                        if(pageIndex>1){
                            pageIndex--;
                        }
                    }
                }
            }, this), ""+pageIndex,uid, authorization);
        }
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
