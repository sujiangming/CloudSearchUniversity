package com.gk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/1.
 */

public class WishReportActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_hy_level)
    TextView tvHyLevel;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.tv_report_status)
    TextView tvReportStatus;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.tv_upgrate_vip)
    TextView tvUpgrateVip;

    @Override
    public int getResouceId() {
        return R.layout.activity_wish_report;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "志愿报告", 0);
    }

    @OnClick({R.id.ll_account, R.id.ll_about, R.id.tv_upgrate_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_account:
                break;
            case R.id.ll_about:
                break;
            case R.id.tv_upgrate_vip:
                break;
        }
    }
}
