package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.VerifyCodeBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.MainActivity;
import com.gk.tools.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/9.
 */

public class LoginLeftFragment extends SjmBaseFragment {

    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_ps)
    TextView tvPs;

    private String descPrefix = "温馨提示：未注册云寻校的手机，登录时将自动注册，且代表您已同意";
    private boolean isLogin = false;
    private static int mEnterFlag = 0;
    private String userName;
    private String password;
    private VerifyCodeBean verifyCodeBean;

    @Override
    public int getResourceId() {
        return R.layout.fragment_login_left;
    }

    public LoginLeftFragment() {
    }

    public static LoginLeftFragment newInstance(int enterFlag) {
        LoginLeftFragment blankFragment = new LoginLeftFragment();
        mEnterFlag = enterFlag;
        return blankFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etUserPwd.setText("");
        etUserPhone.setText("");
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        editViewContentChangeEvent(etUserPhone);
        editViewContentChangeEvent(etUserPwd);
        tvPs.setText(Html.fromHtml(descPrefix + "<font color='red'>《云寻校用户协议》</font>"));
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

    @OnClick({R.id.tv_code, R.id.tv_login})
    public void onViewClicked(View view) {
        userName = etUserPhone.getText().toString();
        if (userName.isEmpty()) {
            toast("请输入手机号");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        switch (view.getId()) {
            case R.id.tv_code:
                showProgress();
                PresenterManager.getInstance()
                        .setmContext(getContext())
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).getVerityfyCode(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
                break;
            case R.id.tv_login:
                showProgress();
                password = etUserPwd.getText().toString();
                if (password.isEmpty()) {
                    toast("请输入验证码");
                    return;
                }
                jsonObject.put("verifyCode", password);
                PresenterManager.getInstance()
                        .setmContext(getContext())
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).verifyLogin(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_SECOND_TIME);
                break;
        }
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                verifyCodeBean = JSON.parseObject(commonBean.getData().toString(), VerifyCodeBean.class);
                ToastUtils.toast(getContext(), "获取验证码成功~" + verifyCodeBean.getVerifyCode());
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
                loginBean.setmContext(getContext()).saveLoginBean(loginBean);
                Log.e(LoginRightFragment.class.getName(), JSON.toJSONString(loginBean));
                if (mEnterFlag == YXXConstants.FROM_SPLASH_FLAG) {
                    openNewActivity(MainActivity.class);
                }
                closeActivity();
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
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