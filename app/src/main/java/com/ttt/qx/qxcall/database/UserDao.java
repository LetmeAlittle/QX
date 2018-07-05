package com.ttt.qx.qxcall.database;

import com.ttt.qx.qxcall.dbbean.UserBean;

import org.xutils.ex.DbException;

/**
 * 用户实体本地数据库操作
 * Created by wyd on 2017/8/14.
 */

public class UserDao extends BaseDao {
    public UserDao() {
        super();
    }

    /**
     * 添加用户数据缓存
     *
     * @param userBean
     */
    public void add(UserBean userBean) {
        try {
            db.save(userBean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户表数据 第一条 也只会有一条
     *
     * @return
     */
    public UserBean queryFirstData() {
        UserBean userBean = null;
        try {
            userBean = db.findFirst(UserBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return userBean;
    }

    /**
     * 清空数据表数据
     */
    public void deleteAll() {
        try {
            db.executeUpdateDelete("Delete From UserBean");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    /**
     * 更新
     *
     * @param bean
     */
    public void update(UserBean bean) {
        try {
            db.update(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户删除操作
     * @param bean
     */
    public void delete(UserBean bean) {
        try {
            db.delete(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
