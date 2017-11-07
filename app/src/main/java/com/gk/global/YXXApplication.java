package com.gk.global;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.gk.beans.DaoMaster;
import com.gk.beans.DaoSession;
import com.gk.beans.VersionBean;
import com.gk.tools.AppManager;


/**
 * Created by wei on 2016/5/27.
 */
public class YXXApplication extends Application {

    private static YXXApplication instance;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupGreenDao();
        initAppManager();
        //insertVersionTest();
    }

    public static YXXApplication getInstance() {
        return instance;
    }

    /**
     * 初始化管理Activity工具类
     */
    private void initAppManager() {
        AppManager.getAppManager();
    }

    /**
     * 配置数据库GreenDao
     */
    private void setupGreenDao() {
        //创建数据库yxx.db （创建SQLite数据库的SQLiteOpenHelper的具体实现）
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "yxx.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象（GreenDao的顶级对象，作为数据库对象、用于创建表和删除表）
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者（管理所有的Dao对象，Dao对象中存在着增删改查等API）
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public void insertVersionTest() {
        VersionBean versionBean = new VersionBean();
        versionBean.setVersionCode(2);
        daoSession.getVersionBeanDao().insertOrReplace(versionBean);
    }
}
