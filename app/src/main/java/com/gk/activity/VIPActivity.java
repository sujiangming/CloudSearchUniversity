package com.gk.activity;

import android.os.Bundle;
import android.view.View;

import com.gk.R;
import com.gk.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/23.
 */

public class VIPActivity extends SjmBaseActivity {

    @BindView(R.id.tv_top_bar)
    TopBarView tvTopBar;

    @Override
    public int getResouceId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        tvTopBar.getTitleView().setText("VIP服务");
        tvTopBar.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
    }

    @OnClick({R.id.tv_open_gold, R.id.tv_open_silver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open_gold:
                toast("开通金卡");
                break;
            case R.id.tv_open_silver:
                toast("开通银卡");
                break;
        }
    }

}
