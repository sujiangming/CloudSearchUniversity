package com.gk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.gk.R;
import com.gk.beans.QuerySchoolBean;
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

public class SchoolRankActivity extends SjmBaseActivity {
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    @OnClick(R.id.back_image)
    public void onClickView() {
        closeActivity();
    }

    private List<QuerySchoolBean> schoolBeanList = new ArrayList<>();

    @Override
    public int getResouceId() {
        return R.layout.activity_rank_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout();
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
        lvQuerySchool.setAdapter(new CommonAdapter<QuerySchoolBean>(this, R.layout.school_rank_list_item, schoolBeanList) {
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
}
