package com.ysxsoft.qxerkai.utils;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

import org.json.JSONObject;

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
		pushPayload.put("id","3");
		pushPayload.put("roomName", teamJson.getRoomName());
		pushPayload.put("members", teamJson.getMembers());
		pushPayload.put("callerName", teamJson.getCallerName());
		notification.setPushPayload(pushPayload);
		// 发送自定义通知
		NIMClient.getService(MsgService.class).sendCustomNotification(notification);
	}

	/**
	 * 发送单聊自定义通知
	 */
	public static void notifyUserById(String targetId,TeamJson teamJson) {
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
//		pushPayload.put("teamId", teamJson.getTeamId());
//		pushPayload.put("teamName", teamJson.getTeamId());
//		pushPayload.put("roomName", teamJson.getRoomName());
//		pushPayload.put("members", teamJson.getMembers());
//		pushPayload.put("callerName", teamJson.getCallerName());
		pushPayload.put("room",teamJson.getRoomName());
		pushPayload.put("NickName",teamJson.getCallerName());
		pushPayload.put("teamName",teamJson.getTeamName());
		pushPayload.put("teamId",teamJson.getTeamId());
		pushPayload.put("id","3");
		pushPayload.put("members",teamJson.getMembers());
//		notification.setPushPayload(new HashMap<>());
		notification.setContent(new Gson().toJson(teamJson));
		// 发送自定义通知
		NIMClient.getService(MsgService.class).sendCustomNotification(notification);
	}

	public static class TeamJson {
		private String teamId;
		private String roomName;
		private List<String> members;
		private String teamName;
		private String callerName;//呼叫人名字

		public String getTeamName() {
			return teamName == null ? "" : teamName;
		}

		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

		public TeamJson() {
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
