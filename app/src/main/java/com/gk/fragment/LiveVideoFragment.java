package com.gk.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gk.R;
import com.gk.adpater.LiveVideoAdapter;
import com.gk.presenter.LiveVideoPresenter;
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

    private LiveVideoPresenter liveVideoPresenter;
    private LiveVideoAdapter liveVideoAdapter;
    private List<String> mStringList;

    @Override
    public int getResourceId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout();
        initBanner();
        liveVideoPresenter = new LiveVideoPresenter(getContext(), this);
        liveVideoAdapter = new LiveVideoAdapter(getContext());
        liveVideoPresenter.getData(0);

        //这个方法和将listView及其父元素隐藏掉的效果是一样的
        //View listEmptyView = View.inflate(getContext(), R.layout.error_tip, (ViewGroup) liveList.getParent().getParent());
        //liveList.setEmptyView(listEmptyView);
        //如果下行代码放在这里的话，每次进来都预先会看到无数据的提示信息
        //liveList.setEmptyView(linearLayout);
        liveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //openNewActivity(LiveVideoDetailActivity.class);
                ToastUtils.toast(getContext(), mStringList.get(position));
            }
        });

    }

    private void initBanner() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.banner_buy_vip);
        imageList.add(R.drawable.banner_intelligent_wish);
        imageList.add(R.drawable.bao_zhang);
        bannerLive.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        bannerLive.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.toast(getContext(), "第 " + position + " 张图片");
                //openNewActivity(LiveMainActivity.class);
            }
        });
    }

    private void initSmartRefreshLayout() {
        smartRfLive.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRfLive.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRfLive.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //liveVideoPresenter.getData(0);
                refreshlayout.finishRefresh();
            }
        });
        smartRfLive.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //liveVideoPresenter.getData(1);
                refreshlayout.finishLoadmore();
            }
        });
    }

    @Override
    public <T> void fillWithNoData(T t) {
        liveList.setEmptyView(linearLayout);
        liveList.setAdapter(null);
    }

    @Override
    public <T> void fillWithData(List<T> list) {
        liveList.setAdapter(liveVideoAdapter);
        mStringList = (List<String>) list;
        liveVideoAdapter.update(mStringList);
    }
}
