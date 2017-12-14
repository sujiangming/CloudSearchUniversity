package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.MBTIResultBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class MBTIActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.btn_mbti_test)
    Button btnMbtiTest;
    @BindView(R.id.btn_mbti_query)
    Button btnMbtiQuery;

    @Override
    public int getResouceId() {
        return R.layout.activity_mbti_test;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "MBTI性格测试", 0);
        httpReqest();
    }

    @OnClick({R.id.btn_mbti_test, R.id.btn_mbti_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mbti_test:
                openNewActivity(MBTITestDetailActivity.class);
                break;
            case R.id.btn_mbti_query:
                openNewActivity(MBTITestResultActivity.class);
                break;
        }
    }

    private void httpReqest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getHldTestReportByUser(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        MBTIResultBean hldReportBean = JSON.parseObject(commonBean.getData().toString(), MBTIResultBean.class);
        if (hldReportBean == null || hldReportBean.getId() == null) {
            return;
        }
        btnMbtiTest.setText("重做一遍");
        btnMbtiQuery.setVisibility(View.VISIBLE);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }
}
