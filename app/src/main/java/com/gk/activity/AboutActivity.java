package com.gk.activity;

import android.os.Bundle;

import com.gk.R;
import com.gk.custom.TopBarView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class AboutActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;

    @Override
    public int getResouceId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "关于云寻校", 0);
    }

}
