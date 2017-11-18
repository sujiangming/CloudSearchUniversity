package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/18.
 */

public class UpdateUserInfoActivity extends SjmBaseActivity {
    @BindView(R.id.tv_top_bar)
    TopBarView tvTopBar;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private int requestCode = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_update_user_info;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(tvTopBar, "更改个人信息", 0);
        requestCode = getIntent().getIntExtra("code", 0);
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        int etUserPwdValue = etUserPwd.getText().length();
        if (etUserPwdValue == 0) {
            toast("请输入修改内容");
            return;
        }
        String value = etUserPwd.getText().toString();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        switch (requestCode) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                jsonObject.put("cname", value);
                break;
            case 3:
                jsonObject.put("address", value);
                break;
            case 4:
                jsonObject.put("score", value);
                break;
            case 5:
                jsonObject.put("ranking", value);
                break;
            case 6:
                jsonObject.put("subjectType", value);
                break;
        }
        String content = jsonObject.toJSONString();
        showProgress();
        PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(content))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        toast(commonBean.getMessage());
        Intent intent = new Intent();
        intent.putExtra("info", etUserPwd.getText().toString());
        this.setResult(0, intent);
        closeActivity(this);
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }
}
