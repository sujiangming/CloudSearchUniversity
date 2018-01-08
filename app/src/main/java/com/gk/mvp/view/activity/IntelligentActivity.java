package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LoginBean;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/3.
 */

public class IntelligentActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_source_address)
    TextView tvSourceAddress;
    @BindView(R.id.tv_student_qx)
    TextView tvStudentQx;
    @BindView(R.id.tv_student_want)
    TextView tvStudentWant;
    @BindView(R.id.tv_student_score)
    TextView tvStudentScore;
    @BindView(R.id.tv_s_want_city)
    TextView tvSWantCity;
    @BindView(R.id.tv_quxiang)
    TextView tvQuxiang;
    @BindView(R.id.tv_zy)
    TextView tvZy;
    @BindView(R.id.tv_wish_desc)
    TextView tvWishDesc;
    @BindView(R.id.tv_wish_report)
    TextView tvWishReport;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.tv_wish_desc_1)
    TextView tvWishDesc1;
    @BindView(R.id.tv_yh_level_low)
    TextView tvYhLevelLow;
    @BindView(R.id.btn_2)
    Button btn2;

    private LoginBean loginBean;
    private int vipLevel = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_intelligent;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "智能海选", 0);
        loginBean = LoginBean.getInstance();
        String address = loginBean.getAddress();
        String wenli = loginBean.getWlDesc();
        String score = loginBean.getScore();
        tvSourceAddress.setText("" + (address == null ? "----" : address) + "  " + (wenli == null ? "----" : wenli));
        tvStudentScore.setText("" + (score == null ? "----" : score) + "分");
        tvSWantCity.setText("" + (loginBean.getWishProvince() == null ? "未知" : loginBean.getWishProvince()));
        tvZy.setText("" + (loginBean.getWishUniversity() == null ? "未知" : loginBean.getWishUniversity()));
        if (vipLevel <= 1) {
            tvWishReport.setText("会员等级低，没有权限");
            btn1.setText("马上升级");
            tvYhLevelLow.setText("会员等级低，没有权限");
            btn2.setText("马上升级");
        }else if(vipLevel == 2){ //fix me
            tvWishReport.setText("未知");
            btn1.setText("立即生成");
            tvYhLevelLow.setText("会员等级低，没有权限");
            btn2.setText("马上升级");
        }else{
            tvWishReport.setText("未知");
            btn1.setText("立即生成");
            tvYhLevelLow.setText("未知");
            btn2.setText("立即生成");
        }
    }

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                showVipDialog();
                break;
            case R.id.btn_2:
                showVipDialog();
                break;
        }
    }

    private void showVipDialog() {
        if (vipLevel > 1) {
            openNewActivity(WishReportResultActivity.class);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("温馨提示");
        builder.setMessage("您需要升级为金卡或者银卡会员吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openNewActivity(VIPActivity.class);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
