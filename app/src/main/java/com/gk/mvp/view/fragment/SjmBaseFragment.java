package com.gk.mvp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gk.mvp.view.IView;
import com.gk.mvp.view.custom.SjmProgressBar;
import com.gk.tools.AppManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public abstract class SjmBaseFragment extends Fragment implements IView {

    private Unbinder unbinder;
    protected Context mContext;
    protected View mRootView;

    private AppManager appManager = AppManager.getAppManager();

    private SjmProgressBar jdryProgressBar;

    public abstract int getResourceId();

    protected abstract void onCreateViewByMe(Bundle savedInstanceState);

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }

    @Override
    public <T> void fillWithData(T t, int order) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getResourceId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        this.mContext = getActivity();
        onCreateViewByMe(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void openNewActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openNewActivityByIntent(Class<?> cls, Intent intent) {
        if (null == intent) {
            return;
        }
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

    public void openNewActivity(Class<?> cls) {
        openNewActivity(cls, null);
    }

    public void closeActivity() {
        appManager.finishActivity();
    }

    public void closeActivity(Activity activity) {
        appManager.finishActivity(activity);
    }

    @Override
    public void showProgress() {
        if (null == this.jdryProgressBar) {
            jdryProgressBar = SjmProgressBar.show(mContext);
        }
    }

    @Override
    public void hideProgress() {
        if (null != jdryProgressBar && jdryProgressBar.isShowing()) {
            jdryProgressBar.dismiss();
        }
    }

    public void refresh(int pageNum) {

    }

    public void loadMore(int pageNum) {

    }

    public void initSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout, boolean isLoadMore, final int page) {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh(page);
                refreshlayout.finishRefresh();
            }
        });
        if (isLoadMore) {
            smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    loadMore(page);
                    refreshlayout.finishLoadmore();
                }
            });
        }
    }
}
