package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXApplication;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.view.custom.TopBarView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/12/15.
 */

public class ForgetPasswordActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_ps)
    TextView tvPs;

    private String userName;
    private String verifyCode;
    private String pageFlag;

    private MyCountDownTimer countDownTimerUtils;
    private final long TIME = 60 * 1000L;
    private final long INTERVAL = 1000L;

    @Override
    public int getResouceId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        editViewContentChangeEvent(etUserPhone);
        editViewContentChangeEvent(etUserPwd);
        pageFlag = getIntent().getStringExtra("flag");
        if (pageFlag.equals("weixin")) {
            setTopBar(topBar, "微信绑定注册", 0);
            tvPs.setText("*由于您的微信号从未登录过，或者从未与任何手机号绑定，需再次绑定");
        } else {
            setTopBar(topBar, "忘记密码", 0);
            tvPs.setText("*密码将以短信的形式发到你手机上，请尽快修改密码");
        }
    }

    @OnClick({R.id.tv_code, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                if (TextUtils.isEmpty(etUserPhone.getText())) {
                    toast("请输入手机号");
                    return;
                }
                userName = etUserPhone.getText().toString();
                getVerifyCode();
                startTimer();
                break;
            case R.id.tv_login:
                login();
                break;
        }
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

    private void login() {
        if (TextUtils.isEmpty(etUserPhone.getText())) {
            toast("请输入手机号");
            return;
        }
        userName = etUserPhone.getText().toString();

        if (TextUtils.isEmpty(etUserPwd.getText())) {
            toast("请输入验证码");
            return;
        }

        verifyCode = etUserPwd.getText().toString();

        if (pageFlag.equals("weixin")) {
            wxLogin();
        } else {
            updatePwd();
        }
    }

    public void wxLogin() {
        if (!YXXApplication.sApi.isWXAppInstalled()) {
            toast("您还未安装微信客户端");
            return;
        }
        LoginBean.getInstance().setUsername(userName);
        LoginBean.getInstance().setVerifyCode(verifyCode);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "yxx_wx_login";
        YXXApplication.sApi.sendReq(req);
    }

    private void updatePwd() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        jsonObject.put("verifyCode", verifyCode);

        forgetPassword(jsonObject.toJSONString());
    }

    private void getVerifyCode() {
        showProgress();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getVerityfyCode(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        CommonBean commonBean = response.body();
                        toast(commonBean.getMessage());
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast(t.getMessage());
                        hideProgress();
                    }
                });
    }

    private void forgetPassword(String para) {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .forgetPassword(para)
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            toast(commonBean.getMessage());
                            closeActivity(ForgetPasswordActivity.this);
                        } else {
                            toast("获取新密码失败！");
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast(t.getMessage());
                        hideProgress();
                    }
                });
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvCode.setEnabled(false);
            tvCode.setText("已发送(" + millisUntilFinished / 1000 + " s)");
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
        tvCode.setEnabled(true);
        tvCode.setText("获取验证码");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
