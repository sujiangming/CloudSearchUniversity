package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.UniversityLuQuDataBean;
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

public class LqRiskTestResultLqDataActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.rtv_data)
    RichText rtvData;
    @BindView(R.id.lv_score)
    ListView lvScore;

    private List<UniversityLuQuDataBean> list = new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_lq_risk_test_result_lq_data;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        String uniName = getIntent().getStringExtra("uniName");
        setTopBar(topBar, uniName, 0);
        initAdapter();
        getMajorAdmissionsData(uniName);
    }

    private void initAdapter() {
        adapter = new CommonAdapter<UniversityLuQuDataBean>(this, list, R.layout.school_detail_luqu_item) {
            @Override
            public void convert(ViewHolder viewHolder, UniversityLuQuDataBean item) {
                viewHolder.setText(R.id.tv_zy_name, item.getMajorName());
                viewHolder.setText(R.id.tv_year, item.getYearStr());
                viewHolder.setText(R.id.tv_type, item.getSubjectType());
                viewHolder.setText(R.id.tv_score_hight, item.getHighestScore());
                viewHolder.setText(R.id.tv_score_lower, item.getLowestScore());
            }
        };
        lvScore.setAdapter(adapter);
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void getMajorAdmissionsData(String uniName) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schoolName", YxxUtils.URLEncode(uniName));
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getUniMajorAdmissionData(jsonObject.toJSONString()))
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
        List<UniversityLuQuDataBean> luQuDataBeans = JSON.parseArray(commonBean.getData().toString(), UniversityLuQuDataBean.class);
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

    private void handleData(List<UniversityLuQuDataBean> luQuDataBeans) {
        list.clear();
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
