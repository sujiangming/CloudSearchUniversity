package com.gk.activity;

import android.os.Bundle;

import com.gk.R;
import com.gk.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class InterestActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;

    @Override
    public int getResouceId() {
        return R.layout.activity_interest_test;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "兴趣测试", 0);
    }

    @OnClick(R.id.btn_mbti_test)
    public void onViewClicked() {
        toast("开始测试");
    }
}
