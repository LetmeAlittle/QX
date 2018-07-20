package com.ysxsoft.qxerkai.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WYUtils {

	/**
	 * 发送群组自定义通知
	 */
	public static void notifyTeam(TeamJson teamJson) {
		if (teamJson == null) {
			return;
		}
		// 构造自定义通知，指定接收者
		CustomNotification notification = new CustomNotification();
		notification.setSessionId(teamJson.getTeamId());
		notification.setSessionType(SessionTypeEnum.Team);
		// 设置该消息需要保证送达
		notification.setSendToOnlineUserOnly(false);
		// 设置 APNS 的推送文本
//		notification.setApnsText("the_content_for_apns");
		// 自定义推送属性
		Map<String, Object> pushPayload = new HashMap<>();
		pushPayload.put("teamId", teamJson.getTeamId());
		pushPayload.put("id", "3");
		pushPayload.put("roomName", teamJson.getRoomName());
		pushPayload.put("members", teamJson.getMembers());
		pushPayload.put("callerName", teamJson.getCallerName());
		notification.setPushPayload(pushPayload);
		// 发送自定义通知
		NIMClient.getService(MsgService.class).sendCustomNotification(notification);
	}

	/**
	 * 解析自定义通知 json转成对象
	 *
	 * @param jsonObject
	 */
	public static TeamJson parseCustom(JSONObject jsonObject) {
		TeamJson teamJson = new TeamJson();
		if (jsonObject != null) {
			JSONArray jsonArray = jsonObject.optJSONArray("members");
			if (jsonArray != null) {
				try {
					List<String> members = new ArrayList<>();
					for (int i = 0; i < jsonArray.length(); i++) {
						String member = jsonArray.getString(i);
						members.add(member);
					}
					teamJson.setMembers(members);//成员列表
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			teamJson.setRoomName(jsonObject.optString("room"));//房间名称
			teamJson.setCallerName(jsonObject.optString("NickName"));//成员名称
			teamJson.setTeamName(jsonObject.optString("teamName"));//群组名称
			teamJson.setTeamId(jsonObject.optString("teamId"));//群组id
			teamJson.setId(jsonObject.optString("id"));//固定值
			teamJson.setUserId(jsonObject.optString("userId"));//固定值
		}
		return teamJson;
	}

	/**
	 * 发送单聊自定义通知
	 */
	public static void notifyUserById(String targetId, TeamJson teamJson) {
		if (teamJson == null) {
			return;
		}
		// 构造自定义通知，指定接收者
		CustomNotification notification = new CustomNotification();
		notification.setSessionId(targetId);
		notification.setSessionType(SessionTypeEnum.P2P);
		// 设置该消息需要保证送达
		notification.setSendToOnlineUserOnly(false);
		// 设置 APNS 的推送文本
//		notification.setApnsText("the_content_for_apns");
		// 自定义推送属性
		Map<String, Object> pushPayload = new HashMap<>();
		pushPayload.put("room", teamJson.getRoomName());
		pushPayload.put("NickName", teamJson.getCallerName());
		pushPayload.put("teamName", teamJson.getTeamName());
		pushPayload.put("teamId", teamJson.getTeamId());
		pushPayload.put("id", "3");
		pushPayload.put("userId", teamJson.getUserId());//发起人id
		pushPayload.put("members", teamJson.getMembers());
		notification.setContent(new Gson().toJson(pushPayload));
		NIMClient.getService(MsgService.class).sendCustomNotification(notification);
	}

	/**
	 * 发送解散群自定义通知
	 */
	public static void notifyToUser(String targetId,String room,String nickName,List<String> members){
		if (targetId == null) {
			return;
		}
		// 构造自定义通知，指定接收者
		CustomNotification notification = new CustomNotification();
		notification.setSessionId(targetId);
		notification.setSessionType(SessionTypeEnum.P2P);
		// 设置该消息需要保证送达
		notification.setSendToOnlineUserOnly(false);
		// 设置 APNS 的推送文本
//		notification.setApnsText("the_content_for_apns");
		// 自定义推送属性
		Map<String, Object> pushPayload = new HashMap<>();
		pushPayload.put("room",room);
		pushPayload.put("NickName",nickName);
		pushPayload.put("members",members);
		pushPayload.put("id","4");
		notification.setContent(new Gson().toJson(pushPayload));
		NIMClient.getService(MsgService.class).sendCustomNotification(notification);
	}

	/**
	 * 离开房间
	 *
	 * @param roomName
	 * @param callback
	 */
	public static void leaveRoom2(String roomName, AVChatCallback<Void> callback) {
		AVChatManager.getInstance().leaveRoom2(roomName, callback);
	}

	/**
	 * 加入多人聊天室
	 */
	public static void joinRoom(String roomName, AVChatCallback<AVChatData> chatCallback) {
		//开启音视频引擎
		AVChatManager.getInstance().enableRtc();
//设置场景, 如果需要高清音乐场景，设置 AVChatChannelProfile#CHANNEL_PROFILE_HIGH_QUALITY_MUSIC
//		AVChatManager.getInstance.setChannelProfile(CHANNEL_PROFILE_DEFAULT);
		//设置通话可选参数
		AVChatParameters parameters = new AVChatParameters();
		AVChatManager.getInstance().setParameters(parameters);
//视频通话设置
//		AVChatManager.getInstance().enableVideo();
//		AVChatManager.getInstance().setupLocalVideoRender(IVideoRender render, boolean mirror, int scalingType);
		//设置视频采集模块
		//		AVChatCameraCapturer videoCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
		//		AVChatManager.getInstance().setupVideoCapturer(videoCapturer);
		//设置视频质量调整策略
		//		AVChatManager.getInstance().setVideoQualityStrategy(boolean preferImageQuality);
		//开启视频预览
		//		AVChatManager.getInstance().startVideoPreview();
		//加入房间
		AVChatManager.getInstance().joinRoom2(roomName, AVChatType.AUDIO, chatCallback);
	}

	///////////////////////////////////////////////////////////////////////////
	// 群组管理
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 解散群组
	 * @param teamId
	 */
	public static void dismissTeam(String teamId) {
		NIMClient.getService(TeamService.class).dismissTeam(teamId).setCallback(new RequestCallback<Void>() {
			@Override
			public void onSuccess(Void param) {
				// 解散群成功
				LogUtils.e("解散群组成功！");
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


	///////////////////////////////////////////////////////////////////////////
	// 自定义通知实体类
	///////////////////////////////////////////////////////////////////////////
	public static class TeamJson implements Serializable {
		private String teamId;
		private String roomName;
		private List<String> members;
		private String teamName;
		private String callerName;//呼叫人名字
		private String id;
		private String userId;

		public TeamJson() {
		}

		public String getUserId() {
			return userId == null ? "" : userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getId() {
			return id == null ? "" : id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTeamName() {
			return teamName == null ? "" : teamName;
		}

		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

		public String getTeamId() {
			return teamId == null ? "" : teamId;
		}

		public void setTeamId(String teamId) {
			this.teamId = teamId;
		}

		public String getRoomName() {
			return roomName == null ? "" : roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public List<String> getMembers() {
			if (members == null) {
				return new ArrayList<>();
			}
			return members;
		}

		public void setMembers(List<String> members) {
			this.members = members;
		}

		public String getCallerName() {
			return callerName == null ? "" : callerName;
		}

		public void setCallerName(String callerName) {
			this.callerName = callerName;
		}
	}
}