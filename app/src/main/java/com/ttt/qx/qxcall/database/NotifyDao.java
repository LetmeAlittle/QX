package com.ttt.qx.qxcall.database;

import com.ttt.qx.qxcall.dbbean.NotifyBean;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * 用户实体本地数据库操作
 * Created by wyd on 2017/8/14.
 */

public class NotifyDao extends BaseDao {
    public NotifyDao() {
        super();
    }

    /**
     * 添加用户数据缓存
     *
     * @param notifyBean
     */
    public void add(NotifyBean notifyBean) {
        try {
            db.save(notifyBean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户表数据 第一条 也只会有一条
     *
     * @return
     */
    public NotifyBean queryFirstData() {
        NotifyBean bean = null;
        try {
            bean = db.findFirst(NotifyBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 查询用户表数据 第一条 也只会有一条
     *
     * @return
     */
    public List<NotifyBean> selectAll() {
        List<NotifyBean> bean = null;
        try {
            bean = db.findAll(NotifyBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 清空数据表数据
     */
    public void deleteAll() {
        try {
            db.executeUpdateDelete("Delete From NotifyBean");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新
     *
     * @param bean
     */
    public void update(NotifyBean bean) {
        try {
            db.update(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户删除操作
     *
     * @param bean
     */
    public void delete(NotifyBean bean) {
        try {
            db.delete(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
