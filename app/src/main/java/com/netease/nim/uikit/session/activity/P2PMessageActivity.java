package com.netease.nim.uikit.session.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.OnlineStateChangeListener;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.fragment.MessageFragment;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 点对点聊天界面
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class P2PMessageActivity extends BaseMessageActivity {

    private boolean isResume = false;
    private boolean isNotifyed=false;

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if("com.need.exit.p2p".equals(intent.getAction())){
                P2PMessageActivity.this.finish();
            }
        }
    };

    public static void start(Context context, String contactId, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    /**
     * 抛话题点头像进去
     * @param context
     * @param contactId
     * @param customization
     * @param anchor
     */
    public static void startByHuaTi(Context context,String contactId,String icon,String name,String title,int isVip,int num,int gid,int callType, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra("icon",icon);
        intent.putExtra("name",name);
        intent.putExtra("title",title);
        intent.putExtra("isVip",isVip);
        intent.putExtra("num",num);
        intent.putExtra("gid",gid);//群组id
        intent.putExtra("callType",callType);// 0默认  1抛话题  2角色扮演  为了区分扣费
        intent.putExtra("isAdmin",false);//是否是发起方

        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    /**
     * 收到通知监听  跳转至聊天页面
     * @param context
     * @param contactId
     * @param icon
     * @param name
     * @param title
     * @param isVip
     * @param num
     * @param customization
     * @param anchor
     * @param callType   0默认  1抛话题  2角色扮演
     */
    public static void startByHuaAccept(Context context,String contactId,String icon,String name,String title,int isVip,int num,int gid,int callType, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra("icon",icon);
        intent.putExtra("name",name);
        intent.putExtra("title",title);
        intent.putExtra("isVip",isVip);
        intent.putExtra("num",num);
        intent.putExtra("gid",gid);//群组id
        intent.putExtra("callType",callType);// 0默认扣费  1抛话题扣费  2角色扮演扣费  为了区分扣费
        intent.putExtra("isAdmin",true);//是否是抛话题人/发起人
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 角色扮演进去
     * @param context
     * @param members 成员列表
     * @param role 0左边  1右边
     * @param teamId 发起者id
     * @param story 故事类型
     * @param callType   0默认  1抛话题  2角色扮演
     */
    public static void startByJiaose(Context context,List<String> members,String role,String teamId,String story,String teamName,String userIcon,int callType, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, teamId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        //角色扮演的  通知  id = 5 ;  members = 匹配的成员数租 ;  teamId = 发起者的id ;  role = (0或者1 — 0是代表扮演左边  1是代表扮演右边) ;  story = 故事类型(0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书)  ;  teamName = 发起者的昵称
        intent.putStringArrayListExtra("members", (ArrayList<String>) members);
        intent.putExtra("teamId",teamId);//发起者的id
        intent.putExtra("role",role);
        intent.putExtra("story",story);
        intent.putExtra("teamName",teamName);
        intent.putExtra("userIcon",userIcon);//对方头像
        intent.putExtra("callType",callType);//0默认  1抛话题  2角色扮演
        intent.putExtra("isAdmin",false);//是否是抛话题人/发起人
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 角色接受扮演 接受进去
     * @param context
     * @param members 成员列表
     * @param role 0左边  1右边
     * @param teamId 发起者id
     * @param story 故事类型
     * @param callType   0默认  1抛话题  2角色扮演
     */
    public static void startByJiaoseAccept(Context context,List<String> members,String role,String teamId,String story,String teamName,String userIcon,String ppid,int callType, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, teamId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        //角色扮演的  通知  id = 5 ;  members = 匹配的成员数租 ;  teamId = 发起者的id ;  role = (0或者1 — 0是代表扮演左边  1是代表扮演右边) ;  story = 故事类型(0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书)  ;  teamName = 发起者的昵称
        intent.putStringArrayListExtra("members", (ArrayList<String>) members);
        intent.putExtra("teamId",teamId);//发起者的id
        intent.putExtra("role",role);
        intent.putExtra("story",story);
        intent.putExtra("teamName",teamName);
        intent.putExtra("userIcon",userIcon);//对方头像
        intent.putExtra("callType",callType);//0默认  1抛话题  2角色扮演
        intent.putExtra("isAdmin",true);//是否是抛话题人/发起人
        intent.putExtra("ppid",ppid);//房间id
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        register();
        // 单聊特例话数据，包括个人信息，
        requestBuddyInfo();
        displayOnlineState();
        registerObservers(true);
        registerOnlineStateChangeListener(true);
    }

    private void register(){
        this.registerReceiver(receiver,new IntentFilter("com.need.exit.p2p"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        registerObservers(false);
        registerOnlineStateChangeListener(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    private void requestBuddyInfo() {
        setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
    }

    private void registerObservers(boolean register) {
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(commandObserver, register);
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }
    };

    private UserInfoObservable.UserInfoObserver uinfoObserver;

    OnlineStateChangeListener onlineStateChangeListener = new OnlineStateChangeListener() {
        @Override
        public void onlineStateChange(Set<String> accounts) {
            // 更新 toolbar
            if (accounts.contains(sessionId)) {
                // 按照交互来展示
                displayOnlineState();
            }
        }
    };

    private void registerOnlineStateChangeListener(boolean register) {
        if (!NimUIKit.enableOnlineState()) {
            return;
        }
        if (register) {
            NimUIKit.addOnlineStateChangeListeners(onlineStateChangeListener);
        } else {
            NimUIKit.removeOnlineStateChangeListeners(onlineStateChangeListener);
        }
    }

    private void displayOnlineState() {
        if (!NimUIKit.enableOnlineState()) {
            return;
        }
        String detailContent = NimUIKit.getOnlineStateContentProvider().getDetailDisplay(sessionId);
        setSubTitle(detailContent);
    }

    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (accounts.contains(sessionId)) {
                        requestBuddyInfo();
                    }
                }
            };
        }

        UserInfoHelper.registerObserver(uinfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (uinfoObserver != null) {
            UserInfoHelper.unregisterObserver(uinfoObserver);
        }
    }

    /**
     * 命令消息接收观察者
     */
    Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            if (!sessionId.equals(message.getSessionId()) || message.getSessionType() != SessionTypeEnum.P2P) {
                return;
            }
            showCommandMessage(message);
        }
    };

    protected void showCommandMessage(CustomNotification message) {
        if (!isResume) {
            return;
        }

        String content = message.getContent();
        try {
            JSONObject json = JSON.parseObject(content);
            int id = json.getIntValue("id");
            if (id == 1) {
                // 正在输入
//                Toast.makeText(P2PMessageActivity.this, "对方正在输入...", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(P2PMessageActivity.this, "command: " + content, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
    }

    @Override
    protected MessageFragment fragment() {
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.nim_message_activity;
    }

    @Override
    protected void initToolBar() {
//        ToolBarOptions options = new ToolBarOptions();
//        setToolBar(R.id.toolbar, options);
    }
}
