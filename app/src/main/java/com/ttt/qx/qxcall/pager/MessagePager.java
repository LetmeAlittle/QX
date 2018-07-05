package com.ttt.qx.qxcall.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.NotifyRecentContactRefresh;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.function.message.view.SystemNotifyActivity;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.utils.IntentUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 直播 pager
 * Created by wyd on 2017/7/19.
 */
public class MessagePager extends BasePager {
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    private MainActivity mMainActivity;
    //如果是登录状态下的第一次刷新
    private boolean firstRefresh = true;

    //保存当前
    public MessagePager(Context ctx) {
        super(ctx);
        mMainActivity = (MainActivity) ctx;
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
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
            DemoCache.setAccount(userBean.getWy_acid());
        }

    }

    @Override
    public View initView() {
        //注意initView只调用执行一次
        View view = View.inflate(ctx, R.layout.message_pager, null);
        ButterKnife.bind(this, view);
        initStatusBar(statusBar);
        tvPublicTitlebarCenter.setText("消息");
//        recent_contacts_fragment = view.findViewById(R.id.recent_contacts_fragment);
//        recent_contacts_fragment.setCallback(new RecentContactsCallback() {
//            @Override
//            public void onRecentContactsLoaded() {
//                //联系人加载完毕 demo测试
//            }
//
//            @Override
//            public void onUnreadCountChange(int unreadCount) {
//                // 未读数发生变化
//            }
//
//            @Override
//            public void onItemClick(RecentContact recent) {
//                //单聊demo测试
//                NimUIKit.startP2PSession(ctx, "wyd");
//            }
//
//            @Override
//            public String getDigestOfAttachment(RecentContact recent, MsgAttachment attachment) {
//                return null;
//            }
//
//            @Override
//            public String getDigestOfTipMsg(RecentContact recent) {
//                return null;
//            }
//        });
//        List<String> friendAccounts = NIMClient.getService(FriendService.class).getFriendAccounts();
        // 该帐号为示例，请先注册
//        String account = "jinpeng";
//        // 以单聊类型为例
//        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
//        String text = "你好我是wyd";
//        // 创建一个文本消息
//        IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, text);
//        // 发送给对方
//        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
//        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
//        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
//        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
//        NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
        return view;
    }

    @Override
    public void initData() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        if (QXCallApplication.login) {//登录状态下
            if (firstRefresh) {
                EventBus.getDefault().post(new NotifyRecentContactRefresh());
                firstRefresh = false;
            }
        }
    }

    @OnClick({R.id.sys_notify_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.sys_notify_rl:
                IntentUtil.jumpIntent(ctx, SystemNotifyActivity.class);
                break;
        }
    }
}
