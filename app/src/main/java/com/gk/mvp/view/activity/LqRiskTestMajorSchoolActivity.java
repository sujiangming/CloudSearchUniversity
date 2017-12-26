package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

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

    private List<QuerySchoolBean.DataBean> schoolBeanList = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;
    private String nullString = "";
    private GlideImageLoader imageLoader = new GlideImageLoader();
    private String majorName = null;

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_test_major_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "请选择学校", 0);
        majorName = getIntent().getStringExtra("major");
        initSmartRefreshLayout(smartRfQuerySchool, true);
        invoke(nullString, nullString, nullString, nullString, nullString);
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
        stopLayoutRefreshByTag(isLoadMore);
        String data = (String) t;
        if (data == null || nullString.equals(data)) {
            toast("没有相关数据");
            return;
        }
        QuerySchoolBean querySchoolBean = JSON.parseObject(data, QuerySchoolBean.class);
        if (mPage == 0 && !isLoadMore) {
            schoolBeanList = querySchoolBean.getData();
            initListView();
            return;
        }
        if (isLoadMore) {
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
        stopLayoutRefreshByTag(isLoadMore);
    }

    private void initListView() {
        lvQuerySchool.setAdapter(new CommonAdapter<QuerySchoolBean.DataBean>(this, R.layout.risk_major_query_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, QuerySchoolBean.DataBean item, int position) {
                String schoolName = item.getSchoolName();
                if (schoolName != null && !"".equals(schoolName)) {
                    viewHolder.setText(R.id.tv_university_name, schoolName);
                }
                if (majorName != null && !"".equals(majorName)) {
                    viewHolder.setText(R.id.tv_major_name, majorName);
                }

                imageLoader.displayImage(LqRiskTestMajorSchoolActivity.this, item.getSchoolLogo(),
                        (ImageView) viewHolder.getView(R.id.iv_logo));
            }
        });
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("schoolName", schoolBeanList.get(i).getSchoolName() + "+" + majorName);
                LqRiskTestMajorSchoolActivity.this.setResult(119, intent);
                closeActivity(LqRiskTestMajorSchoolActivity.this);
            }
        });
    }
}
