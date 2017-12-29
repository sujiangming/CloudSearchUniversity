package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.PackageUtils;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class AboutActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_version)
    TextView tv_version;

    @Override
    public int getResouceId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "关于云寻校", 0);
        tv_version.setText("云寻校V" + PackageUtils.getVersionName(this));
    }

}
