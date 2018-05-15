package com.gk.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.VerifyCodeBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.ForgetPasswordActivity;
import com.gk.mvp.view.activity.MainActivity;
import com.gk.mvp.view.activity.UserAgreementActivity;
import com.gk.tools.ToastUtils;
import com.gk.tools.YxxUtils;

import java.util.List;

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
    @BindView(R.id.btn_code)
    Button button;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_ps)
    TextView tvPs;

    @OnClick({R.id.rtv_weixin_login, R.id.tv_ps})
    public void weixinLogin(View view) {
        switch (view.getId()) {
            case R.id.rtv_weixin_login:
                wxLogin();
                break;
            case R.id.tv_ps:
                openNewActivity(UserAgreementActivity.class);
                break;
        }
    }

    public void wxLogin() {
        goForgetPwd("weixin");
    }

    private MyCountDownTimer countDownTimerUtils;
    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    @Override
    public int getResourceId() {
        return R.layout.fragment_login_left;
    }

    public LoginLeftFragment() {
    }

    public static LoginLeftFragment newInstance(int enterFlag) {
        LoginLeftFragment blankFragment = new LoginLeftFragment();
        int mEnterFlag = enterFlag;
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
        String descPrefix = "温馨提示：未注册云寻校的手机，登录时将自动注册，且代表您已同意";
        tvPs.setText(Html.fromHtml(descPrefix + "<font color='red'>《云寻校用户协议》</font>"));
    }

    private void goForgetPwd(String value) {
        Intent intent = new Intent();
        intent.putExtra("flag", value);
        openNewActivityByIntent(ForgetPasswordActivity.class, intent);
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
        } else {
            tvLogin.setBackgroundResource(R.color.color878787);
        }
    }

    @OnClick({R.id.btn_code, R.id.tv_login})
    public void onViewClicked(View view) {
        String userName = etUserPhone.getText().toString();
        if (userName.isEmpty()) {
            toast("请输入手机号");
            return;
        }
        if(!YxxUtils.isMobile(userName)){
            toast("请输入正确的手机号");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        switch (view.getId()) {
            case R.id.btn_code:
                showProgress();
                presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getVerityfyCode(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
                startTimer();
                break;
            case R.id.tv_login:
                showProgress();
                String password = etUserPwd.getText().toString();
                if (password.isEmpty()) {
                    toast("请输入验证码");
                    return;
                }
                jsonObject.put("verifyCode", password);
                presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).verifyLogin(jsonObject.toJSONString()))
                        .requestForResponseBody(YXXConstants.INVOKE_API_SECOND_TIME);
                break;
        }
    }

    private void getAdsInfo() {
        showProgress();
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getAdsInfoList())
                .request(YXXConstants.INVOKE_API_THREE_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                CommonBean commonBean = (CommonBean) t;
                VerifyCodeBean verifyCodeBean = JSON.parseObject(commonBean.getData().toString(), VerifyCodeBean.class);
                ToastUtils.toast(getContext(), "验证码已发至您的手机上");
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                String data = (String) t;
                JSONObject jsonObject = JSON.parseObject(data);
                int status = jsonObject.getIntValue("status");
                String msg = jsonObject.getString("message");
                if (status == 0) {
                    toast(msg);
                    return;
                }
                String user = jsonObject.get("data").toString();
                LoginBean loginBean = JSON.parseObject(user, LoginBean.class, Feature.IgnoreNotMatch);
                LoginBean.getInstance().saveLoginBean(loginBean);
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
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        hideProgress();
        switch (order) {
            case YXXConstants.INVOKE_API_THREE_TIME:
                openNewActivity(MainActivity.class);
                closeActivity();
                break;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            button.setEnabled(false);
            button.setText("已发送(" + millisUntilFinished / 1000 + " s)");
        }

        @Override
        public void onFinish() {
            resetButton();
            cancelTimer();
        }
    }

    /**
     * 开始倒计时
     */
    private void startTimer() {
        if (countDownTimerUtils == null) {
            long INTERVAL = 1000L;
            long TIME = 60 * 1000L;
            countDownTimerUtils = new MyCountDownTimer(TIME, INTERVAL);
        }
        countDownTimerUtils.start();
    }


    /**
     * 取消倒计时
     */
    private void cancelTimer() {
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }
    }

    private void resetButton() {
        button.setEnabled(true);
        button.setText("获取验证码");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
