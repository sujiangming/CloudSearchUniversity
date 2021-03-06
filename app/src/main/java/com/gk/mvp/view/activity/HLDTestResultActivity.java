package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.HLDTable;
import com.gk.beans.HLDTableDao;
import com.gk.beans.HldReportBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
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
 * Created by JDRY-SJM on 2017/12/11.
 */

public class HLDTestResultActivity extends SjmBaseActivity {

    @BindView(R.id.wv_chart)
    WebView mChart;
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_desc_0)
    TextView tvDesc0;
    @BindView(R.id.iv_1)
    TextView tv1;
    @BindView(R.id.iv_2)
    TextView tv2;
    @BindView(R.id.iv_3)
    TextView tv3;
    @BindView(R.id.tv_desc_1)
    TextView tvDesc1;
    @BindView(R.id.tv_for_iv_1)
    TextView tvForIv1;
    @BindView(R.id.tv_for_iv_2)
    TextView tvForIv2;
    @BindView(R.id.tv_for_iv_3)
    TextView tvForIv3;
    @BindView(R.id.tv_jielun)
    TextView tvJielun;
    @BindView(R.id.rl_up)
    RelativeLayout rlUp;
    @BindView(R.id.tv_type_content)
    TextView tvTypeContent;
    @BindView(R.id.tv_hld_type)
    TextView tvHldType;
    @BindView(R.id.tv_has_xq)
    TextView tvHasXq;
    @BindView(R.id.ll_xg_list)
    LinearLayout llXgList;
    @BindView(R.id.ll_hld_xg_container)
    LinearLayout llHldXgContainer;
    @BindView(R.id.tv_fit_career)
    TextView tvFitCareer;
    @BindView(R.id.ll_xg_fit_career)
    LinearLayout llXgFitCareer;

    @BindView(R.id.tv_test_result)
    TextView tv_test_result;

    private TextView[] textViews;
    private TextView[] textViewType;
    private HldReportBean hldReportBean;
    private JSONObject imageJsonObject = new JSONObject();
    private JSONObject imageDescJsonObject = new JSONObject();
    private JSONObject typeJsonObject = new JSONObject();

    @Override
    public int getResouceId() {
        return R.layout.activity_hld_test_result;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "兴趣测试", 0);
        initJSONObject();
        textViews = new TextView[]{tv1, tv2, tv3};
        textViewType = new TextView[]{tvForIv1, tvForIv2, tvForIv3};
        int flag = getIntent().getIntExtra("flag", 0);
        if (2 == flag) { //正常答题完毕的时候，需要调用减1的接口
            minusUserRechargeTimes();
            httpRequest();
        } else {
            hldReportBean = (HldReportBean) getIntent().getSerializableExtra("bean");
            initData();
        }
    }

    private void initJSONObject() {
        imageJsonObject.put("A", R.drawable.hld);
        imageJsonObject.put("S", R.drawable.hld_blue);
        imageJsonObject.put("E", R.drawable.hld);
        imageJsonObject.put("C", R.drawable.hld_blue);
        imageJsonObject.put("R", R.drawable.hld);
        imageJsonObject.put("I", R.drawable.hld_blue);

        imageDescJsonObject.put("A", R.string.a_type);
        imageDescJsonObject.put("S", R.string.s_type);
        imageDescJsonObject.put("E", R.string.e_type);
        imageDescJsonObject.put("C", R.string.c_type);
        imageDescJsonObject.put("R", R.string.r_type);
        imageDescJsonObject.put("I", R.string.i_type);

        typeJsonObject.put("A", "艺术");
        typeJsonObject.put("S", "社会");
        typeJsonObject.put("E", "企业");
        typeJsonObject.put("C", "传统");
        typeJsonObject.put("R", "实际");
        typeJsonObject.put("I", "研究");
    }

    private void minusUserRechargeTimes() {
        JSONObject imageJsonObject = new JSONObject();
        imageJsonObject.put("username", LoginBean.getInstance().getUsername());
        imageJsonObject.put("rechargeType", 11);//11 心理测试
        RetrofitUtil.getInstance().createReq(IService.class)
                .minusUserRechargeTimes(imageJsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {

                    }
                });
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void httpRequest() {
        showProgress();
        JSONObject imageJsonObject = new JSONObject();
        imageJsonObject.put("username", LoginBean.getInstance().getUsername());
        imageJsonObject.put("answers", getSelectedAnswer());
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getHldTestReport(imageJsonObject.toJSONString()))
                .request();
    }

    private String getSelectedAnswer() {
        HLDTableDao hldTableDao = YXXApplication.getDaoSession().getHLDTableDao();
        List<HLDTable> tableList = hldTableDao.queryBuilder().where(HLDTableDao.Properties.IsSelected.eq(true)).list();
        if (tableList == null || tableList.size() == 0) {
            return null;
        }
        StringBuffer answerStr = new StringBuffer();

        for (int i = 0; i < tableList.size(); i++) {
            if (i == (tableList.size() - 1)) {
                answerStr.append(tableList.get(i).getInterestType());
            } else {
                answerStr.append(tableList.get(i).getInterestType()).append(",");
            }
        }

        return answerStr.toString();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        hldReportBean = JSON.parseObject(commonBean.getData().toString(), HldReportBean.class);
        initData();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void initData() {
        if (hldReportBean == null) {
            return;
        }
        int[] scores = new int[]{hldReportBean.getCareerA(), hldReportBean.getCareerS(), hldReportBean.getCareerE(), hldReportBean.getCareerC(), hldReportBean.getCareerR(), hldReportBean.getCareerI()};
        initWebView(JSON.toJSONString(scores));
        initCommon();
        addCommonSubView();
        addTypicalSubView();
    }

    private void initCommon() {
        String typeValue = hldReportBean.getCareerTypical();
        if (typeValue != null && !"".equals(typeValue)) {
            int len = typeValue.length();
            if (len > 10) {
                tvJielun.setText(typeValue.substring(0, 10));
            } else {
                tvJielun.setText(typeValue);
            }
        }
        tvHldType.setText(hldReportBean.getCareerAlphabet());
        tvTypeContent.setText(hldReportBean.getCareerFeature());
        tv_test_result.setText(hldReportBean.getCareerTestResults());
    }

    private void addCommonSubView() {
        String careerType = hldReportBean.getCareerType();
        if (careerType == null) {
            return;
        }
        String[] strings = careerType.split(",");
        for (int i = 0; i < strings.length; i++) {
            textViews[i].setText(strings[i]);
            textViewType[i].setText(typeJsonObject.getString(strings[i]));
            View view = View.inflate(this, R.layout.hld_result_xg_item, null);
            TextView imageView = view.findViewById(R.id.iv_hld_xg);
            TextView textView = view.findViewById(R.id.tv_hld_xg_desc);
            imageView.setBackgroundResource(imageJsonObject.getIntValue(strings[i]));
            imageView.setText(imageDescJsonObject.getIntValue(strings[i]));
            if (i == 0) {
                textView.setText(hldReportBean.getCareerCommonOne());
            } else if (i == 1) {
                textView.setText(hldReportBean.getCareerCommonTwo());
            } else {
                textView.setText(hldReportBean.getCareerCommonThree());
            }

            llXgList.addView(view);
        }
    }

    private void addTypicalSubView() {
        for (int i = 0; i < 2; i++) {
            View view = View.inflate(this, R.layout.hld_result_xg_fit_item, null);
            TextView tv_hld_xg_desc = view.findViewById(R.id.tv_hld_xg_desc);
            TextView tv_hld_xg_feature = view.findViewById(R.id.tv_hld_xg_feature);
            if (i == 0) {
                tv_hld_xg_desc.setText(hldReportBean.getCareerType().replace(",", "") + "人的职业性格特征");
                String tz = "1、" + hldReportBean.getCareerTypicalOne() + "\n2、" + hldReportBean.getCareerTypicalTwo()
                        + "\n3、" + hldReportBean.getCareerTypicalThree();
                tv_hld_xg_feature.setText(tz);
            } else {
                tv_hld_xg_desc.setText("推荐职业");
                String recommond = hldReportBean.getRecommendOccupation();
                StringBuffer values = new StringBuffer("");
                if (recommond != null) {
                    String[] strings = recommond.split(",");
                    for (int j = 0; j < strings.length; j++) {
                        if (j == (strings.length - 1)) {
                            values.append(j).append("、").append(strings[j]);
                        } else {
                            values.append(j).append("、").append(strings[j]).append("\n");
                        }
                    }
                }
                tv_hld_xg_feature.setText(values.toString());
            }
            llXgFitCareer.addView(view);
        }
    }

    private void initWebView(final String values) {
        WebSettings webSettings = mChart.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        mChart.loadUrl("file:///android_asset/radar_chart.html");
        mChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                updateHandicapList(values);
            }
        });
    }

    private void updateHandicapList(String values) {
        String strCall = "javascript:set_option_value('" + values + "')";
        mChart.loadUrl(strCall);
    }

    private void destroyWebView() {
        if (mChart != null) {
            mChart.pauseTimers();
            mChart.removeAllViews();
            mChart.destroy();
            mChart = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }

}