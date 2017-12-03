package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.gk.R;
import com.gk.mvp.view.custom.ProgressWebView;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/3.
 */

public class AdsDeatilActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.progressWv)
    ProgressWebView webView;

    @Override
    public int getResouceId() {
        return R.layout.activity_ads_detail;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        }
        closeActivity(this);
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        webView.loadUrl(url);

        topBar.getTitleView().setText(title);
        topBar.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                closeActivity(AdsDeatilActivity.this);
            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
