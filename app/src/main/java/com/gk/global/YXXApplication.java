package com.gk.global;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.DaoMaster;
import com.gk.beans.DaoSession;
import com.gk.beans.LoginBean;
import com.gk.listener.GlidePauseOnScrollListener;
import com.gk.load.GlideImageLoader;
import com.gk.tools.AppManager;
import com.gk.wxapi.Constant;
import com.gk.wxapi.WXEntryActivity;
import com.gk.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;


/**
 * Created by wei on 2016/5/27.
 */
public class YXXApplication extends Application {

    private static YXXApplication instance;
    private static DaoSession daoSession;
    private static FunctionConfig functionConfig;
    private static CoreConfig coreConfig;
    public static IWXAPI sApi;
    public static IWXAPI payApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sApi = WXEntryActivity.initWeiXin(this, Constant.WECHAT_APPID);
        payApi = WXPayEntryActivity.initWeiXinPay(this, Constant.WECHAT_APPID);
        setupGreenDao();
        initAppManager();
        initLoginBean();
        initImage();
        initAdsBean();
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
     * 初始化LoginBean
     */
    private void initLoginBean() {
        LoginBean.getInstance().load();
    }

    /**
     * 初始化AdsBean
     */
    private void initAdsBean() {
        AdsBean.getInstance().load();
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

    private void initImage() {
        //设置主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
                .setTitleBarTextColor(Color.BLACK)
                .setTitleBarIconColor(Color.BLACK)
                .setFabNornalColor(Color.RED)
                .setFabPressedColor(Color.BLUE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .setIconBack(R.drawable.ic_action_previous_item)
                .setIconRotate(R.drawable.ic_action_repeat)
                .setIconCrop(R.drawable.ic_action_crop)
                .setIconCamera(R.drawable.ic_action_camera)
                .build();
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static FunctionConfig getFunctionConfig() {
        return functionConfig;
    }

    public static CoreConfig getCoreConfig() {
        return coreConfig;
    }
}
