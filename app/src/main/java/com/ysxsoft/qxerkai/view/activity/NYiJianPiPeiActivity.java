package com.ysxsoft.qxerkai.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.netease.nimlib.sdk.team.model.CreateTeamResult;
import com.netease.nimlib.sdk.team.model.Team;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GetPiPeiListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.utils.WYUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NYiJianPiPeiActivity extends AppCompatActivity {

	private final int U_TIME_CODE = 0x01;
	private final int NOT_FOUND = 0x02;
	@BindView(R.id.iv_zhuanquan)
	ImageView ivZhuanquan;
	@BindView(R.id.time)
	TextView time;
	private int delayTime = 1000;//1s
	private int totalTime = 60;//60s
	private int t;

	private String tName;//群组名称
	private String roomName = "";//房间名称
	private ArrayList<String> members = new ArrayList<>();//成员ID

	private Team team;//创建之后的群组

	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
				case U_TIME_CODE://修改时间
					if (t != 59) {
						t++;
						time.setText((totalTime - t) + "s");
						handler.sendEmptyMessageDelayed(U_TIME_CODE, delayTime);
					} else {//超过60s未匹配到 关闭页面
						handler.removeCallbacksAndMessages(U_TIME_CODE);
						dismissTeam();
						finish();
					}
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yi_jian_pi_pei);
		ButterKnife.bind(this);
		initStatusBar();
		initView();
		handler.obtainMessage(U_TIME_CODE).sendToTarget();
		getPiPeiList();//获取一键匹配列表
	}

	public void initStatusBar() {
		if (!SystemUtils.checkDeviceHasNavigationBar(this)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				/**
				 * Android 5.0以上，全透明状态栏
				 */
				SystemUtils.setTranslucentStatus(this);

			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				/**
				 * Android 4.4以上，半透明状态栏
				 */
				SystemUtils.setTranslucentStatus(this, true);
			}
		}
	}

	private void initView() {
		Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		animation.setRepeatCount(-1);//动画的重复次数
		animation.setInterpolator(new LinearInterpolator());
		ivZhuanquan.startAnimation(animation);//开始动画
	}

	/**
	 * 获取一键匹配列表
	 */
	private void getPiPeiList() {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());
		map.put("sex", DBUtils.getUserSex());
		RetrofitTools.getPiPeiList(map).subscribe(new ResponseSubscriber<GetPiPeiListResponse>() {
			@Override
			public void onSuccess(GetPiPeiListResponse getPiPeiListResponse, int code, String msg) {
				if (code == 200) {
					if (getPiPeiListResponse != null && getPiPeiListResponse.getData() != null) {
						List<GetPiPeiListResponse.DataBeanX.DataBean> list = getPiPeiListResponse.getData().getData();
						if (list != null) {
							members.clear();
							for (int i = 0; i < list.size(); i++) {
								members.add(list.get(i).getId() + "");
							}
							createRoom();//创建聊天室
						}
					}
				} else {
					ToastUtils.showToast(NYiJianPiPeiActivity.this, msg, 0);
				}
			}

			@Override
			public void onFailed(Throwable e) {
			}
		});
	}

	/**
	 * 创建多人音视频通话房间
	 */
	private void createRoom() {
		roomName = DBUtils.getUserId() + "-" + System.currentTimeMillis();//房间名称    用户id- 时间戳
		String extraMessage = "";

		AVChatManager.getInstance().createRoom(roomName, extraMessage, new AVChatCallback<AVChatChannelInfo>() {
			@Override
			public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
				Log.e("tag", "创建多人音视频通话成功!");
				createTeam(roomName);//创建群组   群组名称与多人音视频讨论组一样
			}

			@Override
			public void onFailed(int i) {
				LogUtil.e("tag", "onFailed:" + i);
			}

			@Override
			public void onException(Throwable throwable) {
			}
		});
	}

	/**
	 * 创建群组
	 */
	private void createTeam(String teamName) {
		// 群组类型
		TeamTypeEnum type = TeamTypeEnum.Normal;
		// 创建时可以预设群组的一些相关属性，如果是普通群，仅群名有效。
		// fields 中，key 为数据字段，value 对对应的值，该值类型必须和 field 中定义的 fieldType 一致
		HashMap<TeamFieldEnum, Serializable> fields = new HashMap<TeamFieldEnum, Serializable>();
		fields.put(TeamFieldEnum.Name, teamName);
		fields.put(TeamFieldEnum.Introduce, "砰砰用户" + DBUtils.getUserId() + "匹配群组资料");//群组介绍
		fields.put(TeamFieldEnum.VerifyType, VerifyTypeEnum.Free);//免费

		NIMClient.getService(TeamService.class).createTeam(fields, type, "", members).setCallback(new RequestCallback<CreateTeamResult>() {
			@Override
			public void onSuccess(CreateTeamResult createTeamResult) {
				//群组创建成功
				if (createTeamResult == null) {
					return;
				}
				team = createTeamResult.getTeam();
				addUser();//添加群成员
			}

			@Override
			public void onFailed(int i) {
			}

			@Override
			public void onException(Throwable throwable) {
			}
		});
	}

	private void addUser() {
		NIMClient.getService(TeamService.class).addMembers(team.getId(), members);
		notifyALlUser();
	}

	/**
	 * 通知所有用户
	 */
	private void notifyALlUser() {
		WYUtils.TeamJson teamJson = new WYUtils.TeamJson();
		teamJson.setTeamId(team.getId());
		members.add(DBUtils.getUserId());//把自己的id放进去
		teamJson.setMembers(members);
		teamJson.setRoomName(roomName);
		teamJson.setTeamName(team.getName());
		teamJson.setCallerName(DBUtils.getUserNickName());

		Log.e("tag", "发送通知");
		int size = members.size();
//		for (int i = 0; i < size; i++) {
//			String targetId = members.get(i);
//			if (!DBUtils.getUserId().equals(targetId)) {
//				WYUtils.notifyUserById(targetId, teamJson);
//			}
//		}
		WYUtils.notifyUserById("10164", teamJson);
	}

	public void onBack(View view) {
		handler.removeCallbacksAndMessages(U_TIME_CODE);
		dismissTeam();
		finish();
	}

	/**
	 * 解散群组
	 */
	private void dismissTeam() {
		if (team == null) {
			return;
		}
		NIMClient.getService(TeamService.class).dismissTeam(team.getId()).setCallback(new RequestCallback<Void>() {
			@Override
			public void onSuccess(Void param) {
				// 解散群成功
				Log.e("tag", "解散群组成功！");
			}

			@Override
			public void onFailed(int code) {
				// 解散群失败
			}

			@Override
			public void onException(Throwable exception) {
				// 错误
			}
		});
	}

	@Override
	protected void onPause() {
		overridePendingTransition(0, 0);
		super.onPause();
	}

}
