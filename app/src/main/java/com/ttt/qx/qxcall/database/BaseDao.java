package com.ttt.qx.qxcall.database;


import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by wyd on 2107/8/14.
 */
public class BaseDao {
    protected static DbManager db;

    public BaseDao() {
        if (db==null) {
            db = x.getDb(XUtil.getDaoConfig());
        }
    }
}
