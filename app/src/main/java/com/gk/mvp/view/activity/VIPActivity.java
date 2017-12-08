package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/23.
 */

public class VIPActivity extends SjmBaseActivity {

    @BindView(R.id.tv_top_bar)
    TopBarView tvTopBar;

    @BindView(R.id.ll_gold)
    LinearLayout llGold;

    @BindView(R.id.ll_silve)
    LinearLayout llSilve;

    private String form = null;


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

    @Override
    public int getResouceId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(tvTopBar, "VIP服务", 0);
        initUIByForm();
    }

    private void initUIByForm() {
        Intent intent = getIntent();
        if (intent != null) {
            form = intent.getStringExtra("form");
            if (form != null) {
                if (form.equals("vip_choose")) {
                    llGold.setVisibility(View.GONE);
                } else {
                    llSilve.setVisibility(View.GONE);
                }
            }
        }
    }
}
