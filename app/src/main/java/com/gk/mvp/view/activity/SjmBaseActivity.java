package com.gk.mvp.view.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.global.YXXApplication;
import com.gk.mvp.view.IView;
import com.gk.mvp.view.custom.SjmProgressBar;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public abstract class SjmBaseActivity extends AppCompatActivity implements IView {
    public abstract int getResouceId();

    protected abstract void onCreateByMe(Bundle savedInstanceState);

    private Unbinder unbinder;

    protected YXXApplication app;

    private SjmProgressBar jdryProgressBar;

    /**
     * 设置状态栏透明
     */
    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置状态栏透明
     */
    public void setStatusBarColor(int colorRes) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorRes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置topbar共同的方法
     *
     * @param topBar
     * @param title
     * @param color
     */
    public void setTopBar(TopBarView topBar, String title, int color) {
        topBar.getTitleView().setText(title);
        if (color != 0) {
            topBar.getTitleView().setTextColor(color);
        }
        topBar.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
    }

    public void setTextViewValues(TextView textView, String value) {
        if (null != textView && !TextUtils.isEmpty(value)) {
            textView.setText(value);
        }
    }

    public void setSearchViewText(SearchView searchView) {
        if (searchView == null) {
            return;
        }
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(14);
        //设置字体颜色
        autoComplete.setTextColor(getResources().getColor(R.color.color808080));
        //设置提示文字颜色
        autoComplete.setHintTextColor(getResources().getColor(R.color.color808080));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResouceId());
        unbinder = ButterKnife.bind(this);
        onCreateByMe(savedInstanceState);
        //将当前Activity加进集合
        app = (YXXApplication) getApplication();
        app.activities.add(this);
        getLargeMemory();
    }

    public void openNewActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openNewActivityByIntent(Class<?> cls, Intent intent) {
        if (null == intent) {
            return;
        }
        intent.setClass(this, cls);
        startActivity(intent);
    }

    public void openNewActivity(Class<?> cls) {
        openNewActivity(cls, null);
    }

    public void closeActivity() {
        app.activities.remove(this);
        this.finish();
    }

    public void closeActivity(Activity activity) {
        app.activities.remove(activity);
        activity.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        app.activities.remove(this);//将当前Activity移除集合
        RefWatcher refWatcher = YXXApplication.refWatcher;
        refWatcher.watch(this);
    }

    @Override
    public void showProgress() {
        if (null == this.jdryProgressBar) {
            jdryProgressBar = SjmProgressBar.show(this);
        } else {
            jdryProgressBar.show();
        }
    }

    @Override
    public void hideProgress() {
        if (null != jdryProgressBar && jdryProgressBar.isShowing()) {
            jdryProgressBar.dismiss();
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }

    @Override
    public <T> void fillWithData(T t, int order) {

    }

    public void toast(String desc) {
        if (null == desc) {
            ToastUtils.toast(this, "请求失败");
        } else {
            ToastUtils.toast(this, desc);
        }

    }

    public void refresh() {

    }

    public void loadMore() {

    }

    private RefreshLayout mRefreshLayout;

    public void initSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout, boolean isLoadMore) {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setmRefreshLayout(refreshlayout);
                refresh();
            }
        });
        if (isLoadMore) {
            smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
            smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    setmRefreshLayout(refreshlayout);
                    loadMore();
                }
            });
        }
    }

    public void stopLayoutRefreshByTag(boolean isLoadMore) {
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
    }

    public void stopRefreshLayout() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
        }
    }

    public void stopRefreshLayoutLoadMore() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
    }

    public void setmRefreshLayout(RefreshLayout mRefreshLayout) {
        if (this.mRefreshLayout == null) {
            this.mRefreshLayout = mRefreshLayout;
        }
    }

    public void getLargeMemory() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = activityManager != null ? activityManager.getMemoryClass() : 0;
        Log.d("heapSize:", "" + heapSize);
    }
}
