package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.SpinnerBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class QuerySchoolActivity extends SjmBaseActivity {
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.spinner4)
    Spinner spinner4;
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    @OnClick(R.id.back_image)
    public void onClickView() {
        closeActivity();
    }

    private SpinnerBean spinnerBeanSelected = null;
    private SpinnerBean[] spinnerBeans = null;
    private List<QuerySchoolBean.DataBean> schoolBeanList = new ArrayList<>();
    private GlideImageLoader glideImageLoader = new GlideImageLoader();

    @Override
    public int getResouceId() {
        return R.layout.activity_query_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout();
        setSpinnerByArea(spinner1, initSpinnerData(YXXConstants.PROVINCES));
        setSpinnerByArea(spinner2, initSpinnerData(YXXConstants.FEATURES));
        setSpinnerByArea(spinner3, initSpinnerData(YXXConstants.CLASSIFICATION));
        setSpinnerByArea(spinner4, initSpinnerData(YXXConstants.LEVEL));
        initListView();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", 0);
        jsonObject.put("schoolArea", "");//学校地区
        jsonObject.put("schoolCategory", "");//学校类别
        jsonObject.put("schoolType", "");//学校类型（1本科、2专业）
        jsonObject.put("tese", "");//特色
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniversityList(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        String data = (String) t;
        if (data == null || "".equals(data)) {
            return;
        }
        QuerySchoolBean querySchoolBean = JSON.parseObject(data, QuerySchoolBean.class);
        if (querySchoolBean == null) {
            return;
        }
        schoolBeanList = querySchoolBean.getData();
        initListView();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }

    private void initListView() {
        lvQuerySchool.setAdapter(new CommonAdapter<QuerySchoolBean.DataBean>(this, R.layout.query_school_list_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, QuerySchoolBean.DataBean item, int position) {
                String isDoubleTop = item.getIsDoubleTop();
                String isNef = item.getIsNef();
                String isToo = item.getIsToo();
                viewHolder.getView(R.id.tv_school_mark_0).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_school_mark_1).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_school_mark_2).setVisibility(View.VISIBLE);
                viewHolder.setText(R.id.tv_school_mark_0, "1".equals(isNef) ? "985" : "非985");
                viewHolder.setText(R.id.tv_school_mark_1, "1".equals(isToo) ? "211" : "非211");
                viewHolder.setText(R.id.tv_school_mark_2, isDoubleTop.equals("1") ? "双一流" : "非双一流");
                ImageView imageView = viewHolder.getView(R.id.iv_query_item);
                glideImageLoader.displayImage(QuerySchoolActivity.this, item.getSchoolLogo(), imageView);
                viewHolder.setText(R.id.tv_school_name, item.getSchoolName());
                viewHolder.setText(R.id.tv_school_type, "1".equals(item.getSchoolType()) ? "本科" : "专科");
                viewHolder.setText(R.id.tv_school_level, "1".equals(item.getSchoolCategory()) ? "综合类" : "教育类");
                viewHolder.setText(R.id.tv_school_address, item.getSchoolArea());
            }
        });
    }

    private void initSmartRefreshLayout() {
        smartRfQuerySchool.setRefreshHeader(new ClassicsHeader(this));
        smartRfQuerySchool.setRefreshFooter(new ClassicsFooter(this));
        smartRfQuerySchool.setEnableLoadmore(true);
        smartRfQuerySchool.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        smartRfQuerySchool.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
            }
        });
    }

    private SpinnerBean[] initSpinnerData(String[] strings) {
        int len = strings.length;
        SpinnerBean[] spinnerBeenArray = new SpinnerBean[len];
        for (int i = 0; i < len; ++i) {
            SpinnerBean spinnerBean = new SpinnerBean();
            spinnerBean.index = i;
            spinnerBean.deptId = "addressID" + i;
            spinnerBean.deptName = strings[i];
            spinnerBeenArray[i] = spinnerBean;
        }
        return spinnerBeenArray;
    }

    private void setSpinnerByArea(final Spinner spinner, final SpinnerBean[] spinnerBeanList) {
        ArrayAdapter<SpinnerBean> mArrayAdapter = new ArrayAdapter<SpinnerBean>(this, R.layout.trans_activity_spinner, spinnerBeanList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.trans_activity_spinner_item, parent, false);
                }
                TextView spinnerText = (TextView) convertView.findViewById(R.id.trans_spinner_textView);
                spinnerText.setText(getItem(position).deptName);

                return convertView;
            }
        };
        spinner.setAdapter(mArrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setTextColor(0xFF302f30);
                spinnerBeanSelected = spinnerBeanList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
