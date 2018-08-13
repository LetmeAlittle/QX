package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.GiftSendDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.find.view.PostDeatailActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.DealTimeUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanDetailAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import rx.Subscriber;

public class PengYouQuanDetailActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {


    private static final int UP_TOP = 0;

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
    @BindView(R.id.send_tv)
    TextView tvSend;
    @BindView(R.id.comment_et)
    EditText etContent;

    private DynamicDetail.DataBean data;
    private ImageView ivTou;
    private TextView tvReplyNum;
    private TextView tvGift;
    private TextView tvPing;
    private TextView tvZan;
    private TextView tvLook;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvNick;


    private int pageIndex = 1;
    private int pageTotal = 1;
    private List<DynamicDetail.DataBean.ReplyListBean> list;
    private PengYouQuanDetailAdapter adapter;

    private String id = "";
    private UserDao mUserDao;
    private String mAuthorization;
    private String userId;//朋友id
    private int fuid;//朋友id

    private BGANinePhotoLayout ninePhotoLayout;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UP_TOP:

                    Glide.with(PengYouQuanDetailActivity.this)
                            .load(data.getMember_avatar())
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into((ImageView) ivTou);
                    ivTou.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fuid=getIntent().getIntExtra("id",-1);

                            startActivity(new Intent(PengYouQuanDetailActivity.this, NZhiLiaoActivity.class).putExtra("id", fuid).putExtra("accid", fuid+""));//查看好友资料
                        }
                    });
                    tvNick.setText(data.getNick_name());
                    tvTime.setText(data.getCreate_at());
                    tvContent.setText(data.getContent());

                    tvReplyNum.setText("全部评论：" + data.getReply_num());
                    tvGift.setText(data.getGift_num() + "");
                    tvPing.setText(data.getReply_num() + "");
                    tvZan.setText(data.getZan_num() + "");
                    tvLook.setText(data.getClick_num() + "");
                    tvContent.setText(data.getContent());


                    if (data.getIs_zan() == 0) {
                        zan = false;
                    } else {
                        zan = true;
                        //                        tvZan.setBackgroundResource(R.mipmap.yi_zan_iv);
                    }
                    tvZan.setSelected(zan);

                    ninePhotoLayout.setData((ArrayList<String>) data.getImg_list());
                    ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
                        @Override
                        public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
                            File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
                            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(PengYouQuanDetailActivity.this)
                                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
                            photoPreviewIntentBuilder
                                    .previewPhotos((ArrayList<String>) models).currentPosition(position); // 当前预览图片的索引
                            PengYouQuanDetailActivity.this.startActivity(photoPreviewIntentBuilder.build());
                        }
                    });

                    break;
            }
        }
    };
    private DynamicDetail.DataBean.ReplyListBean replyListBean;
    private UserBean mUserBean;

    private boolean zan = false;
    private String memberId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_you_quan_detail);
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
        tvPublicTitlebarCenter.setText("详情");
    }

    private void initView() {

        tvSend.setClickable(false);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = etContent.getText().toString().trim();
                if (!content.equals("")) {
                    tvSend.setClickable(true);
                    tvSend.setTextColor(getResources().getColor(R.color._ffffff));
                    tvSend.setBackgroundResource(R.drawable.fill_bg_9b5ada_yj);
                } else {
                    tvSend.setClickable(false);
                    tvSend.setTextColor(getResources().getColor(R.color._acaeb1));
                    tvSend.setBackgroundResource(R.drawable.side_bg_bfbfbf_yj);
                }
            }
        });


        list = new ArrayList<>();

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PengYouQuanDetailAdapter(R.layout.activity_pengyouquan_detail_item, list);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getLayoutInflater().inflate(R.layout.activity_peng_you_quan_detail_top, (ViewGroup) swipeTarget.getParent(), false);
        tvNick = (TextView) headView.findViewById(R.id.tv_nickname);
        tvTime = (TextView) headView.findViewById(R.id.tv_time);
        tvContent = (TextView) headView.findViewById(R.id.tv_content);
        tvLook = (TextView) headView.findViewById(R.id.tv_look);
        tvZan = (TextView) headView.findViewById(R.id.tv_zan);
        tvPing = (TextView) headView.findViewById(R.id.tv_ping);
        tvGift = (TextView) headView.findViewById(R.id.tv_gift);
        tvReplyNum = (TextView) headView.findViewById(R.id.tv_reply_num);
        ivTou = (ImageView) headView.findViewById(R.id.iv_tou);

        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);

        id = getIntent().getStringExtra("quan_id");
        fuid=getIntent().getIntExtra("id",-1);
        //首先获取 当前评论id
        mUserDao = new UserDao();
        mUserBean = mUserDao.queryFirstData();
        userId = mUserBean.getUserId() + "";
        mAuthorization = "Bearer " + mUserBean.getToken();

    }

    private void initHeadView(View headView) {
        ninePhotoLayout = (BGANinePhotoLayout) headView.findViewById(R.id.snpl_moment_add_photos);
    }

    private void initData() {

        if (pageIndex == 1) {
            list.clear();
        }

        FindModel.getFindModel().callDetail(new ProgressSubscribe<>(new SubScribeOnNextListener<DynamicDetail>() {
            @Override
            public void onNext(DynamicDetail detail) {
                if (detail.getStatus_code() == 200) {
                    data = detail.getData();
                    handler.sendEmptyMessage(UP_TOP);

                    memberId = detail.getData().getMember_id()+"";
                    list.addAll(data.getReply_list());
                    adapter.setNewData(list);

                } else {
                    ToastUtil.showShort(PengYouQuanDetailActivity.this, detail.getMessage());
                }
            }
        }, this), id, mAuthorization);

    }

    private void setListener() {

        tvGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memberId.equals(userId)){
                    showToast("不能送自己礼物");
                    return;
                }


                FindModel.getFindModel().getGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftList>() {
                    @Override
                    public void onNext(GiftList giftList) {
                        if (giftList.getStatus_code() == 200) {
                            GiftSendDialog.showBottomDialog(PengYouQuanDetailActivity.this, giftList.getData(), new GiftSendDialog.OnComponentClickListener() {
                                @Override
                                public void onCancle() {
                                }

                                @Override
                                public void onSend(String gift_id) {
                                    FindModel.getFindModel().sendGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                        @Override
                                        public void onNext(StandardResponse response) {
                                            if (response.getStatus_code() == 200) {
                                                onToast("赠送成功！");
                                                String s = tvGift.getText().toString();
                                                int i = Integer.parseInt(s);
                                                i++;
                                                tvGift.setText(String.valueOf(i));
                                            } else {
                                                onToast(response.getMessage());
                                            }
                                        }
                                    }, PengYouQuanDetailActivity.this), gift_id, String.valueOf(id), mAuthorization);
                                }
                            });
                        } else {
                            onToast(giftList.getMessage());
                        }
                    }
                }, PengYouQuanDetailActivity.this));

            }
        });

        tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (zan) {
                    onToast("已点过赞！");
                } else {
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
                                zan = true;
                                tvZan.setSelected(true);

                                String s = tvZan.getText().toString();
                                int i = Integer.parseInt(s) + 1;
                                tvZan.setText(String.valueOf(i));
                            }
                            onToast(standardResponse.getMessage());
                        }
                    }, String.valueOf(id), mAuthorization);
                }
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

    @OnClick(R.id.send_tv)
    public void onViewClicked() {
        String content = etContent.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            FindModel.getFindModel().callReplay(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                pageIndex = 1;
                                initData();
                                etContent.setText("");
                            } else {
                                ToastUtil.showShort(PengYouQuanDetailActivity.this, standardResponse.getMessage());
                            }
                        }
                    }, PengYouQuanDetailActivity.this), id
                    , "0", content, mAuthorization);
        } else {
            ToastUtil.showShort(PengYouQuanDetailActivity.this, "评论内容不能为空！");
        }
    }


    private void onToast(String s) {
        ToastUtil.show(this, s, Toast.LENGTH_SHORT);
    }
}
