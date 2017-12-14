package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    private boolean isLogin = false;
    private static int mEnterFlag = 0;
    private String userName;
    private String password;
    private SaltBean saltBean;

    @Override
    public int getResourceId() {
        return R.layout.fragment_login_right;
    }

    public LoginRightFragment() {
    }

    public static LoginRightFragment newInstance(int enterFlag) {
        LoginRightFragment blankFragment = new LoginRightFragment();
        mEnterFlag = enterFlag;
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
                showProgress();
                userName = etUserPhone.getText().toString();
                if (userName.isEmpty()) {
                    toast("请输入手机号");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                password = etUserPwd.getText().toString();
                if (password.isEmpty()) {
                    toast("请输入密码");
                    return;
                }
                jsonObject.put("username", userName);
                PresenterManager.getInstance()
                        .setmContext(getContext())
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).getSalt(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
                break;
            case R.id.tv_forget_pwd:
                openNewActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    private void getAdsInfo() {
        showProgress();
        PresenterManager.getInstance()
                .setmContext(getContext())
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
                Log.d(LoginRightFragment.class.getName(), MD5Util.encrypt(pwd));
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
        toast((String) t);
        hideProgress();
    }
}
