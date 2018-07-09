package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.KeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

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

    private int pageIndex = 1, type = 0;//type =0:全部，1:好友
    private int pageTotal = 1;
    private PengYouQuanAdapter adapter;

    private String userId = "";
    private ArrayList<DynamicResponse.DataBean.ListBean> list;
    private String authorization;
    private ImageView ivBg;
    private ImageView ivTou;
    private TextView tvNickname;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String pic = (String) msg.obj;
                    Glide.with(NPengYouQuanActivity.this)
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
        setContentView(R.layout.activity_npeng_you_quan);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initStatusBar(statusBar2);
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
        llPublicTitlebarLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("");
    }

    private void initView() {
        swipeTarget.setFocusable(false);

        ((RadioButton) rgRadioGroup.getChildAt(0)).setChecked(true);
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                //                LogUtils.i("--->" + totalDy);
                if (totalDy > 200) {
                    llPublicTitlebar.setVisibility(View.VISIBLE);
                } else {
                    llPublicTitlebar.setVisibility(View.INVISIBLE);
                }
            }
        });
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new PengYouQuanAdapter(R.layout.activity_peng_you_quan_item, list);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getLayoutInflater().inflate(R.layout.activity_peng_you_quan_top, (ViewGroup) swipeTarget.getParent(), false);
        headView.findViewById(R.id.tv_fabu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NPengYouQuanActivity.this, NPengYouQuanFaBuActivity.class));
            }
        });
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
        type = 0;
        pageIndex = 1;
        getAllData();
    }


    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(NPengYouQuanActivity.this, PengYouQuanDetailActivity.class)
                .putExtra("quan_id",list.get(position).getId()+""));
            }
        });

        rgRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all:
                        type = 0;
                        pageIndex = 1;
                        getAllData();
                        break;
                    case R.id.rb_friend:
                        type = 1;
                        pageIndex = 1;
                        getFriendData();
                        break;
                }
            }
        });

        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIndex = 1;

                if (type == 0) {
                    getAllData();
                } else {
                    getFriendData();
                }
            }
        });
    }


    private void getAllData() {

        if (pageIndex == 1) {
            list.clear();
        }
        FindModel.getFindModel().getAllDynamic(new Subscriber<DynamicResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(DynamicResponse dynamicResponse) {
                if (dynamicResponse.getStatus_code() == 200) {
                    DynamicResponse.DataBean data = dynamicResponse.getData();

                    if (pageIndex == 1){
//                        LogUtils.e("------"+data.getBackground_pic());
                        Message message = new Message();
                        message.what = 0;
                        message.obj = data.getBackground_pic();
                        handler.sendMessage(message);
                    }
                    pageTotal = data.getLast_page();

                    if (data.getList().size() <= 0){
                        multipleStatusView.showEmpty();
                    }else {
                        multipleStatusView.showContent();
                    }
                    list.addAll(data.getList());
                    adapter.setNewData(list);
                } else {
                    adapter.setNewData(list);
                    ToastUtil.showShort(NPengYouQuanActivity.this, dynamicResponse.getMessage());
                }

            }
        }, "", pageIndex + "", userId);
    }

    private void getFriendData() {
        if (pageIndex == 1) {
            list.clear();
        }

        FindModel.getFindModel().getFriendDynamic(new Subscriber<DynamicResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DynamicResponse dynamicResponse) {
//                LogUtils.e(new Gson().toJson(dynamicResponse) + "");
                if (dynamicResponse.getStatus_code() == 200) {
                    DynamicResponse.DataBean data = dynamicResponse.getData();

                    pageTotal = data.getLast_page();

                    if (data.getList().size() <= 0){
                       multipleStatusView.showEmpty();
                    }else {
                        multipleStatusView.showContent();
                    }
                    list.addAll(data.getList());
                    adapter.setNewData(list);

                } else {
                    adapter.setNewData(list);
                    ToastUtil.showShort(NPengYouQuanActivity.this, dynamicResponse.getMessage());
                }
            }
        }, authorization, pageIndex + "", userId);
    }


    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            if (type == 0) {
                getAllData();
            } else {
                getFriendData();
            }
        } else {
            adapter.loadMoreEnd();
        }
    }

}
