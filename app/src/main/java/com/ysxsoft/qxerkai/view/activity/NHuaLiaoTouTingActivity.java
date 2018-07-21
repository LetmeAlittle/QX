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

import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.function.voice.floatw.FloatViewService;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingDetailResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.utils.WYUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.TouTingSuoDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Subscriber;

public class NHuaLiaoTouTingActivity extends NBaseActivity implements AVChatStateObserver {

	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.iv_public_titlebar_left_1)
	ImageView ivPublicTitlebarLeft1;
	@BindView(R.id.ll_public_titlebar_left)
	LinearLayout llPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_right_2)
	ImageView ivPublicTitlebarRight2;
	@BindView(R.id.ll_public_titlebar_right)
	LinearLayout llPublicTitlebarRight;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.tv_gonggao)
	TextView tvGonggao;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
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
	@BindView(R.id.leftIcon)
	CircleImageView leftIcon;
	@BindView(R.id.lock1)
	ImageView lock1;
	@BindView(R.id.shichang)
	TextView shichang;
	@BindView(R.id.rightIcon)
	CircleImageView rightIcon;
	@BindView(R.id.lock2)
	ImageView lock2;
	@BindView(R.id.luyin)
	TextView luyin;
	@BindView(R.id.touTingNum)
	TextView touTingNum;
	@BindView(R.id.taAudio)
	TextView taAudio;
	@BindView(R.id.sendDanMu)
	TextView sendDanMu;
	@BindView(R.id.leftState)
	TextView leftState;
	@BindView(R.id.rightState)
	TextView rightState;
	private String roomName;
	private String jiesuo;

	private boolean leftLocked = false;//左已经解锁
	private boolean rightlocked = false;//右已经解锁
	private GetTouTingDetailResponse.DataBean data;
	private boolean speakerMode;//是否扩音
	private int qzUserAccid;//房主id
	private int fUserAccid;//接收人id
	private LocalBroadcastManager localBroadcastManager;

	public final static int UPDATE_CODE=0x02;
	private int s;

	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
				case UPDATE_CODE://每s更新时间
					s++;
					if (s > 60 && s % 60 == 0) {//每分钟购买
//						if (isAdmin) {
//							buy();//管理员调用
//						}
					}
					handler.sendEmptyMessageDelayed(UPDATE_CODE, 1000);
					shichang.setText(parseTime(s));
					break;
			}
		}
	};

	private String parseTime(int s) {
		String str = StringUtils.getM(s) + ":" + StringUtils.getS(s);
		return str;
	}

	public static void start(Context context, String roomName){
		Intent intent=new Intent(context,NHuaLiaoTouTingActivity.class);
		intent.putExtra("roomName",roomName);
		context.startActivity(intent);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String room = intent.getStringExtra("roomName");
			if (room != null && room.equals(roomName)) {
				ToastUtils.showToast(NHuaLiaoTouTingActivity.this, "该房间已加锁！", 1);
				finish();
			}
		}
	};

	private void register() {
		localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
		localBroadcastManager.registerReceiver(receiver, new IntentFilter("com.ysxsoft.qxerkai.needexit"));
	}

	private void register(boolean register) {
		AVChatManager.getInstance().observeAVChatState(this, register);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nhua_liao_tou_ting);
		ButterKnife.bind(this);
		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
		initView();
		register();
		register(true);
		initData();
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
		tvPublicTitlebarCenter.setText("偷听中");
		ivPublicTitlebarRight2.setVisibility(View.VISIBLE);
		ivPublicTitlebarRight2.setImageResource(R.mipmap.activity_hualiao_right);
		llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				QXCallApplication.baseTime = System.currentTimeMillis();
				Intent intent = new Intent(NHuaLiaoTouTingActivity.this, FloatViewService.class);
				startService(intent);
				moveTaskToBack(true);
			}
		});
	}

	private void initView() {
	}

	private void initData() {
		roomName = getIntent().getStringExtra("roomName");//房间名称就是tid
		if(roomName==null){
			return;
		}
		WYUtils.joinRoom(roomName, new AVChatCallback<AVChatData>() {
			@Override
			public void onSuccess(AVChatData avChatData) {
				Log.e("tag","进入房间成功!");
			}

			@Override
			public void onFailed(int i) {
				Log.e("tag","onFailed!");
			}

			@Override
			public void onException(Throwable throwable) {
				Log.e("tag","进入房间onException!"+throwable.getMessage());
			}
		});
		getUserInfo();
		getNotify();
		getDetail();
		handler.sendEmptyMessageDelayed(UPDATE_CODE, 1000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		register(false);
		AVChatProfile.getInstance().setAVChatting(false);
		leave();
	}

	private void leave(){
		WYUtils.leaveRoom2(roomName, new AVChatCallback<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				Log.e("tag","离开房间onSuccess");
				finish();
			}

			@Override
			public void onFailed(int i) {
				Log.e("tag","离开房间onFailed");
			}

			@Override
			public void onException(Throwable throwable) {
				Log.e("tag","离开房间onException");
			}
		});
	}

	@OnClick({R.id.leftIcon, R.id.lock1, R.id.rightIcon, R.id.lock2, R.id.luyin, R.id.taAudio, R.id.sendDanMu})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.leftIcon://左边用户头像
				break;
			case R.id.lock1://左边头像解锁
				TouTingSuoDialog dialog=new TouTingSuoDialog(NHuaLiaoTouTingActivity.this,R.style.dialogHuaTiStyle);
				dialog.setMoney(""+js);
				dialog.show(new TouTingSuoDialog.OnTouTingSuoDialogListener() {
					@Override
					public void sure() {
						jiesuo(true);
					}
				});
				break;
			case R.id.rightIcon://右边用户头像
				break;
			case R.id.lock2://右边头像解锁
				if (data == null) {
					ToastUtils.showToast(NHuaLiaoTouTingActivity.this, "房间信息缺失!", 1);
					return;
				}
				if(rightlocked){
					ToastUtils.showToast(NHuaLiaoTouTingActivity.this, "已解锁!", 1);
					return;
				}
				TouTingSuoDialog dialog2=new TouTingSuoDialog(NHuaLiaoTouTingActivity.this,R.style.dialogHuaTiStyle);
				dialog2.setMoney(""+js);
				dialog2.show(new TouTingSuoDialog.OnTouTingSuoDialogListener() {
					@Override
					public void sure() {
						jiesuo(false);
					}
				});
				break;
			case R.id.luyin://录音
				break;
			case R.id.taAudio://扩音  他的声音
				AVChatManager.getInstance().setSpeaker(speakerMode = !speakerMode);
				break;
			case R.id.sendDanMu://发送弹幕
				sendText();
				break;
		}
	}

	/**
	 * 解锁头像
	 */
	private void jiesuo(boolean isLeft) {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());
		map.put("type", "");//1房间的 2录音的
		map.put("tid", roomName);//type 1房间的 2录音的 ：id
		RetrofitTools.jiesuo(map)
				.subscribe(new ResponseSubscriber<BaseResponse>() {
					@Override
					public void onSuccess(BaseResponse baseResponse, int code, String msg) {
						if (code == 200) {
							if (isLeft) {
								leftLocked = true;
							} else {
								rightlocked = true;
							}
							showImage();
						} else {
							ToastUtil.showToast(NHuaLiaoTouTingActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	///////////////////////////////////////////////////////////////////////////
	// 网络请求
	///////////////////////////////////////////////////////////////////////////
	/**
	 * 发弹幕
	 */
	private void sendText() {
		Map<String, String> s = new HashMap<>();
		s.put("user_id", DBUtils.getUserId());
		s.put("tid", roomName);
		s.put("title", "呵呵呵呵");//TODO:need更换
		RetrofitTools.fadanmu(s).subscribe(new ResponseSubscriber<BaseResponse>() {
			@Override
			public void onSuccess(BaseResponse baseResponse, int code, String msg) {
				if (code == 200) {
					ToastUtils.showToast(NHuaLiaoTouTingActivity.this, "发布成功", 1);
				} else {
					ToastUtils.showToast(NHuaLiaoTouTingActivity.this, baseResponse.getMessage(), 1);
				}
			}

			@Override
			public void onFailed(Throwable e) {
			}
		});
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
							ToastUtil.showToast(NHuaLiaoTouTingActivity.this, msg);
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
							ToastUtil.showToast(NHuaLiaoTouTingActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	///////////////////////////////////////////////////////////////////////////
	// 填充视图
	///////////////////////////////////////////////////////////////////////////
	/**
	 * 填充详情页
	 *
	 * @param getTouTingDetailResponse
	 */
	private void fillView(GetTouTingDetailResponse getTouTingDetailResponse) {
		if (getTouTingDetailResponse != null) {
			data = getTouTingDetailResponse.getData();
			if (data == null) {
				return;
			}
			showImage();
			leftState.setText(leftLocked ? "已解锁" : "未解锁");
			rightState.setText(rightlocked ? "已解锁" : "未解锁");
			//偷听数量
			touTingNum.setText(data.getTtnum() + "人在偷听");

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
		}
	}

	/**
	 * 显示两人头像
	 */
	private void showImage() {
		// 发起人头像
		if (leftLocked) {
			Glide.with(NHuaLiaoTouTingActivity.this).load(data.getUser().getIcon())
					.into(leftIcon);
			lock1.setVisibility(View.GONE);
		} else {
			Glide.with(NHuaLiaoTouTingActivity.this).load(data.getUser().getIcon())
					.bitmapTransform(new BlurTransformation(NHuaLiaoTouTingActivity.this, 15))
					.into(leftIcon);
			lock1.setVisibility(View.VISIBLE);
		}
		// 接收人头像
		if (rightlocked) {
			Glide.with(NHuaLiaoTouTingActivity.this).load(data.getF_user().getIcon())
					.into(rightIcon);
			lock2.setVisibility(View.GONE);
		} else {
			Glide.with(NHuaLiaoTouTingActivity.this).load(data.getF_user().getIcon())
					.bitmapTransform(new BlurTransformation(NHuaLiaoTouTingActivity.this, 15))
					.into(rightIcon);
			lock2.setVisibility(View.VISIBLE);
		}
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
		getDetail();
	}

	@Override
	public void onUserLeave(String s, int i) { //－1,用户超时离开  0,正常退出
		//用户离开房间
		Log.e("tag", "有人离开房间" + s);
	}

	@Override
	public void onLeaveChannel() {//当前用户离开房间通知
		Log.e("tag", "你离开了房间" );
	}

	@Override
	public void onProtocolIncompatible(int i) {
		//双方协议版本不兼容
//		0 自己版本过低 1 对方版本过低。
	}

	@Override
	public void onDisconnectServer() {
//		服务器断开回调
		ToastUtils.showToast(NHuaLiaoTouTingActivity.this, "服务器断开", 1);
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

	///////////////////////////////////////////////////////////////////////////
	// 获取个人信息
	///////////////////////////////////////////////////////////////////////////
	private int js;//解锁头像价格
	private void getUserInfo() {
		String authorization = "Bearer " + DBUtils.getUserToken();
		//初始化金币数
		HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(UserDetailInfo userDetailInfo) {
				if (userDetailInfo.getStatus_code() == 200) {
//					price.setText("抛一次话题收取"+StringUtils.convert(""+userDetailInfo.getData().getPaohuati())+"砰砰豆");
					js=userDetailInfo.getData().getJs();
				}
			}
		}, "", authorization);
	}
}
