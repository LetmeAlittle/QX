package com.ttt.qx.qxcall.function.voice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatControlEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatOnlineAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dialog.ReceiveGiftDialog;
import com.ttt.qx.qxcall.eventbus.ReCeiveGiftAVChatActivity;
import com.ttt.qx.qxcall.function.message.entity.GiftSendNotify;
import com.ttt.qx.qxcall.function.voice.floatw.FloatViewService;
import com.ttt.qx.qxcall.receiver.PhoneCallStateObserver;
import com.ysxsoft.qxerkai.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * 音视频界面
 * Created by hzxuwen on 2015/4/21.
 */
public class AVChatActivity extends UI implements AVChatUI.AVChatListener, AVChatStateObserver, AVChatSurface.TouchZoneCallback {
    // constant
    private static final String TAG = "AVChatActivity";
    private static final String KEY_IN_CALLING = "KEY_IN_CALLING";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    private static final String KEY_ACTION_TYPE = "CALL_TYPE";//新增 通话类型 Sincerly   0默认  1抛话题  2角色扮演
    private static final String KEY_ACTION_GID = "CALL_GID";//新增 抛话题Gid Sincerly
    private static final String KEY_ACTION_PPID = "CALL_PPID";//新增 一键匹配PPid  Sincerly
    private static final String KEY_ACTION_IS_ADMIN = "IS_ADMIN";//新增 判断是否是发起用户
    private static final String KEY_CALL_TYPE = "KEY_CALL_TYPE";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_CALL_CONFIG = "KEY_CALL_CONFIG";
    public static final String INTENT_ACTION_AVCHAT = "INTENT_ACTION_AVCHAT";

    /**
     * 来自广播
     */
    public static final int FROM_BROADCASTRECEIVER = 0;
    /**
     * 来自发起方
     */
    public static final int FROM_INTERNAL = 1;
    /**
     * 来自通知栏
     */
    public static final int FROM_NOTIFICATION = 2;
    /**
     * 未知的入口
     */
    public static final int FROM_UNKNOWN = -1;

    // data
    private AVChatUI avChatUI; // 音视频总管理器
    private AVChatData avChatData; // config for connect video server
    private int state; // calltype 音频或视频
    private String receiverId; // 对方的account
    // state
    private boolean isUserFinish = false;
    private boolean mIsInComingCall = false;// is incoming call or outgoing call
    private boolean isCallEstablished = false; // 电话是否接通
    private static boolean needFinish = true; // 若来电或去电未接通时，点击home。另外一方挂断通话。从最近任务列表恢复，则finish
    private boolean hasOnPause = false; // 是否暂停音视频

    // notification
    private AVChatNotification notifier;
    private boolean serviceStart = false;
    //添加是否是用户挂起操作
    private boolean hangUp = true;
    private boolean notification = false;

    private int type;//0默认   1抛话题  2角色扮演
    private boolean isAdmin;//是否是发起人
    private int gid;//话题id
    private String ppid;//角色扮演话题id
    private Context context;

    //拨打启动
    public static void launch(Context context, String account, int callType, int source) {
        needFinish = false;
        QXCallApplication.accid = account;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, AVChatActivity.class);
        intent.putExtra(KEY_ACCOUNT, account);
        intent.putExtra(KEY_IN_CALLING, false);
        intent.putExtra(KEY_CALL_TYPE, callType);
        intent.putExtra(KEY_SOURCE, source);

        context.startActivity(intent);
    }

    //抛话题 通知跳转 携带类型
    public static void launch(Context context,int type,int gid,String ppid, String account, int callType,boolean isAdmin, int source) {
        needFinish = false;
        QXCallApplication.accid = account;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, AVChatActivity.class);
        intent.putExtra(KEY_ACTION_TYPE, type);
        intent.putExtra(KEY_ACTION_GID, gid);
        intent.putExtra(KEY_ACTION_PPID, ppid);
        intent.putExtra(KEY_ACCOUNT, account);
        intent.putExtra(KEY_IN_CALLING, false);
        intent.putExtra(KEY_CALL_TYPE, callType);
        intent.putExtra(KEY_ACTION_IS_ADMIN, isAdmin);
        intent.putExtra(KEY_SOURCE, source);
        context.startActivity(intent);
    }

    /**
     * 来电启动
     * incoming call
     *
     * @param context
     */
    public static void launch(Context context, AVChatData config, int source) {
        needFinish = false;
        QXCallApplication.accid = config.getAccount();
        Intent intent = new Intent();
        intent.setClass(context, AVChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_CALL_CONFIG, config);
        intent.putExtra(KEY_IN_CALLING, true);
        intent.putExtra(KEY_SOURCE, source);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needFinish || !checkSource()) {
            finish();
            return;
        }
        type=getIntent().getIntExtra(KEY_ACTION_TYPE, 0);//打电话类型
        gid=getIntent().getIntExtra(KEY_ACTION_GID, 0);//抛话题id
        ppid=getIntent().getStringExtra(KEY_ACTION_PPID);//ppid test
        isAdmin=getIntent().getBooleanExtra(KEY_ACTION_IS_ADMIN, false);//是否是发起方

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        QXCallApplication.mActivity = this;
        // 锁屏唤醒
        dismissKeyguard();
        View root = LayoutInflater.from(this).inflate(R.layout.avchat_activity, null);
        setContentView(root);
        View view = root.findViewById(R.id.chat_bg_rl);
//        com.ttt.qx.qxcall.utils.ImageUtil.blur(ImageUtil.getBitmap(getResources().getDrawable(R.mipmap.chat_bg_iv)),view);
        mIsInComingCall = getIntent().getBooleanExtra(KEY_IN_CALLING, false);
        avChatUI = new AVChatUI(this, root, this);
        avChatUI.setCallType(type);
        avChatUI.setIsAdmin(isAdmin);//是否是发起方
        avChatUI.setGid(gid);//新加参数   gid
        avChatUI.setPPid(ppid);//新加参数 ppid 角色房间id
        if (!avChatUI.init(this)) {
            this.finish();
            return;
        }
        registerNetCallObserver(true);
        if (mIsInComingCall) {
            inComingCalling();
        } else {
            outgoingCalling();
        }

        notifier = new AVChatNotification(this);
        notifier.init(receiverId != null ? receiverId : avChatData.getAccount());
        isCallEstablished = false;
        //放到所有UI的基类里面注册，所有的UI实现onKickOut接口
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, true);
    }

    @Subscribe
    public void onEventReCeiveGiftAVChatActivity(ReCeiveGiftAVChatActivity reCeiveGift) {
        GiftSendNotify giftSendNotify = reCeiveGift.giftSendNotify;
        Map<String, String> receiveMap = new HashMap<String, String>();
        receiveMap.put("avator", giftSendNotify.getMember_info().getAvatar());
        receiveMap.put("gift_avator", giftSendNotify.getGift_info().getGift_img());
        receiveMap.put("nick_name", giftSendNotify.getMember_info().getNick_name());
        receiveMap.put("gift_num", giftSendNotify.getGift_info().getGit_num());
        ReceiveGiftDialog.showReceiveGiftDialog(this, receiveMap, new ReceiveGiftDialog.OnComponentClickListener() {
            @Override
            public void onCancle() {
            }

            @Override
            public void onSend(String gift_id) {
            }
        });
    }

    /**
     * 判斷是否销毁Activity
     *  
     */
    @Override
    public void finish() {
        isUserFinish = true;
        super.finish();
        if (hangUp) {
            stopPackUpService();
        }
    }

    // 设置窗口flag，亮屏并且解锁/覆盖在锁屏界面上
    private void dismissKeyguard() {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        avChatUI.pauseVideo(); // 暂停视频聊天（用于在视频聊天过程中，APP退到后台时必须调用）
        hasOnPause = true;

    }

    @Override
    protected void onStop() {
        super.onStop();
        activeCallingNotifier();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelCallingNotifier();
        if (hasOnPause) {
            avChatUI.resumeVideo();
            hasOnPause = false;
        }
//        if (mybinder != null) {
//            if (mybinder.isMiUI8) {
////                mybinder.hidwindowMiUI8();
//            } else {
//                mybinder.hidFloatingWindow();
//            }
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSoundPlayer();
    }

    /**
     * 停止所有和音视频有关的操作
     */
    private void stopSoundPlayer() {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, false);
        AVChatProfile.getInstance().setAVChatting(false);
        registerNetCallObserver(false);
        cancelCallingNotifier();
        needFinish = true;

    }

    @Override
    public void onBackPressed() {
    }

    /**
     * 判断来电还是去电
     *
     * @return
     */
    private boolean checkSource() {
        switch (getIntent().getIntExtra(KEY_SOURCE, FROM_UNKNOWN)) {
            case FROM_BROADCASTRECEIVER: // incoming call
                parseIncomingIntent();
                return true;
            case FROM_INTERNAL: // outgoing call
                parseOutgoingIntent();
                if (state == AVChatType.VIDEO.getValue() || state == AVChatType.AUDIO.getValue()) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /**
     * 来电参数解析
     */
    private void parseIncomingIntent() {
        avChatData = (AVChatData) getIntent().getSerializableExtra(KEY_CALL_CONFIG);
        state = avChatData.getChatType().getValue();
    }

    /**
     * 去电参数解析
     */
    private void parseOutgoingIntent() {
        receiverId = getIntent().getStringExtra(KEY_ACCOUNT);
        state = getIntent().getIntExtra(KEY_CALL_TYPE, -1);
    }

    /**
     * 注册监听
     *
     * @param register
     */
    private void registerNetCallObserver(boolean register) {
        AVChatManager.getInstance().observeAVChatState(this, register);
        AVChatManager.getInstance().observeCalleeAckNotification(callAckObserver, register);
        AVChatManager.getInstance().observeControlNotification(callControlObserver, register);
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, register);
        AVChatManager.getInstance().observeOnlineAckNotification(onlineAckObserver, register);
//        超时挂断请在demo上层实现，sdk未来会移除超时相关接口
//        AVChatManager.getInstance().observeTimeoutNotification(timeoutObserver, register);
//        demo上层实现超时挂断示例
        AVChatTimeoutObserver.getInstance().observeTimeoutNotification(timeoutObserver, register, mIsInComingCall);
        PhoneCallStateObserver.getInstance().observeAutoHangUpForLocalPhone(autoHangUpForLocalPhoneObserver, register);
    }

    /**
     * 注册/注销网络通话被叫方的响应（接听、拒绝、忙）
     */
    Observer<AVChatCalleeAckEvent> callAckObserver = new Observer<AVChatCalleeAckEvent>() {
        @Override
        public void onEvent(AVChatCalleeAckEvent ackInfo) {
            AVChatData info = avChatUI.getAvChatData();
            if (info != null && info.getChatId() == ackInfo.getChatId()) {
                AVChatSoundPlayer.instance().stop();

                if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_BUSY) {

                    AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.PEER_BUSY);

                    avChatUI.closeSessions(AVChatExitCode.PEER_BUSY);
                } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_REJECT) {
                    avChatUI.closeRtc();
                    avChatUI.closeSessions(AVChatExitCode.REJECT);
                } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_AGREE) {
                    avChatUI.isCallEstablish.set(true);
                    avChatUI.canSwitchCamera = true;
                }
            }
        }
    };

    Observer<Integer> timeoutObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer integer) {
            hangUp = true;
            avChatUI.onHangUp();
            // 来电超时，自己未接听
            if (mIsInComingCall) {
                activeMissCallNotifier();
            }
            AVChatSoundPlayer.instance().stop();
            stopSoundPlayer();
            stopPackUpService();


        }
    };

    /**
     * 停止 收起服务
     */
    private void stopPackUpService() {
        //如果收起服务已经开启 同时停止服务
        if (serviceStart) {
            stopService(new Intent(AVChatActivity.this, FloatViewService.class));
            serviceStart = false;
        }
    }

    Observer<Integer> autoHangUpForLocalPhoneObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer integer) {

            AVChatSoundPlayer.instance().stop();
            stopSoundPlayer();
            avChatUI.closeSessions(AVChatExitCode.PEER_BUSY);

            if(type!=0&&type!=-1){//普通通话 不关闭页面
                Intent intent=new Intent();
                intent.setAction("com.need.exit.p2p");
                sendBroadcast(intent);
            }
        }
    };

    /**
     * 注册/注销网络通话控制消息（音视频模式切换通知）
     */
    Observer<AVChatControlEvent> callControlObserver = new Observer<AVChatControlEvent>() {
        @Override
        public void onEvent(AVChatControlEvent netCallControlNotification) {
            handleCallControl(netCallControlNotification);
        }
    };

    /**
     * 注册/注销网络通话对方挂断的通知
     */
    Observer<AVChatCommonEvent> callHangupObserver = new Observer<AVChatCommonEvent>() {
        @Override
        public void onEvent(AVChatCommonEvent avChatHangUpInfo) {
            AVChatData info = avChatUI.getAvChatData();
            if (info != null && info.getChatId() == avChatHangUpInfo.getChatId()) {
                AVChatSoundPlayer.instance().stop();
                avChatUI.closeRtc();
                avChatUI.closeSessions(AVChatExitCode.HANGUP);
                cancelCallingNotifier();
                // 如果是incoming call主叫方挂断，那么通知栏有通知
                if (mIsInComingCall && !isCallEstablished) {
                    activeMissCallNotifier();
                }
                stopSoundPlayer();
                stopPackUpService();

                if(type!=0&&type!=-1){//普通通话 不关闭页面
                    Intent intent=new Intent();
                    intent.setAction("com.need.exit.p2p");
                    sendBroadcast(intent);
                }
            }
        }
    };

    /**
     * 注册/注销同时在线的其他端对主叫方的响应
     */
    Observer<AVChatOnlineAckEvent> onlineAckObserver = new Observer<AVChatOnlineAckEvent>() {
        @Override
        public void onEvent(AVChatOnlineAckEvent ackInfo) {
            AVChatData info = avChatUI.getAvChatData();
            if (info != null && info.getChatId() == ackInfo.getChatId()) {
                AVChatSoundPlayer.instance().stop();

                String client = null;
                switch (ackInfo.getClientType()) {
                    case ClientType.Web:
                        client = "Web";
                        client = "Windows";
                        break;
                    case ClientType.Android:
                        client = "Android";
                        break;
                    case ClientType.Windows:
                        break;
                    case ClientType.iOS:
                        client = "iOS";
                        break;
                    case ClientType.MAC:
                        client = "Mac";
                        break;
                    default:
                        break;
                }
                if (client != null) {
                    String option = ackInfo.getEvent() == AVChatEventType.CALLEE_ONLINE_CLIENT_ACK_AGREE ? "接听！" : "拒绝！";
                    Toast.makeText(AVChatActivity.this, "通话已在" + client + "端被" + option, Toast.LENGTH_SHORT).show();
                }
                avChatUI.closeSessions(-1);
            }
        }
    };


    /**
     * 接听
     */
    private void inComingCalling() {
        avChatUI.inComingCalling(avChatData);
    }

    /**
     * 拨打
     */
    private void outgoingCalling() {
        if (!NetworkUtil.isNetAvailable(AVChatActivity.this)) { // 网络不可用
            Toast.makeText(this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            needFinish = true;
            finish();
            return;
        }
        avChatUI.outGoingCalling(receiverId, AVChatType.typeOfValue(state));
    }

    /**
     * *************************** AVChatListener *********************************
     */

    @Override
    public void uiExit() {
        finish();
    }

    @Override
    public void onPackUp() {
        hangUp = false;
        Intent intent = new Intent(this, FloatViewService.class);
        startService(intent);
        serviceStart = true;
        //获取当前通话时间
        QXCallApplication.baseTime = avChatUI.getTime().getBase();
        //不销毁 activity
        moveTaskToBack(true);
    }

    /****************************** 连接建立处理 ********************/

    /**
     * 处理连接服务器的返回值
     *
     * @param auth_result
     */
    protected void handleWithConnectServerResult(int auth_result) {
        LogUtil.i(TAG, "result code->" + auth_result);
        if (auth_result == 200) {
            LogUtil.d(TAG, "onConnectServer success");
        } else if (auth_result == 101) { // 连接超时
            avChatUI.closeSessions(AVChatExitCode.PEER_NO_RESPONSE);
        } else if (auth_result == 401) { // 验证失败
            avChatUI.closeSessions(AVChatExitCode.CONFIG_ERROR);
        } else if (auth_result == 417) { // 无效的channelId
            avChatUI.closeSessions(AVChatExitCode.INVALIDE_CHANNELID);
        } else { // 连接服务器错误，直接退出
            avChatUI.closeSessions(AVChatExitCode.CONFIG_ERROR);
        }
    }

    /**************************** 处理音视频切换 *********************************/

    /**
     * 处理音视频切换请求
     *
     * @param notification
     */
    private void handleCallControl(AVChatControlEvent notification) {
        if (AVChatManager.getInstance().getCurrentChatId() != notification.getChatId()) {
            return;
        }
        switch (notification.getControlCommand()) {
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO:
                avChatUI.incomingAudioToVideo();
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_AGREE:
                onAudioToVideo();
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_REJECT:
                avChatUI.onCallStateChange(CallStateEnum.AUDIO);
                Toast.makeText(AVChatActivity.this, R.string.avchat_switch_video_reject, Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.SWITCH_VIDEO_TO_AUDIO:
                onVideoToAudio();
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_OFF:
//                avChatUI.peerVideoOff();
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_ON:
//                avChatUI.peerVideoOn();
                break;
            default:
//                Toast.makeText(this, "对方发来指令值：" + notification.getControlCommand(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 音频切换为视频
     */
    private void onAudioToVideo() {
//        avChatUI.onAudioToVideo();
//        avChatUI.initAllSurfaceView(avChatUI.getVideoAccount());
    }

    /**
     * 视频切换为音频
     */
    private void onVideoToAudio() {
        avChatUI.onCallStateChange(CallStateEnum.AUDIO);
        avChatUI.onVideoToAudio();
    }

    /**
     * 通知栏
     */
    private void activeCallingNotifier() {
        if (notifier != null && !isUserFinish) {
            notifier.activeCallingNotification(true);
        }
    }

    private void cancelCallingNotifier() {
        if (notification) {
            stopPackUpService();
            notification = false;
        }
        if (notifier != null) {
            notifier.activeCallingNotification(false);
        }
        notification = true;
    }

    private void activeMissCallNotifier() {
        if (notifier != null) {
            notifier.activeMissCallNotification(true);
        }
    }

    /**
     * ************************ AVChatStateObserver ****************************
     */

    @Override
    public void onTakeSnapshotResult(String account, boolean success, String file) {

    }

    @Override
    public void onConnectionTypeChanged(int netType) {

    }

    @Override
    public void onAVRecordingCompletion(String account, String filePath) {
        if (account != null && filePath != null && filePath.length() > 0) {
            String msg = "音视频录制已结束, " + "账号：" + account + " 录制文件已保存至：" + filePath;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "录制已结束.", Toast.LENGTH_SHORT).show();
        }
        if (avChatUI != null) {
            avChatUI.resetRecordTip();
        }
    }

    @Override
    public void onAudioRecordingCompletion(String filePath) {
        if (filePath != null && filePath.length() > 0) {
            String msg = "音频录制已结束, 录制文件已保存至：" + filePath;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "录制已结束.", Toast.LENGTH_SHORT).show();
        }
        if (avChatUI != null) {
            avChatUI.resetRecordTip();
        }
    }

    @Override
    public void onLowStorageSpaceWarning(long availableSize) {
        if (avChatUI != null) {
            avChatUI.showRecordWarning();
        }
    }


    @Override
    public void onFirstVideoFrameAvailable(String account) {

    }

    @Override
    public void onVideoFpsReported(String account, int fps) {

    }

    @Override
    public void onJoinedChannel(int code, String audioFile, String videoFile, int i) {
        handleWithConnectServerResult(code);
    }

    @Override
    public void onLeaveChannel() {

    }

    @Override
    public void onUserJoined(String account) {
        LogUtil.d(TAG, "onUserJoin -> " + account);
        avChatUI.setVideoAccount(account);
        avChatUI.initLargeSurfaceView(avChatUI.getVideoAccount());
    }

    @Override
    public void onUserLeave(String account, int event) {
        LogUtil.d(TAG, "onUserLeave -> " + account);
        hangUp = true;
        avChatUI.onHangUp();
        avChatUI.closeSessions(AVChatExitCode.HANGUP);
    }

    @Override
    public void onProtocolIncompatible(int status) {

    }

    @Override
    public void onDisconnectServer() {

    }

    @Override
    public void onNetworkQuality(String user, int quality, AVChatNetworkStats stats) {

    }

    @Override
    public void onCallEstablished() {
        LogUtil.d(TAG, "onCallEstablished");
        //移除超时监听
        AVChatTimeoutObserver.getInstance().observeTimeoutNotification(timeoutObserver, false, mIsInComingCall);
        if (avChatUI.getTimeBase() == 0)
            avChatUI.setTimeBase(SystemClock.elapsedRealtime());

        if (state == AVChatType.AUDIO.getValue()) {
            avChatUI.onCallStateChange(CallStateEnum.AUDIO);
        } else {
            avChatUI.initSmallSurfaceView();
            avChatUI.onCallStateChange(CallStateEnum.VIDEO);
        }
        isCallEstablished = true;
    }

    @Override
    public void onDeviceEvent(int code, String desc) {

    }

    @Override
    public void onFirstVideoFrameRendered(String user) {

    }

    @Override
    public void onVideoFrameResolutionChanged(String user, int width, int height, int rotate) {

    }

    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
//        if (faceU != null) {
//            faceU.effect(frame.data, frame.width, frame.height, FaceU.VIDEO_FRAME_FORMAT.I420);
//        }

        return true;
    }

    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
        return true;
    }

    @Override
    public void onAudioDeviceChanged(int device) {

    }

    @Override
    public void onReportSpeaker(Map<String, Integer> speakers, int mixedEnergy) {

    }

    @Override
    public void onAudioMixingEvent(int event) {

    }

    @Override
    public void onSessionStats(AVChatSessionStats sessionStats) {

    }

    @Override
    public void onLiveEvent(int event) {

    }


    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                AVChatSoundPlayer.instance().stop();
                finish();
            }
        }
    };


    private void showOrHideFaceULayout(boolean show) {
        ViewGroup vp = findView(R.id.avchat_video_face_unity);
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onTouch() {
    }
}

