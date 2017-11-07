package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.SpinnerBean;
import com.gk.global.YXXConstants;
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
    private List<QuerySchoolBean> schoolBeanList = new ArrayList<>();

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
    }

    private void initListView() {
        for (int i = 0; i < 20; ++i) {
            QuerySchoolBean querySchoolBean = new QuerySchoolBean();
            querySchoolBean.setSchoolAddress("北京" + i);
            querySchoolBean.setSchoolIcon(R.drawable.qinghuadaxue3x);
            querySchoolBean.setSchoolLevel("本科" + i);
            querySchoolBean.setSchoolName("清华大学" + i);
            querySchoolBean.setSchoolType("综合");
            String[] schoolMarks = new String[3];
            for (int j = 0; j < 3; ++j) {
                if (j == 0) {
                    schoolMarks[j] = "985";
                } else if (j == 1) {
                    schoolMarks[j] = "211";
                } else {
                    schoolMarks[j] = "双一流";
                }
            }
            querySchoolBean.setSchoolMark(schoolMarks);
            schoolBeanList.add(querySchoolBean);
        }
        lvQuerySchool.setAdapter(new CommonAdapter<QuerySchoolBean>(this, R.layout.query_school_list_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, QuerySchoolBean item, int position) {
                String[] marks = item.getSchoolMark();
                viewHolder.getView(R.id.tv_school_mark_0).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_school_mark_1).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_school_mark_2).setVisibility(View.VISIBLE);
                viewHolder.setText(R.id.tv_school_mark_0, marks[0]);
                viewHolder.setText(R.id.tv_school_mark_1, marks[1]);
                viewHolder.setText(R.id.tv_school_mark_2, marks[2]);
                viewHolder.setImageResource(R.id.iv_query_item, item.getSchoolIcon());
                viewHolder.setText(R.id.tv_school_name, item.getSchoolName());
                viewHolder.setText(R.id.tv_school_type, item.getSchoolType());
                viewHolder.setText(R.id.tv_school_level, item.getSchoolLevel());
                viewHolder.setText(R.id.tv_school_address, item.getSchoolAddress());
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
