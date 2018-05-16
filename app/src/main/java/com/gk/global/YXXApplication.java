package com.gk.global;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.DaoMaster;
import com.gk.beans.DaoSession;
import com.gk.beans.LoginBean;
import com.gk.beans.SubjectTypeBean;
import com.gk.beans.UniversityAreaBean;
import com.gk.beans.UniversityFeatureBean;
import com.gk.beans.UniversityLevelBean;
import com.gk.beans.UniversityTypeBean;
import com.gk.listener.GlidePauseOnScrollListener;
import com.gk.load.GlideImageLoader;
import com.gk.wxapi.Constant;
import com.gk.wxapi.WXEntryActivity;
import com.gk.wxapi.WXPayEntryActivity;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.List;

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
    public static IWXAPI sApi;
    public static IWXAPI payApi;
    public static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sApi = WXEntryActivity.initWeiXin(this, Constant.WECHAT_APPID);
        payApi = WXPayEntryActivity.initWeiXinPay(this, Constant.WECHAT_APPID);
        initLeakCanary();
        CrashHandler.instance().init();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
        setupGreenDao();
        initLoginBean();
        initImage();
        initAdsBean();
        initSubjectTypeBean();
        initUniversityAreaBean();
        initUniversityFeatureBean();
        initUniversityLevelBean();
        initUniversityTypeBean();
    }

    public static YXXApplication getInstance() {
        return instance;
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
                .setTitleBarBgColor(R.color.colorAccent)
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
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static FunctionConfig getFunctionConfig() {
        return functionConfig;
    }

    private void initUniversityAreaBean() {
        List<UniversityAreaBean> universityAreaBeanList = daoSession.getUniversityAreaBeanDao().loadAll();
        if (null == universityAreaBeanList || 0 == universityAreaBeanList.size()) {
            //daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(0, "不限"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(1, "北京"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(2, "天津"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(3, "上海"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(4, "重庆"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(5, "河北"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(6, "山西"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(7, "辽宁"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(8, "吉林"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(9, "黑龙江"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(10, "江苏"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(11, "浙江"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(12, "安徽"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(13, "福建"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(14, "江西"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(15, "山东"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(16, "河南"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(17, "湖北"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(18, "湖南"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(19, "广东"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(20, "海南"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(21, "四川"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(22, "贵州"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(23, "云南"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(24, "陕西"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(25, "甘肃"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(26, "青海"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(27, "台湾"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(28, "蒙古"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(29, "广西"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(30, "西藏"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(31, "宁夏"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(32, "新疆"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(33, "香港"));
            daoSession.getUniversityAreaBeanDao().insertOrReplace(new UniversityAreaBean(34, "澳门"));
        }
    }

    private void initUniversityFeatureBean() {
        List<UniversityFeatureBean> universityAreaBeanList = daoSession.getUniversityFeatureBeanDao().loadAll();
        if (null == universityAreaBeanList || 0 == universityAreaBeanList.size()) {
            daoSession.getUniversityFeatureBeanDao().insertOrReplace(new UniversityFeatureBean("985", 1));
            daoSession.getUniversityFeatureBeanDao().insertOrReplace(new UniversityFeatureBean("211", 2));
            daoSession.getUniversityFeatureBeanDao().insertOrReplace(new UniversityFeatureBean("双一流", 3));
        }
    }

    private void initUniversityLevelBean() {
        List<UniversityLevelBean> universityAreaBeanList = daoSession.getUniversityLevelBeanDao().loadAll();
        if (null == universityAreaBeanList || 0 == universityAreaBeanList.size()) {
            daoSession.getUniversityLevelBeanDao().insertOrReplace(new UniversityLevelBean("提前批", 1));
            daoSession.getUniversityLevelBeanDao().insertOrReplace(new UniversityLevelBean("一批", 2));
            daoSession.getUniversityLevelBeanDao().insertOrReplace(new UniversityLevelBean("二批", 3));
        }
    }

    private void initUniversityTypeBean() {
        List<UniversityTypeBean> universityAreaBeanList = daoSession.getUniversityTypeBeanDao().loadAll();
        if (null == universityAreaBeanList || 0 == universityAreaBeanList.size()) {
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("综合", 1));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("工科", 2));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("师范", 3));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("财经", 4));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("政法", 5));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("语言", 6));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("医药", 7));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("农业", 8));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("林业", 9));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("民族", 10));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("艺术", 11));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("体育", 12));
            daoSession.getUniversityTypeBeanDao().insertOrReplace(new UniversityTypeBean("军事", 13));
        }
    }

    private void initSubjectTypeBean() {
        List<SubjectTypeBean> subjectTypeBeans = daoSession.getSubjectTypeBeanDao().loadAll();
        if (null == subjectTypeBeans || 0 == subjectTypeBeans.size()) {
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("yuwen", "语文"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("shuxue", "数学"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("yingyu", "英语"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("lizong", "理综"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("wenzong", "文综"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("wuli", "物理"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("huaxue", "化学"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("lishi", "历史"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("dili", "地理"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("zhengzhi", "政治"));
            daoSession.getSubjectTypeBeanDao().insertOrReplace(new SubjectTypeBean("shengwu", "生物"));
        }
    }

    //定义List，用来存放所有Activity实例
    public List<Activity> activities = new ArrayList<>();

    //重写onTerminate()，将所有Activity实例finish掉
    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
