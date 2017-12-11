package com.gk.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.HLDImageTypeEnum;
import com.gk.beans.HLDTable;
import com.gk.beans.HLDTableDao;
import com.gk.beans.HLDTypeEnum;
import com.gk.beans.HldReportBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXApplication;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.RadarMarkerView;
import com.gk.mvp.view.custom.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/11.
 */

public class HLDTestResultActivity extends SjmBaseActivity {

    @BindView(R.id.chart1)
    RadarChart mChart;
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

    @Override
    public int getResouceId() {
        return R.layout.activity_hld_test_result;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "兴趣测试", 0);
        textViews = new TextView[]{tv1, tv2, tv3};
        textViewType = new TextView[]{tvForIv1, tvForIv2, tvForIv3};
        httpRequest();
        initData();
    }

    private void initData() {
        mChart.setBackgroundColor(Color.rgb(60, 65, 82));

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData();

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(mTfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.radar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionToggleRotate: {
                if (mChart.isRotationEnabled())
                    mChart.setRotationEnabled(false);
                else
                    mChart.setRotationEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {

                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) mChart.getData()
                        .getDataSets();

                for (IRadarDataSet set : sets) {
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlightCircle: {

                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) mChart.getData()
                        .getDataSets();

                for (IRadarDataSet set : sets) {
                    set.setDrawHighlightCircleEnabled(!set.isDrawHighlightCircleEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
            case R.id.actionToggleXLabels: {
                mChart.getXAxis().setEnabled(!mChart.getXAxis().isEnabled());
                mChart.notifyDataSetChanged();
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleYLabels: {

                mChart.getYAxis().setEnabled(!mChart.getYAxis().isEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                mChart.spin(2000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
            }
        }
        return true;
    }

    public void setData() {

        float mult = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mult) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mult) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();
    }

    private void httpRequest() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("answers", getSelectedAnswer());
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getHldTestReport(jsonObject.toJSONString()))
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
        if (hldReportBean == null) {
            return;
        }
        initCommon();
        addCommonSubView();
        addTypicalSubView();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void initCommon() {
        tvJielun.setText(hldReportBean.getCareerTypical());
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
            textViewType[i].setText(HLDTypeEnum.getName(strings[i]));
            View view = View.inflate(this, R.layout.hld_result_xg_item, null);
            ImageView imageView = view.findViewById(R.id.iv_hld_xg);
            TextView textView = view.findViewById(R.id.tv_hld_xg_desc);
            imageView.setImageResource(HLDImageTypeEnum.getImageRes(strings[i]));
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
                String tz = "1、" + hldReportBean.getCareerTypicalOne() + "\n 2、" + hldReportBean.getCareerTypicalTwo()
                        + "\n 3、" + hldReportBean.getCareerTypicalThree();
                tv_hld_xg_feature.setText(tz);
            } else {
                tv_hld_xg_desc.setText("推荐职业");
                String recommond = hldReportBean.getRecommendOccupation();
                StringBuffer values = new StringBuffer("");
                if (recommond != null) {
                    String[] strings = recommond.split(",");
                    for (int j = 0; j < strings.length; j++) {
                        if (j == (strings.length - 1)) {
                            values.append(strings[j]);
                        } else {
                            values.append(j + "、").append(strings[j]).append("\n ");
                        }
                    }
                }
                tv_hld_xg_feature.setText(values.toString());
            }
            llXgFitCareer.addView(view);
        }
    }
}
