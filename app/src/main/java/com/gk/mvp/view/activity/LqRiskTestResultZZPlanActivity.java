package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.UniversityZsPlanBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2018/3/5.
 */

public class LqRiskTestResultZZPlanActivity extends SjmBaseActivity {
    @BindView(R.id.rtv_zs)
    RichText rtvData;
    @BindView(R.id.lv_zs)
    ListView lvScore;
    @BindView(R.id.top_bar)
    TopBarView topBar;

    private List<UniversityZsPlanBean> list = new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_lq_risk_test_result_zs_plan;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        String uniName = getIntent().getStringExtra("uniName");
        setTopBar(topBar, uniName, 0);
        initAdapter();
        getMajorAdmissionsData(uniName);
    }

    private void initAdapter() {
        adapter = new CommonAdapter<UniversityZsPlanBean>(this, list, R.layout.school_detail_list_item) {
            @Override
            public void convert(ViewHolder viewHolder, UniversityZsPlanBean item) {
                viewHolder.setText(R.id.tv_year, (item.getMajorName() == null ? "----" : item.getMajorName()));
                viewHolder.setText(R.id.tv_type, (item.getYearStr() == null ? "----" : item.getYearStr()));
                viewHolder.setText(R.id.tv_score_hight, (item.getSubjectType() == null ? "----" : item.getSubjectType()));
                viewHolder.setText(R.id.tv_score_lower, (item.getPlanNum() == null ? "----" : item.getPlanNum()));
                viewHolder.setText(R.id.tv_score_control, (item.getArea() == null ? "----" : item.getArea()));
            }
        };
        lvScore.setAdapter(adapter);
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void getMajorAdmissionsData(String uniName) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schoolName", YxxUtils.URLEncode(uniName));
        presenterManager
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getUniMajorPlan(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        if (null == commonBean || null == commonBean.getData()) {
            toast("获取数据失败");
            return;
        }
        List<UniversityZsPlanBean> luQuDataBeans = JSON.parseArray(commonBean.getData().toString(), UniversityZsPlanBean.class);
        if (luQuDataBeans == null) {
            toast(commonBean.getMessage());
            return;
        }
        handleData(luQuDataBeans);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void handleData(List<UniversityZsPlanBean> luQuDataBeans) {
        list = luQuDataBeans;
        adapter.setItems(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }
}
