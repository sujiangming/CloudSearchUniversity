package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.gk.beans.SchoolRankBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.RankSchoolAdapter;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class SchoolRankActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    @OnClick(R.id.back_image)
    public void onClickView() {
        closeActivity(this);
    }

    private List<SchoolRankBean> schoolBeanList = new ArrayList<>();
    private GlideImageLoader glideImageLoader = new GlideImageLoader();
    private JSONObject jsonObject;
    private int mPage = 0;
    private boolean isLoadMore = false;
    private RankSchoolAdapter adapter;
    private String searchKey = "";
    private boolean isSearch = false;

    @Override
    public int getResouceId() {
        return R.layout.activity_rank_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        initKeyBoardParameter();
        jsonObject = new JSONObject();
        setLvQuerySchool();
        invoke();
        showSearch();
        setSearchViewText(searchView);
    }

    private void invoke() {
        showProgress();
        jsonObject.put("page", mPage);
        jsonObject.put("schoolName", searchKey);
        new PresenterManager()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniRankingList(jsonObject.toJSONString()))
                .request();
    }

    private void showSearch() {
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private String TAG = getClass().getSimpleName();

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchKey = YxxUtils.URLEncode(s);
                isSearch = true;
                invoke();
                if (searchView != null) {
                    hideSoftKey();
                }
                searchView.clearFocus(); // 不获取焦点
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s == null || "".equals(s)) {
                    searchKey = "";
                    isSearch = false;
                    invoke();
                    hideSoftKey();
                }
                return true;
            }
        });
    }

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    private void hideSoftKey() {
        //隐藏软盘
        InputMethodManager imm = (InputMethodManager) searchView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //editText失去焦点
        searchView.clearFocus();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            searchView.clearFocus();
        }
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        CommonBean commonBean = (CommonBean) t;
        List<SchoolRankBean> beanList = JSON.parseArray(commonBean.getData().toString(), SchoolRankBean.class);
        if (isSearch) {
            schoolBeanList.clear();
            schoolBeanList.addAll(beanList);
            if (schoolBeanList.size() == 0) {
                showTvNoData();
                return;
            }
            hideTvNoData();
            adapter.update(schoolBeanList);
            return;
        }

        if (isLoadMore) {
            if (beanList == null || beanList.size() == 0) {
                toast("已经扯到底啦");
                return;
            }
            schoolBeanList.addAll(beanList);
            adapter.update(beanList, true);
            lvQuerySchool.smoothScrollToPosition(lvQuerySchool.getLastVisiblePosition(), 0);
            return;
        }
        if (beanList == null || beanList.size() == 0) {
            toast("没有查询到数据");
            showTvNoData();
            return;
        }
        hideTvNoData();
        int currentSize = schoolBeanList.size();
        schoolBeanList.addAll(beanList);
        schoolBeanList = removeDuplicate(schoolBeanList);
        int afterSize = schoolBeanList.size();
        if (currentSize == afterSize) {
            toast("没有最新数据");
            return;
        }
        adapter.update(schoolBeanList);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        isSearch = false;
        invoke();
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        isSearch = false;
        invoke();
    }

    private void setLvQuerySchool() {
        adapter = new RankSchoolAdapter(this);
        lvQuerySchool.setAdapter(adapter);
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("uniName", schoolBeanList.get(i));
                intent.putExtra("flag", "rank");
                openNewActivityByIntent(SchoolDetailActivity.class, intent);
            }
        });
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

    public List<SchoolRankBean> removeDuplicate(List<SchoolRankBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getSchoolId().equals(list.get(i).getSchoolId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
