package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.SchoolZZZsBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.SchoolZZZSAdapter;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class SchoolZiZhuZSListActivity extends SjmBaseActivity {
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.ll_top_bar)
    LinearLayout llTopBar;
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    @OnClick(R.id.back_image)
    public void onClickView() {
        closeActivity(this);
    }

    private List<SchoolZZZsBean> schoolBeanList = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;
    private String nullStr = "";
    private String searchKey = nullStr;
    private SchoolZZZSAdapter adapter;
    private boolean isSearcher = false;

    @Override
    public int getResouceId() {
        return R.layout.activity_zizhu_zs;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        initListView();
        invoke();
        showSearch();
        setSearchViewText(searchview);
    }

    private void initListView() {
        adapter = new SchoolZZZSAdapter(this);
        lvQuerySchool.setAdapter(adapter);
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("uniName", schoolBeanList.get(i));
                openNewActivityByIntent(SchoolZZZSDetailActivity.class, intent);
            }
        });
    }

    private void showSearch() {
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchKey = YxxUtils.URLEncode(s);
                isLoadMore = false;
                isSearcher = true;
                mPage = 0;
                search(searchKey);
                clearSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    private void clearSearch() {
        hideSoftKey();
        searchview.clearFocus(); // 不获取焦点
    }

    private void hideSoftKey() {
        // 得到输入管理对象
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
            imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
        }
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        if (isSearcher) {
            search(searchKey);
            return;
        }
        invoke();
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        if (isSearcher) {
            search(searchKey);
            return;
        }
        invoke();
    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void invoke() {
        showProgress();
        jsonObject.put("page", mPage);
        presenterManager.setCall(RetrofitUtil.getInstance()
                .createReq(IService.class).getSelfRecruitUniversity(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenterManager && null != presenterManager.getCall()){
            presenterManager.getCall().cancel();
        }
    }

    private void search(String selfUniversityName) {
        showProgress();
        jsonObject.put("selfUniversityName", selfUniversityName);
        presenterManager.setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getSelfRecruitUniversityLikeName(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        String data = (String) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                handleListData(data);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                handleSearchData(data);
                break;
            default:
                break;
        }

    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        if (order == YXXConstants.INVOKE_API_DEFAULT_TIME) {
            stopLayoutRefreshByTag(isLoadMore);
        }
    }

    private void handleListData(String data) {
        if (data == null || nullStr.equals(data)) {
            toast("没有相关数据");
            return;
        }
        JSONObject jsonObject = JSON.parseObject(data);
        List<SchoolZZZsBean> querySchoolBean = JSONObject.parseArray(jsonObject.getString("data"), SchoolZZZsBean.class);
        List<SchoolZZZsBean> schoolBeanListTmp = new ArrayList<>();
        if (!isLoadMore) {
            schoolBeanList = querySchoolBean;
            schoolBeanListTmp = schoolBeanList;
            adapter.update(schoolBeanList);
            return;
        }
        stopRefreshLayoutLoadMore();
        List<SchoolZZZsBean> dataBeans = querySchoolBean;
        if (data == null) {
            toast("别扯了，我是有底线的");
            return;
        }
        schoolBeanList.addAll(dataBeans);
        schoolBeanListTmp = schoolBeanList;
        adapter.update(dataBeans, true);
        if (lvQuerySchool != null) {
            lvQuerySchool.smoothScrollToPosition(lvQuerySchool.getLastVisiblePosition(), 0);
        }
    }

    private void handleSearchData(String data) {
        if (data == null || nullStr.equals(data)) {
            toast("没有相关数据");
            return;
        }
        JSONObject jsonObject = JSON.parseObject(data);
        List<SchoolZZZsBean> querySchoolBean = JSONObject.parseArray(jsonObject.getString("data"), SchoolZZZsBean.class);
        if (null == querySchoolBean || 0 == querySchoolBean.size()) {
            toast("找不到相关数据");
            return;
        }
        schoolBeanList = querySchoolBean;
        adapter.update(schoolBeanList);
    }
}
