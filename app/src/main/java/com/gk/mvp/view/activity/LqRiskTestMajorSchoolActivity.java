package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.QuerySchoolByMajorBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class LqRiskTestMajorSchoolActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    private List<QuerySchoolByMajorBean> schoolBeanList = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private String majorName = null;
    private CommonAdapter adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_test_major_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "请选择学校", 0);
        initSmartRefreshLayout(smartRfQuerySchool, false);
        majorName = getIntent().getStringExtra("major");
        initListView();
        invoke();
    }

    @Override
    public void refresh() {
        invoke();
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void invoke() {
        showProgress();
        jsonObject.put("majorName", YxxUtils.URLEncode(majorName));
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniversityByMajor(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopRefreshLayout();
        CommonBean commonBean = (CommonBean) t;
        if (null == commonBean.getData()) {
            toast("请求失败");
            return;
        }
        schoolBeanList = JSON.parseArray(commonBean.getData().toString(), QuerySchoolByMajorBean.class);
        if (schoolBeanList.size() == 0) {
            return;
        }
        adapter.setItems(schoolBeanList);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        stopRefreshLayout();
    }

    private void initListView() {
        adapter = new CommonAdapter<QuerySchoolByMajorBean>(this, schoolBeanList, R.layout.risk_major_query_item) {
            @Override
            public void convert(ViewHolder viewHolder, QuerySchoolByMajorBean item) {
                String schoolName = item.getSchoolName();

                if (!TextUtils.isEmpty(schoolName)) {
                    viewHolder.setText(R.id.tv_university_name, schoolName);
                }

                if (!TextUtils.isEmpty(majorName)) {
                    viewHolder.setText(R.id.tv_major_name, majorName);
                }

                GlideImageLoader.displayByImgRes(LqRiskTestMajorSchoolActivity.this, item.getSchoolLogo(),
                        (ImageView) viewHolder.getView(R.id.iv_logo), R.drawable.gaoxiaozhanweitu);
            }
        };
        lvQuerySchool.setAdapter(adapter);
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("majorName", majorName);
                intent.putExtra("schoolName", schoolBeanList.get(i).getSchoolName());
                LqRiskTestMajorSchoolActivity.this.setResult(119, intent);
                closeActivity(LqRiskTestMajorSchoolActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }
}
