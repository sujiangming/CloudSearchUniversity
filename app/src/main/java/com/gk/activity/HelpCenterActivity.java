package com.gk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gk.R;
import com.gk.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class HelpCenterActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.ll_wish_report)
    LinearLayout llWishReport;
    @BindView(R.id.ll_vip_open)
    LinearLayout llVipOpen;
    @BindView(R.id.ll_solve)
    LinearLayout llSolve;
    @BindView(R.id.ll_hot_line)
    LinearLayout llHotLine;
    @BindView(R.id.ll_office_site)
    LinearLayout llOfficeSite;
    @BindView(R.id.ll_web_chat_number)
    LinearLayout llWebChatNumber;

    @Override
    public int getResouceId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar,"帮助中心",0);
    }

    @OnClick({R.id.ll_wish_report, R.id.ll_vip_open, R.id.ll_solve, R.id.ll_hot_line, R.id.ll_office_site, R.id.ll_web_chat_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wish_report:
                break;
            case R.id.ll_vip_open:
                break;
            case R.id.ll_solve:
                break;
            case R.id.ll_hot_line:
                break;
            case R.id.ll_office_site:
                break;
            case R.id.ll_web_chat_number:
                break;
        }
    }
}
