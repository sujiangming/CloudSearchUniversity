package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class LqRiskTestResultActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_level_1)
    TextView tvLevel1;
    @BindView(R.id.tv_level_2)
    TextView tvLevel2;
    @BindView(R.id.iv_lq)
    ImageView imageView;
    @BindView(R.id.tv_student_score)
    RichText tvStudentScore;
    @BindView(R.id.tv_student_mb)
    RichText tvStudentMb;

    @BindView(R.id.tv_test_desc)
    TextView tv_test_desc;

    private int faultLevel = 1; //默认显示高校

    @Override
    public int getResouceId() {
        return R.layout.activity_lq_risk_test;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "录取测试", 0);
    }

    @OnClick({R.id.tv_level_1, R.id.tv_level_2, R.id.ll_aim, R.id.btn_lq_risk_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_level_1:
                tvLevel1Click();
                break;
            case R.id.tv_level_2:
                tvLevel2Click();
                break;
            case R.id.ll_aim://进入高校和专业查询的页面
                break;
            case R.id.btn_lq_risk_test://立即测试
                break;

        }
    }

    private void tvLevel1Click() {
        imageView.setImageResource(R.drawable.lq_yx3x);
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_press);
        tvLevel1.setTextColor(0xFFFFFFFF);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_normal);
        tvLevel2.setTextColor(0xFF555555);
        faultLevel = 1;
        tv_test_desc.setText("目标高校");
    }

    private void tvLevel2Click() {
        imageView.setImageResource(R.drawable.lq_zy3x);
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_normal);
        tvLevel1.setTextColor(0xFF555555);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_press);
        tvLevel2.setTextColor(0xFFFFFFFF);
        faultLevel = 2;
        tv_test_desc.setText("目标专业");
    }

    private void openWin() {
        Intent intent = new Intent();
        if (faultLevel == 1) { //进入高校查询页面
            intent.setClass(this, RiskChooseSchoolActivity.class);
            startActivityForResult(intent, 110);
        } else {//进入专业查询页面
            intent.setClass(this, RiskQueryMajorActivity.class);
            startActivityForResult(intent, 119);
        }
    }
}
