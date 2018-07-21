package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class NPengYouQuanMyActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;


    private int pageIndex = 1, pageTotal = 1;
    private PengYouQuanAdapter adapter;
    private ImageView ivBg;
    private ImageView ivTou;
    private TextView tvNickname;

    private String userId = "", authorization = "";
    private HashMap<String, String> map = new HashMap<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String pic = (String) msg.obj;
                    Glide.with(NPengYouQuanMyActivity.this)
                            .load(pic)
                            .skipMemoryCache(true)
                            .placeholder(R.mipmap.image2)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivBg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npeng_you_quan_my);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        tvPublicTitlebarCenter.setText("我的朋友圈");

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PengYouQuanAdapter(R.layout.activity_peng_you_quan_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getLayoutInflater().inflate(R.layout.activity_peng_you_quan_top, (ViewGroup) swipeTarget.getParent(), false);
        headView.findViewById(R.id.tv_fabu).setVisibility(View.INVISIBLE);
        headView.findViewById(R.id.take_photo).setVisibility(View.GONE);

        tvNickname = (TextView) headView.findViewById(R.id.tv_nickname);
        ivTou = (ImageView) headView.findViewById(R.id.iv_tou);
        ivBg = (ImageView) headView.findViewById(R.id.iv_bg);

        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);


        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            String token = userBean.getToken() + "";
            authorization = "Bearer" + token;
            userId = userBean.getUserId() + "";

            tvNickname.setText(userBean.getNick_name() + "");

            Glide.with(this)
                    .load(userBean.getMember_avatar())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into((ImageView) ivTou);
        }
    }

    private void initData() {
        pageIndex = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            getData();
        } else {
            adapter.loadMoreEnd();
        }
    }


    private void getData() {
        map.clear();
        map.put("page", pageIndex + "");
        map.put("class", 1 + "");
        map.put("user_id", userId);

        FindModel.getFindModel().getMyPengYouQuanList(new Subscriber<DynamicResponse>() {
            @Override
            public void onStart() {
                super.onStart();
                multipleStatusView.showLoading();
            }

            @Override
            public void onCompleted() {
                multipleStatusView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.toString());
                multipleStatusView.hideLoading();
            }

            @Override
            public void onNext(DynamicResponse dynamicResponse) {
                //                LogUtils.e(new Gson().toJson(dynamicResponse));
                if (dynamicResponse.getStatus_code() == 200) {
                    DynamicResponse.DataBean data = dynamicResponse.getData();

                    if (data == null) {
                        return;
                    }

                    if (pageIndex == 1) {
                        Message message = new Message();
                        message.what = 0;
                        message.obj = data.getBackground_pic();
                        handler.sendMessage(message);

                        if (data.getList().size() <= 0) {
                            multipleStatusView.showEmpty();
                        } else {
                            multipleStatusView.showContent();
                        }

                        pageTotal = data.getLast_page();
                        adapter.setNewData(data.getList());
                    } else {
                        adapter.addData(data.getList());
                    }

                } else {
                    showToast(dynamicResponse.getMessage());
                }

            }
        }, authorization, pageIndex + "", 1 + "", userId);

    }

    private void setListener() {
        adapter.setOnIconClickListener(new PengYouQuanAdapter.OnIconClickListener() {
            @Override
            public void onZanClick(DynamicResponse.DataBean.ListBean item) {
                postZan(item);
            }

            @Override
            public void onGiftClick(DynamicResponse.DataBean.ListBean item) {
                showToast("不能送自己礼物");
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DynamicResponse.DataBean.ListBean listBean = (DynamicResponse.DataBean.ListBean) adapter.getItem(position);
                startActivity(new Intent(NPengYouQuanMyActivity.this, PengYouQuanDetailActivity.class)
                        .putExtra("quan_id", listBean.getId() + ""));
            }
        });
    }



    @OnClick(R.id.iv_public_titlebar_left_1)
    public void onViewClicked() {
        finish();
    }

    private void postZan(DynamicResponse.DataBean.ListBean listBean) {
        int isZan = listBean.getIs_zan();
        if (isZan == 0) {
            FindModel.getFindModel().callDianZan(new Subscriber<StandardResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(StandardResponse standardResponse) {
                    if (standardResponse.getStatus_code() == 200) {

                        listBean.setIs_zan(1);
                        listBean.setZan_num(listBean.getZan_num() + 1);
                        adapter.notifyDataSetChanged();
                    }
                    showToast(standardResponse.getMessage());
                }
            }, listBean.getId() + "", authorization);
        } else {
            showToast("已点过赞！");
        }
    }
}
