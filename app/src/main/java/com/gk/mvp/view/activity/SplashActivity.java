package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.DaoSession;
import com.gk.beans.LoginBean;
import com.gk.beans.SaltBean;
import com.gk.beans.VersionBean;
import com.gk.beans.VersionBeanDao;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.MD5Util;
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
            //getAdsInfo();
            autoLogin();
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


    private String userName;
    private String password;

    private void autoLogin() {
        LoginBean loginBean = LoginBean.getInstance();
        if (loginBean == null) {
            openNewActivity(LoginActivity.class);
            return;
        }
        userName = LoginBean.getInstance().getUsername();
        password = LoginBean.getInstance().getPassword();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            openNewActivity(LoginActivity.class);
            return;
        }
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getSalt(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    private void getAdsInfo() {
        PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getAdsInfoList())
                .request(YXXConstants.INVOKE_API_THREE_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                SaltBean saltBean = JSON.parseObject(commonBean.getData().toString(), SaltBean.class);
                String pwd = userName + saltBean.getSalt() + password;// + password;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", userName);
                jsonObject.put("password", MD5Util.encrypt(pwd));
                PresenterManager.getInstance()
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).login(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_SECOND_TIME);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
                LoginBean.getInstance().saveLoginBean(loginBean);
                LoginBean.getInstance().setPassword(password).save();
                getAdsInfo();
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                List<AdsBean.MDataBean> mDataBeans = JSON.parseArray(commonBean.getData().toString(), AdsBean.MDataBean.class);
                AdsBean.getInstance().saveAdsBean(mDataBeans);
                openNewActivity(MainActivity.class);
                break;
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        openNewActivity(LoginActivity.class);
        hideProgress();
    }

    public void updateVersion(int code) {
        VersionBean versionBean = new VersionBean();
        versionBean.setVersionCode(code);
        daoSession.getVersionBeanDao().insertOrReplace(versionBean);
    }
}
