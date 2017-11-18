package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class AccountSaveActivity extends SjmBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_confirm_new_pwd)
    EditText etConfirmNewPwd;

    private String userName;
    private String newPwd1;

    @Override
    public int getResouceId() {
        return R.layout.activity_account_save;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {

    }

    @OnClick({R.id.iv_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeActivity();
                break;
            case R.id.tv_save:
                userName = etAccount.getText().toString();
                String oldPwd = etOldPwd.getText().toString();
                newPwd1 = etNewPwd.getText().toString();
                String newPwd2 = etConfirmNewPwd.getText().toString();
                if (userName == null || userName.equals("")) {
                    toast("请输入账号");
                    return;
                }
                if (oldPwd == null || oldPwd.equals("")) {
                    toast("请输入旧密码");
                    return;
                }
                if (newPwd1 == null || newPwd1.equals("")) {
                    toast("请输入新密码");
                    return;
                }
                if (newPwd2 == null || newPwd2.equals("")) {
                    toast("请确认新密码");
                    return;
                }
                if (!newPwd1.equals(newPwd2)) {
                    toast("两次输入的新密码不一样");
                    return;
                }
                showProgress();
                JSONObject jsonObject = new JSONObject();
                String oldPwds = userName + LoginBean.getInstance().getSalt() + oldPwd;
                oldPwds = MD5Util.encrypt(oldPwds);
                String newPwds = userName + LoginBean.getInstance().getSalt() + newPwd1;
                newPwds = MD5Util.encrypt(newPwds);
                jsonObject.put("username", userName);
                jsonObject.put("oldPassword", oldPwds);
                jsonObject.put("newPassword", newPwds);
                PresenterManager.getInstance()
                        .setmContext(this)
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).updatePassword(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
                break;
        }
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", etAccount.getText().toString());
                showProgress();
                PresenterManager.getInstance()
                        .setmContext(this)
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).confirmUpdatePassword(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_SECOND_TIME);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                LoginBean.getInstance().setPassword(newPwd1);
                LoginBean.getInstance().setmContext(this).save();
                Log.e(AccountSaveActivity.class.getName(), JSON.toJSONString(LoginBean.getInstance()));
                toast(commonBean.getMessage());
                closeActivity(this);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }
}
