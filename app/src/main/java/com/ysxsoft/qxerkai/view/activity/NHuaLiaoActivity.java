package com.ysxsoft.qxerkai.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dialog.GiftSendDialog;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.function.voice.floatw.FloatViewService;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingDetailResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.utils.WYUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.PiPeiSuoDialog;
import com.ysxsoft.qxerkai.view.widget.PiPeiTipsDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ttt.qx.qxcall.QXCallApplication.onToast;

/**
 * 语音聊天页面
 */
public class NHuaLiaoActivity extends NBaseActivity implements AVChatStateObserver {

	private static final int UPDATE_CODE = 0x01;
	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.iv_public_titlebar_left_1)
	ImageView ivPublicTitlebarLeft1;
	@BindView(R.id.ll_public_titlebar_left)
	LinearLayout llPublicTitlebarLeft;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.tv_gonggao)
	TextView tvGonggao;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	@BindView(R.id.iv_public_titlebar_right_2)
	ImageView ivPublicTitlebarRight2;
	@BindView(R.id.ll_public_titlebar_right)
	LinearLayout llPublicTitlebarRight;
	@BindView(R.id.tv_public_titlebar_left)
	TextView tvPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_left_2)
	ImageView ivPublicTitlebarLeft2;
	@BindView(R.id.iv_public_titlebar_right_1)
	ImageView ivPublicTitlebarRight1;
	@BindView(R.id.tv_public_titlebar_right)
	TextView tvPublicTitlebarRight;
	@BindView(R.id.ll_public_titlebar)
	LinearLayout llPublicTitlebar;
	@BindView(R.id.luyin)
	TextView luyin;
	@BindView(R.id.time)
	TextView time;
	@BindView(R.id.lock)
	TextView lock;
	@BindView(R.id.moneyNum)
	TextView moneyNum;
	@BindView(R.id.listenerNum)
	TextView listenerNum;
	@BindView(R.id.otherAudio)
	TextView otherAudio;
	@BindView(R.id.myAudio)
	TextView myAudio;
	@BindView(R.id.sendDanMu)
	TextView sendDanMu;
	@BindView(R.id.sendLiWu)
	TextView sendLiWu;
	boolean speakerMode = false;//控制是否扩音
	///////////////////////////////////////////////////////////////////////////
	// 广播
	///////////////////////////////////////////////////////////////////////////
	LocalBroadcastManager localBroadcastManager;
	private String type;//当前页面类型
	private String roomName;//房间名称
	private String callerName;//发起人名称
	private String teamId;//发起人名称
	private int s = 0;//总运行时间
	private boolean serviceStart = false;
	private String targetId;//接收人id
	private boolean isAdmin = false;//是否是房主
	private List<String> members = new ArrayList<>();
	private int currentSuo = 0;//当前房间锁级别
	private int qzUserAccid;
	private int fUserAccid;

	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
				case UPDATE_CODE://每s更新时间
					s++;
					if (s > 60 && s % 60 == 0) {//每分钟购买
						buy();
					}
					handler.sendEmptyMessageDelayed(UPDATE_CODE, 1000);
					time.setText(parseTime(s));
					break;
			}
		}
	};

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("tag", "收到房主取消通知");
			String room=intent.getStringExtra("roomName");
			if(room!=null&&room.equals(roomName)){
				ToastUtils.showToast(NHuaLiaoActivity.this,"房主关闭房间！",1);
				finish();
			}
		}
	};

	public static void start(Context context, String roomName, String teamId, String type, String callerName, boolean isAdmin, List<String> members) {
		Intent intent = new Intent(context, NHuaLiaoActivity.class);
		intent.putExtra("roomName", roomName);
		intent.putExtra("teamId", teamId);
		intent.putExtra("type", type);
		intent.putExtra("callerName", callerName);
		intent.putExtra("isAdmin", isAdmin);
		intent.putStringArrayListExtra("members", (ArrayList<String>) members);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nhua_liao);
		ButterKnife.bind(this);
		parseIntent();
		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
		initView();
		showTips();
		register();//注册广播
		register(true);
		initData();
	}

	private void parseIntent() {
		roomName = getIntent().getStringExtra("roomName");
		teamId = getIntent().getStringExtra("teamId");
		type = getIntent().getStringExtra("type");
		callerName = getIntent().getStringExtra("callerName");
		isAdmin = getIntent().getBooleanExtra("isAdmin", false);//是否是房主
		members = getIntent().getStringArrayListExtra("members");//成员列表  不是房主时候才有
	}

	private void initTitleBar() {
		ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
		ivPublicTitlebarLeft1.setImageResource(R.mipmap.activity_hualiao_exit);
		llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();//离开房间
			}
		});
		tvPublicTitlebarCenter.setText("");
		ivPublicTitlebarRight2.setVisibility(View.VISIBLE);
		ivPublicTitlebarRight2.setImageResource(R.mipmap.activity_hualiao_right);
		llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				QXCallApplication.baseTime = System.currentTimeMillis() - (s * 1000);
				Intent intent = new Intent(NHuaLiaoActivity.this, FloatViewService.class);
				startService(intent);
				serviceStart = true;
				//不销毁 activity
				moveTaskToBack(true);
			}
		});
	}

	private void initView() {
		tvGonggao.setSelected(true);
	}

	/**
	 * 刚进来显示  禁止偷听请加锁弹窗
	 */
	private void showTips() {
		if (isAdmin) {//如果是管理员的话  点进去直接显示
			PiPeiTipsDialog tipDialog = new PiPeiTipsDialog(NHuaLiaoActivity.this, R.style.dialogHuaTiStyle);
			tipDialog.show(new PiPeiTipsDialog.OnPiPeiTipsDialogListener() {
				@Override
				public void sure() {
				}
			});
			Log.e("tag", "管理员弹窗首次提示");
		} else {
			Log.e("tag", "管理员提示");
		}
	}

	private void register() {
		localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
		localBroadcastManager.registerReceiver(receiver, new IntentFilter("com.ysxsoft.qxerkai.needexit"));
	}

	private void register(boolean register) {
		AVChatManager.getInstance().observeAVChatState(this, register);
	}

	///////////////////////////////////////////////////////////////////////////
	// 网络请求
	///////////////////////////////////////////////////////////////////////////

	private void initData() {
		getNotify();
		getDetail();
		handler.sendEmptyMessageDelayed(UPDATE_CODE, 1000);
	}

	/**
	 * 获取顶部公告
	 */
	private void getNotify() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "0");//	0公告 1随机话题 2老司机开车 3闺蜜私房语4两性研究社
		RetrofitTools.getNoticeList(map)
				.subscribe(new ResponseSubscriber<GetNoticeListResponse>() {
					@Override
					public void onSuccess(GetNoticeListResponse getNoticeListResponse, int code, String msg) {
						if (code == 200) {
							if (getNoticeListResponse != null && getNoticeListResponse.getData() != null) {
								List<GetNoticeListResponse.DataBeanX.DataBean> list = getNoticeListResponse.getData().getData();
								if (list != null && list.size() > 0) {
									StringBuilder sb = new StringBuilder();
									for (int i = 0; i < list.size(); i++) {
										sb.append(list.get(i).getContent() + "\t\t");
									}
									tvGonggao.setText(StringUtils.convert(sb.toString()));
								}
							}
						} else {
							ToastUtil.showToast(NHuaLiaoActivity.this, msg);
						}
					}

					@Override
					public void onStart() {
						super.onStart();
					}


					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	/**
	 * 获取偷听详情  刷新页面
	 */
	private void getDetail() {
		Map<String, String> map = new HashMap<>();
		map.put("tid", roomName);
		RetrofitTools.getTouTingDetail(map)
				.subscribe(new ResponseSubscriber<GetTouTingDetailResponse>() {
					@Override
					public void onSuccess(GetTouTingDetailResponse getTouTingDetailResponse, int code, String msg) {
						if (code == 200) {
							fillView(getTouTingDetailResponse);
						} else {
							ToastUtil.showToast(NHuaLiaoActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	/**
	 * 填充详情页
	 *
	 * @param getTouTingDetailResponse
	 */
	private void fillView(GetTouTingDetailResponse getTouTingDetailResponse) {
		if (getTouTingDetailResponse != null) {
			GetTouTingDetailResponse.DataBean data = getTouTingDetailResponse.getData();
			if (data == null) {
				return;
			}
			switch (data.getSuo()) {
				case 0://无锁
					lock.setText("加锁");
					currentSuo = 0;
					break;
				case 1://1级锁
					lock.setText("上锁");
					currentSuo = 1;
					break;
				case 2://2级锁
					lock.setText("已加锁");
					currentSuo = 2;
					break;
			}
			//消费豆子数量
			moneyNum.setText("已计费：" + data.getNum() + "砰砰豆");
			//收听数量
			if (data.getTtnum() == 0) {
				listenerNum.setText("暂未有人收听");
			} else {
				listenerNum.setText(data.getTtnum() + "人在收听");
			}
			/**
			 * 发起人信息
			 */
			GetTouTingDetailResponse.DataBean.UserBean userBean = data.getUser();
			qzUserAccid = data.getUid();
			/**
			 * 接收人信息
			 */
			GetTouTingDetailResponse.DataBean.FUserBean fUserBean = data.getF_user();
			fUserAccid = data.getFuid();
			//顶部title
			if(isAdmin){
				tvPublicTitlebarCenter.setText(fUserBean.getNick_name());
			}else{
				tvPublicTitlebarCenter.setText(userBean.getNick_name());
			}
		}
	}

	private String parseTime(int s) {
		String str = StringUtils.getM(s) + ":" + StringUtils.getS(s);
		return str;
	}

	/**
	 * 每分钟扣钱
	 */
	private void buy() {
		LogUtils.e("每分钟扣钱 房间名：" + roomName);
		Map<String, String> map = new HashMap<>();
		map.put("tid", roomName);//房间id
		RetrofitTools.piPeiBuy(map).subscribe(new ResponseSubscriber<BaseResponse>() {
			@Override
			public void onSuccess(BaseResponse baseResponse, int code, String msg) {
				if (code == 200) {
					Log.e("tag", "每分钟扣钱成功！");
					getDetail();
				} else {
				}
			}

			@Override
			public void onFailed(Throwable e) {
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("tag", "开始销毁");
		//如果收起服务已经开启 同时停止服务
		if (serviceStart) {
			stopService(new Intent(NHuaLiaoActivity.this, FloatViewService.class));
			serviceStart = false;
		}
		register(false);//取消注册
		unregister();
		leave();
	}

	private void unregister() {
		localBroadcastManager.unregisterReceiver(receiver);
	}

	//离开房间
	private void leave() {
		if (isAdmin) {//房主 需要通知别人关闭页面
			WYUtils.dismissTeam(teamId);//解散群
			members.add("10196");
			for (int i = 0; i < members.size(); i++) {
				if (DBUtils.getUserId().equals(members.get(i))) {//如果是管理员 不发送
					continue;
				}
				WYUtils.notifyToUser(members.get(i), roomName, callerName, members);//通知成员关闭
			}
		}
		WYUtils.leaveRoom2(roomName, new AVChatCallback<Void>() {//离开房间
			@Override
			public void onSuccess(Void aVoid) {
				Log.e("tag", "离开房间onSuccess");
				NHuaLiaoActivity.this.finish();
			}

			@Override
			public void onFailed(int i) {
				Log.e("tag", "离开房间onFailed");
			}

			@Override
			public void onException(Throwable throwable) {
				Log.e("tag", "离开房间异常" + throwable.getMessage());
			}
		});
	}

	@OnClick({R.id.luyin, R.id.lock, R.id.otherAudio, R.id.myAudio, R.id.sendDanMu, R.id.sendLiWu})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.luyin://录音
				break;
			case R.id.lock://上锁
				if (currentSuo == 2) {
					ToastUtil.showToast(NHuaLiaoActivity.this, "当前已上锁");
					return;
				}
				PiPeiSuoDialog dialog = new PiPeiSuoDialog(NHuaLiaoActivity.this, R.style.dialogHuaTiStyle);
				dialog.setType(currentSuo == 0 ? true : false);
				dialog.setMoney(currentSuo == 0 ? "20":"5");
				dialog.show(new PiPeiSuoDialog.OnPiPeiSuoDialogListener() {
					@Override
					public void lock() {
						suo();
					}
				});
				break;
			case R.id.otherAudio://他的声音   扩音
				AVChatManager.getInstance().setSpeaker(speakerMode = !speakerMode);
				break;
			case R.id.myAudio://我的声音
				break;
			case R.id.sendDanMu://发弹幕
				break;
			case R.id.sendLiWu://送礼物
				if (isAdmin) {//房主送给接听用户
					sendGift(fUserAccid);
				} else {//接听用户送给房主
					sendGift(qzUserAccid);
				}
				break;
		}
	}

	/**
	 * 匹配上锁
	 */
	private void suo() {
		Map<String, String> map = new HashMap<>();
		map.put("tid", roomName);
		map.put("user_id", DBUtils.getUserId());
		RetrofitTools.suo(map)
				.subscribe(new ResponseSubscriber<BaseResponse>() {
					@Override
					public void onSuccess(BaseResponse baseResponse, int code, String msg) {
						if (code == 200) {
							getDetail();
						} else {
							ToastUtil.showToast(NHuaLiaoActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	/**
	 * 发送礼物给用户
	 */
	private void sendGift(int id) {
		//获取当前用户账号 调用语音通话
		String accid = id + "";
		String authorization = "Bearer " + DBUtils.getUserToken();
		FindModel.getFindModel().getGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftList>() {
			@Override
			public void onNext(GiftList giftList) {
				if (giftList.getStatus_code() == 200) {
					GiftSendDialog.showBottomDialog(NHuaLiaoActivity.this, giftList.getData(), new GiftSendDialog.OnComponentClickListener() {
						@Override
						public void onCancle() {
						}

						@Override
						public void onSend(String gift_id) {
							FindModel.getFindModel().sendCallGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
								@Override
								public void onNext(StandardResponse response) {
									if (response.getStatus_code() == 200) {
										onToast("赠送成功！");
									} else {
										onToast(response.getMessage());
									}
								}
							}, NHuaLiaoActivity.this), gift_id, accid, authorization);
						}
					});
				} else {
					onToast(giftList.getMessage());
				}
			}
		}, NHuaLiaoActivity.this));
	}

	///////////////////////////////////////////////////////////////////////////
	// 房间事件监听
	///////////////////////////////////////////////////////////////////////////
	@Override
	public void onJoinedChannel(int i, String s, String s1, int i1) {
//		当前用户加入房间会返回服务器连接是否成功的回调
	}

	@Override
	public void onUserJoined(String s) {
		//用户加入房间
		Log.e("tag", "有人进入房间" + s);  //偷听
		members.add(s);//添加
		getDetail();
	}

	@Override
	public void onUserLeave(String s, int i) { //－1,用户超时离开  0,正常退出
		//用户离开房间
		Log.e("tag", "有人离开房间" + s);
		if(!s.equals(DBUtils.getUserId())){
			members.remove(s);
		}
		if(members.size()==1){
			ToastUtils.showToast(NHuaLiaoActivity.this,"对方已离开房间！",1);
			finish();
		}else{
			getDetail();
		}
	}

	@Override
	public void onLeaveChannel() {//当前用户离开房间通知
		Log.e("tag", "你离开了房间" + s);
	}

	@Override
	public void onProtocolIncompatible(int i) {
		//双方协议版本不兼容
//		0 自己版本过低 1 对方版本过低。
	}

	@Override
	public void onDisconnectServer() {
//		服务器断开回调
		ToastUtils.showToast(NHuaLiaoActivity.this, "服务器断开", 1);
		finish();
	}

	@Override
	public void onNetworkQuality(String s, int i, AVChatNetworkStats avChatNetworkStats) {

	}

	@Override
	public void onCallEstablished() {
//		音视频连接成功建立回调
	}

	@Override
	public void onDeviceEvent(int i, String s) {
//		音视频设备状态发生改变时，会回调 onDeviceEvent
	}

	@Override
	public void onTakeSnapshotResult(String s, boolean b, String s1) {
//		用户执行截图后会回调 onTakeSnapshotResult
	}

	@Override
	public void onConnectionTypeChanged(int i) {
//		本地客户端网络类型发生改变时回调，会通知当前网络类型。
	}

	@Override
	public void onAVRecordingCompletion(String s, String s1) {
	}

	@Override
	public void onAudioRecordingCompletion(String s) {

	}

	@Override
	public void onLowStorageSpaceWarning(long l) {

	}

	@Override
	public void onFirstVideoFrameAvailable(String s) {
//		当用户第一帧视频画面绘制前通知。

	}

	@Override
	public void onFirstVideoFrameRendered(String s) {
//		用户第一帧画面绘制后通知
	}

	@Override
	public void onVideoFrameResolutionChanged(String s, int i, int i1, int i2) {
//		用户视频画面分辨率改变通知
	}

	@Override
	public void onVideoFpsReported(String s, int i) {
//		用户视频帧率汇报
	}

	@Override
	public boolean onVideoFrameFilter(AVChatVideoFrame avChatVideoFrame, boolean b) {
//		采集视频数据回调
		return false;
	}

	@Override
	public boolean onAudioFrameFilter(AVChatAudioFrame avChatAudioFrame) {
//		采集语音数据回调
		return false;
	}

	@Override
	public void onAudioDeviceChanged(int i) {
//		语音播放设备变化通知
	}

	@Override
	public void onReportSpeaker(Map<String, Integer> map, int i) {
//		语音正在说话用户声音强度通知
	}

	@Override
	public void onAudioMixingEvent(int i) {
//伴音事件通知
	}

	@Override
	public void onSessionStats(AVChatSessionStats avChatSessionStats) {
//		实时统计信息汇报
		Log.e("tag", "会话时长：" + avChatSessionStats.sessionDuration);
	}

	@Override
	public void onLiveEvent(int i) {

	}
}
