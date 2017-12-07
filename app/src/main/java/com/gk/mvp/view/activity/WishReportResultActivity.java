package com.gk.mvp.view.activity;

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
import com.gk.beans.WishResultBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/11/3.
 */

public class WishReportResultActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_my_wish_report)
    TextView tvMyWishReport;
    @BindView(R.id.iv_blue_line)
    ImageView ivBlueLine;
    @BindView(R.id.tv_student_name)
    TextView tvStudentName;
    @BindView(R.id.tv_student_address)
    TextView tvStudentAddress;
    @BindView(R.id.tv_student_score_and_rank)
    TextView tvStudentScoreAndRank;
    @BindView(R.id.tv_wen_li_ke)
    TextView tvWenLiKe;
    @BindView(R.id.tv_wish_province)
    TextView tvWishProvince;
    @BindView(R.id.tv_wish_zy)
    TextView tvWishZy;
    @BindView(R.id.tv_mind_test)
    TextView tvMindTest;
    @BindView(R.id.tv_mind_test_result)
    TextView tvMindTestResult;
    @BindView(R.id.ll_report_container)
    LinearLayout llReportContainer;

    private String[] orderIndex = {"①  ", "②  ", "③  ", "④  ", "⑤  ", "⑥  ", "⑦  ", "⑧  ", "⑨  ", "⑩  "};

    @Override
    public int getResouceId() {
        return R.layout.activity_wish_report_result;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "我的志愿报告", 0);
        httpRequest();
    }

    private void httpRequest() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        LoginBean loginBean = LoginBean.getInstance();
        jsonObject.put("score", loginBean.getScore());//必传
        jsonObject.put("ranking", loginBean.getRanking());//必传
        jsonObject.put("subjectName", loginBean.getSubjectType());
        jsonObject.put("birthPlace", loginBean.getAddress());
        jsonObject.put("intentSch", loginBean.getWishUniversity());//选传 意向高校(多个逗号隔开)
        jsonObject.put("intentArea", loginBean.getWishProvince());//(选填):意向省份(多个逗号隔开)
        jsonObject.put("heartTest", loginBean.getHeartTest());//(选填):心理测试记过
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .generateWishReport(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        WishResultBean wishResultBean = JSON.parseObject(commonBean.getData().toString(), WishResultBean.class);
        if (wishResultBean != null) {
            initData(wishResultBean);
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void initData(WishResultBean wishResultBean) {
        tvStudentName.setText("姓名：   " + (wishResultBean.getCname() == null ? "您还未设置名称" : wishResultBean.getCname()));
        tvStudentAddress.setText("生源地：  " + wishResultBean.getBirthPlace());
        tvStudentScoreAndRank.setText("成绩：  " + wishResultBean.getScoreRanking());
        tvWenLiKe.setText("文理科：  " + wishResultBean.getSubjectName());
        tvWishProvince.setText("意向省市：  " + wishResultBean.getIntentArea());
        tvWishZy.setText("意向专业：  " + wishResultBean.getIntentMajors());
        tvMindTestResult.setText(wishResultBean.getHeartTest());
        initFirstBatch(wishResultBean.getFirstBatch());
        initSecondBatch(wishResultBean.getSecondBatch());
    }

    private void initFirstBatch(List<WishResultBean.FirstBatchBean> firstBatchBeans) {
        if (firstBatchBeans != null && firstBatchBeans.size() > 0) {
            for (int i = 0; i < firstBatchBeans.size(); i++) {
                WishResultBean.FirstBatchBean firstBean = firstBatchBeans.get(i);
                View view = View.inflate(this, R.layout.wish_report_result_item, null);
                RichText richText = view.findViewById(R.id.rtv_bk);
                TextView tvTuiJuan = view.findViewById(R.id.tv_tuijuan_size);//tv_wish_bk_1
                TextView tvUniversityName = view.findViewById(R.id.tv_wish_bk_1);
                TextView tvIsTiaoji = view.findViewById(R.id.tv_tiaoji);
                TextView tvMinScore = view.findViewById(R.id.tv_left_title_1);
                TextView tvLuQuRate = view.findViewById(R.id.tv_right_title_1);
                LinearLayout llTuijuanZy = view.findViewById(R.id.ll_tuijuan_zy);

                if (i == 0) {
                    richText.setVisibility(View.VISIBLE);
                    tvTuiJuan.setVisibility(View.VISIBLE);
                    richText.setText("本科第一批");
                    tvTuiJuan.setText("(推荐志愿" + firstBatchBeans.size() + "个）");
                }
                tvUniversityName.setText(orderIndex[i] + firstBean.getSchoolName());
                tvIsTiaoji.setText("服从调剂： " + (firstBean.getIsAdjust().equals("1") ? "是" : "否"));
                tvMinScore.setText(firstBean.getLastYearLowestScore());
                tvLuQuRate.setText("录取概率  " + firstBean.getAdmissionProbability());

                String[] recommendMajors = firstBean.getRecommend_majors().split("、");
                if (recommendMajors != null && recommendMajors.length > 0) {
                    for (int j = 0; j < recommendMajors.length; j++) {
                        View view1 = View.inflate(this, R.layout.wish_result_item_child, null);
                        TextView tvLeft = view1.findViewById(R.id.tv_left);
                        TextView tvRight = view1.findViewById(R.id.tv_right);

                        int mode = j % 2;

                        if (mode == 0) {
                            tvLeft.setText(j + "、" + recommendMajors[j]);
                            if (j != (recommendMajors.length - 1)) {
                                tvRight.setText((j + 1) + "、" + recommendMajors[j + 1]);
                            }
                            llTuijuanZy.addView(view1);
                        }

                    }
                }

                llReportContainer.addView(view);
            }
        }
    }

    private void initSecondBatch(List<WishResultBean.SecondBatchBean> firstBatchBeans) {
        if (firstBatchBeans != null && firstBatchBeans.size() > 0) {
            for (int i = 0; i < firstBatchBeans.size(); i++) {
                WishResultBean.SecondBatchBean firstBean = firstBatchBeans.get(i);
                View view = View.inflate(this, R.layout.wish_report_result_item, null);
                RichText richText = view.findViewById(R.id.rtv_bk);
                TextView tvTuiJuan = view.findViewById(R.id.tv_tuijuan_size);//tv_wish_bk_1
                TextView tvUniversityName = view.findViewById(R.id.tv_wish_bk_1);
                TextView tvIsTiaoji = view.findViewById(R.id.tv_tiaoji);
                TextView tvMinScore = view.findViewById(R.id.tv_left_title_1);
                TextView tvLuQuRate = view.findViewById(R.id.tv_right_title_1);
                LinearLayout llTuijuanZy = view.findViewById(R.id.ll_tuijuan_zy);

                if (i == 0) {
                    richText.setVisibility(View.VISIBLE);
                    tvTuiJuan.setVisibility(View.VISIBLE);
                    richText.setText("本科第二批");
                    tvTuiJuan.setText("(推荐志愿" + firstBatchBeans.size() + "个）");
                }
                tvUniversityName.setText(orderIndex[i] + firstBean.getSchoolName());
                tvIsTiaoji.setText("服从调剂： " + (firstBean.getIsAdjust().equals("1") ? "是" : "否"));
                tvMinScore.setText(firstBean.getLastYearLowestScore());
                tvLuQuRate.setText("录取概率  " + firstBean.getAdmissionProbability());

                String[] recommendMajors = firstBean.getRecommend_majors().split("、");
                if (recommendMajors != null && recommendMajors.length > 0) {
                    for (int j = 0; j < recommendMajors.length; j++) {
                        View view1 = View.inflate(this, R.layout.wish_result_item_child, null);
                        TextView tvLeft = view1.findViewById(R.id.tv_left);
                        TextView tvRight = view1.findViewById(R.id.tv_right);

                        int mode = j % 2;

                        if (mode == 0) {
                            tvLeft.setText(j + "、" + recommendMajors[j]);
                            if (j != (recommendMajors.length - 1)) {
                                tvRight.setText((j + 1) + "、" + recommendMajors[j + 1]);
                            }
                            llTuijuanZy.addView(view1);
                        }
                    }
                }

                llReportContainer.addView(view);
            }
        }
    }
}
