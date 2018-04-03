package com.gk.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import com.gk.mvp.view.activity.LiveVideoDetailActivity;
import com.gk.mvp.view.activity.OnLiveRoomActivity;
import com.gk.mvp.view.adpater.LiveVideoAdapter;
import com.gk.tools.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LiveVideoFragment extends SjmBaseFragment {
    @BindView(R.id.banner_live)
    Banner banner;
    @BindView(R.id.live_list)
    ListView liveList;
    @BindView(R.id.smart_rf_live)
    SmartRefreshLayout smartRfLive;

    @BindView(R.id.ll_no_data_tip)
    LinearLayout linearLayout;

    private LiveVideoAdapter liveVideoAdapter;
    private JSONObject jsonObject = new JSONObject();
    private List<LiveBean> liveBeanList = new ArrayList<>();

    private int mPage = 0;
    private List<OnLiveBean> list = new ArrayList<>();
    private boolean isLoadMore = false;

    @Override
    public int getResourceId() {
        return R.layout.fragment_live;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getVideoAdsList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        invoke(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            invoke(0);
        }
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfLive, true);
        liveVideoAdapter = new LiveVideoAdapter(getContext());
        liveList.setAdapter(liveVideoAdapter);
        liveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("videoId", liveBeanList.get(position));
                openNewActivityByIntent(LiveVideoDetailActivity.class, intent);
            }
        });
        addListener();
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke(mPage);
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke(mPage);
        stopLayoutRefreshByTag(isLoadMore);
    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void getVideoAdsList() {
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getVideoAdsList())
                .request(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    private void invoke(int page) {
        showProgress();
        jsonObject.put("page", page);
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getVideoList(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                if (!isLoadMore) {
                    if (commonBean.getData() == null) {
                        toast("没有数据");
                        return;
                    }
                    liveBeanList = JSON.parseArray(commonBean.getData().toString(), LiveBean.class);
                    liveVideoAdapter.update(liveBeanList);
                    return;
                }
                if (commonBean.getData() == null) {
                    toast("没有更多数据");
                    return;
                }
                List<LiveBean> liveBeans = JSON.parseArray(commonBean.getData().toString(), LiveBean.class);
                liveBeanList.addAll(liveBeans);
                liveVideoAdapter.update(liveBeanList);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                list = JSON.parseArray(commonBean.getData().toString(), OnLiveBean.class);
                initBanner();
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void initBanner() {
        final List<String> imageList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            imageList.add(list.get(i).getLiveCrossLogo());
        }
        banner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
    }

    private void addListener() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list.size() == 0) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("bean", list.get(position));
                openNewActivityByIntent(OnLiveRoomActivity.class, intent);
            }
        });
    }
}
