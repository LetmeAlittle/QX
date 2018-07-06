package com.ysxsoft.qxerkai.utils;

import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;

public class DBUtils {
    /**
     * 获取用户Id
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
}
