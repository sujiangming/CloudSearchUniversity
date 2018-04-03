package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LiveBean;
import com.gk.beans.OnLiveBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.LiveVideoAdapter;
import com.gk.mvp.view.custom.TopBarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2018/1/9.
 */

public class VideoListActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.live_list)
    ListView liveList;
    @BindView(R.id.smart_rf_live)
    SmartRefreshLayout smartRfLive;

    private LiveVideoAdapter liveVideoAdapter;
    private JSONObject jsonObject = new JSONObject();
    private List<LiveBean> liveBeanList = new ArrayList<>();

    private int mPage = 0;
    private List<OnLiveBean> list = new ArrayList<>();
    private boolean isLoadMore = false;

    @Override
    public int getResouceId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "视频列表", 0);
        initSmartRefreshLayout(smartRfLive, true);
        initData();
        invoke(mPage);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke(mPage);
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke(mPage);
    }

    private void initData() {
        liveVideoAdapter = new LiveVideoAdapter(this);
        liveList.setAdapter(liveVideoAdapter);
        liveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("videoId", liveBeanList.get(position));
                openNewActivityByIntent(LiveVideoDetailActivity.class, intent);
            }
        });
    }

    private void invoke(int page) {
        showProgress();
        jsonObject.put("page", page);
        new PresenterManager()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getVideoList(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        if (commonBean.getData() == null) {
            toast("没有数据");
            return;
        }
        List<LiveBean> liveBeans = JSON.parseArray(commonBean.getData().toString(), LiveBean.class);
        if (!isLoadMore) {
            liveBeanList = liveBeans;
            liveVideoAdapter.update(liveBeanList);
        } else {
            liveBeanList.addAll(liveBeans);
            liveVideoAdapter.notifyDataSetChanged();
        }
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast("获取失败");
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }
}
