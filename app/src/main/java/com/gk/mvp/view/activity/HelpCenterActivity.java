package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.YxxUtils;

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
    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @Override
    public int getResouceId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "帮助中心", 0);
    }

    @OnClick({R.id.ll_wish_report, R.id.ll_vip_open, R.id.ll_solve, R.id.ll_hot_line, R.id.ll_web_chat_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wish_report:
                openNewActivity(OperationHelpActivity.class);
                break;
            case R.id.ll_vip_open:
                openNewActivity(VIPHelpActivity.class);
                break;
            case R.id.ll_solve:
                openNewActivity(QuestionHelpActivity.class);
                break;
            case R.id.ll_hot_line:
                YxxUtils.dialPhoneNumber(this, tv_phone.getText().toString());
                break;
            case R.id.ll_web_chat_number:
                break;
        }
    }
}
