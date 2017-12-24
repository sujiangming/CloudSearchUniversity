package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.global.YXXConstants;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.JdryPersistence;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class SetActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @Override
    public int getResouceId() {
        return R.layout.activity_info_set;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "设置", 0);
    }

    @OnClick({R.id.ll_account, R.id.ll_about, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_account:
                openNewActivity(AccountSaveActivity.class);
                break;
            case R.id.ll_about:
                openNewActivity(AboutActivity.class);
                break;
            case R.id.tv_logout:
                JdryPersistence.deleteLoginBean(this, YXXConstants.LOGIN_INFO_SERIALIZE_KEY);
                openNewActivity(LoginActivity.class);
                break;
        }
    }
}
