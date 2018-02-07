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
import com.gk.mvp.view.activity.MainActivity;
import com.gk.mvp.view.activity.MaterialListActivity;
import com.gk.mvp.view.activity.OnLiveRoomActivity;
import com.gk.mvp.view.activity.QuerySchoolActivity;
import com.gk.mvp.view.activity.WishReportEnterActivity;
import com.gk.mvp.view.adpater.LiveVideoAdapter;
import com.gk.tools.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
        initSmartRefreshLayout();
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

    private void initSmartRefreshLayout() {
        smartRfLive.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRfLive.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRfLive.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 0;
                invoke(mPage);
                refreshlayout.finishRefresh();
            }
        });
        smartRfLive.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                invoke(mPage);
                refreshlayout.finishRefresh();
            }
        });
    }

    private void getVideoAdsList() {
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getVideoAdsList())
                .request(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    private void invoke(int page) {
        showProgress();
        jsonObject.put("page", page);
        PresenterManager.getInstance()
                .setmContext(getContext())
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getVideoList(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                if (commonBean.getData() == null) {
                    toast("没有数据");
                    return;
                }
                liveBeanList = JSON.parseArray(commonBean.getData().toString(), LiveBean.class);
                liveVideoAdapter.update(liveBeanList);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                list = JSON.parseArray(commonBean.getData().toString(), OnLiveBean.class);
                initBanner();
                break;
        }
        hideProgress();
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

    private void goActivityByRedirectUrl(String redirectUr) {
        Intent intent = new Intent();
        switch (redirectUr) {
            case "schoolList"://大学列表
                openNewActivityByIntent(QuerySchoolActivity.class, intent);
                break;
            case "schoolVideoList"://视频列表
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeNavStyle(mainActivity.getLlVideo());
                break;
            case "pastQuestion"://历史真题
                intent.putExtra("type", 2);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case "simulationQuestion"://模拟试卷
                intent.putExtra("type", 3);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case "teacherRoom"://名师讲堂
                intent.putExtra("type", 1);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case "applicationPaper"://志愿报告
                openNewActivity(WishReportEnterActivity.class);
                break;
        }
    }
}
