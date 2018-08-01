package com.ttt.qx.qxcall.pager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kyleduo.switchbutton.SwitchButton;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.ExitLogin;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerHeaderModify;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerNickModify;
import com.ttt.qx.qxcall.eventbus.NotifyRecentContactRefresh;
import com.ttt.qx.qxcall.eventbus.SetSelectItem;
import com.ttt.qx.qxcall.eventbus.VerifySuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.view.IdentifyAuthActivity;
import com.ttt.qx.qxcall.function.login.view.InvitedFriendsActivity;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.MemberCenterActivity;
import com.ttt.qx.qxcall.function.login.view.MyProfitActivity;
import com.ttt.qx.qxcall.function.login.view.MyVIPActivity;
import com.ttt.qx.qxcall.function.login.view.RechargeActivity;
import com.ttt.qx.qxcall.function.login.view.SetCallPriceActivity;
import com.ttt.qx.qxcall.function.login.view.SetUserSkilActivity;
import com.ttt.qx.qxcall.function.login.view.SettingActivity;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.getApplication;
import static com.ttt.qx.qxcall.QXCallApplication.login;


/**
 * 我 pager
 * Created by wyd on 2017/7/19.
 */
public class MinePager extends BasePager {
    //未登录
    @BindView(R.id.un_login_rl)
    RelativeLayout un_login_rl;
    //已登录
    @BindView(R.id.logined_rl)
    RelativeLayout logined_rl;
    @BindView(R.id.user_head_icon_iv)
    CircleImageView user_head_icon_iv;
    @BindView(R.id.member_level_tv)
    TextView member_level_tv;
    @BindView(R.id.user_nick_name_tv)
    TextView user_nick_name_tv;
    @BindView(R.id.user_sex_iv)
    ImageView user_sex_iv;
    @BindView(R.id.user_ID_tv)
    TextView user_ID_tv;
    @BindView(R.id.verify_status_tv)
    TextView verify_status_tv;
    //偷听开关
    @BindView(R.id.steal_switch_btn)
    SwitchButton steal_switch_btn;
    //在线状态
    @BindView(R.id.online_status_switch_btn)
    SwitchButton online_status_switch_btn;
    //当前用户登录之后的实体对象
    private User mUser;
    //添加标记标识是否来自于交易模块的登录成功！
    public static boolean tradeLogined = false;
    private static final int SHOW_TOAST = 0;
    private String authorization;
    private boolean firstEnter = true;
    private boolean verifyStatus = false;

    public MinePager(Context ctx) {
        super(ctx);
    }

    //是否是再次验证标记
    boolean againCheck = false;

    @Override
    public View initView() {
        View view = null;
        view = View.inflate(ctx, R.layout.mine_pager, null);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        steal_switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!firstEnter) {
                    if (login) {
                        MineModel.getMineModel().setListenState(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                            @Override
                            public void onNext(StandardResponse standardResponse) {

                            }
                        }, ctx), b ? "1" : "0", authorization);
                    } else {
                        steal_switch_btn.setChecked(b);
                        IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                    }
                }
                firstEnter = false;
            }
        });

        online_status_switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                        online_status_switch_btn.setChecked(b);
                        IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                    }
                }
                firstEnter = false;
            }
        });
        return view;
    }

    @OnClick({R.id.un_login_rl, R.id.logined_rl, R.id.my_profit_rl
            , R.id.my_vip_rl, R.id.yqhy_rl, R.id.identify_auth_rl
            , R.id.member_center_rl, R.id.set_yythjg_rl, R.id.recharge_rl
            , R.id.mine_tech_rl, R.id.setting_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.un_login_rl: //用户未登录，跳转至登录页
                IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                break;
            case R.id.logined_rl:
                //将当前用户id传递过去  启动UserMainActivity
                UserDao userDao = new UserDao();
                UserBean userBean = userDao.queryFirstData();
                Intent intent = new Intent(ctx, UserMainActivity.class);
                intent.putExtra("id", userBean.getUserId());//用户id
                intent.putExtra("accid", userBean.getWy_acid());//网易accid
                ctx.startActivity(intent);
                break;
            case R.id.my_vip_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, MyVIPActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.my_profit_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, MyProfitActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.mine_tech_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, SetUserSkilActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.recharge_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, RechargeActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.identify_auth_rl:
                if (login) {
                    if (verifyStatus) {
                        TipDialog.showCenterTipDialog(ctx, "您已提交身份信息，确定要重新提交吗？", new TipDialog.OnComponentClickListener() {
                            @Override
                            public void onCancle() {
                            }

                            @Override
                            public void onConfirm() {
                                IntentUtil.jumpIntent(ctx, IdentifyAuthActivity.class);
                            }
                        }, true);
                    } else {
                        IntentUtil.jumpIntent(ctx, IdentifyAuthActivity.class);
                    }
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.member_center_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, MemberCenterActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.yqhy_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, InvitedFriendsActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.set_yythjg_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, SetCallPriceActivity.class);
//                    IntentUtil.jumpIntent(ctx, StealListenDetailActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
            case R.id.setting_rl:
                if (login) {
                    IntentUtil.jumpIntent(ctx, SettingActivity.class);
                } else {
                    IntentUtil.jumpIntent(ctx, LoginTransferActivity.class);
                }
                break;
        }
    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            //应用宝分享
            UMWeb web = new UMWeb("http://m.fxbtg.com/share");
            UMImage umImage = new UMImage(ctx, R.mipmap.app_icon_right_angle_iv);
            web.setTitle("微财财经");//标题
            web.setThumb(umImage);  //缩略图
            web.setDescription(ctx.getResources().getString(R.string.app_download_share_content_text));//描述
            new ShareAction((MainActivity) ctx)
                    .withMedia(web)
                    .setPlatform(share_media)
                    .share();
        }
    };

    @Override
    public void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //首先从数据库中获取用户数据
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            String token = userBean.getToken();
            System.out.println("token = " + token);
            authorization = "Bearer " + token;
            if (token != null && !"".equals(token)) {
                //页面初始化
                setUserInfo(token);
                un_login_rl.setVisibility(View.GONE);
                logined_rl.setVisibility(View.VISIBLE);
            } else {
                login = false;
                un_login_rl.setVisibility(View.VISIBLE);
                logined_rl.setVisibility(View.GONE);
            }
        } else {
            login = false;
            un_login_rl.setVisibility(View.VISIBLE);
            logined_rl.setVisibility(View.GONE);
        }
    }


    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(ctx, message, Toast.LENGTH_SHORT);
    }

    @Subscribe
    public void onEventLoginSuccess(LoginSuccess loginSuccess) {
        un_login_rl.setVisibility(View.GONE);
        logined_rl.setVisibility(View.VISIBLE);
        //根据当前用户登录token  获取用户详细信息
        String token = loginSuccess.token;
        authorization = "Bearer " + token;
        setUserInfo(token);
        EventBus.getDefault().post(new NotifyRecentContactRefresh());
    }

    @Subscribe
    public void onEventVerifySuccess(VerifySuccess verifySuccess) {
        verifyStatus = true;
        verify_status_tv.setText("已提交认证");
    }

    @Subscribe
    public void onEventExitLogin(ExitLogin exitLogin) {
        un_login_rl.setVisibility(View.VISIBLE);
        logined_rl.setVisibility(View.GONE);
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
                System.out.println("e = " + e);
                Toast.makeText(ctx, "获取用户信息失败1" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //获取用户详细信息成功之后 首先赋值给视图控件 然后将必要信息保存到数据中
                    UserDetailInfo.DataBean userDetailInfoData = userDetailInfo.getData();
                    setUserIcon(userDetailInfoData.getMember_avatar());
                    if (userDetailInfoData.getLevel() == 0) {
                        member_level_tv.setVisibility(View.INVISIBLE);
                    } else {
//                        member_level_tv.setVisibility(View.VISIBLE);
//                        member_level_tv.setText("VIP" + userDetailInfoData.getLevel());
                    }
                    user_nick_name_tv.setText(userDetailInfoData.getNick_name());
                    user_ID_tv.setText(String.valueOf(userDetailInfoData.getId()));
                    //根据 用户性别设置不同的标识
                    if (userDetailInfoData.getMember_sex().equals("1")) {
                        user_sex_iv.setBackgroundResource(R.mipmap.man_iv);
                    } else {
                        user_sex_iv.setBackgroundResource(R.mipmap.women_iv);
                    }
                    if (!login) {
                        login = true;
                        //同时登录网易云
                        QXCallApplication qxCallApplication = (QXCallApplication) getApplication();
                        DemoCache.setContext(qxCallApplication);
                        qxCallApplication.logigIM(ctx, new LoginInfo(userDetailInfoData.getWy_acid(), userDetailInfoData.getWy_token()));
                    }
                    //偷听状态设置
                    if (userDetailInfoData.getListen_state() == 0) {
                        steal_switch_btn.setChecked(false);
                    } else {
                        steal_switch_btn.setChecked(true);
                    }
                    //认证状态设置
                    if (userDetailInfoData.getIs_true() == 1) {
                        verifyStatus = true;
                        verify_status_tv.setText("已提交认证");
                    } else {
                        verifyStatus = false;
                        verify_status_tv.setText("未认证");
                    }
                    //在线状态设置
                    if (userDetailInfoData.getIs_online() == 0) {
                        online_status_switch_btn.setChecked(false);
                    } else {
                        online_status_switch_btn.setChecked(true);
                    }
                    //将用户信息 保存到数据库中
                    UserBean userBean = new UserBean();
                    userBean.setUserId(userDetailInfoData.getId());
                    userBean.setLevel(String.valueOf(userDetailInfoData.getLevel()));
                    userBean.setMember_avatar(userDetailInfoData.getMember_avatar());
                    userBean.setNick_name(userDetailInfoData.getNick_name());
                    userBean.setToken(token);
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
        Glide.clear(user_head_icon_iv);
        Glide.with(ctx).load(avatar)
                .skipMemoryCache(true)//跳过内部缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_head_icon_iv);
    }


    //
//    @Subscribe
//    public void onEventSetNick(SetNick nick) {
//        user_nick_name_tv.setText(nick.nick);
//        mUser.getData().setNickName(nick.nick);
//    }
//
//    @Subscribe
//    public void onEventSetPhone(SetPhone phone) {
//        mUser.getData().setMobile(phone.phone);
//    }
    @Subscribe
    public void onEventNotifyMinePagerNickModify(NotifyMinePagerNickModify notifyMinePagerNickModify) {
        user_nick_name_tv.setText(notifyMinePagerNickModify.content);
    }

    @Subscribe
    public void onEventNotifyMinePagerHeaderModify(NotifyMinePagerHeaderModify notifyMinePagerHeaderModify) {
        setUserIcon(notifyMinePagerHeaderModify.avatar);
    }
//
//    @Subscribe
//    public void onEventSetHeadImg(SetHeadImg setHeadImg) {
//        Glide.clear(default_user_icon_iv);
//        Glide.with(ctx).load(setHeadImg.img)
//                .into(default_user_icon_iv);
//        mUser.getData().setHeadImg(setHeadImg.img);
//    }
//
//    @Subscribe
//    public void onEventAgainCheckTradeAccount(AgainCheckTradeAccount againCheckTradeAccount) {
//        againCheck = true;
//        UserBean userBean = new UserDao().queryFirstData();
//        User user = getUser(userBean);
//        relativeMattHandle(user.getData(), getApplication());
//        if (againCheck) {//如果依然是true 说明再次验证失败
//            CheckTradeAccountSuccess checkTradeAccountSuccess = new CheckTradeAccountSuccess();
//            checkTradeAccountSuccess.success = false;
//            EventBus.getDefault().post(checkTradeAccountSuccess);
//            againCheck = false;
//        }
//    }
}
