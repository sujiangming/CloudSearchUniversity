package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.SchoolRankBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;

    @OnClick(R.id.back_image)
    public void onClickView() {
        closeActivity();
    }

    private List<SchoolRankBean> schoolBeanList = new ArrayList<>();
    private GlideImageLoader glideImageLoader = new GlideImageLoader();

    @Override
    public int getResouceId() {
        return R.layout.activity_rank_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", 0);
        jsonObject.put("schoolName", "");
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniRankingList(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        schoolBeanList = JSON.parseArray(commonBean.getData().toString(), SchoolRankBean.class);
        initListView();
    }

    private void initListView() {
        lvQuerySchool.setAdapter(new CommonAdapter<SchoolRankBean>(this, R.layout.school_rank_list_item, schoolBeanList) {
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
    }

    private void initSmartRefreshLayout() {
        smartRfQuerySchool.setRefreshHeader(new ClassicsHeader(this));
        smartRfQuerySchool.setRefreshFooter(new ClassicsFooter(this));
        smartRfQuerySchool.setEnableLoadmore(true);
        smartRfQuerySchool.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        smartRfQuerySchool.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
            }
        });
    }
}
