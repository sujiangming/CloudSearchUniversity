package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.LuQuRiskBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.ll_up_score)
    LinearLayout ll_up_score;
    @BindView(R.id.tv_tuijuan_desc)
    TextView tv_tuijuan_desc;
    @BindView(R.id.rl_up_data)
    RelativeLayout rl_up_data;
    @BindView(R.id.tv_line_2)
    TextView tv_line_2;
    @BindView(R.id.tv_b_y_b)
    TextView tv_b_y_b;
    @BindView(R.id.tv_risk_desc)
    TextView tv_risk_desc;

    @OnClick({R.id.tv_tag_1, R.id.tv_zs_plan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tag_1:
                Intent intent = new Intent();
                if (flag == 2) {
                    intent.putExtra("uniName", schoolName);
                } else {
                    intent.putExtra("uniName", valueDesc);
                }
                openNewActivityByIntent(LqRiskTestResultLqDataActivity.class, intent);
                break;
            case R.id.tv_zs_plan:
                Intent intent1 = new Intent();
                if (flag == 2) {
                    intent1.putExtra("uniName", schoolName);
                } else {
                    intent1.putExtra("uniName", valueDesc);
                }
                openNewActivityByIntent(LqRiskTestResultZZPlanActivity.class, intent1);
                break;
        }
    }

    private String valueDesc;
    private int flag = 0;
    private String schoolName;

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_result_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "录取风险", 0);
        flag = getIntent().getIntExtra("flag", 0);
        valueDesc = getIntent().getStringExtra("aim");
        initData();
    }

    private void initData() {
        tvMyScore.setText("我的成绩：" + LoginBean.getInstance().getScore() + "分");
        String aimSchool = "目标院校： ";
        if (flag == 2) {
            schoolName = getIntent().getStringExtra("schoolName");
            tv_my_aim_major.setVisibility(View.VISIBLE);
            tv_my_aim_major.setText("目标专业： " + valueDesc);
            tvMyAimUniversity.setText(aimSchool + schoolName);
            evaluateMajorReport();
        } else {
            tvMyAimUniversity.setText(aimSchool + valueDesc);
            evaluateReport();
        }
    }

    private void showGoneView() {
        ll_up_score.setVisibility(View.VISIBLE);
        rl_up_data.setVisibility(View.VISIBLE);
        tv_tuijuan_desc.setVisibility(View.VISIBLE);
        tv_line_2.setVisibility(View.VISIBLE);
    }

    private void hideGoneView() {
        ll_up_score.setVisibility(View.GONE);
        rl_up_data.setVisibility(View.GONE);
        tv_tuijuan_desc.setVisibility(View.GONE);
        tv_line_2.setVisibility(View.GONE);
    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void evaluateReport() { //按高校生成报告
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("score", LoginBean.getInstance().getScore());
        jsonObject.put("schoolName", YxxUtils.URLEncode(valueDesc));
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        presenterManager
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .evaluateReport(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    private void evaluateMajorReport() { //按专业生成报告
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("score", LoginBean.getInstance().getScore());
        jsonObject.put("schoolName", YxxUtils.URLEncode(schoolName));
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("majorName", (valueDesc == null ? "" : YxxUtils.URLEncode(valueDesc)));
        presenterManager
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .evaluateMajorReport(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (null == commonBean || null == commonBean.getData()) {
            hideGoneView();
            return;
        }
        LuQuRiskBean luQuRiskBean = JSON.parseObject(commonBean.getData().toString(), LuQuRiskBean.class);
        if (luQuRiskBean == null) {
            hideGoneView();
            return;
        }
        showGoneView();
        String probability = luQuRiskBean.getAdmissionProbability();
        initAddViews(luQuRiskBean.getRecommendSchs());
        if (!TextUtils.isEmpty(probability)) {
            tvChart.setText(probability);
            String probabilitySub = probability.replace("%", "");
            initViewData(Integer.valueOf(probabilitySub));
        }
    }

    private void initViewData(int probability_int) {
        if (probability_int <= 50) {
            ///录取概率低，风险较高，不推荐
            tv_b_y_b.setText("不考虑");
            tv_b_y_b.setText(" 录取概率低，填报风险很高");
        } else if (probability_int > 50 && probability_int <= 65) {
            ///有一定的概率呗录取， 但是录取概率仍然较低，只能是冲一冲
            tv_b_y_b.setText("冲一冲");
            tv_b_y_b.setText("有一定的录取概率，但风险较高");
        } else if (probability_int > 65 && probability_int <= 75) {
            ///有一定的概率呗录取， 但是录取概率仍然较低，只能是稳一稳
            tv_b_y_b.setText("稳一稳");
            tv_b_y_b.setText(" 录取概率一般，可作为参考院校");
        } else if (probability_int > 75) {
            ///有一定的概率呗录取， 但是录取概率仍然较高，只能是保证
            tv_b_y_b.setText("保一保");
            tv_b_y_b.setText("录取概率较高，可作为保底院校");
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast("没有查询到数据");
        hideProgress();
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
        for (int i = 0; i < recommendSchsBeans.size(); i++) {
            final LuQuRiskBean.RecommendSchsBean bean = recommendSchsBeans.get(i);
            View view = View.inflate(this, R.layout.risk_result_item, null);
            TextView textView = view.findViewById(R.id.tv_university_name);
            ImageView ivLogo = view.findViewById(R.id.iv_logo);

            textView.setText(bean.getSchoolName());
            GlideImageLoader.displayImage(this, bean.getSchoolLogo(), ivLogo);

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
        for (int i = 0; i < recommendSchsBeans.size(); i++) {
            final LuQuRiskBean.RecommendSchsBean bean = recommendSchsBeans.get(i);
            View view = View.inflate(this, R.layout.risk_major_query_item, null);
            TextView tv_university_name = view.findViewById(R.id.tv_university_name);
            TextView tv_major_name = view.findViewById(R.id.tv_major_name);
            ImageView ivLogo = view.findViewById(R.id.iv_logo);
            tv_university_name.setText(bean.getSchoolName());
            tv_major_name.setText(bean.getSchoolMajor());
            GlideImageLoader.displayImage(this, bean.getSchoolLogo(), ivLogo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("flag", 2);
                    intent.putExtra("aim", bean.getSchoolMajor());
                    intent.putExtra("schoolName", bean.getSchoolName());
                    openNewActivityByIntent(LqRiskTestResultActivity.class, intent);
                }
            });

            llTuijuanList.addView(view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }
}
