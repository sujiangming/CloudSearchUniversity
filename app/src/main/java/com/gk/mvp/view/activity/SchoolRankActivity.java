package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.SchoolRankBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.GlideImageLoader;
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

public class SchoolRankActivity extends SjmBaseActivity {
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
    private CommonAdapter<SchoolRankBean> adapter;
    private String searchKey = "";
    private boolean isSearch = false;

    @Override
    public int getResouceId() {
        return R.layout.activity_rank_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        jsonObject = new JSONObject();
        invoke();
        showSearch();
    }

    private void invoke() {
        jsonObject.put("page", mPage);
        jsonObject.put("schoolName", searchKey);
        PresenterManager.getInstance()
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
                Log.d(TAG, "onQueryTextSubmit = " + s);
                searchKey = YxxUtils.URLEncode(s);
                isSearch = true;
                invoke();
                if (searchView != null) {
                    closeKeySoftInput();
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
                    closeKeySoftInput();
                }
                return true;
            }
        });
        //搜索框展开时后面叉叉按钮的点击事件
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchKey = "";
                isSearch = false;
                invoke();
                closeKeySoftInput();
                return true;
            }
        });
    }

    private void closeKeySoftInput() {
        // 得到输入管理对象
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
            imm.hideSoftInputFromWindow(searchView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
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
            setLvQuerySchool();
            return;
        }

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
            int currentSize = schoolBeanList.size();
            schoolBeanList.addAll(beanList);
            schoolBeanList = removeDuplicate(schoolBeanList);
            int afterSize = schoolBeanList.size();
            if (currentSize == afterSize) {
                toast("没有最新数据");
                return;
            }
        }
        setLvQuerySchool();

    }

    private void setLvQuerySchool() {
        lvQuerySchool.setAdapter(adapter = new CommonAdapter<SchoolRankBean>(this, R.layout.school_rank_list_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, SchoolRankBean item, int position) {
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
                glideImageLoader.displayImage(SchoolRankActivity.this, item.getSchoolLogo(), imageView);
                viewHolder.setText(R.id.tv_school_name, item.getSchoolName());
                viewHolder.setText(R.id.tv_school_type, "1".equals(item.getSchoolType()) ? "本科" : "专科");
                viewHolder.setText(R.id.tv_school_level, "1".equals(item.getSchoolCategory()) ? "综合类" : "教育类");
                viewHolder.setText(R.id.tv_school_address, item.getSchoolArea());
            }
        });

        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("当前点击的是：" + i);
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
