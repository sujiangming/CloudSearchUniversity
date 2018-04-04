package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LocalVersionBean;
import com.gk.beans.LoginBean;
import com.gk.beans.VersionResultBean;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.AppUpdateUtils;
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
        goWinByVersion();
    }

    private String userName;
    private String password;

    private PresenterManager presenterManager= new PresenterManager().setmIView(this);

    private void autoLogin() {
        LoginBean loginBean = LoginBean.getInstance();
        if (loginBean == null) {
            openNewActivity(LoginActivity.class);
            return;
        }
        userName = LoginBean.getInstance().getUsername();
        if (TextUtils.isEmpty(userName)) {
            openNewActivity(LoginActivity.class);
            return;
        }

        password = LoginBean.getInstance().getPassword();
        if (TextUtils.isEmpty(password)) {
            openNewActivity(LoginActivity.class);
            return;
        }

        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        jsonObject.put("password", password);
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).login(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    private void getAdsInfo() {
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getAdsInfoList())
                .request(YXXConstants.INVOKE_API_THREE_TIME);
    }

    private void checkVersion() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", PackageUtils.getVersionName(this));
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).checkVersion(jsonObject.toJSONString()))
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
                LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
                LoginBean.getInstance().saveLoginBean(loginBean);
                LoginBean.getInstance().setPassword(password).save();
                getAdsInfo();
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                List<AdsBean.MDataBean> mDataBeans = JSON.parseArray(commonBean.getData().toString(), AdsBean.MDataBean.class);
                AdsBean.getInstance().saveAdsBean(mDataBeans);
                checkVersion();
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                VersionResultBean versionResultBean = JSON.parseObject(commonBean.getData().toString(), VersionResultBean.class);
                AppUpdateUtils.updateVersion(versionResultBean);//有更新
                openNewActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        if (order == YXXConstants.INVOKE_API_THREE_TIME) {
            openNewActivity(MainActivity.class);
        } else if (order == YXXConstants.INVOKE_API_FORTH_TIME) {
            openNewActivity(MainActivity.class);
        } else {
            openNewActivity(LoginActivity.class);
        }
        hideProgress();
    }

    private void goWinByVersion() {
        if (isNewVersion()) {
            openNewActivity(NewFeatureActivity.class);
        } else {
            autoLogin();
        }
    }

    private boolean isNewVersion() {
        LocalVersionBean localVersionBean = YXXApplication.getDaoSession().getLocalVersionBeanDao().queryBuilder().unique();
        String currentVersion = PackageUtils.getVersionName(this);
        if (null == localVersionBean || TextUtils.isEmpty(localVersionBean.getVersionName())) {
            localVersionBean = new LocalVersionBean();
            localVersionBean.setVersionName(currentVersion);
            YXXApplication.getDaoSession().getLocalVersionBeanDao().insertOrReplace(localVersionBean);
            return true;
        }
        if (!currentVersion.equals(localVersionBean.getVersionName())) {
            localVersionBean.setVersionName(currentVersion);
            YXXApplication.getDaoSession().getLocalVersionBeanDao().insertOrReplace(localVersionBean);
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenterManager && null != presenterManager.getCall()){
            presenterManager.getCall().cancel();
        }
    }
}
