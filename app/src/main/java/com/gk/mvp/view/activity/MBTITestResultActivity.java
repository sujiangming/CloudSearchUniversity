package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.MBITTbale;
import com.gk.beans.MBITTbaleDao;
import com.gk.beans.MBTIResultBean;
import com.gk.beans.MBTITypeEnum;
import com.gk.global.YXXApplication;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/12/12.
 */

public class MBTITestResultActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_desc_0)
    TextView tvDesc0;
    @BindView(R.id.iv_1)
    TextView iv1;
    @BindView(R.id.iv_2)
    TextView iv2;
    @BindView(R.id.iv_3)
    TextView iv3;
    @BindView(R.id.iv_4)
    TextView iv4;
    @BindView(R.id.tv_desc_1)
    TextView tvDesc1;
    @BindView(R.id.tv_for_iv_1)
    TextView tvForIv1;
    @BindView(R.id.tv_for_iv_2)
    TextView tvForIv2;
    @BindView(R.id.tv_for_iv_3)
    TextView tvForIv3;
    @BindView(R.id.tv_for_iv_4)
    TextView tvForIv4;
    @BindView(R.id.tv_jielun)
    TextView tvJielun;
    @BindView(R.id.tv_tz_1)
    TextView tvTz1;
    @BindView(R.id.tv_tz_2)
    TextView tvTz2;
    @BindView(R.id.tv_tz_3)
    TextView tvTz3;
    @BindView(R.id.tv_tz_4)
    TextView tvTz4;
    @BindView(R.id.rl_up)
    RelativeLayout rlUp;
    @BindView(R.id.tv_has_xq)
    TextView tvHasXq;
    @BindView(R.id.tv_xg_score_desc)
    TextView tvXgScoreDesc;
    @BindView(R.id.tv_xg_desc_1)
    TextView tvXgDesc1;
    @BindView(R.id.tv_xg_desc_2)
    TextView tvXgDesc2;
    @BindView(R.id.tv_xg_desc_3)
    TextView tvXgDesc3;
    @BindView(R.id.tv_xg_desc_4)
    TextView tvXgDesc4;
    @BindView(R.id.progressBar_1_left)
    ProgressBar progressBar1Left;
    @BindView(R.id.progressBar_2_left)
    ProgressBar progressBar2Left;
    @BindView(R.id.progressBar_3_left)
    ProgressBar progressBar3Left;
    @BindView(R.id.progressBar_4_left)
    ProgressBar progressBar4Left;
    @BindView(R.id.tv_center_line)
    TextView tvCenterLine;
    @BindView(R.id.progressBar_1_right)
    ProgressBar progressBar1Right;
    @BindView(R.id.progressBar_2_right)
    ProgressBar progressBar2Right;
    @BindView(R.id.progressBar_3_right)
    ProgressBar progressBar3Right;
    @BindView(R.id.progressBar_4_right)
    ProgressBar progressBar4Right;
    @BindView(R.id.tv_xg_desc_1_right)
    TextView tvXgDesc1Right;
    @BindView(R.id.tv_xg_desc_2_right)
    TextView tvXgDesc2Right;
    @BindView(R.id.tv_xg_desc_3_right)
    TextView tvXgDesc3Right;
    @BindView(R.id.tv_xg_desc_4_right)
    TextView tvXgDesc4Right;
    @BindView(R.id.tv_my_xg)
    TextView tvMyXg;
    @BindView(R.id.tv_hld_type)
    TextView tvHldType;
    @BindView(R.id.ll_hld_xg_container)
    LinearLayout llHldXgContainer;
    @BindView(R.id.tv_fit_career)
    TextView tvFitCareer;
    @BindView(R.id.ll_xg_fit_career)
    LinearLayout llXgFitCareer;

    private MBTIResultBean mbtiResultBean;
    private TextView[] mbtiTypesTvs;
    private TextView[] mbtiTypesTvsDesc;
    private TextView[] mbtiCareerSummaryTvs;

    @Override
    public int getResouceId() {
        return R.layout.activity_mbti_test_result;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "MBIT性格测试", 0);
        mbtiTypesTvs = new TextView[]{iv1, iv2, iv3, iv4};
        mbtiTypesTvsDesc = new TextView[]{tvForIv1, tvForIv2, tvForIv3, tvForIv4};
        mbtiCareerSummaryTvs = new TextView[]{tvTz1, tvTz2, tvTz3, tvTz4};
        getReport();
        int flag = getIntent().getIntExtra("flag", 0);
        if (2 == flag) { //正常答题完毕的时候，需要调用减1的接口
            minusUserRechargeTimes();
        }
    }

    private void minusUserRechargeTimes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("rechargeType", 11);//11 心理测试
        RetrofitUtil.getInstance().createReq(IService.class)
                .minusUserRechargeTimes(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean rechargeTimes = response.body();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {

                    }
                });
    }

    private String getAnswers() {
        MBITTbaleDao mbitTbaleDao = YXXApplication.getDaoSession().getMBITTbaleDao();
        List<MBITTbale> list = mbitTbaleDao.queryBuilder().list();
        StringBuffer answers = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == (list.size() - 1)) {
                answers.append(list.get(i).getSelectItem());
            } else {
                answers.append(list.get(i).getSelectItem()).append(",");
            }
        }
        return answers.toString();
    }

    private void getReport() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("answers", getAnswers());
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class)
                        .getMbtiTestReport(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        mbtiResultBean = JSON.parseObject(commonBean.getData().toString(), MBTIResultBean.class);
        if (mbtiResultBean == null) {
            return;
        }
        initCommon();
        initProgressBars();
        addTypicalSubView();

    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void initCommon() {
        tvJielun.setText(mbtiResultBean.getCareerFeature());
        String careerTypes = mbtiResultBean.getCareerType();
        if (careerTypes != null && !careerTypes.equals("")) {
            String[] types = careerTypes.split(",");
            int len = types.length;
            for (int i = 0; i < len; i++) {
                mbtiTypesTvs[i].setText(types[i]);
                mbtiTypesTvsDesc[i].setText(MBTITypeEnum.getName(types[i]));
            }
        }
        String careerSummary = mbtiResultBean.getCareerSummary();
        if (careerSummary != null && !careerSummary.equals("")) {
            String[] careerSummaryArray = careerSummary.split(",");
            for (int j = 0; j < careerSummaryArray.length; j++) {
                mbtiCareerSummaryTvs[j].setText(careerSummaryArray[j]);
            }
        }
        tvXgScoreDesc.setText("我的性格测试结果为" + mbtiResultBean.getCareerType().replace(",", "") + "，四种性格倾向用条形图显示如下，条形图越长，该性格的倾向就越明显。");
        tvMyXg.setText(mbtiResultBean.getMyFeature());
    }

    private void initProgressBars() {
        int e = mbtiResultBean.getCareerE();
        int i = mbtiResultBean.getCareerI();
        int s = mbtiResultBean.getCareerS();
        int n = mbtiResultBean.getCareerN();
        int t = mbtiResultBean.getCareerT();
        int f = mbtiResultBean.getCareerF();
        int j = mbtiResultBean.getCareerJ();
        int p = mbtiResultBean.getCareerP();
        setProgressBarStyle(e, i, progressBar1Left, progressBar1Right);
        setProgressBarStyle(s, n, progressBar2Left, progressBar2Right);
        setProgressBarStyle(t, f, progressBar3Left, progressBar3Right);
        setProgressBarStyle(j, p, progressBar4Left, progressBar4Right);

    }

    private void setProgressBarStyle(int left, int right, ProgressBar leftProgress, ProgressBar rightProgress) {
        int max = left + right;
        if (left > right) {
            leftProgress.setMax(max);
            leftProgress.setProgress(left);
        } else {
            rightProgress.setMax(max);
            rightProgress.setProgress(right);
        }
    }

    private void addTypicalSubView() {
        for (int i = 0; i < 2; i++) {
            View view = View.inflate(this, R.layout.hld_result_xg_fit_item, null);
            TextView tv_hld_xg_desc = view.findViewById(R.id.tv_hld_xg_desc);
            TextView tv_hld_xg_feature = view.findViewById(R.id.tv_hld_xg_feature);
            if (i == 0) {
                String feature = mbtiResultBean.getCareerTypeFeature();
                feature = feature.replace(";", "\n");
                tv_hld_xg_desc.setText(mbtiResultBean.getCareerType().replace(",", "") + "人的职业性格特征");
                tv_hld_xg_feature.setText(feature);
            } else {
                tv_hld_xg_desc.setText("推荐职业");
                String recommond = mbtiResultBean.getRecommendOccupation();
                StringBuffer values = new StringBuffer("");
                if (recommond != null) {
                    String[] strings = recommond.split(",");
                    for (int j = 0; j < strings.length; j++) {
                        if (j == (strings.length - 1)) {
                            values.append(j + "、").append(strings[j]);
                        } else {
                            values.append(j + "、").append(strings[j]).append("\n");
                        }
                    }
                }
                tv_hld_xg_feature.setText(values.toString());
            }
            llXgFitCareer.addView(view);
        }
    }
}
