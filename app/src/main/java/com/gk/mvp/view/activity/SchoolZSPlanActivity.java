package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.SchoolZSBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
    @BindView(R.id.searchview)
    SearchView searchView;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        closeActivity(this);
    }

    private List<SchoolZSBean> schoolBeanList;
    private JSONObject jsonObject;
    private int mPage = 0;
    private boolean isLoadMore = false;
    private String searchKey = "";
    private CommonAdapter<SchoolZSBean> adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_zs_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        schoolBeanList = new ArrayList<>();
        jsonObject = new JSONObject();
        invoke();
        showSearch();
        setSearchViewText(searchView);
    }

    private void invoke() {
        jsonObject.put("page", mPage);
        jsonObject.put("schoolName", searchKey);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniRecruitPlanList(jsonObject.toJSONString()))
                .request();
    }

    private void showSearch() {
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchKey = YxxUtils.URLEncode(s);
                invoke();
                if (searchView != null) {
                    // 得到输入管理对象
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                    }
                }
                searchView.clearFocus(); // 不获取焦点
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s == null || "".equals(s)) {
                    searchKey = "";
                    invoke();
                }
                return true;
            }
        });
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        CommonBean commonBean = (CommonBean) t;
        List<SchoolZSBean> beanList = JSON.parseArray(commonBean.getData().toString(), SchoolZSBean.class);
        if (isLoadMore) {
            if (beanList == null || beanList.size() == 0) {
                toast("已经扯到底啦");
                return;
            }
            schoolBeanList.addAll(beanList);
        } else {
            if (beanList == null || beanList.size() == 0) {
                toast("没有查询到数据");
                showTvNoData();
                return;
            }
            hideTvNoData();
            schoolBeanList = beanList;
        }
        lvQuerySchool.setAdapter(adapter = new CommonAdapter<SchoolZSBean>(this, R.layout.school_zhaosheng_list_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, SchoolZSBean item, int position) {
                viewHolder.setText(R.id.tv_school_plan, item.getYearPlan() + "年招生计划");
                viewHolder.setText(R.id.tv_school_address, item.getArea());
                viewHolder.setText(R.id.tv_bk_num, item.getUndergraduate() + "");
                viewHolder.setText(R.id.tv_zk_num, item.getSpecializedSubject() + "");
                viewHolder.setText(R.id.tv_tiqian, item.getAdvanceBatch() + "");
                viewHolder.setText(R.id.tv_school_name, item.getSchoolName() == null ? "未知" : item.getSchoolName());
            }
        });
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("您点击的是：" + i);
            }
        });
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke();
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke();
    }

    private void showTvNoData() {
        lvQuerySchool.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }

    private void hideTvNoData() {
        if (lvQuerySchool.getVisibility() == View.GONE) {
            lvQuerySchool.setVisibility(View.VISIBLE);
        }
        if (tvNoData.getVisibility() == View.VISIBLE) {
            tvNoData.setVisibility(View.GONE);
        }
    }

}
