package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.UniversityZsPlanBean;
import com.gk.beans.UniversityZsPlanItemBean;
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

public class SchoolDetailZsPlanFragment extends SjmBaseFragment {
    @BindView(R.id.rtv_zs)
    RichText rtvData;
    @BindView(R.id.lv_zs)
    ListView lvScore;

    private List<UniversityZsPlanItemBean> list = new ArrayList<>();
    private String uniName;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_detail_zs_plan;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        uniName = getArguments().getString("uniName");
        Log.e(SchoolDetailZsPlanFragment.class.getName(), uniName);
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
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMajorRecruitPlan(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        List<UniversityZsPlanBean> luQuDataBeans = JSON.parseArray(commonBean.getData().toString(), UniversityZsPlanBean.class);
        if (luQuDataBeans == null) {
            toast(commonBean.getMessage());
            return;
        }
        handleData(luQuDataBeans);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void handleData(List<UniversityZsPlanBean> luQuDataBeans) {
        list.clear();
        for (int i = 0; i < luQuDataBeans.size(); i++) {
            UniversityZsPlanItemBean bean = new UniversityZsPlanItemBean();
            bean.setMajorName(luQuDataBeans.get(i).getMajorName());
            bean.setMajorId(luQuDataBeans.get(i).getMajorId());
            List<UniversityZsPlanBean.MajorRecruitPlanBean> admissionsDataBeanList = luQuDataBeans.get(i).getMajorRecruitPlan();
            if (admissionsDataBeanList != null && admissionsDataBeanList.size() > 0) {
                for (UniversityZsPlanBean.MajorRecruitPlanBean plan : admissionsDataBeanList) {
                    bean.setArea(plan.getArea());
                    bean.setId(plan.getId());
                    bean.setMajorBasicId(plan.getMajorBasicId());
                    bean.setPlanNum(plan.getPlanNum());
                    bean.setSubjectType(plan.getSubjectType());
                    bean.setYearStr(plan.getYearStr());
                }
            }
            list.add(bean);
        }
        lvScore.setAdapter(new CommonAdapter<UniversityZsPlanItemBean>(getContext(), R.layout.school_detail_list_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, UniversityZsPlanItemBean item, int position) {
                viewHolder.setText(R.id.tv_year, (item.getMajorName() == null ? "----" : item.getMajorName()));
                viewHolder.setText(R.id.tv_type, (item.getYearStr() == null ? "----" : item.getYearStr()));
                viewHolder.setText(R.id.tv_score_hight, (item.getSubjectType() == null ? "----" : item.getSubjectType()));
                viewHolder.setText(R.id.tv_score_lower, (item.getPlanNum() == null ? "----" : item.getPlanNum()));
                viewHolder.setText(R.id.tv_score_control, (item.getArea() == null ? "----" : item.getArea()));
            }
        });
    }
}
