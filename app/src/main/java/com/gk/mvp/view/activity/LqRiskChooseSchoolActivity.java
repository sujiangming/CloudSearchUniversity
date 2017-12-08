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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.global.YXXConstants;
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

public class LqRiskChooseSchoolActivity extends SjmBaseActivity {
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

    private List<QuerySchoolBean.DataBean> schoolBeanList = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;
    private String nullString = "";

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_test_query_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        invoke(nullString, nullString, nullString, nullString, nullString);
        showSearch();
    }

    private void showSearch() {
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private String TAG = getClass().getSimpleName();

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit = " + s);
                invoke(nullString, nullString, nullString, nullString, YxxUtils.URLEncode(s));
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
                searchview.setQueryHint("请输入学校名称");
                return true;
            }
        });
    }

    private void invoke(String schoolArea, String schoolCategory, String schoolType, String tese, String schoolName) {
        jsonObject.put("page", mPage);
        jsonObject.put("schoolArea", schoolArea);//学校地区
        jsonObject.put("schoolCategory", schoolCategory);//学校类别
        jsonObject.put("schoolType", schoolType);//学校类型（1本科、2专业）
        jsonObject.put("tese", tese);//特色
        jsonObject.put("schoolName", schoolName);//学校名称
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniversityList(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke(nullString, nullString, nullString, nullString, nullString);

    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke(nullString, nullString, nullString, nullString, nullString);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        String data = (String) t;
        if (data == null || nullString.equals(data)) {
            toast("没有相关数据");
            return;
        }
        QuerySchoolBean querySchoolBean = JSON.parseObject(data, QuerySchoolBean.class);
        if (mPage == 0 && !isLoadMore) {
            schoolBeanList = querySchoolBean.getData();
            initListView();
            stopRefreshLayout();
            return;
        }
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
            List<QuerySchoolBean.DataBean> dataBeans = querySchoolBean.getData();
            if (data == null) {
                toast("没有更多数据了");
                return;
            }
            schoolBeanList.addAll(dataBeans);
            initListView();
            lvQuerySchool.smoothScrollByOffset(lvQuerySchool.getHeight());
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
    }

    private void initListView() {
        lvQuerySchool.setAdapter(new CommonAdapter<QuerySchoolBean.DataBean>(this, R.layout.risk_test_query_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, QuerySchoolBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getSchoolName());
            }
        });
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("schoolName", schoolBeanList.get(i).getSchoolName());
                LqRiskChooseSchoolActivity.this.setResult(110, intent);
                closeActivity(LqRiskChooseSchoolActivity.this);
            }
        });
    }
}
