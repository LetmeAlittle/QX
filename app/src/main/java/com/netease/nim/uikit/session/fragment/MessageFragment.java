package com.netease.nim.uikit.session.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.CustomPushContentProvider;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.ait.AitManager;
import com.netease.nim.uikit.cache.RobotInfoCache;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.actions.CameraAction;
import com.netease.nim.uikit.session.actions.GiftAction;
import com.netease.nim.uikit.session.actions.ImageAction;
import com.netease.nim.uikit.session.actions.PhoneAction;
import com.netease.nim.uikit.session.actions.PickImageAction;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.module.Container;
import com.netease.nim.uikit.session.module.ModuleProxy;
import com.netease.nim.uikit.session.module.input.InputPanel;
import com.netease.nim.uikit.session.module.list.MessageListPanelEx;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.MemberPushOption;
import com.netease.nimlib.sdk.msg.model.MessageReceipt;
import com.netease.nimlib.sdk.robot.model.NimRobotInfo;
import com.netease.nimlib.sdk.robot.model.RobotAttachment;
import com.netease.nimlib.sdk.robot.model.RobotMsgType;
import com.ttt.qx.qxcall.DimenTool;
import com.ttt.qx.qxcall.utils.SystemUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetGuShiResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.GlideCircleTransform;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoTouTingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天界面基类
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class MessageFragment extends TFragment implements ModuleProxy {

    private View rootView;

    private SessionCustomization customization;

    protected static final String TAG = "MessageActivity";

    // 聊天对象
    protected String sessionId; // p2p对方Account或者群id

    protected SessionTypeEnum sessionType;

    // modules
    protected InputPanel inputPanel;
    protected MessageListPanelEx messageListPanel;

    protected AitManager aitManager;

    private ImageView ivLiwu;
    private ImageView ivPhone;
    private TextView close;
    private LinearLayout banYanLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIntent();
        parseTitle();
        parseJiaose();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.nim_message_fragment, container, false);
        LinearLayout linearLayout= (LinearLayout) rootView.findViewById(R.id.banYanLayout);
        linearLayout.setVisibility(View.GONE);//角色扮演默认关闭
        FrameLayout parent = (FrameLayout) rootView.findViewById(R.id.fl_bg);
        parent.setVisibility(View.GONE);//抛话题默认关闭
        return rootView;
    }

    /**
     * ***************************** life cycle *******************************
     */

    @Override
    public void onPause() {
        super.onPause();

        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE,
                SessionTypeEnum.None);
        inputPanel.onPause();
        messageListPanel.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        messageListPanel.onResume();
        NIMClient.getService(MsgService.class).setChattingAccount(sessionId, sessionType);
        getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL); // 默认使用听筒播放
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messageListPanel.onDestroy();
        registerObservers(false);
        if (inputPanel != null) {
            inputPanel.onDestroy();
        }
        aitManager.reset();
    }

    public boolean onBackPressed() {
        if (inputPanel.collapse(true)) {
            return true;
        }

        if (messageListPanel.onBackPressed()) {
            return true;
        }
        return false;
    }

    public void refreshMessageList() {
        messageListPanel.refreshMessageList();
    }

    private void parseIntent() {
        sessionId = getArguments().getString(Extras.EXTRA_ACCOUNT);
        sessionType = (SessionTypeEnum) getArguments().getSerializable(Extras.EXTRA_TYPE);
        IMMessage anchor = (IMMessage) getArguments().getSerializable(Extras.EXTRA_ANCHOR);

        customization = (SessionCustomization) getArguments().getSerializable(Extras.EXTRA_CUSTOMIZATION);
        Container container = new Container(getActivity(), sessionId, sessionType, this);

        if (messageListPanel == null) {
            messageListPanel = new MessageListPanelEx(container, rootView, anchor, false, false);
        } else {
            messageListPanel.reload(container, anchor);
        }

        if (inputPanel == null) {
            inputPanel = new InputPanel(container, rootView, getActionList());
            inputPanel.setCustomization(customization);
        } else {
            inputPanel.reload(container, customization);
        }

        aitManager = new AitManager(getContext(), sessionType == SessionTypeEnum.Team ? sessionId : null, true);

        inputPanel.addAitTextWatcher(aitManager);

        aitManager.setTextChangeListener(inputPanel);

        inputPanel.switchRobotMode(RobotInfoCache.getInstance().getRobotByAccount(sessionId) != null);

        registerObservers(true);

        if (customization != null) {
            messageListPanel.setChattingBackground(customization.backgroundUri, customization.backgroundColor);
        }

        ivLiwu = (ImageView) findView(R.id.iv_liwu);
        ivPhone = (ImageView) findView(R.id.iv_phone);
        banYanLayout = (LinearLayout) findView(R.id.banYanLayout);
        close = (TextView) findView(R.id.close);
//        bottomLayout=(LinearLayout) findView(R.id.bottomLayout);

        GiftAction giftAction = new GiftAction();
        giftAction.setContainer(container);
        ivLiwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giftAction.onClick();
            }
        });
        PhoneAction phoneAction = new PhoneAction();
        phoneAction.setContainer(container);
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneAction.onClick();
            }
        });
    }

    /**
     * ************************* 消息收发 **********************************
     */
    // 是否允许发送消息
    protected boolean isAllowSendMessage(final IMMessage message) {
        return true;
    }

    /**
     * ****************** 观察者 **********************
     */

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(incomingMessageObserver, register);
        service.observeMessageReceipt(messageReceiptObserver, register);
    }

    /**
     * 消息接收观察者
     */
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }

            messageListPanel.onIncomingMessage(messages);
            sendMsgReceipt(); // 发送已读回执
        }
    };

    private Observer<List<MessageReceipt>> messageReceiptObserver = new Observer<List<MessageReceipt>>() {
        @Override
        public void onEvent(List<MessageReceipt> messageReceipts) {
            receiveReceipt();
        }
    };


    /**
     * ********************** implements ModuleProxy *********************
     */
    @Override
    public boolean sendMessage(IMMessage message) {
        if (!isAllowSendMessage(message)) {
            return false;
        }

        appendTeamMemberPush(message);
        message = changeToRobotMsg(message);
        appendPushConfig(message);
        // send message to server and save to db
        NIMClient.getService(MsgService.class).sendMessage(message, false);

        messageListPanel.onMsgSend(message);

        aitManager.reset();
        return true;
    }

    private void appendTeamMemberPush(IMMessage message) {
        if (sessionType == SessionTypeEnum.Team) {
            List<String> pushList = aitManager.getAitTeamMember();
            if (pushList == null || pushList.isEmpty()) {
                return;
            }
            MemberPushOption memberPushOption = new MemberPushOption();
            memberPushOption.setForcePush(true);
            memberPushOption.setForcePushContent(message.getContent());
            memberPushOption.setForcePushList(pushList);
            message.setMemberPushOption(memberPushOption);
        }
    }

    private IMMessage changeToRobotMsg(IMMessage message) {
        if (isChatWithRobot()) {
            if (message.getMsgType() == MsgTypeEnum.text && message.getContent() != null) {
                String content = message.getContent().equals("") ? " " : message.getContent();
                message = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), message.getSessionId(), content, RobotMsgType.TEXT, content, null, null);
            }
        } else {
            String robotAccount = aitManager.getAitRobot();
            if (TextUtils.isEmpty(robotAccount)) {
                return message;
            }
            String text = message.getContent();
            String content = aitManager.removeRobotAitString(text, robotAccount);
            content = content.equals("") ? " " : content;
            message = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), robotAccount, text, RobotMsgType.TEXT, content, null, null);

        }
        return message;
    }

    private boolean isChatWithRobot() {
        return RobotInfoCache.getInstance().getRobotByAccount(sessionId) != null;
    }

    private void appendPushConfig(IMMessage message) {
        CustomPushContentProvider customConfig = NimUIKit.getCustomPushContentProvider();
        if (customConfig != null) {
            String content = customConfig.getPushContent(message);
            Map<String, Object> payload = customConfig.getPushPayload(message);
            message.setPushContent(content);
            message.setPushPayload(payload);
        }
    }

    @Override
    public void onInputPanelExpand() {
        messageListPanel.scrollToBottom();
    }

    @Override
    public void shouldCollapseInputPanel() {
        inputPanel.collapse(false);
    }

    @Override
    public boolean isLongClickEnabled() {
        return !inputPanel.isRecording();
    }

    @Override
    public void onItemFooterClick(IMMessage message) {
        if (messageListPanel.isSessionMode()) {
            RobotAttachment attachment = (RobotAttachment) message.getAttachment();
            NimRobotInfo robot = RobotInfoCache.getInstance().getRobotByAccount(attachment.getFromRobotAccount());
            aitManager.insertAitRobot(robot.getAccount(), robot.getName(), inputPanel.getEditSelectionStart());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        aitManager.onActivityResult(requestCode, resultCode, data);
        inputPanel.onActivityResult(requestCode, resultCode, data);
        messageListPanel.onActivityResult(requestCode, resultCode, data);
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();

        //相册
        actions.add(new ImageAction(PickImageAction.ImageActionType.PHOTO));
        //拍照
        actions.add(new CameraAction(PickImageAction.ImageActionType.CAMERA));

//        actions.add(new PhoneAction());
//        actions.add(new GiftAction());
//        actions.add(new VideoAction());
//        actions.add(new LocationAction());

        if (customization != null && customization.actions != null) {
            actions.addAll(customization.actions);
        }
        return actions;
    }

    /**
     * 发送已读回执
     */
    private void sendMsgReceipt() {
        messageListPanel.sendReceipt();
    }

    /**
     * 收到已读回执
     */
    public void receiveReceipt() {
        messageListPanel.receiveReceipt();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Sincerly  2018.07.13 15:39:00  新增抛话题
    ///////////////////////////////////////////////////////////////////////////
    private void parseTitle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int isVip = bundle.getInt("isVip");//1是vip  0不是vip
            String title = bundle.getString("title");//话题标题
            int num = bundle.getInt("num");//话题价格
            String icon = bundle.getString("icon");
            FrameLayout parent = findView(R.id.fl_bg);
            LinearLayout msgLayout=findView(R.id.msgLayout);//动态设置布局  布局下移

            if (title != null) {//是vip
                parent.setVisibility(View.VISIBLE);
                msgLayout.setPadding(0, DimenUtils.dp2px(getActivity(),70),0,0);
            } else {
                parent.setVisibility(View.GONE);
            }

            ImageView logo = findView(R.id.logo);
            ImageView iv_vip = findView(R.id.iv_vip);
            TextView t = findView(R.id.tv_title);
            TextView p = findView(R.id.price);

            if (isVip == 0) {
                iv_vip.setVisibility(View.GONE);
            } else {
                iv_vip.setVisibility(View.VISIBLE);
            }
            Glide.with(getActivity()).load(icon).transform(new GlideCircleTransform(getActivity())).into(logo);//圆角
            t.setText(StringUtils.convert(title));
            p.setText(StringUtils.convert(num + "") + "砰砰豆/分钟");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Sincerly  2018.07.20 18:02:00  新增角色
    ///////////////////////////////////////////////////////////////////////////
    private void parseJiaose(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            //角色扮演的  通知  id = 5 ;  members = 匹配的成员数租 ;  teamId = 发起者的id ;  role = (0或者1 — 0是代表扮演左边  1是代表扮演右边) ;  story = 故事类型(0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书)  ;  teamName = 发起者的昵称
//            intent.putStringArrayListExtra("members", (ArrayList<String>) members);
//            intent.putExtra("teamId",teamId);//发起者的id
//            intent.putExtra("role",role);
//            intent.putExtra("story",story);
//            intent.putExtra("teamName",teamName);
            String teamId=bundle.getString("teamId");
            String role=bundle.getString("role");
            String story=bundle.getString("story");
            String teamName=bundle.getString("teamName");
            List<String> members=bundle.getStringArrayList("members");
            String userIcon=bundle.getString("userIcon");//发起人头像  数据源带过来的

            if(story!=null){//角色扮演
                banYanLayout= (LinearLayout) rootView.findViewById(R.id.banYanLayout);
                banYanLayout.setVisibility(View.VISIBLE);

                content= (TextView) rootView.findViewById(R.id.content);//故事
                contentTitle= (TextView) rootView.findViewById(R.id.contentTitle);//故事title

                getGuShi(story);//获取故事
                close= (TextView) rootView.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isExspanded=!isExspanded){
                            close.setText("展开故事");
                            contentTitle.setVisibility(View.GONE);
                            content.setVisibility(View.GONE);
                        }else{
                            close.setText("收起故事");
                            contentTitle.setVisibility(View.VISIBLE);
                            content.setVisibility(View.VISIBLE);
                        }
                    }
                });

                ImageView leftUserIcon=findView(R.id.leftLogo);//左边用户logo
                ImageView leftRobotIcon=findView(R.id.leftLogo2);//系统角色
                ImageView rightUserIcon=findView(R.id.leftLogo3);//右边用户logo
                ImageView rightRobotIcon=findView(R.id.leftLogo4);//系统角色

                TextView leftName=findView(R.id.leftName);
                TextView rightName=findView(R.id.rightName);//系统角色
                TextView leftName2=findView(R.id.leftName2);
                TextView rightName2=findView(R.id.rightName2);//系统角色

				String[] s=getNameByStory(story);//根据故事类型得到两个系统角色
				if("0".equals(role)){//对方扮演角色  左边
					leftName.setText(StringUtils.convert(teamName));//发起人在左
					rightName.setText(s[0]);
					leftName2.setText(StringUtils.convert(DBUtils.getUserNickName()));//自己在右
					rightName2.setText(s[1]);
				}else{
					leftName.setText(StringUtils.convert(DBUtils.getUserNickName()));//自己在左
					rightName.setText(s[0]);
					leftName2.setText(StringUtils.convert(teamName));//发起人在右
					rightName2.setText(s[1]);
				}

				//头像
				int[] s2=getIconByStory(story);
				if("0".equals(role)){//对方扮演角色  左边
					Glide.with(getActivity()).load(userIcon).transform(new GlideCircleTransform(getActivity())).into(leftUserIcon);
					leftRobotIcon.setImageResource(s2[0]);
					Glide.with(getActivity()).load(DBUtils.getUserAvatar()).transform(new GlideCircleTransform(getActivity())).into(rightUserIcon);
					rightRobotIcon.setImageResource(s2[1]);
				}else{
					Glide.with(getActivity()).load(DBUtils.getUserAvatar()).transform(new GlideCircleTransform(getActivity())).into(leftUserIcon);
					leftRobotIcon.setImageResource(s2[0]);
					Glide.with(getActivity()).load(userIcon).transform(new GlideCircleTransform(getActivity())).into(rightUserIcon);
					rightRobotIcon.setImageResource(s2[1]);
				}
            }else{
                banYanLayout= (LinearLayout) rootView.findViewById(R.id.banYanLayout);
                banYanLayout.setVisibility(View.GONE);
            }
        }
    }
    TextView content;
    TextView contentTitle;
    private boolean isExspanded=true;//已展开

    /**
     * 获取故事内容
     */
    private void getGuShi(String story){
        Map<String,String> map=new HashMap<>();
        map.put("type",story);
        RetrofitTools.getGuShi(map).subscribe(new ResponseSubscriber<GetGuShiResponse>() {
            @Override
            public void onSuccess(GetGuShiResponse getGuShiResponse, int code, String msg) {
                if(code==200){
                   String gushi=getGuShiResponse.getData();
                   content.setText(StringUtils.convert(gushi));//填充故事内容
                }
            }

            @Override
            public void onFailed(Throwable e) {
            }
        });
    }

    /**
     * 根据角色 得到两个名字
     * @param story
     * @return
     */
    private String[] getNameByStory(String story){
        String[] s=new String[2];
		//角色扮演的  通知  id = 5 ;  members = 匹配的成员数租 ;  teamId = 发起者的id ;  role = (0或者1 — 0是代表扮演左边  1是代表扮演右边) ;
		// story = 故事类型(0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书)  ;  teamName = 发起者的昵称
		String leftName="";
		String rightName="";
		switch (story){
			case "0":
				leftName="教师";
				rightName="学生";
				break;
			case "1":
				leftName="亲王";
				rightName="宠妃";
				break;
			case "2":
				leftName="护士";
				rightName="病人";
				break;
			case "3":
				leftName="大叔";
				rightName="萝莉";
				break;
			case "4":
				leftName="空姐";
				rightName="乘客";
				break;
			case "5":
				leftName="老板";
				rightName="秘书";
				break;
        }
        s[0]=leftName;
		s[1]=rightName;
		return s;
    }

	/**
	 * 根据角色 得到两个头像
	 * @param story
	 * @return
	 */
	private int[] getIconByStory(String story){
		int[] s=new int[2];
		//角色扮演的  通知  id = 5 ;  members = 匹配的成员数租 ;  teamId = 发起者的id ;  role = (0或者1 — 0是代表扮演左边  1是代表扮演右边) ;
		// story = 故事类型(0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书)  ;  teamName = 发起者的昵称
		int leftIcon=R.drawable.icon_js_js;
		int rightIcon=R.drawable.icon_js_xs;
		switch (story){
			case "0":
				leftIcon=R.drawable.icon_js_js;
				rightIcon=R.drawable.icon_js_xs;
				break;
			case "1":
				leftIcon=R.drawable.icon_js_qw;
				rightIcon=R.drawable.icon_js_cf;
				break;
			case "2":
				leftIcon=R.drawable.icon_js_hs;
				rightIcon=R.drawable.icon_js_bing;
				break;
			case "3":
				leftIcon=R.drawable.icon_js_ds;
				rightIcon=R.drawable.icon_js_ll;
				break;
			case "4":
				leftIcon=R.drawable.icon_js_kj;
				rightIcon=R.drawable.icon_js_ck;
				break;
			case "5":
				leftIcon=R.drawable.icon_js_lb;
				rightIcon=R.drawable.icon_js_ms;
				break;
		}
		s[0]=leftIcon;
		s[1]=rightIcon;
		return s;
	}

}
