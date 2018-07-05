package com.ttt.qx.qxcall.database;

import android.os.Environment;

import org.xutils.DbManager;

import java.io.File;

/**
 * 数据库操作工具类
 * Created by wyd on 2017/8/14.
 */

public class XUtil {
    static DbManager.DaoConfig daoConfig;
    public static final int BUFFER_SIZE = 400000;
    public static final int versionCode = 1;
    public static final String DB_NAME = "qxcall.db";//"qxcall.db";//保存的数据库文件名
    public static final String PACKAGE_NAME = "com.ttt.qx.qxcall";// /包名
    //数据库存储路径
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;
    public static DbManager.DaoConfig getDaoConfig() {
        File file = new File(DB_PATH);
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(DB_NAME)
                    .setDbDir(file)
                    .setDbVersion(versionCode)
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }
}
