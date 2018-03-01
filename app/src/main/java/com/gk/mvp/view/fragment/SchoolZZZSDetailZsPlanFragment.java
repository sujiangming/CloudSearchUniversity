package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.SchoolZZZSPlanBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.RichText;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolZZZSDetailZsPlanFragment extends SjmBaseFragment {
    @BindView(R.id.rtv_zs)
    RichText rtvData;
    @BindView(R.id.lv_zs)
    ListView lvScore;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;

    private List<SchoolZZZSPlanBean> list = new ArrayList<>();
    private String uniName;
    private int page = 0;
    private boolean isLoadMore = false;
    private CommonAdapter adapter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_zzzs_detail_zs_plan;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRefreshLayout, true);
        initAdapter();
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

    @Override
    public void refresh() {
        page = 0;
        isLoadMore = false;
        getMajorAdmissionsData(uniName);
    }

    @Override
    public void loadMore() {
        page++;
        isLoadMore = true;
        getMajorAdmissionsData(uniName);
    }

    private void initAdapter() {
        adapter = new CommonAdapter<SchoolZZZSPlanBean>(getContext(), list, R.layout.school_detail_list_item) {
            @Override
            public void convert(ViewHolder viewHolder, SchoolZZZSPlanBean item) {
                viewHolder.setText(R.id.tv_year, (item.getMajorName() == null ? "----" : item.getMajorName()));
                viewHolder.setText(R.id.tv_type, (item.getPlanYear() == null ? "----" : item.getPlanYear()));
                viewHolder.setText(R.id.tv_score_hight, (item.getSubjectType() == null ? "----" : item.getSubjectType()));
                viewHolder.setText(R.id.tv_score_lower, (item.getPlanNum() == null ? "----" : item.getPlanNum()));
                viewHolder.setText(R.id.tv_score_control, (item.getPlanArea() == null ? "----" : item.getPlanArea()));
            }
        };
        lvScore.setAdapter(adapter);
    }

    private void getMajorAdmissionsData(String uniName) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("selfUniversityName", YxxUtils.URLEncode(uniName));
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getSelfRecruitMajor(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        CommonBean commonBean = (CommonBean) t;
        if (!isLoadMore) {
            if (null == commonBean || null == commonBean.getData()) {
                toast("获取数据失败");
                return;
            }
            list = JSON.parseArray(commonBean.getData().toString(), SchoolZZZSPlanBean.class);
            if (list == null) {
                toast(commonBean.getMessage());
                return;
            }
            adapter.setItems(list);
            return;
        }
        if (isLoadMore) {
            if (null == commonBean || null == commonBean.getData()) {
                toast("没有更多数据了");
                return;
            }
            List<SchoolZZZSPlanBean> dataBeans = JSON.parseArray(commonBean.getData().toString(), SchoolZZZSPlanBean.class);
            list.addAll(dataBeans);
            adapter.setItems(list);
            if (lvScore != null) {
                lvScore.smoothScrollToPosition(lvScore.getLastVisiblePosition(), 0);
            }
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }
}
