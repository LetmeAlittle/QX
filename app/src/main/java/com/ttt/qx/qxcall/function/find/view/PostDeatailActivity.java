package com.ttt.qx.qxcall.function.find.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.DetailReplayAdapter;
import com.ttt.qx.qxcall.adapter.FindPicAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.GiftSendDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.DealTimeUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 王亚东 on 2017/10/6.
 */

public class PostDeatailActivity extends BaseActivity {
    @BindView(R.id.publish_content_tv)
    TextView publish_content_tv;
    @BindView(R.id.user_head_iv)
    CircleImageView user_head_iv;
    @BindView(R.id.user_nick_name_tv)
    TextView user_nick_name_tv;
    @BindView(R.id.publish_time_tv)
    TextView publish_time_tv;
    @BindView(R.id.user_address_tv)
    TextView user_address_tv;
    @BindView(R.id.browse_num_tv)
    TextView browse_num_tv;
    @BindView(R.id.comment_num_tv)
    TextView comment_num_tv;
    @BindView(R.id.gift_num_tv)
    TextView gift_num_tv;
    @BindView(R.id.zan_iv)
    ImageView zan_iv;
    @BindView(R.id.zan_num_tv)
    TextView zan_num_tv;
    @BindView(R.id.comment_rl)
    RelativeLayout comment_rl;
    @BindView(R.id.public_pic_rv)
    RecyclerView public_pic_rv;
    @BindView(R.id.comment_replay_rv)
    RecyclerView comment_replay_rv;

    @BindView(R.id.send_tv)
    TextView send_tv;
    @BindView(R.id.comment_et)
    EditText comment_et;
    private int i16;
    //当前说说id
    private int id;
    private UserDao mUserDao;
    private UserBean mUserBean;
    private String mAuthorization;
    private DynamicDetail.DataBean data;
    private boolean zan = false;
    //说说 评论以及评论的回复数据
    private DetailReplayAdapter detailReplayAdapter;
    //判断当前用户是要进行对回复的回复 还是要对说说的回复
    private boolean reply = false;
    private DynamicDetail.DataBean.ReplyListBean mReplyListBean;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        send_tv.setClickable(false);
        comment_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = comment_et.getText().toString().trim();
                if (!content.equals("")) {
                    send_tv.setClickable(true);
                    send_tv.setTextColor(getResources().getColor(R.color._ffffff));
                    send_tv.setBackgroundResource(R.drawable.fill_bg_9b5ada_yj);
                } else {
                    send_tv.setClickable(false);
                    send_tv.setTextColor(getResources().getColor(R.color._acaeb1));
                    send_tv.setBackgroundResource(R.drawable.side_bg_bfbfbf_yj);
                }
            }
        });
        //设置控件
        publish_content_tv.setText(data.getContent());
        Glide.with(this)
                .load(data.getMember_avatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_head_iv);
        user_nick_name_tv.setText(data.getNick_name());
        user_address_tv.setText(data.getMember_area());
        browse_num_tv.setText(String.valueOf(data.getClick_num()) + "次");
        publish_time_tv.setText(data.getCreate_at());
        comment_num_tv.setText(String.valueOf(data.getReply_num()));
        gift_num_tv.setText(String.valueOf(data.getGift_num()));
        zan_num_tv.setText(String.valueOf(data.getZan_num()));
        if (data.getIs_zan() == 0) {
            zan = false;
            zan_iv.setBackgroundResource(R.mipmap.wei_zan_iv);
        } else {
            zan = true;
            zan_iv.setBackgroundResource(R.mipmap.yi_zan_iv);
        }
        //设置 说说图片
        List<String> img_list = data.getImg_list();
        GridLayoutManager gridLayoutManager;
        if (img_list != null && img_list.size() > 0) {
            FindPicAdapter findPicAdapter;
            if (img_list.size() == 1) {
                gridLayoutManager = new GridLayoutManager(context, 1);
                findPicAdapter = new FindPicAdapter(context, img_list, "PostDeatailActivity", 1);
            } else if (img_list.size() == 2) {
                gridLayoutManager = new GridLayoutManager(context, 2);
                findPicAdapter = new FindPicAdapter(context, img_list, "PostDeatailActivity", 2);
            } else {
                gridLayoutManager = new GridLayoutManager(context, 3);
                findPicAdapter = new FindPicAdapter(context, img_list, "PostDeatailActivity", 3);
            }
            public_pic_rv.setLayoutManager(gridLayoutManager);
            public_pic_rv.setAdapter(findPicAdapter);
        }
        //回复评论显示
        List<DynamicDetail.DataBean.ReplyListBean> reply_list = data.getReply_list();
        if (reply_list != null && reply_list.size() > 0) {
        } else {
            reply_list = new ArrayList<>();
        }
        comment_replay_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        detailReplayAdapter = new DetailReplayAdapter(this, reply_list);
        comment_replay_rv.setAdapter(detailReplayAdapter);
        detailReplayAdapter.setOnItemLongClickListener(new DetailReplayAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongLick(View view, int position) {
                DynamicDetail.DataBean.ReplyListBean replyListBean = detailReplayAdapter.getReply_list().get(position);
                if (replyListBean.getMember_id() != mUserBean.getUserId()) {//如果@的不是当前登录用户自己
                    reply = true;
                    mReplyListBean = replyListBean;
                    comment_et.setHint(mUserBean.getNick_name() + "回复" + replyListBean.getMember_info().getNick_name());
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        i16 = getResources().getInteger(R.integer.i16);
        context = this;
        //首先获取 当前评论id
        id = getIntent().getIntExtra("id", -1);
        mUserDao = new UserDao();
        mUserBean = mUserDao.queryFirstData();
        mAuthorization = "Bearer " + mUserBean.getToken();
        FindModel.getFindModel().callDetail(new ProgressSubscribe<>(new SubScribeOnNextListener<DynamicDetail>() {
            @Override
            public void onNext(DynamicDetail detail) {
                if (detail.getStatus_code() == 200) {
                    data = detail.getData();
                    initView();
                } else {
                    onToast(detail.getMessage());
                }
            }
        }, this), String.valueOf(id), mAuthorization);

    }

    @OnClick({R.id.top_back_rl, R.id.send_tv, R.id.user_head_iv, R.id.zan_iv, R.id.say_comment_ll, R.id.gift_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.user_head_iv:
                Intent intent = new Intent(context, UserMainActivity.class);
                intent.putExtra("id",data.getMember_id() );//用户id
                intent.putExtra("accid", data.getMember_id() );
                context.startActivity(intent);
                break;
            case R.id.say_comment_ll:
                comment_et.setFocusable(true);
                comment_et.setFocusableInTouchMode(true);
                comment_et.requestFocus();
                comment_et.setHint("发表评论");
                reply = false;
                break;
            case R.id.gift_ll:
                FindModel.getFindModel().getGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftList>() {
                    @Override
                    public void onNext(GiftList giftList) {
                        if (giftList.getStatus_code() == 200) {
                            GiftSendDialog.showBottomDialog((PostDeatailActivity) context, giftList.getData(), new GiftSendDialog.OnComponentClickListener() {
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
                                                String s = gift_num_tv.getText().toString();
                                                int i = Integer.parseInt(s);
                                                i++;
                                                gift_num_tv.setText(String.valueOf(i));
                                            } else {
                                                onToast(response.getMessage());
                                            }
                                        }
                                    }, context), gift_id, String.valueOf(id), mAuthorization);
                                }
                            });
                        } else {
                            onToast(giftList.getMessage());
                        }
                    }
                }, context));
                break;
            case R.id.zan_iv:
                if (zan) {
                    onToast("已点过赞！");
                } else {
                    FindModel.getFindModel().callDianZan(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                zan = true;
                                zan_iv.setBackgroundResource(R.mipmap.yi_zan_iv);
                            }
                            onToast(standardResponse.getMessage());
                        }
                    }, this), String.valueOf(id), mAuthorization);
                }
                break;
            case R.id.send_tv:
                String content = comment_et.getText().toString().trim();
                if (!content.equals("")) {
                    FindModel.getFindModel().callReplay(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                @Override
                                public void onNext(StandardResponse standardResponse) {
                                    if (standardResponse.getStatus_code() == 200) {
                                        //用发表成功后处理 将当前发表的数据添加到评论列表中
                                        DynamicDetail.DataBean.ReplyListBean replyListBean = new DynamicDetail.DataBean.ReplyListBean();
                                        replyListBean.setId(0);//新增的默认为0 不可被自己回复
                                        replyListBean.setContent(content);
                                        replyListBean.setCreate_at(DealTimeUtil.dealTime2(System.currentTimeMillis()));
                                        replyListBean.setSay_id(id);
                                        replyListBean.setMember_id(mUserBean.getUserId());
                                        DynamicDetail.DataBean.ReplyListBean.MemberInfoBean memberInfoBean
                                                = new DynamicDetail.DataBean.ReplyListBean.MemberInfoBean();
                                        memberInfoBean.setId(mUserBean.getUserId());
                                        memberInfoBean.setNick_name(mUserBean.getNick_name());
                                        memberInfoBean.setAvatar(mUserBean.getMember_avatar());
                                        replyListBean.setMember_info(memberInfoBean);
                                        if (reply) {
                                            replyListBean.setTo_member_id(mReplyListBean.getMember_id());
                                            DynamicDetail.DataBean.ReplyListBean.ToMemberInfoBean toMemberInfoBean
                                                    = new DynamicDetail.DataBean.ReplyListBean.ToMemberInfoBean();
                                            toMemberInfoBean.setAvatar(mReplyListBean.getMember_info().getAvatar());
                                            toMemberInfoBean.setId(mReplyListBean.getMember_id());
                                            toMemberInfoBean.setNick_name(mReplyListBean.getMember_info().getNick_name());
                                            replyListBean.setTo_member_info(toMemberInfoBean);
                                            reply = false;
                                        } else {
                                            replyListBean.setTo_member_id(0);
                                        }
                                        detailReplayAdapter.addReply(replyListBean);
                                        detailReplayAdapter.notifyDataSetChanged();
                                        comment_et.setText("");
                                    }
                                    onToast(standardResponse.getMessage());
                                }
                            }, PostDeatailActivity.this), reply ? String.valueOf(mReplyListBean.getSay_id()) : String.valueOf(id)
                            , reply ? String.valueOf(mReplyListBean.getId()) : "0", content, mAuthorization);
                } else {
                    onToast("评论内容不能为空！");
                }
                break;
        }

    }

    private void onToast(String s) {
        ToastUtil.show(this, s, Toast.LENGTH_SHORT);
    }
}
