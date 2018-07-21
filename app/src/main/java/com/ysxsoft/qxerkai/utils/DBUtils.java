package com.ysxsoft.qxerkai.utils;

import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;

public class DBUtils {
	/**
	 * 获取用户Id
	 *
	 * @return
	 */
	public static String getUserId() {
		String userId = "";
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null && userBean.getUserId() != null) {
			userId = "" + userBean.getUserId();
		} else {
			userId = "";
		}
		return userId;
	}

	public static String getUserToken() {
		String userToken = "";
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null && userBean.getToken() != null) {
			userToken = "" + userBean.getToken();
		} else {
			userToken = "";
		}
		return userToken;
	}

	public static int getIntUserId() {
		int userId = -1;
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null && userBean.getUserId() != null) {
			userId = userBean.getUserId();
		}
		return userId;
	}

	public static String getUserSex() {
		String memberSex = "";
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null && userBean.getMember_sex() != null) {
			memberSex = "" + userBean.getMember_sex();
		} else {
			memberSex = "";
		}
		return memberSex;
	}

	public static String getUserNickName() {
		String userName = "";
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null && userBean.getNick_name() != null) {
			userName = "" + userBean.getNick_name();
		} else {
			userName = "";
		}
		return userName;
	}

	public static String getUserAvatar() {
		String avatar = "";
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null && userBean.getMember_avatar() != null) {
			avatar = "" + userBean.getMember_avatar();
		} else {
			avatar = "";
		}
		return avatar;
	}
}
