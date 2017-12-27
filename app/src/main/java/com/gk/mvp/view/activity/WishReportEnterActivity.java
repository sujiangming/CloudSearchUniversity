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

public class WishReportEnterActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_wish_report)
    TextView tvWishReport;
    @BindView(R.id.tv_yh_level_low)
    TextView tvYhLevelLow;
    @BindView(R.id.btn_rg)
    Button btnRg;
    @BindView(R.id.btn_zj)
    Button btnZj;

    private int vipLevel = LoginBean.getInstance().getVipLevel();

    @Override
    public int getResouceId() {
        return R.layout.activity_wish_report_enter;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "志愿报告", 0);
        if (vipLevel <= 1) {
            tvWishReport.setText("会员等级低，没有权限");
            btnRg.setText("马上升级");
            tvYhLevelLow.setText("会员等级低，没有权限");
            btnZj.setText("马上升级");
        } else if (vipLevel == 2) {
            tvYhLevelLow.setText("会员等级低，没有权限");
            btnZj.setText("马上升级");
        }
    }

    @OnClick({R.id.btn_rg, R.id.btn_zj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rg:
                //openNewActivity(WishReportResultActivity.class); fix me
                vipAuth();
                break;
            case R.id.btn_zj:
                vipAuth();
                break;
        }
    }

    private void vipAuth() {
        if (vipLevel <= 1) {
            showDialog();
        } else {//银卡和金卡用户才有这个权限，此刻需要进行是否已经生成报告来判断是否应该生成
            openNewActivity(WishReportResultActivity.class);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setMessage("会员等级低，请立即升级！");
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
