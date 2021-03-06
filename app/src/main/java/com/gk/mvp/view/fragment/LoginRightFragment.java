package com.gk.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.SaltBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.ForgetPasswordActivity;
import com.gk.mvp.view.activity.MainActivity;
import com.gk.tools.MD5Util;
import com.gk.tools.YxxUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/9.
 */

public class LoginRightFragment extends SjmBaseFragment {
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;

    @OnClick(R.id.rtv_weixin_login)
    public void weixinLogin() {
        goForgetPwd("weixin");
    }

    private String userName;
    private String password;
    private SaltBean saltBean;
    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    @Override
    public int getResourceId() {
        return R.layout.fragment_login_right;
    }

    public LoginRightFragment() {
    }

    public static LoginRightFragment newInstance(int enterFlag) {
        LoginRightFragment blankFragment = new LoginRightFragment();
        int mEnterFlag = enterFlag;
        return blankFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etUserPhone.setText("");
        etUserPwd.setText("");
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        editViewContentChangeEvent(etUserPhone);
        editViewContentChangeEvent(etUserPwd);
    }


    private void editViewContentChangeEvent(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTvLoginBackgroundRes();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setTvLoginBackgroundRes() {
        int etName = etUserPhone.getText().length();
        int etPwd = etUserPwd.getText().length();
        boolean isLogin = false;
        if (etName > 0 && etPwd > 0) {
            tvLogin.setBackgroundResource(R.drawable.login_press_style);
            isLogin = true;
        } else {
            tvLogin.setBackgroundResource(R.color.color878787);
            isLogin = false;
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                userName = etUserPhone.getText().toString();
                if (userName.isEmpty()) {
                    toast("请输入手机号");
                    return;
                }
                if (!YxxUtils.isMobile(userName)) {
                    toast("请输入正确的手机号");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                password = etUserPwd.getText().toString();
                if (password.isEmpty()) {
                    toast("请输入密码");
                    return;
                }
                showProgress();
                jsonObject.put("username", userName);
                presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getSalt(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
                break;
            case R.id.tv_forget_pwd:
                goForgetPwd("forget_pwd");
                break;
        }
    }

    private void goForgetPwd(String value) {
        Intent intent = new Intent();
        intent.putExtra("flag", value);
        openNewActivityByIntent(ForgetPasswordActivity.class, intent);
    }

    private void getAdsInfo() {
        showProgress();
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getAdsInfoList())
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
                presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).login(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_SECOND_TIME);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
                LoginBean.getInstance().saveLoginBean(loginBean);
                //LoginBean.getInstance().setPassword(password).save();
                getAdsInfo();
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                CommonBean commonBean1 = (CommonBean) t;
                List<AdsBean.MDataBean> mDataBeans = JSON.parseArray(commonBean1.getData().toString(), AdsBean.MDataBean.class);
                AdsBean.getInstance().saveAdsBean(mDataBeans);
                openNewActivity(MainActivity.class);
                closeActivity();
                break;
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        if (YXXConstants.INVOKE_API_SECOND_TIME == order) {
            toast("登录失败");
        }
        hideProgress();
    }
}
