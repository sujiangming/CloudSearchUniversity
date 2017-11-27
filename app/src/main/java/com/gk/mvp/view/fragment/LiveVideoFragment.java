package com.gk.mvp.view.fragment;

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
import com.gk.beans.AdsBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LiveBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.LiveVideoDetailActivity;
import com.gk.mvp.view.adpater.LiveVideoAdapter;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.ToastUtils;
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
    Banner bannerLive;
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

    @Override
    public int getResourceId() {
        return R.layout.fragment_live;
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
        initBanner();
        liveVideoAdapter = new LiveVideoAdapter(getContext());
        liveList.setAdapter(liveVideoAdapter);
        //这个方法和将listView及其父元素隐藏掉的效果是一样的
        //View listEmptyView = View.inflate(getContext(), R.layout.error_tip, (ViewGroup) liveList.getParent().getParent());
        //liveList.setEmptyView(listEmptyView);
        //如果下行代码放在这里的话，每次进来都预先会看到无数据的提示信息
        //liveList.setEmptyView(linearLayout);
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
        PresenterManager.getInstance()
                .setmContext(getContext())
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getVideoList(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        if (commonBean.getData() == null) {
            toast("没有数据");
            return;
        }
        liveBeanList = JSON.parseArray(commonBean.getData().toString(), LiveBean.class);
        liveVideoAdapter.update(liveBeanList);
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void initBanner() {
        List<String> imageList = new ArrayList<>();
        List<AdsBean.MDataBean> mDataBeans = AdsBean.getInstance().getMData();
        if (mDataBeans == null || mDataBeans.size() == 0) {
            return;
        }
        for (int i = 0; i < mDataBeans.size(); i++) {
            AdsBean.MDataBean mDataBean = mDataBeans.get(i);
            if (mDataBean.getType() == 2) {
                imageList.add(mDataBean.getUrl());
            }
        }
        bannerLive.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        bannerLive.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.toast(getContext(), "第 " + position + " 张图片");
            }
        });
    }

    private void initSmartRefreshLayout() {
        smartRfLive.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRfLive.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRfLive.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                invoke(0);
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
}
