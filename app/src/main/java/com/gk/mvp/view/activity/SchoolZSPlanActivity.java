package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.SchoolZSBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.YxxUtils;
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

public class SchoolZSPlanActivity extends SjmBaseActivity {
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    @OnClick(R.id.back_image)
    public void onClickView() {
        closeActivity();
    }

    private List<SchoolZSBean> schoolBeanList = new ArrayList<>();

    @Override
    public int getResouceId() {
        return R.layout.activity_zs_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout();
        initListView();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", 0);
        jsonObject.put("schoolName", YxxUtils.URLEncode("清华大学"));
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniRecruitPlanList(jsonObject.toJSONString()))
                .request();
    }

    private void initListView() {
        for (int i = 0; i < 20; ++i) {
            SchoolZSBean querySchoolBean = new SchoolZSBean();
            querySchoolBean.setbKeNum(10 + i);
            querySchoolBean.setzKeNum(10 + i);
            querySchoolBean.setSchoolName("清华大学" + i);
            querySchoolBean.setZsPlanNum("2017年招生计划：" + i);
            querySchoolBean.setZsAddress("贵州");
            schoolBeanList.add(querySchoolBean);
        }
        lvQuerySchool.setAdapter(new CommonAdapter<SchoolZSBean>(this, R.layout.school_zhaosheng_list_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, SchoolZSBean item, int position) {
                viewHolder.setText(R.id.tv_school_plan, item.getZsPlanNum());
                viewHolder.setText(R.id.tv_school_address, item.getZsAddress());
                viewHolder.setText(R.id.tv_bk_num, item.getbKeNum() + "");
                viewHolder.setText(R.id.tv_zk_num, item.getzKeNum() + "");
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
