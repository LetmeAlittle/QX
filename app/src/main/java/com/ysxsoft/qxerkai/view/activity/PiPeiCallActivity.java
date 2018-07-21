package com.ysxsoft.qxerkai.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.UserPreferences;
import com.netease.nim.uikit.session.audio.MessageAudioControl;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.voice.AVChatSoundPlayer;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetPiPeiListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.utils.WYUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PiPeiCallActivity extends AppCompatActivity {
	@BindView(R.id.name)
	TextView name;
	@BindView(R.id.content)
	TextView content;
	@BindView(R.id.accept)
	ImageView accept;
	@BindView(R.id.refuse)
	ImageView refuse;
	///////////////////////////////////////////////////////////////////////////
	// 广播
	///////////////////////////////////////////////////////////////////////////
	LocalBroadcastManager localBroadcastManager;
	private String roomName;
	private String callerName;//发起人名称
	private String userId;//发起人id
	private String teamId;//群组id
	private String type;//类型 1系统匹配2专属匹配3角色扮演
	private String role, story, userId2, teamName,userIcon;

	private ArrayList<String> members = new ArrayList<>();
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("tag", "收到房主取消通知");
			finish();
		}
	};

	public static void start(Context context, String callerName, String roomName, String userId, String type, String teamId, List<String> members) {
		Intent intent = new Intent(context, PiPeiCallActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("callerName", callerName);
		intent.putExtra("roomName", roomName);//房间名称
		intent.putExtra("userId", userId);//房主用户Id
		intent.putExtra("type", type);//类型
		intent.putExtra("teamId", teamId);//类型   1系统匹配2专属匹配3角色扮演
		intent.putStringArrayListExtra("members", (ArrayList<String>) members);//类型
		context.startActivity(intent);
	}

	/**
	 * 角色扮演
	 *
	 * @param context
	 * @param teamId
	 * @param members
	 */
	public static void start(Context context, List<String> members, String role, String teamId, String story, String teamName,String userIcon) {
		Intent intent = new Intent(context, PiPeiCallActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("role", role);//0代表左边  1代表右边
		intent.putExtra("teamId", teamId);//发起人id
		intent.putExtra("story", story);////故事类型 0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书
		intent.putExtra("teamName", teamName);//发起人名称
		intent.putStringArrayListExtra("members", (ArrayList<String>) members);//类型

		intent.putExtra("userIcon",userIcon);//发起人用户头像
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pi_pei_call);
		ButterKnife.bind(this);
		initAudio();
		parseIntent();
		register();
	}

	/**
	 * 初始化语音播报
	 */
	private void initAudio() {
//		UserPreferences.setEarPhoneModeEnable(true);
//		MessageAudioControl.getInstance(PiPeiCallActivity.this).setEarPhoneModeEnable(true);
		AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
	}

	private void parseIntent() {
		roomName = getIntent().getStringExtra("roomName");
		callerName = getIntent().getStringExtra("callerName");
		userId = getIntent().getStringExtra("userId");
		teamId = getIntent().getStringExtra("teamId");
		type = getIntent().getStringExtra("type");
		members = getIntent().getStringArrayListExtra("members");

		//角色扮演
		role = getIntent().getStringExtra("role");//0代表左边  1代表右边
		story = getIntent().getStringExtra("story");//故事类型 0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书
		userId2 = getIntent().getStringExtra("teamId");//发起人id
		teamName = getIntent().getStringExtra("teamName");//发起人名称
		userIcon = getIntent().getStringExtra("userIcon");//发起人头像

		if(story==null){
			content.setText("正在呼叫你");
			name.setText(StringUtils.convert(callerName));
		}else{
			content.setText("邀请你进行角色扮演");
			name.setText(StringUtils.convert(teamName));
		}
	}

	private void register() {
		localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
		localBroadcastManager.registerReceiver(receiver, new IntentFilter("com.ysxsoft.qxerkai.needexit"));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeAudio();
		unregister();
	}

	private void closeAudio() {
		AVChatSoundPlayer.instance().stop();
	}

	private void unregister() {
		localBroadcastManager.unregisterReceiver(receiver);
	}

	@OnClick({R.id.accept, R.id.refuse})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.accept:
				if(story==null){
					check();//先判断房间能不能加   一键匹配 专属匹配
				}else{
					check2();//角色扮演
				}
				break;
			case R.id.refuse:
				ToastUtil.showToast(this, "已挂断！");
				finish();
				break;
		}
	}

	/**
	 * 检测房间能不能加入
	 */
	private void check() {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", userId);//发起人id
		map.put("f_user_id", DBUtils.getUserId());//接收人用户id
		map.put("tid", roomName);//房间id
		map.put("type", type);//1系统匹配2专属匹配3角色扮演
		map.put("meetingname", teamId);//群组id
		RetrofitTools.startPiPei(map).subscribe(new ResponseSubscriber<BaseResponse>() {
			@Override
			public void onSuccess(BaseResponse baseResponse, int code, String msg) {
				if (code == 200) {
					//跳转至多人聊天
					joinRoom();
				} else {
					//该房间已经有人存在   不能加入
					ToastUtils.showToast(PiPeiCallActivity.this, "加入失败！已经有人进入该房间！", 0);
				}
			}

			@Override
			public void onFailed(Throwable e) {
			}
		});
	}


	/**
	 * 检测房间能不能加入
	 */
	private void check2() {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());//用户的id
		map.put("f_user_id", teamId);//发起人用户id
		RetrofitTools.acceptJiaoSeCheck(map).subscribe(new ResponseSubscriber<BaseResponse>() {
			@Override
			public void onSuccess(BaseResponse baseResponse, int code, String msg) {
				if (code == 200) {
					Log.e("tag","检测房间");
					//跳转至多人聊天
					NimUIKit.startP2PSessionWithJiaoSe(PiPeiCallActivity.this, members, role,teamId, story, teamName,userIcon);//携带对方id 对方名字
					finish();
				} else {
					//该房间已经有人存在   不能加入
					ToastUtils.showToast(PiPeiCallActivity.this, "加入失败！", 0);
				}
			}

			@Override
			public void onFailed(Throwable e) {
			}
		});
	}


	/**
	 * 添加至多人聊天室
	 */
	private void joinRoom() {
		WYUtils.joinRoom(roomName, new AVChatCallback<AVChatData>() {
			@Override
			public void onSuccess(AVChatData avChatData) {
				if ("1".equals(type)) {//系统匹配
					closeAudio();//关闭响铃
					NHuaLiaoActivity.start(PiPeiCallActivity.this, roomName, teamId, type, callerName, false, members);
					finish();
				} else if ("2".equals(type)) {//1系统匹配2专属匹配3角色扮演
					closeAudio();//关闭响铃
					NHuaLiaoActivity.start(PiPeiCallActivity.this, roomName, teamId, type, callerName, false, members);
					finish();
				} else if ("3".equals(type)) {
					closeAudio();//关闭响铃
					NHuaLiaoActivity.start(PiPeiCallActivity.this, roomName, teamId, type, callerName, false, members);
					finish();
				}
			}

			@Override
			public void onFailed(int i) {
				ToastUtil.showToast(PiPeiCallActivity.this, "加入失败！");
			}

			@Override
			public void onException(Throwable throwable) {
				ToastUtil.showToast(PiPeiCallActivity.this, "加入失败！");
			}
		});
	}
}
