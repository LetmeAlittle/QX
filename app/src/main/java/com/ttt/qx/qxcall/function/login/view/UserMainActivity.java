package com.ttt.qx.qxcall.function.login.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.UserMainPicAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.AddressSetSuccess;
import com.ttt.qx.qxcall.eventbus.RefreshHomeCurrentCategory;
import com.ttt.qx.qxcall.eventbus.UserInfoModifyed;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.home.view.FansActivity;
import com.ttt.qx.qxcall.function.home.view.FollowActivity;
import com.ttt.qx.qxcall.function.home.view.VisitorActivity;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.widget.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;


/**
 * 用户主页
 * Created by wyd on 2017/7/19.
 */
public class UserMainActivity extends BaseActivity {
    @BindView(R.id.user_img_recycler)
    RecyclerView user_img_recycler;
    @BindView(R.id.eidt_info_ll)
    LinearLayout eidt_info_ll;
    @BindView(R.id.user_img_recycler_rl)
    RelativeLayout user_img_recycler_rl;
    @BindView(R.id.more_rl)
    RelativeLayout more_rl;
    @BindView(R.id.yunyin_ll)
    LinearLayout yunyin_ll;
    @BindView(R.id.send_message_ll)
    LinearLayout send_message_ll;
    @BindView(R.id.user_head_icon_iv)
    CircleImageView user_head_icon_iv;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.member_level_tv)
    TextView member_level_tv;
    @BindView(R.id.fensi_num_tv)
    TextView fensi_num_tv;
    @BindView(R.id.guanzhu_name_tv)
    TextView guanzhu_name_tv;
    @BindView(R.id.guanzhu_num_tv)
    TextView guanzhu_num_tv;
    @BindView(R.id.zan_num_tv)
    TextView zan_num_tv;
    @BindView(R.id.fangke_num_tv)
    TextView fangke_num_tv;
    @BindView(R.id.member_id_tv)
    TextView member_id_tv;
    @BindView(R.id.sex_ll)
    LinearLayout sex_ll;
    @BindView(R.id.sounds_ll)
    LinearLayout sounds_ll;
    @BindView(R.id.paly_status_iv)
    ImageView paly_status_iv;
    @BindView(R.id.vip_flag_iv)
    ImageView vip_flag_iv;
    @BindView(R.id.audio_time_tv)
    TextView audio_time_tv;
    @BindView(R.id.sex_iv)
    ImageView sex_iv;
    @BindView(R.id.no_pic_iv)
    ImageView no_pic_iv;
    @BindView(R.id.no_pic_tip_tv)
    TextView no_pic_tip_tv;
    @BindView(R.id.age_tv)
    TextView age_tv;
    @BindView(R.id.user_nick_name_tv)
    TextView user_nick_name_tv;
    @BindView(R.id.sign_name_tv)
    TextView sign_name_tv;
    @BindView(R.id.location_tv)
    TextView location_tv;
    @BindView(R.id.member_cate_name_tv)
    TextView member_cate_name_tv;
    @BindView(R.id.flow_tag_layout)
    FlowLayout flow_tag_layout;
    private Context mContext;
    private UserMainPicAdapter userMainPicAdapter;
    private Integer id;
    private String accid;
    private UserBean mUserBean;
    private String Authorization;
    private UserDetailInfo.DataBean mInfoData;
    //当前页面  用户是否已经处于关注状态
    private boolean follow = false;
    private int fans_num;
    private MediaPlayer mPlayer = new MediaPlayer();
    private String mSoundFile;
    private boolean firstEnter = true;
    private int _ffffff;
    private float tagSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        ButterKnife.bind(this);
        initData();
//        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.send_message_ll, R.id.yunyin_ll, R.id.more_rl, R.id.guanzhu_name_tv, R.id.eidt_info_ll
            , R.id.user_info_rl, R.id.visitor_rl, R.id.guan_zhu_rl, R.id.fans_rl, R.id.sounds_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.yunyin_ll:
                StealListenModel.getStealListenModel().isAllowTalk(new Subscriber<StandardResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(StandardResponse standardResponse) {
                        if (standardResponse.getStatus_code() == 200) {
                            //调起拨打界面。
                            AVChatProfile.getInstance().setAVChatting(true);
                            AVChatActivity.launch(getApplication(), accid, AVChatType.AUDIO.getValue(), AVChatActivity.FROM_INTERNAL);
                        } else {
                            onToast(standardResponse.getMessage());
                        }
                    }
                }, String.valueOf(id), Authorization);
                break;
            case R.id.eidt_info_ll:
                //跳转到编辑资料界面。
                Intent intent = new Intent(this, UserMainEditActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.sounds_ll:
                paly_status_iv.setBackgroundResource(R.mipmap.audio_playing_iv);
                if (mPlayer != null) {
                    mPlayer.start();
                } else {
                    mPlayer = new MediaPlayer();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer player) {
                            paly_status_iv.setBackgroundResource(R.mipmap.audio_pause_iv);
                            mPlayer.release();
                            mPlayer = null;
                        }
                    });
                    try {
                        mPlayer.setDataSource(mContext, Uri.parse(mSoundFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mPlayer.start();
                        }
                    });
                }
                break;
            case R.id.send_message_ll:
                StealListenModel.getStealListenModel().isAllowTalk(new Subscriber<StandardResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(StandardResponse standardResponse) {
                        if (standardResponse.getStatus_code() == 200) {
                            List<String> accounts = new ArrayList<String>();
                            accounts.add(String.valueOf(id));
                            NIMClient.getService(UserService.class).fetchUserInfo(accounts).setCallback(new RequestCallback<List<NimUserInfo>>() {
                                @Override
                                public void onSuccess(List<NimUserInfo> infos) {
                                    //点击打开与该用户的聊天界面
                                    NimUIKit.startP2PSession(mContext, accid);
                                }

                                @Override
                                public void onFailed(int i) {
                                    Log.i(UserMainActivity.class.getSimpleName(), "拉取用户信息失败");
                                }

                                @Override
                                public void onException(Throwable throwable) {
                                    Log.i(UserMainActivity.class.getSimpleName(), throwable.getMessage());
                                }
                            });
                        } else {
                            onToast(standardResponse.getMessage());
                        }
                    }
                }, String.valueOf(id), Authorization);
                break;
            case R.id.more_rl:
                //显示更多对话框
                showMoreDialog();
                break;
            case R.id.guanzhu_name_tv:
                if (!follow)
                {
                    HomeModel.getHomeModel().followUser(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                follow = true;
                                onToast(standardResponse.getMessage());
                                guanzhu_name_tv.setText("取消关注");
                                fans_num++;
                                guanzhu_num_tv.setText(String.valueOf(fans_num));
                            } else {
                                onToast(standardResponse.getMessage());
                            }
                        }
                    }, UserMainActivity.this), String.valueOf(id), Authorization);
                } else

                {//如果已经关注
                    HomeModel.getHomeModel().unfollowUser(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                follow = false;
                                onToast(standardResponse.getMessage());
                                guanzhu_name_tv.setText("关注");
                                fans_num--;
                                guanzhu_num_tv.setText(String.valueOf(fans_num));
                            } else {
                                onToast(standardResponse.getMessage());
                            }
                        }
                    }, UserMainActivity.this), String.valueOf(id), Authorization);
                }

                break;
            case R.id.user_info_rl:
                //用户详细信息页
//                IntentUtil.jumpIntent(this, UserInfoActivity.class);
                break;
            case R.id.visitor_rl:
                Intent tenVisitor = new Intent(this, VisitorActivity.class);
                tenVisitor.putExtra("id", id);
                startActivity(tenVisitor);

                break;
            case R.id.guan_zhu_rl:
                Intent ten = new Intent(this, FollowActivity.class);
                ten.putExtra("id", id);
                startActivity(ten);

                break;
            case R.id.fans_rl:
                Intent tenFans = new Intent(this, FansActivity.class);
                tenFans.putExtra("id", id);
                startActivity(tenFans);
                break;
        }
    }

    /**
     * 给出提示对话框
     */

    private void showMoreDialog() {
        Dialog dialog = new Dialog(this, R.style.dialogStyle);
        View view = View.inflate(this, R.layout.user_main_more_dialog, null);
        view.findViewById(R.id.report_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //举报
                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtra("id", id);
                mContext.startActivity(intent);
            }
        });
        view.findViewById(R.id.pull_back_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //拉黑
                HomeModel.getHomeModel().blackFans(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                    @Override
                    public void onNext(StandardResponse standardResponse) {
                        onToast(standardResponse.getMessage());
                    }
                }, mContext), String.valueOf(id), Authorization);
            }
        });
        view.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //取消
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(view, params);
        dialog.show();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        _ffffff = getResources().getColor(R.color._ffffff);
        tagSize = getResources().getInteger(R.integer.fm_anim_i12);
        mContext = this;
        Intent intent = getIntent();
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        String token = mUserBean.getToken();
        Authorization = "Bearer " + mUserBean.getToken();
        id = intent.getIntExtra("id", -1);
        accid = String.valueOf(id);
        if (id == mUserBean.getUserId() || mUserBean.getUserId().equals(id)) {//如果当前点击条目就是登录用户自己 登录NimUIKit 同时缓存账户id
            guanzhu_name_tv.setVisibility(View.GONE);
            more_rl.setVisibility(View.INVISIBLE);
            NimUIKit.doLogin(new LoginInfo(mUserBean.getWy_acid(), mUserBean.getWy_token()), new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
                    //登录成功
                    String account = param.getAccount();
                }

                @Override
                public void onFailed(int code) {
                    //登录失败
                }

                @Override
                public void onException(Throwable exception) {
                    //登录异常
                }

            });
            DemoCache.setAccount(String.valueOf(accid));
        } else {
            guanzhu_name_tv.setVisibility(View.VISIBLE);
        }
        setUserInfo();
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo() {
        //根据id 获取当前用户信息
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo info) {
                if (info.getStatus_code() == 200) {
                    mInfoData = info.getData();
                    initView();
                } else {
                    onToast(info.getMessage());
                }
            }
        }, this), String.valueOf(id), Authorization);
    }

    /**
     * 初始化view
     */
    private void initView() {
        paly_status_iv.setBackgroundResource(R.mipmap.audio_pause_iv);
        //如果当前点击用户就是用户本身只保留编辑资料按钮
        if (mUserBean.getUserId() == id || mUserBean.getUserId().equals(id)) {
            send_message_ll.setVisibility(View.GONE);
            yunyin_ll.setVisibility(View.GONE);
            eidt_info_ll.setVisibility(View.VISIBLE);
        } else {
            send_message_ll.setVisibility(View.VISIBLE);
            yunyin_ll.setVisibility(View.VISIBLE);
            eidt_info_ll.setVisibility(View.GONE);
        }
        //初始化相关控件
        if (mInfoData != null) {
            setUserHeadIcon(mInfoData.getMember_avatar());
            if (mInfoData.getLevel() == 0) {
                vip_flag_iv.setVisibility(View.GONE);
                member_level_tv.setVisibility(View.INVISIBLE);
            } else {
                vip_flag_iv.setVisibility(View.VISIBLE);
//                member_level_tv.setVisibility(View.VISIBLE);
//                member_level_tv.setText("VIP" + mInfoData.getLevel());
            }
            user_nick_name_tv.setText(mInfoData.getNick_name());
            //设置关注状态
            if (mInfoData.getIs_fans() == 0) {
                follow = false;
                guanzhu_name_tv.setText("关注");
            } else {
                follow = true;
                guanzhu_name_tv.setText("取消关注");
            }
            mSoundFile = mInfoData.getSound_file();
            initMediaPlayer();
            sign_name_tv.setText(mInfoData.getMember_signature());
            member_cate_name_tv.setText(mInfoData.getMember_cate_name());
            fans_num = mInfoData.getFans_num();
            fensi_num_tv.setText(String.valueOf(mInfoData.getFans_num()));
            guanzhu_num_tv.setText(String.valueOf(mInfoData.getFans_num()));
            zan_num_tv.setText(String.valueOf(mInfoData.getZan_num()));
            fangke_num_tv.setText(String.valueOf(mInfoData.getVisitor_num()));
            member_id_tv.setText("ID：" + String.valueOf(mInfoData.getId()));
            //标签处理
            List<UserDetailInfo.DataBean.MemberTagBean> member_tag = mInfoData.getMember_tag();
            if (member_tag.size() > 0) {
                flow_tag_layout.setVisibility(View.VISIBLE);
            }
            flow_tag_layout.removeAllViews();
            for (UserDetailInfo.DataBean.MemberTagBean memberTagBean : member_tag) {
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 35;
                TextView textView = new TextView(mContext);
                textView.setTextColor(_ffffff);
                textView.setTextSize(tagSize);
                textView.setText(memberTagBean.getText());
                textView.setLayoutParams(layoutParams);
                flow_tag_layout.addView(textView);
            }
            //地址信息
            String province = mInfoData.getMember_province();
            if (province != null && !province.equals("")) {
                location_tv.setText(province + "   " + mInfoData.getMember_city());
            } else {
                location_tv.setText("暂无地址信息");
            }
            if (mInfoData.getMember_sex().equals("1")) {
                sex_ll.setBackgroundResource(R.drawable.fill_bg_5d9cfb_yj);
                sex_iv.setBackgroundResource(R.mipmap.boy_iv);
            } else {
                sex_ll.setBackgroundResource(R.drawable.fill_bg_ff79a7_yj);
                sex_iv.setBackgroundResource(R.mipmap.girl_iv);
            }
            String member_age = mInfoData.getMember_age();
            if (null == member_age || member_age.equals("")|| member_age.equals(" ")|| member_age.equals("0")) {
                age_tv.setText("未知");
            } else {
                age_tv.setText(mInfoData.getMember_age());
            }
            List<String> imgs = new ArrayList<>();
            imgs.add(mInfoData.getMember_img_1());
            imgs.add(mInfoData.getMember_img_2());
            imgs.add(mInfoData.getMember_img_3());
            imgs.add(mInfoData.getMember_img_4());
            boolean no_pic = true;
            for (String img : imgs) {
                if (!img.equals("")) {
                    no_pic = false;
                    break;
                }
            }
            if (no_pic) {
                no_pic_iv.setVisibility(View.VISIBLE);
                no_pic_tip_tv.setVisibility(View.VISIBLE);
                user_img_recycler_rl.setVisibility(View.GONE);
            } else {
                no_pic_iv.setVisibility(View.GONE);
                no_pic_tip_tv.setVisibility(View.GONE);
                user_img_recycler_rl.setVisibility(View.VISIBLE);
                if (userMainPicAdapter == null) {
                    userMainPicAdapter = new UserMainPicAdapter(this, imgs, false);
                    LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    user_img_recycler.setLayoutManager(layout);
                    user_img_recycler.setAdapter(userMainPicAdapter);
                } else {
                    userMainPicAdapter.setImgs(imgs);
                    userMainPicAdapter.notifyDataSetChanged();
                }
            }
        }
        //设置divider
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
//        user_img_recycler.addItemDecoration(dividerItemDecoration);
    }

    private void initMediaPlayer() {
        if (mSoundFile != null && !mSoundFile.equals("")) {
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        paly_status_iv.setBackgroundResource(R.mipmap.audio_pause_iv);
                        mPlayer.release();
                        mPlayer = null;
                    }
                });
                mPlayer.setDataSource(mContext, Uri.parse(mSoundFile));
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        audio_time_tv.setText(String.valueOf(mPlayer.getDuration() / 1000) + "''");
                    }
                });
                sounds_ll.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUserHeadIcon(String avatar) {
        Glide.clear(user_head_icon_iv);
        Glide.with(UserMainActivity.this)
                .load(avatar)
                .skipMemoryCache(true)//跳过内部缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_head_icon_iv);
    }

    @Subscribe
    public void onEventUserInfoModifyed(UserInfoModifyed userInfoModifyed) {
        //重新获取用户详细信息 刷新视图
        setUserInfo();
        if (id == mUserBean.getUserId() || mUserBean.getUserId().equals(id)) {//如果用户从用户列表中进入 对自己的信息进行了修改 同时刷新首页列表
            EventBus.getDefault().post(new RefreshHomeCurrentCategory());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            paly_status_iv.setBackgroundResource(R.mipmap.audio_pause_iv);
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Subscribe
    public void onEventAddressSetSuccess(AddressSetSuccess addressSetSuccess) {
        //地址设置成功
        location_tv.setText(addressSetSuccess.address);
    }

    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }
}
