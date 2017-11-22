package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.DaoSession;
import com.gk.beans.LoginBean;
import com.gk.beans.VersionBean;
import com.gk.beans.VersionBeanDao;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.PackageUtils;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/10/23.
 */

public class SplashActivity extends SjmBaseActivity {

    private static DaoSession daoSession;

    @Override
    public int getResouceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarTransparent();
        if (isNewVersionExit()) {
            openNewActivity(NewFeatureActivity.class);
        } else {
            getAdsInfo();
        }
    }

    private boolean isNewVersionExit() {
        daoSession = YXXApplication.getDaoSession();
        VersionBeanDao versionBeanDao = daoSession.getVersionBeanDao();
        List<VersionBean> list = versionBeanDao.loadAll();
        if (list == null || list.size() == 0) { //第一次安装，本地数据是没有数据的，说明是最新的版本
            int firstVersionCode = PackageUtils.getVersionCode(this);
            updateVersion(firstVersionCode);
            return true;
        }
        int oldVersion = list.get(0).getVersionCode();
        int newVersion = PackageUtils.getVersionCode(this);
        if (newVersion > oldVersion) {//如果有更新版本，则更新本地库
            updateVersion(newVersion);
            return true;
        }
        return false;
    }

    private void getAdsInfo() {
        PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getAdsInfoList())
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<AdsBean.MDataBean> mDataBeans = JSON.parseArray(commonBean.getData().toString(), AdsBean.MDataBean.class);
        AdsBean.getInstance().setmContext(getApplicationContext()).saveAdsBean(mDataBeans);
        goMainActivity();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        goMainActivity();
    }

    private void goMainActivity() {
        LoginBean loginBean = LoginBean.getInstance();
        if (loginBean != null && loginBean.getUsername() != null) {
            openNewActivity(MainActivity.class);
        } else {
            Intent intent = new Intent();
            intent.putExtra(YXXConstants.ENTER_LOGIN_PAGE_FLAG, YXXConstants.FROM_SPLASH_FLAG);
            openNewActivityByIntent(LoginActivity.class, intent);
        }
    }

    public void updateVersion(int code) {
        VersionBean versionBean = new VersionBean();
        versionBean.setVersionCode(code);
        daoSession.getVersionBeanDao().insertOrReplace(versionBean);
    }
}
