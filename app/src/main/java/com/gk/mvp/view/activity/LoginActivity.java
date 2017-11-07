package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.gk.R;
import com.gk.global.YXXConstants;
import com.gk.mvp.view.adpater.LoginFragmentPagerAdapter;
import com.gk.mvp.view.custom.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/10/9.
 */

public class LoginActivity extends SjmBaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.top_bar_login)
    TopBarView topBarLogin;

    private List<String> list;
    private int mEnterFlag = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initTopBar();
        initData();
        pager.setAdapter(new LoginFragmentPagerAdapter(getSupportFragmentManager(), list,mEnterFlag));
        tabLayout.setupWithViewPager(pager);
    }

    private void initTopBar() {
        Intent intent = getIntent();
        mEnterFlag = intent.getIntExtra(YXXConstants.ENTER_LOGIN_PAGE_FLAG, 0);
        if (mEnterFlag == YXXConstants.FROM_SPLASH_FLAG) {
            topBarLogin.getBackView().setVisibility(View.GONE);
        }
        topBarLogin.getTitleView().setText("登录");
        topBarLogin.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

    }

    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        list.add("验证码登录");
        list.add("密码登录");
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mEnterFlag == YXXConstants.FROM_SPLASH_FLAG) {
                //两秒之内按返回键就会退出
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    toast("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    appManager.finishAllActivity();
                }
                return true;
            } else {
                closeActivity();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
