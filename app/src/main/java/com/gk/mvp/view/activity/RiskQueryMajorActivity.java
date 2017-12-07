package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gk.R;
import com.gk.beans.MajorQueryBean;
import com.gk.global.YXXConstants;
import com.gk.mvp.presenter.MajorPresenter;
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

public class RiskQueryMajorActivity extends SjmBaseActivity {
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;
    @BindView(R.id.searchview)
    SearchView searchview;

    @OnClick(R.id.back_image)
    public void onViewClicked(View view) {
        closeActivity(this);
    }

    private List<MajorQueryBean.DataBean> listQuery = new ArrayList<>();
    private MajorPresenter majorPresenter;
    private CommonAdapter<MajorQueryBean.DataBean> adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_test_query_major;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        showProgress();
        majorPresenter = new MajorPresenter(this);
        majorPresenter.queryMajorByName("", 1);//默认查询的是本科 2 是专科
        showSearch();
    }

    @Override
    public void refresh() {
        showProgress();
        majorPresenter.queryMajorByName("", 1);//默认查询的是本科 2 是专科
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        switch (order) {
            case YXXConstants.INVOKE_API_SECOND_TIME:
                listQuery = (List<MajorQueryBean.DataBean>) t;
                initQueryData();
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
        stopRefreshLayout();
    }

    /**
     * 以下为搜索部分
     */
    private void initQueryData() {
        lvQuerySchool.setAdapter(adapter = new CommonAdapter<MajorQueryBean.DataBean>(this, R.layout.professional_query_item, listQuery) {
            @Override
            protected void convert(ViewHolder viewHolder, MajorQueryBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_zy_name, item.getMajorName());
            }
        });
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(RiskQueryMajorActivity.this, RiskTestMajorSchoolActivity.class);
                intent.putExtra("major", listQuery.get(i).getMajorName());
                startActivityForResult(intent, 119);

            }
        });
    }

    private void showSearch() {
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private String TAG = getClass().getSimpleName();

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit = " + s);
                majorPresenter.queryMajorByName(YxxUtils.URLEncode(s), 1);
                if (searchview != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                    }
                }
                searchview.clearFocus(); // 不获取焦点
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setQueryHint("请输入专业名称");
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 119) {
            this.setResult(119, data);
            closeActivity(this);
        }
    }
}
