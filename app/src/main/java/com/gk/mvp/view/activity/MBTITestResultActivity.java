package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/12.
 */

public class MBTITestResultActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_desc_0)
    TextView tvDesc0;
    @BindView(R.id.iv_1)
    TextView iv1;
    @BindView(R.id.iv_2)
    TextView iv2;
    @BindView(R.id.iv_3)
    TextView iv3;
    @BindView(R.id.iv_4)
    TextView iv4;
    @BindView(R.id.tv_desc_1)
    TextView tvDesc1;
    @BindView(R.id.tv_for_iv_1)
    TextView tvForIv1;
    @BindView(R.id.tv_for_iv_2)
    TextView tvForIv2;
    @BindView(R.id.tv_for_iv_3)
    TextView tvForIv3;
    @BindView(R.id.tv_for_iv_4)
    TextView tvForIv4;
    @BindView(R.id.tv_jielun)
    TextView tvJielun;
    @BindView(R.id.tv_tz_1)
    TextView tvTz1;
    @BindView(R.id.tv_tz_2)
    TextView tvTz2;
    @BindView(R.id.tv_tz_3)
    TextView tvTz3;
    @BindView(R.id.tv_tz_4)
    TextView tvTz4;
    @BindView(R.id.rl_up)
    RelativeLayout rlUp;
    @BindView(R.id.tv_has_xq)
    TextView tvHasXq;
    @BindView(R.id.tv_xg_score_desc)
    TextView tvXgScoreDesc;
    @BindView(R.id.tv_xg_desc_1)
    TextView tvXgDesc1;
    @BindView(R.id.tv_xg_desc_2)
    TextView tvXgDesc2;
    @BindView(R.id.tv_xg_desc_3)
    TextView tvXgDesc3;
    @BindView(R.id.tv_xg_desc_4)
    TextView tvXgDesc4;
    @BindView(R.id.progressBar_1_left)
    ProgressBar progressBar1Left;
    @BindView(R.id.progressBar_2_left)
    ProgressBar progressBar2Left;
    @BindView(R.id.progressBar_3_left)
    ProgressBar progressBar3Left;
    @BindView(R.id.progressBar_4_left)
    ProgressBar progressBar4Left;
    @BindView(R.id.tv_center_line)
    TextView tvCenterLine;
    @BindView(R.id.progressBar_1_right)
    ProgressBar progressBar1Right;
    @BindView(R.id.progressBar_2_right)
    ProgressBar progressBar2Right;
    @BindView(R.id.progressBar_3_right)
    ProgressBar progressBar3Right;
    @BindView(R.id.progressBar_4_right)
    ProgressBar progressBar4Right;
    @BindView(R.id.tv_xg_desc_1_right)
    TextView tvXgDesc1Right;
    @BindView(R.id.tv_xg_desc_2_right)
    TextView tvXgDesc2Right;
    @BindView(R.id.tv_xg_desc_3_right)
    TextView tvXgDesc3Right;
    @BindView(R.id.tv_xg_desc_4_right)
    TextView tvXgDesc4Right;
    @BindView(R.id.tv_my_xg)
    TextView tvMyXg;
    @BindView(R.id.tv_hld_type)
    TextView tvHldType;
    @BindView(R.id.ll_hld_xg_container)
    LinearLayout llHldXgContainer;
    @BindView(R.id.tv_fit_career)
    TextView tvFitCareer;
    @BindView(R.id.ll_xg_fit_career)
    LinearLayout llXgFitCareer;

    @Override
    public int getResouceId() {
        return R.layout.activity_mbti_test_result;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "MBIT性格测试", 0);
    }
}
