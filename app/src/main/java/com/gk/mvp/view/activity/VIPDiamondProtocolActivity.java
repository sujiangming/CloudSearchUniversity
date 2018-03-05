package com.gk.mvp.view.activity;

import android.os.Bundle;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2018/3/4.
 */

public class VIPDiamondProtocolActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;

    @Override
    public int getResouceId() {
        return R.layout.activity_vip_diamond_protocol;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "钻石卡会员服务协议", 0);
    }

}
