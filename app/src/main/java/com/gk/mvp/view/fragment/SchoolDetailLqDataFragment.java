package com.gk.mvp.view.fragment;

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
import com.gk.mvp.view.custom.RichText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolDetailLqDataFragment extends SjmBaseFragment {
    @BindView(R.id.rtv_data)
    RichText rtvData;
    @BindView(R.id.lv_score)
    ListView lvScore;

    private List<UniversityLuQuDataBean> list = new ArrayList<>();
    private String uniName;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_detail_lq_data;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        uniName = getArguments().getString("uniName");
        getMajorAdmissionsData(uniName);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getMajorAdmissionsData(uniName);
        }
    }

    private void getMajorAdmissionsData(String uniName) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schoolId", uniName);
        PresenterManager.getInstance()
                .setmIView(this)
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
        lvScore.setAdapter(new CommonAdapter<UniversityLuQuDataBean>(getContext(), R.layout.school_detail_luqu_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, UniversityLuQuDataBean item, int position) {
                viewHolder.setText(R.id.tv_year, item.getArea());
                viewHolder.setText(R.id.tv_type, item.getMajorName());
                viewHolder.setText(R.id.tv_score_hight, item.getHighestScore());
                viewHolder.setText(R.id.tv_score_lower, item.getLowestScore());
                viewHolder.setText(R.id.tv_plan_num, item.getPlanNum());
            }
        });
    }

}
