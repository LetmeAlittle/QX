package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.ImgZoomFragment;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.home.view.FansActivity;
import com.ttt.qx.qxcall.function.home.view.FollowActivity;
import com.ttt.qx.qxcall.function.home.view.VisitorActivity;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.widget.FlowLayout;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.RoundAngleImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * 获取用户资料
 */
public class NZhiLiaoActivity extends NBaseActivity implements View.OnClickListener {

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
    @BindView(R.id.ll_liwubang)
    LinearLayout llLiwubang;
    @BindView(R.id.ll_liaorenqu)
    LinearLayout llLiaorenqu;
    @BindView(R.id.ll_shouhu)
    LinearLayout llShouhu;
    @BindView(R.id.tv_xiangche_more)
    TextView tvXiangcheMore;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_fsnum)
    TextView tvFsnum;
    @BindView(R.id.tv_gznum)
    TextView tvGznum;
    @BindView(R.id.tv_dznum)
    TextView tvDznum;
    @BindView(R.id.tv_fknum)
    TextView tvFknum;
    @BindView(R.id.iv_image1)
    RoundAngleImageView ivImage1;
    @BindView(R.id.iv_image2)
    RoundAngleImageView ivImage2;
    @BindView(R.id.iv_image3)
    RoundAngleImageView ivImage3;
    @BindView(R.id.iv_image4)
    RoundAngleImageView ivImage4;
    @BindView(R.id.paly_status_iv)
    ImageView paly_status_iv;
    @BindView(R.id.audio_time_tv)
    TextView audio_time_tv;
    @BindView(R.id.sounds_ll)
    LinearLayout sounds_ll;
    @BindView(R.id.flow_tag_layout)
    FlowLayout flow_tag_layout;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_yuyin)
    LinearLayout llYuyin;
    @BindView(R.id.ll_liaotian)
    LinearLayout llLiaotian;
    @BindView(R.id.ll_price)
    TextView llPrice;
    @BindView(R.id.ll_fensi)
    LinearLayout llFensi;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.ll_fangke)
    LinearLayout llFangke;
    @BindView(R.id.tv_shouhunum)
    TextView tvShouhunum;
    @BindView(R.id.tv_gouliangnum)
    TextView tvGouliangnum;

    private int id;
    private String accid;
    private String Authorization;
    private UserDetailInfo.DataBean mInfoData;
    private String mSoundFile;
    private MediaPlayer mPlayer = new MediaPlayer();
    private int _ffffff;
    private float tagSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nzhi_liao);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
        accid = getIntent().getStringExtra("accid");
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
        tvPublicTitlebarCenter.setText("");
    }

    private void initView() {
        llLiwubang.setOnClickListener(this);
        llLiaorenqu.setOnClickListener(this);
        llShouhu.setOnClickListener(this);
        tvXiangcheMore.setOnClickListener(this);
        sounds_ll.setOnClickListener(this);
        llYuyin.setOnClickListener(this);
        llLiaotian.setOnClickListener(this);
        llFensi.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llFangke.setOnClickListener(this);
    }

    private void initViewData() {
        _ffffff = getResources().getColor(R.color._ffffff);
        tagSize = getResources().getInteger(R.integer.fm_anim_i10);
        //初始化相关控件
        if (mInfoData != null) {
            tvPublicTitlebarCenter.setText(mInfoData.getNick_name());
            llPrice.setText(mInfoData.getMember_price() + "砰砰豆/分钟");
            tvShouhunum.setText(mInfoData.getGuard());
            tvGouliangnum.setText(mInfoData.getDog());
            Glide.with(this).load(mInfoData.getMember_avatar()).into(civHead);
            if (mInfoData.getLevel() == 0) {
                ivVip.setVisibility(View.GONE);
                tvVip.setVisibility(View.VISIBLE);
            } else {
                ivVip.setVisibility(View.VISIBLE);
                tvVip.setVisibility(View.GONE);
//                member_level_tv.setVisibility(View.VISIBLE);
//                member_level_tv.setText("VIP" + mInfoData.getLevel());
            }
            tvNickname.setText(mInfoData.getNick_name());
            //设置关注状态
            if (mInfoData.getIs_fans() == 0) {
//                follow = false;
//                guanzhu_name_tv.setText("关注");
            } else {
//                follow = true;
//                guanzhu_name_tv.setText("取消关注");
            }
            mSoundFile = mInfoData.getSound_file();
            initMediaPlayer();
//            个性签名
//            sign_name_tv.setText(mInfoData.getMember_signature());
//            个人技能
//            member_cate_name_tv.setText(mInfoData.getMember_cate_name());
            tvFsnum.setText(String.valueOf(mInfoData.getFans_num()));
            tvGznum.setText(String.valueOf(mInfoData.getFans_num()));
            tvDznum.setText(String.valueOf(mInfoData.getZan_num()));
            tvFknum.setText(String.valueOf(mInfoData.getVisitor_num()));
            tvId.setText("ID：" + String.valueOf(mInfoData.getId()));
            //标签处理
            List<UserDetailInfo.DataBean.MemberTagBean> member_tag = mInfoData.getMember_tag();
            if (member_tag.size() > 0) {
                flow_tag_layout.setVisibility(View.VISIBLE);
            }
            flow_tag_layout.removeAllViews();
            for (UserDetailInfo.DataBean.MemberTagBean memberTagBean : member_tag) {
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 25;
                layoutParams.topMargin=10;
                layoutParams.bottomMargin=10;
                TextView textView = new TextView(NZhiLiaoActivity.this);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setBackgroundResource(R.drawable.activity_biaoqian_bg);
                GradientDrawable myGrad = (GradientDrawable)textView.getBackground();
                myGrad.setColor(Color.parseColor(memberTagBean.getColor()));
                textView.setTextSize(tagSize);
                textView.getPaint().setFakeBoldText(true);
                textView.setText(memberTagBean.getText());
                textView.setPadding(10,2,10,2);
                textView.setLayoutParams(layoutParams);
                flow_tag_layout.addView(textView);
            }
            //地址信息
            String province = mInfoData.getMember_province();
            if (province != null && !province.equals("")) {
                tvAddress.setText(province + "   " + mInfoData.getMember_city());
            } else {
                tvAddress.setText("暂无地址信息");
            }
            if (mInfoData.getMember_sex().equals("1")) {
                ivSex.setImageResource(R.mipmap.fragment_one_nan);
            } else {
                ivSex.setImageResource(R.mipmap.fragment_one_nv);
            }
            String member_age = mInfoData.getMember_age();
            if (null == member_age || member_age.equals("") || member_age.equals(" ") || member_age.equals("0") || member_age.equals("未知")) {
                tvAge.setText("未知");
            } else {
                tvAge.setText(mInfoData.getMember_age() + "岁");
            }
            ArrayList<RoundAngleImageView> ivImages=new ArrayList<>();
            ivImages.add(ivImage1);
            ivImages.add(ivImage2);
            ivImages.add(ivImage3);
            ivImages.add(ivImage4);
            for(int i=0;i<mInfoData.getXiangce().size();i++){
                if(i<4){
                    Glide.with(this).load(mInfoData.getXiangce().get(i).getIcon()).into(ivImages.get(i));
                }
            }
            if(mInfoData.getXiangce().size()>4){
                tvXiangcheMore.setVisibility(View.VISIBLE);
            }else {
                tvXiangcheMore.setVisibility(View.GONE);
            }
            ivImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ArrayList<String> photosStr=new ArrayList<>();
                        for(int i=0;i<mInfoData.getXiangce().size();i++){
                            photosStr.add(mInfoData.getXiangce().get(i).getIcon());
                        }
                        openImgLookBig(photosStr,0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ivImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ArrayList<String> photosStr=new ArrayList<>();
                        for(int i=0;i<mInfoData.getXiangce().size();i++){
                            photosStr.add(mInfoData.getXiangce().get(i).getIcon());
                        }
                        openImgLookBig(photosStr,1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ivImage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ArrayList<String> photosStr=new ArrayList<>();
                        for(int i=0;i<mInfoData.getXiangce().size();i++){
                            photosStr.add(mInfoData.getXiangce().get(i).getIcon());
                        }
                        openImgLookBig(photosStr,2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ivImage4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ArrayList<String> photosStr=new ArrayList<>();
                        for(int i=0;i<mInfoData.getXiangce().size();i++){
                            photosStr.add(mInfoData.getXiangce().get(i).getIcon());
                        }
                        openImgLookBig(photosStr,3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void openImgLookBig(ArrayList<String> photosStr,int  position) {
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
        photoPreviewIntentBuilder
                .previewPhotos(photosStr).currentPosition(position); // 当前预览图片的索引
        startActivity(photoPreviewIntentBuilder.build());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fensi:
                Intent tenFans = new Intent(this, FansActivity.class);
                tenFans.putExtra("id", id);
                startActivity(tenFans);
                break;
            case R.id.ll_guanzhu:
                Intent ten = new Intent(this, FollowActivity.class);
                ten.putExtra("id", id);
                startActivity(ten);
                break;
            case R.id.ll_fangke:
                Intent tenVisitor = new Intent(this, VisitorActivity.class);
                tenVisitor.putExtra("id", id);
                startActivity(tenVisitor);
                break;
            case R.id.ll_liwubang:
                startActivity(new Intent(this, LiWuBangActivity.class));
                break;
            case R.id.ll_liaorenqu:
                NLiaoRenQuActivity.start(NZhiLiaoActivity.this,id);
//                startActivity(new Intent(this, NLiaoRenQuActivity.class));
                break;
            case R.id.ll_shouhu:
                startActivity(new Intent(this, NShouHuBangActivity.class)
                                .putExtra("uid",""+id)
                                .putExtra("nickname",mInfoData.getNick_name())
                                .putExtra("avatar",mInfoData.getMember_avatar())
                                .putExtra("type","1"));
                break;
            case R.id.tv_xiangche_more:
                startActivity(new Intent(this, NXiangCheChaKanActivity.class).putExtra("photos",mInfoData.getXiangce()));
                break;
            case R.id.sounds_ll:
                paly_status_iv.setImageResource(R.mipmap.audio_playing_iv);
                if (mPlayer != null) {
                    mPlayer.start();
                } else {
                    mPlayer = new MediaPlayer();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer player) {
                            paly_status_iv.setImageResource(R.mipmap.activity_zhiliao_bofang);
                            mPlayer.release();
                            mPlayer = null;
                        }
                    });
                    try {
                        mPlayer.setDataSource(NZhiLiaoActivity.this, Uri.parse(mSoundFile));
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
            case R.id.ll_yuyin:
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
                            ToastUtils.showToast(NZhiLiaoActivity.this, standardResponse.getMessage(), 0);
                        }
                    }
                }, String.valueOf(id), Authorization);
                break;
            case R.id.ll_liaotian:
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
                                    NimUIKit.startP2PSession(NZhiLiaoActivity.this, accid);
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
                            ToastUtils.showToast(NZhiLiaoActivity.this, standardResponse.getMessage(), 0);
                        }
                    }
                }, String.valueOf(id), Authorization);
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            Authorization = "Bearer " + userBean.getToken();
        }
        if (Integer.valueOf(id) == userBean.getUserId() || userBean.getUserId().equals(id)) {//如果当前点击条目就是登录用户自己 登录NimUIKit 同时缓存账户id
//            guanzhu_name_tv.setVisibility(View.GONE);
//            more_rl.setVisibility(View.INVISIBLE);
            NimUIKit.doLogin(new LoginInfo(userBean.getWy_acid(), userBean.getWy_token()), new RequestCallback<LoginInfo>() {
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
//            guanzhu_name_tv.setVisibility(View.VISIBLE);
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
                    initViewData();
                } else {
                    ToastUtils.showToast(NZhiLiaoActivity.this, info.getMessage(), 0);
                }
            }
        }, this), String.valueOf(id), Authorization);
    }

    private void initMediaPlayer() {
        if (mSoundFile != null && !mSoundFile.equals("")) {
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        paly_status_iv.setImageResource(R.mipmap.activity_zhiliao_bofang);
                        mPlayer.release();
                        mPlayer = null;
                    }
                });
                mPlayer.setDataSource(NZhiLiaoActivity.this, Uri.parse(mSoundFile));
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
        } else {
            sounds_ll.setVisibility(View.GONE);
        }
    }

}
