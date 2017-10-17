package com.gk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gk.IView;
import com.gk.custom.SjmProgressBar;
import com.gk.tools.AppManager;
import com.gk.tools.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public abstract class SjmBaseActivity extends AppCompatActivity implements IView {
    public abstract int getResouceId();

    protected abstract void onCreateByMe(Bundle savedInstanceState);

    private Unbinder unbinder;

    public AppManager appManager = AppManager.getAppManager();

    private SjmProgressBar jdryProgressBar;

    /**
     * 设置状态栏透明
     */
    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public <T> void fillWithNoData(T t) {

    }

    @Override
    public <T> void fillWithData(List<T> list) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResouceId());
        unbinder = ButterKnife.bind(this);
        onCreateByMe(savedInstanceState);
        appManager.addActivity(this);
    }

    @Override
    public void openNewActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void openNewActivityByIntent(Class<?> cls, Intent intent) {
        if (null == intent) {
            return;
        }
        intent.setClass(this, cls);
        startActivity(intent);
    }

    @Override
    public void openNewActivity(Class<?> cls) {
        openNewActivity(cls, null);
    }

    @Override
    public void closeActivity() {
        appManager.finishActivity();
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
    }

    @Override
    public void showProgress() {
        if (null == this.jdryProgressBar) {
            jdryProgressBar = SjmProgressBar.show(this);
        }
    }

    @Override
    public void hideProgress() {
        if (null != jdryProgressBar && jdryProgressBar.isShowing()) {
            jdryProgressBar.dismiss();
        }
    }

    public void toast(String desc) {
        ToastUtils.toast(this, desc);
    }
}
