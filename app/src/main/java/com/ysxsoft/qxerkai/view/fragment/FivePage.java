package com.ysxsoft.qxerkai.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kyleduo.switchbutton.SwitchButton;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.ExitLogin;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.eventbus.NotifyRecentContactRefresh;
import com.ttt.qx.qxcall.eventbus.SetSelectItem;
import com.ttt.qx.qxcall.eventbus.UserInfoModifyed;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.home.view.FansActivity;
import com.ttt.qx.qxcall.function.home.view.FollowActivity;
import com.ttt.qx.qxcall.function.home.view.VisitorActivity;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.UserShareInfo;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.MineBlacksActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.activity.NChongZhiActivity;
import com.ysxsoft.qxerkai.view.activity.NGouMaiVipActivity;
import com.ysxsoft.qxerkai.view.activity.NLiaoRenQuActivity;
import com.ysxsoft.qxerkai.view.activity.NMyLiWuBangActivity;
import com.ysxsoft.qxerkai.view.activity.NMyShouYiActivity;
import com.ysxsoft.qxerkai.view.activity.NPengYouQuanMyActivity;
import com.ysxsoft.qxerkai.view.activity.NPersonCenterActivity;
import com.ysxsoft.qxerkai.view.activity.NSettingActivity;
import com.ysxsoft.qxerkai.view.activity.NShouHuBangActivity;
import com.ysxsoft.qxerkai.view.activity.ShouFeiBiaoZhunActivity;
import com.ysxsoft.qxerkai.view.activity.VipCenterActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ObservableScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.getApplication;
import static com.ttt.qx.qxcall.QXCallApplication.login;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class FivePage extends BasePager implements View.OnClickListener {

	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	@BindView(R.id.status_bar2)
	View statusBar2;
	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.tv_public_titlebar_right)
	TextView tvPublicTitlebarRight;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.status_bar3)
	View statusBar3;
	@BindView(R.id.ll_public_titlebar_right)
	LinearLayout llPublicTitlebarRight;
	@BindView(R.id.civ_head)
	CircleImageView civHead;
	@BindView(R.id.tv_nickname)
	TextView tvNickname;
	@BindView(R.id.iv_vip)
	ImageView ivVip;
	@BindView(R.id.tv_currId)
	TextView tvCurrId;
	@BindView(R.id.iv_sex)
	ImageView ivSex;
	@BindView(R.id.ll_pengyouquan)
	LinearLayout llPengyouquan;
	@BindView(R.id.ll_wodeshouyi)
	LinearLayout llWodeshouyi;
	@BindView(R.id.ll_chongzhi)
	LinearLayout llChongzhi;
	@BindView(R.id.ll_goumaivip)
	LinearLayout llGoumaivip;
	@BindView(R.id.ll_huiyuanzhongxin)
	LinearLayout llHuiyuanzhongxin;
	@BindView(R.id.ll_gouliangqu)
	LinearLayout llGouliangqu;
	@BindView(R.id.ll_liaorenqu)
	LinearLayout llLiaorenqu;
	@BindView(R.id.ll_liwubang)
	LinearLayout llLiwubang;
	@BindView(R.id.ll_shoufeibiaozhun)
	LinearLayout llShoufeibiaozhun;
	@BindView(R.id.ll_wodeyaoqing)
	LinearLayout llWodeyaoqing;
	@BindView(R.id.ll_heimingdan)
	LinearLayout llHeimingdan;
	@BindView(R.id.ll_shouhu)
	LinearLayout llShouhu;
	@BindView(R.id.tv_fenshi)
	TextView tvFenshi;
	@BindView(R.id.tv_guanzhu)
	TextView tvGuanzhu;
	@BindView(R.id.tv_dianzan)
	TextView tvDianzan;
	@BindView(R.id.tv_fangke)
	TextView tvFangke;
	@BindView(R.id.ll_fenshi)
	LinearLayout llFenshi;
	@BindView(R.id.ll_guanzhu)
	LinearLayout llGuanzhu;
	@BindView(R.id.ll_dianzan)
	LinearLayout llDianzan;
	@BindView(R.id.ll_fangke)
	LinearLayout llFangke;
	@BindView(R.id.sv_scrollview)
	ObservableScrollView svScrollView;
	@BindView(R.id.ll_titlebar_bg)
	LinearLayout llTitlebarBg;
	@BindView(R.id.online_status_switch_btn)
	SwitchButton onlineStatusSwitchBtn;
	@BindView(R.id.tv_dogs)
	TextView tvDogs;
	UserDetailInfo.DataBean userDetailInfoData;
	@BindView(R.id.tv_shouhunum)
	TextView tvShouhunum;
	private View rootView;
	private String authorization;
	private boolean firstEnter = true;
	/**
	 * 弹出分享面板
	 */
	private UserShareInfo.DataBean shareInfoData = null;
	private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

		@Override
		public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
			//邀请分享
			UMWeb web = new UMWeb(shareInfoData.getUrl());
//            Bitmap bitmap = BitmapFactory.decodeResource(InvitedFriendsActivity.this.getResources(), R.mipmap.app_right_icon);
            UMImage umImage = new UMImage(ctx, shareInfoData.getImg());
            web.setTitle(shareInfoData.getTitle());//标题
            web.setThumb(umImage);  //缩略图
            web.setDescription(shareInfoData.getDescription());//描述
            new ShareAction(activity)
                    .withMedia(web)
                    .setPlatform(share_media)
                    .share();
        }
    };

    public FivePage(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_five, null);
        ButterKnife.bind(this, rootView);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initStatusBar(statusBar);
        initStatusBar(statusBar2);
//        initStatusBar2(statusBar3);
        initTitleBar();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return rootView;
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("我的");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("设置");
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.jumpIntent(ctx, NSettingActivity.class);
            }
        });
        civHead.setOnClickListener(this);
        llShouhu.setOnClickListener(this);
        llPengyouquan.setOnClickListener(this);
        llWodeshouyi.setOnClickListener(this);
        llChongzhi.setOnClickListener(this);
        llGoumaivip.setOnClickListener(this);
        llHuiyuanzhongxin.setOnClickListener(this);
        llGouliangqu.setOnClickListener(this);
        llLiaorenqu.setOnClickListener(this);
        llLiwubang.setOnClickListener(this);
        llShoufeibiaozhun.setOnClickListener(this);
        llWodeyaoqing.setOnClickListener(this);
        llHeimingdan.setOnClickListener(this);
        llFenshi.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llDianzan.setOnClickListener(this);
        llFangke.setOnClickListener(this);
        svScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (oldy > 50) {
                    llTitlebarBg.setAlpha((y - 50) * 0.01f);
                }
            }
        });
        onlineStatusSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!firstEnter) {
                    if (login) {
                        MineModel.getMineModel().setOnlineStatus(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                            @Override
                            public void onNext(StandardResponse standardResponse) {

                            }
                        }, ctx), b ? "1" : "0", authorization);
                    } else {
                        onlineStatusSwitchBtn.setChecked(b);
                        IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                    }
                }
                firstEnter = false;
            }
        });
    }

    @Override
    public void initData() {
        //首先从数据库中获取用户数据
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            String token = userBean.getToken();
            System.out.println("token = " + token);
            authorization = "Bearer " + token;
            if (token != null && !"".equals(token)) {
                //页面初始化
                setUserIcon(userBean.getMember_avatar());
                if (userBean.getLevel().equals("0")) {
                    ivVip.setVisibility(View.GONE);
                } else {
                    ivVip.setVisibility(View.VISIBLE);
//                        member_level_tv.setText("VIP" + userDetailInfoData.getLevel());
                }
                tvNickname.setText(userBean.getNick_name());
                tvCurrId.setText("ID:" + String.valueOf(userBean.getUserId()));
                //根据 用户性别设置不同的标识
                if ("1".equals(userBean.getMember_sex())) {
                    ivSex.setBackgroundResource(R.mipmap.fragment_five_sex_nan);
                    if ("0".equals(userBean.getLevel())) {
                        llShoufeibiaozhun.setVisibility(View.GONE);
                    } else {
                        llShoufeibiaozhun.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivSex.setBackgroundResource(R.mipmap.fragment_five_sex_nv);
                    llShoufeibiaozhun.setVisibility(View.VISIBLE);
                }
                tvFenshi.setText("");
                tvGuanzhu.setText("");
                tvDianzan.setText("");
                tvFangke.setText("");
                setUserInfo(token);
            } else {
                login = false;
            }
        } else {
            login = false;
        }
    }

    @Subscribe
    public void onEventLoginSuccess(LoginSuccess loginSuccess) {
        //根据当前用户登录token  获取用户详细信息
        String token = loginSuccess.token;
        authorization = "Bearer " + token;
        setUserInfo(token);
        EventBus.getDefault().post(new NotifyRecentContactRefresh());
    }

    /**
     * 根据当前用户token 获取用户详细信息
     *
     * @param token
     */
    private void setUserInfo(final String token) {
        HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
            @Override
            public void onCompleted() {
                System.out.println("");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
//                Toast.makeText(ctx, "获取用户信息失败5", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //获取用户详细信息成功之后 首先赋值给视图控件 然后将必要信息保存到数据中
                    userDetailInfoData = userDetailInfo.getData();
                    setUserIcon(userDetailInfoData.getMember_avatar());
                    if (userDetailInfoData.getLevel() == 0) {
                        ivVip.setVisibility(View.GONE);
                    } else {
                        ivVip.setVisibility(View.VISIBLE);
//                        member_level_tv.setText("VIP" + userDetailInfoData.getLevel());
                    }
                    tvNickname.setText(userDetailInfoData.getNick_name());
                    tvCurrId.setText("ID:" + String.valueOf(userDetailInfoData.getId()));
                    //根据 用户性别设置不同的标识
                    if (userDetailInfoData.getMember_sex().equals("1")) {
                        ivSex.setBackgroundResource(R.mipmap.fragment_five_sex_nan);
                        if (userDetailInfoData.getLevel() == 0) {
                            llShoufeibiaozhun.setVisibility(View.VISIBLE);
                        } else {
                            llShoufeibiaozhun.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ivSex.setBackgroundResource(R.mipmap.fragment_five_sex_nv);
                        llShoufeibiaozhun.setVisibility(View.VISIBLE);
                    }
                    //在线状态设置
                    if (userDetailInfoData.getIs_online() == 0) {
                        onlineStatusSwitchBtn.setChecked(false);
                    } else {
                        onlineStatusSwitchBtn.setChecked(true);
                    }
                    tvFenshi.setText(String.valueOf(userDetailInfoData.getFans_num()));
                    tvGuanzhu.setText(String.valueOf(userDetailInfoData.getFlow_num()));
                    tvDianzan.setText(String.valueOf(userDetailInfoData.getZan_num()));
                    tvFangke.setText(String.valueOf(userDetailInfoData.getVisitor_num()));
                    tvDogs.setText(userDetailInfoData.getDog());
                    tvShouhunum.setText(userDetailInfoData.getGuard());
                    if (!login) {
                        login = true;
                        //同时登录网易云
                        QXCallApplication qxCallApplication = (QXCallApplication) getApplication();
                        DemoCache.setContext(qxCallApplication);
                        qxCallApplication.logigIM(ctx, new LoginInfo(userDetailInfoData.getWy_acid(), userDetailInfoData.getWy_token()));
                    }
                    //偷听状态设置
//                    if (userDetailInfoData.getListen_state() == 0) {
//                        steal_switch_btn.setChecked(false);
//                    } else {
//                        steal_switch_btn.setChecked(true);
//                    }
                    //认证状态设置
//                    if (userDetailInfoData.getIs_true() == 1) {
//                        verifyStatus = true;
//                        verify_status_tv.setText("已提交认证");
//                    } else {
//                        verifyStatus = false;
//                        verify_status_tv.setText("未认证");
//                    }
                    //在线状态设置
//                    if (userDetailInfoData.getIs_online() == 0) {
//                        online_status_switch_btn.setChecked(false);
//                    } else {
//                        online_status_switch_btn.setChecked(true);
//                    }
                    //将用户信息 保存到数据库中
                    UserBean userBean = new UserBean();
                    userBean.setUserId(userDetailInfoData.getId());
                    userBean.setLevel(String.valueOf(userDetailInfoData.getLevel()));
                    userBean.setMember_avatar(userDetailInfoData.getMember_avatar());
                    userBean.setNick_name(userDetailInfoData.getNick_name());
                    userBean.setToken(token);
                    userBean.setMember_sex(userDetailInfoData.getMember_sex());
                    userBean.setWy_acid(userDetailInfoData.getWy_acid());
                    userBean.setWy_token(userDetailInfoData.getWy_token());
                    UserDao userDao = new UserDao();
                    //首先清空数据库表
                    userDao.deleteAll();
                    userDao.add(userBean);
                } else {
                    onToast(userDetailInfo.getMessage());
                }

            }
        }, "", "Bearer " + token);
        //登录通知
        LoginModel.getLoginModel().loginReward(new Subscriber<StandardResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(StandardResponse standardResponse) {
            }
        }, "Bearer " + token);
    }

    /**
     * 设置用户头像
     *
     * @param avatar
     */
    private void setUserIcon(String avatar) {
//        Glide.clear(civHead);
        Glide.with(ctx).load(avatar)
//                .skipMemoryCache(true)//跳过内部缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(civHead);
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(ctx, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        switch (view.getId()) {
            //编辑资料
            case R.id.civ_head:
                //将当前用户id传递过去  启动UserMainActivity
//                UserDao userDao = new UserDao();
//                UserBean userBean = userDao.queryFirstData();
//                Intent intent = new Intent(ctx, UserMainActivity.class);
//                intent.putExtra("id", userBean.getUserId());//用户id
//                intent.putExtra("accid", userBean.getWy_acid());//网易accid
//                ctx.startActivity(intent);
                IntentUtil.jumpIntent(ctx, NPersonCenterActivity.class);
                break;
            //守护榜
            case R.id.ll_shouhu:
                if (userDetailInfoData != null) {
                    ctx.startActivity(new Intent(ctx, NShouHuBangActivity.class)
                            .putExtra("uid", "" + userDetailInfoData.getId())
                            .putExtra("nickname", userDetailInfoData.getNick_name())
                            .putExtra("avatar", userDetailInfoData.getMember_avatar())
                            .putExtra("type", "1"));
                }
                break;
            //我的朋友圈
            case R.id.ll_pengyouquan:
                IntentUtil.jumpIntent(ctx, NPengYouQuanMyActivity.class);
                break;
            //我的收益
            case R.id.ll_wodeshouyi:
//                IntentUtil.jumpIntent(ctx, MyProfitActivity.class);
                IntentUtil.jumpIntent(ctx, NMyShouYiActivity.class);
                break;
            //充值
            case R.id.ll_chongzhi:
//                IntentUtil.jumpIntent(ctx, RechargeActivity.class);
                IntentUtil.jumpIntent(ctx, NChongZhiActivity.class);
                break;
            //购买VIP
            case R.id.ll_goumaivip:
//                IntentUtil.jumpIntent(ctx, MyVIPActivity.class);
                IntentUtil.jumpIntent(ctx, NGouMaiVipActivity.class);
                break;
            //会员中心
            case R.id.ll_huiyuanzhongxin:
                ctx.startActivity(new Intent(ctx, VipCenterActivity.class)
                        .putExtra("url", userDetailInfoData.getMember_avatar())
                        .putExtra("uid", "" + userDetailInfoData.getId()));
                break;
            //狗粮区
            case R.id.ll_gouliangqu:
                break;
            //撩人区
            case R.id.ll_liaorenqu:
//                IntentUtil.jumpIntent(ctx, NLiaoRenQuActivity.class);
                NLiaoRenQuActivity.start(ctx, DBUtils.getIntUserId());
                break;
            //礼物榜
            case R.id.ll_liwubang:
                ctx.startActivity(new Intent(ctx, NMyLiWuBangActivity.class).putExtra("uid", "" + userDetailInfoData.getId()));
                break;
            //收费标准
            case R.id.ll_shoufeibiaozhun:
                IntentUtil.jumpIntent(ctx, ShouFeiBiaoZhunActivity.class);
                break;
            //我的邀请
            case R.id.ll_wodeyaoqing:
//                IntentUtil.jumpIntent(ctx, InvitedFriendsActivity.class);
//                IntentUtil.jumpIntent(ctx, NMyYaoQingActivity.class);
                showShareBoard();
                break;
            //黑名单
            case R.id.ll_heimingdan:
                IntentUtil.jumpIntent(ctx, MineBlacksActivity.class);
                break;
            //粉丝
            case R.id.ll_fenshi:
                Intent tenFans = new Intent(ctx, FansActivity.class);
                tenFans.putExtra("id", userBean.getUserId());
                ctx.startActivity(tenFans);
//                IntentUtil.jumpIntent(ctx, NFansListActivity.class);
                break;
            //关注
            case R.id.ll_guanzhu:
                Intent ten = new Intent(ctx, FollowActivity.class);
                ten.putExtra("id", userBean.getUserId());
                ctx.startActivity(ten);
//                IntentUtil.jumpIntent(ctx, NFansListActivity.class);
                break;
            //点赞
            case R.id.ll_dianzan:
//                IntentUtil.jumpIntent(ctx, NFansListActivity.class);
                break;
            //访客
            case R.id.ll_fangke:
                Intent tenVisitor = new Intent(ctx, VisitorActivity.class);
                tenVisitor.putExtra("id", userBean.getUserId());
                ctx.startActivity(tenVisitor);
//                IntentUtil.jumpIntent(ctx, NFansListActivity.class);
                break;
        }
    }

    @Subscribe
    public void onEventExitLogin(ExitLogin exitLogin) {
        UserDao userDao = new UserDao();
        //首先清空数据库表
        userDao.deleteAll();
        login = false;
        onToast("退出成功！");
        //切换到首页
        SetSelectItem setSelectItem = new SetSelectItem();
        setSelectItem.selectPosition = CommonConstant.SELECT_HOME;//上次选择就是首页
        EventBus.getDefault().post(setSelectItem);
    }

    @Subscribe
    public void onEventUserInfoModifyed(UserInfoModifyed userInfoModifyed) {
        //重新获取用户详细信息 刷新视图
        initData();
    }

    private void showShareBoard() {
        if (shareInfoData == null) {
            LoginModel.getLoginModel().getShareInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserShareInfo>() {
                @Override
                public void onNext(UserShareInfo userShareInfo) throws IOException {
                    if (userShareInfo.getStatus_code() == 200) {
                        shareInfoData = userShareInfo.getData();
                        showBorad();
                    } else {
                        onToast(userShareInfo.getMessage());
                    }
                }
            }, activity), String.valueOf(userDetailInfoData.getId()));
        } else {
            showBorad();
        }
    }

    private void showBorad() {
        ShareAction shareAction = new ShareAction(activity);
        shareAction.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE);
        shareAction.setShareboardclickCallback(shareBoardlistener);
        shareAction.open();
    }


}
