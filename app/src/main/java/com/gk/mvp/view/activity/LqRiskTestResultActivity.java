package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.LuQuRiskBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;

import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class LqRiskTestResultActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_my_score)
    TextView tvMyScore;
    @BindView(R.id.tv_my_aim_university)
    TextView tvMyAimUniversity;
    @BindView(R.id.tv_chart)
    TextView tvChart;
    @BindView(R.id.tv_tag_1)
    TextView tvTag1;
    @BindView(R.id.tv_lq_data)
    TextView tvLqData;
    @BindView(R.id.tv_zy_data)
    TextView tvZyData;
    @BindView(R.id.tv_td_data)
    TextView tvTdData;
    @BindView(R.id.tv_zx_data)
    TextView tvZxData;
    @BindView(R.id.tv_my_aim_major)
    TextView tv_my_aim_major;
    @BindView(R.id.tv_other_data)
    TextView tvOtherData;
    @BindView(R.id.ll_tuijuan_list)
    LinearLayout llTuijuanList;

    private String valueDesc;
    private int flag = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_result_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "录取风险", 0);
        flag = getIntent().getIntExtra("flag", 0);
        valueDesc = getIntent().getStringExtra("aim");
        httpRequest();
    }

    private void httpRequest() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("score", LoginBean.getInstance().getScore());
        jsonObject.put("subjectName", LoginBean.getInstance().getSubjectType());
        if (flag == 1) {
            jsonObject.put("intentSch", valueDesc);
            jsonObject.put("intentMajor", "");
        } else {
            jsonObject.put("intentSch", "");
            jsonObject.put("intentMajor", valueDesc);
        }
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .evaluateReport(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        LuQuRiskBean luQuRiskBean = JSON.parseObject(commonBean.getData().toString(), LuQuRiskBean.class);
        if (luQuRiskBean == null) {
            return;
        }
        initData(luQuRiskBean);
        initAddViews(luQuRiskBean.getRecommendSchs());
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void initData(LuQuRiskBean luQuRiskBean) {
        tvMyScore.setText("我的成绩：" + LoginBean.getInstance().getScore() + "分");
        tvMyAimUniversity.setText("目标院校： " + valueDesc);
        tvChart.setText(luQuRiskBean.getAdmissionProbability());
        if (flag == 2) {
            tv_my_aim_major.setVisibility(View.VISIBLE);
            tv_my_aim_major.setText("目标专业： " + valueDesc);
        }
    }

    private void initAddViews(List<LuQuRiskBean.RecommendSchsBean> recommendSchsBeans) {
        llTuijuanList.removeAllViews();
        if (flag == 1) {
            addTuiJianUniversity(recommendSchsBeans);
        } else {
            addTuiJianMajorUniversity(recommendSchsBeans);
        }
    }

    private void addTuiJianUniversity(List<LuQuRiskBean.RecommendSchsBean> recommendSchsBeans) {
        if (recommendSchsBeans == null || recommendSchsBeans.size() == 0) {
            return;
        }
        GlideImageLoader imageLoader = new GlideImageLoader();
        for (int i = 0; i < recommendSchsBeans.size(); i++) {
            final LuQuRiskBean.RecommendSchsBean bean = recommendSchsBeans.get(i);
            View view = View.inflate(this, R.layout.risk_result_item, null);
            TextView textView = view.findViewById(R.id.tv_university_name);
            ImageView ivLogo = view.findViewById(R.id.iv_logo);

            textView.setText(bean.getSchoolName());
            imageLoader.displayImage(this, bean.getSchoolLogo(), ivLogo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("flag", 1);
                    intent.putExtra("aim", bean.getSchoolName());
                    openNewActivityByIntent(LqRiskTestResultActivity.class, intent);
                }
            });

            llTuijuanList.addView(view);
        }
    }

    private void addTuiJianMajorUniversity(List<LuQuRiskBean.RecommendSchsBean> recommendSchsBeans) {
        if (recommendSchsBeans == null || recommendSchsBeans.size() == 0) {
            return;
        }
        GlideImageLoader imageLoader = new GlideImageLoader();
        for (int i = 0; i < recommendSchsBeans.size(); i++) {
            LuQuRiskBean.RecommendSchsBean bean = recommendSchsBeans.get(i);
            View view = View.inflate(this, R.layout.risk_major_query_item, null);
            TextView tv_university_name = view.findViewById(R.id.tv_university_name);
            TextView tv_major_name = view.findViewById(R.id.tv_major_name);
            ImageView ivLogo = view.findViewById(R.id.iv_logo);
            tv_university_name.setText(bean.getSchoolName());
            tv_major_name.setText(bean.getSchoolMajor());
            imageLoader.displayImage(this, bean.getSchoolLogo(), ivLogo);
            llTuijuanList.addView(view);
        }
    }
}
