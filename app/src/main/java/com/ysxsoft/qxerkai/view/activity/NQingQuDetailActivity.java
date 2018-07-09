package com.ysxsoft.qxerkai.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dialog.GiftSendDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.ObserverMap;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanDetailAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import rx.Subscriber;

public class NQingQuDetailActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final int UP_TOP = 0;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.tv_public_titlebar_left)
    TextView tvPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_left_2)
    ImageView ivPublicTitlebarLeft2;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_right_1)
    ImageView ivPublicTitlebarRight1;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.iv_public_titlebar_right_2)
    ImageView ivPublicTitlebarRight2;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.ll_public_titlebar)
    LinearLayout llPublicTitlebar;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.send_tv)
    TextView sendTv;
    @BindView(R.id.comment_et)
    EditText commentEt;
    @BindView(R.id.send_rl)
    RelativeLayout sendRl;

    //headerView
    private View headView;
    private TextView tvNick, tvTime, tvContent, tvLook, tvZan, tvPing, tvReplyNum;
    private de.hdodenhof.circleimageview.CircleImageView logo;
    private cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout ninePhotoLayout;
    private boolean isLiked;
    private int tid;//帖子ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nqing_qu_detail);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            tid = getIntent().getIntExtra("tid", 0);//帖子id
        }
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        setListener();
        initData();
    }

    /**
     * 跳转至帖子详情页
     *
     * @param activity
     * @param tid         帖子id
     * @param requestCode 请求码
     */
    public static void start(Activity activity, int tid, int requestCode) {
        Intent intent = new Intent(activity, NQingQuDetailActivity.class);
        intent.putExtra("tid", tid);
        activity.startActivityForResult(intent, requestCode);
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
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentAdapter(R.layout.activity_pengyouquan_detail_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        headView = getLayoutInflater().inflate(R.layout.header_xiao_qing_qu_detail, (ViewGroup) swipeTarget.getParent(), false);
        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
        sendTv.setClickable(false);
    }

    /**
     * 填充详情页 基础信息
     */
    private void fillHeadView(GetCardDetailResponse.DataBeanX.TopBean topBean) {
        Glide.with(NQingQuDetailActivity.this)
                .load(topBean.getAvatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(logo);
        tvNick.setText(topBean.getNick_name());
        tvTime.setText(topBean.getDates());
        tvReplyNum.setText("全部评论：" + topBean.getCom_num());
        tvPing.setText(topBean.getCom_num() + "");
        tvZan.setText(topBean.getLikes() + "");
        tvLook.setText(topBean.getLooks() + "");
        tvContent.setText(topBean.getContent());
        if ("1".equals(topBean.getIs_like())) {
            isLiked = true;
        } else {
            isLiked = false;
        }
        tvZan.setSelected(isLiked);

        List<GetCardDetailResponse.DataBeanX.ImageBean> images = topBean.getImgs();
        if (images != null) {
            int size = images.size();
            ArrayList<String> imgs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                imgs.add(images.get(i).getImg());
            }
            ninePhotoLayout.setData(imgs);
            ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
                @Override
                public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
                    File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
                    BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(NQingQuDetailActivity.this)
                            .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
                    photoPreviewIntentBuilder
                            .previewPhotos((ArrayList<String>) models).currentPosition(position); // 当前预览图片的索引
                    NQingQuDetailActivity.this.startActivity(photoPreviewIntentBuilder.build());
                }
            });
        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suid = "" + topBean.getUser_id();//发表人id
                if (suid.equals(DBUtils.getUserId())) {//本人
                    IntentUtil.jumpIntent(NQingQuDetailActivity.this, NPersonCenterActivity.class);
                } else {
                    if ("".equals(suid)) {
                        return;
                    }
                    int id = Integer.parseInt(suid);
                    NQingQuDetailActivity.this.startActivity(new Intent(NQingQuDetailActivity.this, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", suid));//查看好友资料
                }
            }
        });
    }

    private void initHeadView(View headView) {
        ninePhotoLayout = (BGANinePhotoLayout) headView.findViewById(R.id.snpl_moment_add_photos);
        tvNick = (TextView) headView.findViewById(R.id.name);
        tvTime = (TextView) headView.findViewById(R.id.time);
        tvContent = (TextView) headView.findViewById(R.id.content);
        tvLook = (TextView) headView.findViewById(R.id.lookNum);
        tvZan = (TextView) headView.findViewById(R.id.zanNum);
        tvPing = (TextView) headView.findViewById(R.id.pingNum);
        tvReplyNum = (TextView) headView.findViewById(R.id.replyNum);
        logo = (de.hdodenhof.circleimageview.CircleImageView) headView.findViewById(R.id.logo);
    }

    private void initData() {
        getDetail();
    }

    private void setListener() {
        tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked) {
                    ToastUtils.showToast(NQingQuDetailActivity.this, "已点赞", 1);
                } else {
                    like(tid);//详情页点赞
                }
            }
        });
        commentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = commentEt.getText().toString().trim();
                if (!content.equals("")) {
                    sendTv.setClickable(true);
                    sendTv.setTextColor(getResources().getColor(R.color._ffffff));
                    sendTv.setBackgroundResource(R.drawable.fill_bg_9b5ada_yj);
                } else {
                    sendTv.setClickable(false);
                    sendTv.setTextColor(getResources().getColor(R.color._acaeb1));
                    sendTv.setBackgroundResource(R.drawable.side_bg_bfbfbf_yj);
                }
            }
        });
    }

    private int pageIndex = 1;
    private int pageTotal = 1;
    private CommentAdapter adapter;

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
        String content = commentEt.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            submitComment();
        } else {
            ToastUtil.showShort(NQingQuDetailActivity.this, "评论内容不能为空！");
        }
    }

    /**
     * 评论列表
     */
    private class CommentAdapter extends BaseQuickAdapter<GetCardDetailResponse.DataBeanX.ListBean.DataBean, BaseViewHolder> {
        public CommentAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, GetCardDetailResponse.DataBeanX.ListBean.DataBean item) {
            helper.setText(R.id.tv_nickname, StringUtils.convert(item.getNick_name()));
            helper.setText(R.id.tv_time, StringUtils.convert(item.getDates()));
            helper.setText(R.id.tv_content, StringUtils.convert(item.getContent()));
            Glide.with(mContext)
                    .load(item.getAvatar())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into((ImageView) helper.getView(R.id.iv_tou));

            ImageView logo = helper.getView(R.id.iv_tou);
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String suid = "" + item.getUser_id();//发表人id
                    if (suid.equals(DBUtils.getUserId())) {//本人
                        IntentUtil.jumpIntent(mContext, NPersonCenterActivity.class);
                    } else {
                        if ("".equals(suid)) {
                            return;
                        }
                        int id = Integer.parseInt(suid);
                        mContext.startActivity(new Intent(mContext, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", suid));//查看好友资料
                    }
                }
            });
        }
    }

    /**
     * 获取小情趣列表
     */
    private void getDetail() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DBUtils.getUserId());
        map.put("tid", tid + "");//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        map.put("page", pageIndex + "");

        RetrofitTools.getCardDetail(map).subscribe(new ResponseSubscriber<GetCardDetailResponse>() {
            @Override
            public void onSuccess(GetCardDetailResponse getCardDetailResponse, int code, String msg) {
                multipleStatusView.hideLoading();
                if (code == 200) {
                    if (getCardDetailResponse.getData() == null) return;
                    //填充顶部
                    GetCardDetailResponse.DataBeanX.TopBean topBean = getCardDetailResponse.getData().getTop();
                    if (topBean != null) {
                        fillHeadView(topBean);
                    }
                    //填充下方评论列表
                    GetCardDetailResponse.DataBeanX.ListBean commonList = getCardDetailResponse.getData().getList();
                    if (commonList == null) return;
                    List<GetCardDetailResponse.DataBeanX.ListBean.DataBean> data = commonList.getData();
                    if (pageIndex == 1) {
                        adapter.setNewData(data);
                        pageTotal = getCardDetailResponse.getData().getList().getLast_page();
                    } else {
                        adapter.addData(data);
                    }
                } else {
                    if (pageIndex > 1) {
                        pageIndex--;
                    }
                    ToastUtil.showToast(NQingQuDetailActivity.this, msg);
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

    /**
     * 点赞
     *
     * @param tid
     */
    private void like(int tid) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());
        map.put("cid", "" + tid);//列表的id
        map.put("type", "1");//	1帖子 2评论

        RetrofitTools.cardLike(map)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        if (code == 200) {
                            tvZan.setSelected(true);
                            ObserverMap.notifyAllPage();//通知第二页
                        } else {
                            ToastUtil.showToast(NQingQuDetailActivity.this, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    /**
     * 评论
     */
    private void submitComment() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());
        map.put("content", commentEt.getText().toString());//列表的id
        map.put("tid", "" + tid);//	1帖子 2评论

        RetrofitTools.submitCardComment(map)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        if (code == 200) {
                            getDetail();
                            ObserverMap.notify("MainActivity");
                        } else {
                            ToastUtil.showToast(NQingQuDetailActivity.this, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

}
