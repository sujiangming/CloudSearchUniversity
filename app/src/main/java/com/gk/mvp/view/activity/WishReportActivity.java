package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LoginBean;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;

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
        tvHyLevel.setText(LoginBean.getInstance().getVipLevelDesc() == null ? "普通会员" : LoginBean.getInstance().getVipLevelDesc());
        initData();
    }

    private void initData() {
        switch (LoginBean.getInstance().getVipLevel()) {//1普通、2银卡、3金卡
            case 1:
                tvReportStatus.setText("不能生成");
                tvUpgrateVip.setText("升级为VIP");
                tvUpgrateVip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openNewActivity(VIPActivity.class);
                    }
                });
                break;
            case 2:
            case 3:
                initDataAndAddListener();
                break;
        }
    }

    private void initDataAndAddListener() {
        if (LoginBean.getInstance().isHasReport()) {//已经生成报告的情况
            tvReportStatus.setText("已经生成");
            tvUpgrateVip.setText("立即查看");
            tvUpgrateVip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNewActivity(WishReportResultActivity.class);
                }
            });
        } else {
            tvReportStatus.setText("还未生成");
            tvUpgrateVip.setText("立即生成");
            tvUpgrateVip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNewActivity(WishReportEnterActivity.class);
                }
            });
        }
    }
}
