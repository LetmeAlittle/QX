package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.UserMainPicAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.widget.FlowLayout;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.RoundAngleImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

    UserListInfo.DataBean.ListBean data;
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

    private Integer id;
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
        data = (UserListInfo.DataBean.ListBean) getIntent().getSerializableExtra("data");
        id=data.getId();
        accid=data.getWy_acid();
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
        llLiwubang.setOnClickListener(this);
        llLiaorenqu.setOnClickListener(this);
        llShouhu.setOnClickListener(this);
        tvXiangcheMore.setOnClickListener(this);
        sounds_ll.setOnClickListener(this);
    }

    private void initViewData() {
        _ffffff = getResources().getColor(R.color._ffffff);
        tagSize = getResources().getInteger(R.integer.fm_anim_i12);
        //初始化相关控件
        if (mInfoData != null) {
            Glide.with(this).load(mInfoData.getMember_avatar()).into(civHead);
            if (mInfoData.getLevel() == 0) {
                ivVip.setVisibility(View.GONE);
                tvVip.setVisibility(View.INVISIBLE);
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
                layoutParams.leftMargin = 35;
                TextView textView = new TextView(NZhiLiaoActivity.this);
                textView.setTextColor(_ffffff);
                textView.setTextSize(tagSize);
                textView.setText(memberTagBean.getText());
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
            if (null == member_age || member_age.equals("") || member_age.equals(" ") || member_age.equals("0")) {
                tvAge.setText("未知");
            } else {
                tvAge.setText(mInfoData.getMember_age()+"岁");
            }
            Glide.with(this).load(mInfoData.getMember_img_1()).into(ivImage1);
            Glide.with(this).load(mInfoData.getMember_img_2()).into(ivImage2);
            Glide.with(this).load(mInfoData.getMember_img_3()).into(ivImage3);
            Glide.with(this).load(mInfoData.getMember_img_4()).into(ivImage4);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_liwubang:
                startActivity(new Intent(this, LiWuBangActivity.class));
                break;
            case R.id.ll_liaorenqu:
                startActivity(new Intent(this, NLiaoRenQuActivity.class));
                break;
            case R.id.ll_shouhu:
                startActivity(new Intent(this, NShouHuBangActivity.class));
                break;
            case R.id.tv_xiangche_more:
                startActivity(new Intent(this, NXiangCheActivity.class));
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
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            Authorization = "Bearer " + userBean.getToken();
        }
        if (id == userBean.getUserId() || userBean.getUserId().equals(id)) {//如果当前点击条目就是登录用户自己 登录NimUIKit 同时缓存账户id
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
                        paly_status_iv.setBackgroundResource(R.mipmap.audio_pause_iv);
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
        }
    }

}
