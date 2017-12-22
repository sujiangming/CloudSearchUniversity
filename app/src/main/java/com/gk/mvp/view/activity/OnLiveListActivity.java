package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.OnLiveBean;
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
 * Created by JDRY-SJM on 2017/12/22.
 */

public class OnLiveListActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_onlive)
    ListView lvOnlive;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartLayout;

    @Override
    public int getResouceId() {
        return R.layout.activity_on_live_list;
    }

    private List<OnLiveBean> list = new ArrayList<>();
    private GlideImageLoader loader = new GlideImageLoader();

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "直播列表", 0);
        initSmartRefreshLayout(smartLayout, false);
        getVideoAdsList();
    }

    @Override
    public void refresh() {
        getVideoAdsList();
    }

    private void getVideoAdsList() {
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getVideoAdsList())
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        list = JSON.parseArray(commonBean.getData().toString(), OnLiveBean.class);
        lvOnlive.setAdapter(new CommonAdapter<OnLiveBean>(this, R.layout.on_live_list_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, OnLiveBean item, int position) {
                viewHolder.setText(R.id.tv_on_live_name, item.getLiveName());
                ImageView imageView = viewHolder.getView(R.id.iv_item);
                loader.displayCircleRadius(OnLiveListActivity.this, item.getLiveCrossLogo(), imageView, 12);
            }
        });
        lvOnlive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("bean", list.get(i));
                openNewActivityByIntent(OnLiveRoomActivity.class, intent);
            }
        });
        hideProgress();
        stopRefreshLayout();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast("获取直播列表失败");
        hideProgress();
        stopRefreshLayout();
    }

}
