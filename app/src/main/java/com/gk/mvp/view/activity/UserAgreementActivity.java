package com.gk.mvp.view.activity;

import android.os.Bundle;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2018/1/7.
 */

public class UserAgreementActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;

    @Override
    public int getResouceId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "用户协议", 0);
    }
}
