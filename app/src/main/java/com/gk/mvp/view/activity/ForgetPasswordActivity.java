package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.VerifyCodeBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.view.custom.TopBarView;

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
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private String userName;

    @Override
    public int getResouceId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "忘记密码", 0);
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        userName = etUserPhone.getText().toString();
        if (userName.isEmpty()) {
            toast("请输入手机号");
            return;
        }
        getVerifyCode();
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
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            VerifyCodeBean verifyCodeBean = JSON.parseObject(commonBean.getData().toString(), VerifyCodeBean.class);
                            jsonObject.put("verifyCode", verifyCodeBean.getVerifyCode());
                            forgetPassword(jsonObject.toJSONString());
                        } else {
                            hideProgress();
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
}
