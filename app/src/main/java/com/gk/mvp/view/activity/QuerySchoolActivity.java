package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.UniversityAreaEnum;
import com.gk.beans.UniversityFeatureEnum;
import com.gk.beans.UniversityLevelEnum;
import com.gk.beans.UniversityTypeEnum;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.GridViewChooseAdapter;
import com.gk.mvp.view.adpater.QuerySchoolAdapter;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class QuerySchoolActivity extends SjmBaseActivity {
    @BindView(R.id.spinner1)
    TextView spinner1;
    @BindView(R.id.spinner2)
    TextView spinner2;
    @BindView(R.id.spinner3)
    TextView spinner3;
    @BindView(R.id.spinner4)
    TextView spinner4;
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.gv_channel)
    GridView gvChannel;
    @BindView(R.id.btn_choose)
    Button btnChoose;
    @BindView(R.id.rl_choose)
    LinearLayout rlChoose;
    @BindView(R.id.tv_muti_choose)
    TextView tv_muti_choose;

    @OnClick({R.id.back_image, R.id.spinner1, R.id.spinner2,
            R.id.spinner3, R.id.spinner4, R.id.btn_choose, R.id.tv_bg_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                closeActivity(this);
                break;
            case R.id.spinner1:
                if (!isSpinner1Clicked) {
                    rlChoose.setVisibility(View.VISIBLE);
                    tv_muti_choose.setVisibility(View.VISIBLE);
                    gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityAreaEnum.getAreaList(), 1);
                    initGridViewAdapter();
                    isSpinner1Clicked = true;
                } else {
                    rlChoose.setVisibility(View.GONE);
                    isSpinner1Clicked = false;
                }
                type = 1;
                break;
            case R.id.spinner2:
                if (!isSpinner2Clicked) {
                    rlChoose.setVisibility(View.VISIBLE);
                    tv_muti_choose.setVisibility(View.GONE);
                    gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityTypeEnum.getUniversityList(), 2);
                    initGridViewAdapter();
                    isSpinner2Clicked = true;
                } else {
                    rlChoose.setVisibility(View.GONE);
                    isSpinner2Clicked = false;
                }
                type = 2;
                break;
            case R.id.spinner3:
                if (!isSpinner3Clicked) {
                    rlChoose.setVisibility(View.VISIBLE);
                    tv_muti_choose.setVisibility(View.GONE);
                    gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityFeatureEnum.getUniversityFeatureList(), 3);
                    initGridViewAdapter();
                    isSpinner3Clicked = true;
                } else {
                    rlChoose.setVisibility(View.GONE);
                    isSpinner3Clicked = false;
                }

                type = 3;
                break;
            case R.id.spinner4:
                if (!isSpinner4Clicked) {
                    rlChoose.setVisibility(View.VISIBLE);
                    tv_muti_choose.setVisibility(View.GONE);
                    gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityLevelEnum.getUniversityLevelList(), 4);
                    initGridViewAdapter();
                    isSpinner4Clicked = true;
                } else {
                    rlChoose.setVisibility(View.GONE);
                    isSpinner4Clicked = false;
                }

                type = 4;
                break;
            case R.id.btn_choose:
                String str = nullStr;
                String strName = nullStr;
                rlChoose.setVisibility(View.GONE);
                queryIndexList = gridViewChooseAdapter.getCheckedArray();
                if (queryIndexList != null) {
                    for (int i = 0; i < queryIndexList.size(); i++) {
                        gridViewChooseAdapter.getIsCheck().set(i, false);
                        if (i == queryIndexList.size() - 1) {
                            str += queryIndexList.get(i);
                            strName += getEnumName(Integer.valueOf(queryIndexList.get(i)));
                        } else {
                            str += queryIndexList.get(i) + ",";
                            strName += getEnumName(Integer.valueOf(queryIndexList.get(i))) + ",";
                        }
                    }
                }
                if (!strName.equals(nullStr)) {
                    setEnumName(strName, str);
                }
                isLoadMore = false;
                mPage = 0;
                isSearcher = false;
                if (type == 1) {
                    spinner1.setTag(str);
                    invoke(str, spinner2.getTag().toString(), spinner3.getTag().toString(), spinner4.getTag().toString(), nullStr);
                } else if (type == 2) {
                    spinner2.setTag(str);
                    invoke(spinner1.getTag().toString(), str, spinner3.getTag().toString(), spinner4.getTag().toString(), nullStr);
                } else if (type == 3) {
                    spinner3.setTag(str);
                    invoke(spinner1.getTag().toString(), spinner2.getTag().toString(), str, spinner4.getTag().toString(), nullStr);
                } else if (type == 4) {
                    spinner4.setTag(str);
                    invoke(spinner1.getTag().toString(), spinner2.getTag().toString(), spinner3.getTag().toString(), str, nullStr);
                }
                break;
            case R.id.tv_bg_click:
                rlChoose.setVisibility(View.GONE);
                tv_muti_choose.setVisibility(View.GONE);
                break;
        }
    }

    private List<QuerySchoolBean.DataBean> schoolBeanList = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;
    private GridViewChooseAdapter gridViewChooseAdapter;
    private List<String> queryIndexList;
    private int type = 1;
    private String nullStr = "";
    private String searchKey = nullStr;
    private QuerySchoolAdapter adapter;
    private boolean isSpinner1Clicked, isSpinner2Clicked, isSpinner3Clicked, isSpinner4Clicked;
    private boolean isSearcher = false;


    @Override
    public int getResouceId() {
        return R.layout.activity_query_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        initListView();
        invoke(nullStr, nullStr, nullStr, nullStr, nullStr);
        showSearch();
        setSearchViewText(searchview);
    }

    private void initListView() {
        adapter = new QuerySchoolAdapter(this);
        lvQuerySchool.setAdapter(adapter);
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("uniName", schoolBeanList.get(i));
                intent.putExtra("flag", "query");
                openNewActivityByIntent(SchoolDetailActivity.class, intent);
            }
        });
    }

    private String getEnumName(int index) {
        String name = nullStr;
        if (type == 1) {
            name = UniversityAreaEnum.getName(index);
        } else if (type == 2) {
            name = UniversityTypeEnum.getName(index);
        } else if (type == 3) {
            name = UniversityFeatureEnum.getName(index);
        } else {
            name = UniversityLevelEnum.getName(index);
        }
        return name;
    }

    private void setEnumName(String name, String tag) {
        if (type == 1) {
            spinner1.setText(name);
            spinner1.setTag(tag);
        } else if (type == 2) {
            spinner2.setText(name);
            spinner2.setTag(tag);
        } else if (type == 3) {
            spinner3.setText(name);
            spinner3.setTag(tag);
        } else {
            spinner4.setText(name);
            spinner4.setTag(tag);
        }
    }

    private void initGridViewAdapter() {
        gvChannel.setAdapter(gridViewChooseAdapter);
        gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridViewChooseAdapter.choiceState(i, view);
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
                if (!TextUtils.isEmpty(spinner1.getTag().toString())) {
                    spinner1.setTag("");
                    spinner1.setText("所在地");
                }
                if (!TextUtils.isEmpty(spinner2.getTag().toString())) {
                    spinner2.setTag("");
                    spinner2.setText("高校类型");
                }
                if (!TextUtils.isEmpty(spinner3.getTag().toString())) {
                    spinner3.setTag("");
                    spinner3.setText("重点院校");
                }
                if (!TextUtils.isEmpty(spinner4.getTag().toString())) {
                    spinner4.setTag("");
                    spinner4.setText("批次");
                }
                invoke(nullStr, nullStr, nullStr, nullStr, searchKey);
                clearSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //initQueryData(s);
                return true;
            }
        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setQueryHint("请输入关键字");
                hideSoftKey();
                return true;
            }
        });
    }

    private void clearSearch() {
        if (searchview != null) {
            hideSoftKey();
        }
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

    private PresenterManager presenterManager = new PresenterManager();

    private void invoke(String schoolArea, String schoolCategory, String schoolType, String tese, String schoolName) {
        showProgress();
        jsonObject.put("page", mPage);
        jsonObject.put("schoolArea", schoolArea);
        jsonObject.put("schoolCategory", schoolCategory);
        jsonObject.put("schoolBatch", tese);
        jsonObject.put("tese", schoolType);
        jsonObject.put("schoolName", schoolName);
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniversityList(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        if (isSearcher) {
            invoke(nullStr, nullStr, nullStr, nullStr, searchKey);
            return;
        }
        invoke(spinner1.getTag().toString(),
                spinner2.getTag().toString(),
                spinner3.getTag().toString(),
                spinner4.getTag().toString(),
                nullStr);
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        if (isSearcher) {
            invoke(nullStr, nullStr, nullStr, nullStr, searchKey);
            return;
        }
        invoke(spinner1.getTag().toString(),
                spinner2.getTag().toString(),
                spinner3.getTag().toString(),
                spinner4.getTag().toString(),
                nullStr);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        String data = (String) t;
        if (data == null || nullStr.equals(data)) {
            toast("没有相关数据");
            return;
        }
        QuerySchoolBean querySchoolBean = JSON.parseObject(data, QuerySchoolBean.class);
        if (!isLoadMore) {
            schoolBeanList = querySchoolBean.getData();
            adapter.update(schoolBeanList);
            return;
        }
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
            List<QuerySchoolBean.DataBean> dataBeans = querySchoolBean.getData();
            if (data == null) {
                toast("别扯了，我是有底线的");
                return;
            }
            schoolBeanList.addAll(dataBeans);
            adapter.update(dataBeans, true);
            if (lvQuerySchool != null) {
                lvQuerySchool.smoothScrollToPosition(lvQuerySchool.getLastVisiblePosition(), 0);
            }
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }
}
