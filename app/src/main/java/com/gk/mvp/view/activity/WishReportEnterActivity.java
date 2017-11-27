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
        }
    }

    @OnClick({R.id.btn_rg, R.id.btn_zj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rg:
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

        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置对话框标题
        builder.setTitle("警告");
        //设置对话框内的文本
        builder.setMessage("会员等级低，请立即升级！");
        //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 执行点击确定按钮的业务逻辑
                openNewActivity(VIPActivity.class);
            }
        });
        //设置取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 执行点击取消按钮的业务逻辑
                dialog.dismiss();
            }
        });
        //使用builder创建出对话框对象
        AlertDialog dialog = builder.create();
        //显示对话框
        dialog.show();
    }

}
