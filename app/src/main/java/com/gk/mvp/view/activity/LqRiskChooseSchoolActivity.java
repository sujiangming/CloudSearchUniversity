package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
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
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class LqRiskChooseSchoolActivity extends SjmBaseActivity {
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.searchview)
    SearchView searchview;

    @OnClick(R.id.back_image)
    public void onViewClicked(View view) {
        closeActivity(this);
    }

    private List<String> schoolBeanList = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private String nullString = "";
    private String[] schoolArray = {"清华大学", "北京大学", "中国人民大学", "北京交通大学", "北京工业大学",
            "北京航空航天大学", "北京理工大学", "北京科技大学", "中国政法大学",
            "中央财经大学", "华北电力大学", "北京体育大学", "上海外国语大学", "复旦大学",
            "华东师范大学", "上海大学", "河北工业大学"};

    private CommonAdapter<String> adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_test_query_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        showSearch();
        setSearchViewText(searchview);
        initData();
    }

    private void initData() {
        Collections.addAll(schoolBeanList, schoolArray);
        initListView();
    }

    private void showSearch() {
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                invoke(nullString, nullString, nullString, nullString, YxxUtils.URLEncode(s));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
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

    private void initListView() {
        adapter = new CommonAdapter<String>(this, schoolBeanList, R.layout.risk_test_query_item) {
            @Override
            public void convert(ViewHolder viewHolder, String item) {
                viewHolder.setText(R.id.tv_name, item);
            }
        };
        lvQuerySchool.setAdapter(adapter);
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("schoolName", schoolBeanList.get(i));
                LqRiskChooseSchoolActivity.this.setResult(110, intent);
                closeActivity(LqRiskChooseSchoolActivity.this);
            }
        });

    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void invoke(String schoolArea, String schoolCategory, String schoolType, String tese, String schoolName) {
        showProgress();
        int mPage = 0;
        jsonObject.put("page", mPage);
        jsonObject.put("schoolArea", schoolArea);//学校地区
        jsonObject.put("schoolCategory", schoolCategory);//学校类别
        jsonObject.put("schoolType", schoolType);//学校类型（1本科、2专业）
        jsonObject.put("tese", tese);//特色
        jsonObject.put("schoolName", schoolName);
        presenterManager
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniversityList(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        String data = (String) t;
        if (TextUtils.isEmpty(data)) {
            toast("没有相关数据");
            return;
        }
        QuerySchoolBean querySchoolBean = JSON.parseObject(data, QuerySchoolBean.class);
        List<QuerySchoolBean.DataBean> dataBeanList = querySchoolBean.getData();
        if (null == dataBeanList || dataBeanList.size() == 0) {
            toast("没有相关数据");
            return;
        }
        schoolBeanList.clear();
        for (int i = 0; i < dataBeanList.size(); i++) {
            schoolBeanList.add(dataBeanList.get(i).getSchoolName());
        }
        adapter.setItems(schoolBeanList);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }
}
