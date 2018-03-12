package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.SaltBean;
import com.gk.beans.VersionBean;
import com.gk.beans.VersionResultBean;
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

    @Override
    public int getResouceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarTransparent();
        checkVersion();
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

    private void checkVersion() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", PackageUtils.getVersionName(this));
        PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).checkVersion(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_FORTH_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (1 != commonBean.getStatus()) {
            return;
        }
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
            case YXXConstants.INVOKE_API_FORTH_TIME:
                VersionResultBean versionResultBean = JSON.parseObject(commonBean.getData().toString(), VersionResultBean.class);
                if (isNewVersion(versionResultBean)) {//有更新
                    openNewActivity(NewFeatureActivity.class);
                } else {
                    autoLogin();
                }
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        openNewActivity(LoginActivity.class);
        hideProgress();
    }

    private boolean isNewVersion(VersionResultBean versionResultBean) {
        boolean isNew = false;
        String newVersion = versionResultBean.getVersion();
        String oldVersion = PackageUtils.getVersionName(this);
        String[] oldVersionArray = oldVersion.split("\\.");
        String[] newVersionArray = newVersion.split("\\.");

        if (oldVersionArray.length >= newVersionArray.length) {
            for (int i = 0; i < newVersionArray.length; i++) {
                int oldIndex = Integer.valueOf(oldVersionArray[i]);
                int newIndex = Integer.valueOf(newVersionArray[i]);
                if (newIndex > oldIndex) {//如果有更新版本，则更新本地库
                    updateVersion(versionResultBean);
                    isNew = true;
                }
            }
        } else {
            for (int i = 0; i < oldVersionArray.length; i++) {
                int oldIndex = Integer.valueOf(oldVersionArray[i]);
                int newIndex = Integer.valueOf(newVersionArray[i]);
                if (newIndex > oldIndex) {//如果有更新版本，则更新本地库
                    updateVersion(versionResultBean);
                    isNew = true;
                }
            }
        }

        return isNew;
    }

    public void updateVersion(VersionResultBean versionResultBean) {
        VersionBean versionBean = new VersionBean();
        versionBean.setVersionCode(versionResultBean.getVersion());
        versionBean.setDownUrl(versionResultBean.getDownUrl());
        versionBean.setPublishTime(versionResultBean.getPublishTime());
        versionBean.setUpdateContent(versionResultBean.getUpdateContent());
        YXXApplication.getDaoSession().getVersionBeanDao().insertOrReplace(versionBean);
    }
}
