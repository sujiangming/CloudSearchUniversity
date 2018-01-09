package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.WishResultBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
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

    @OnClick({R.id.btn_rg, R.id.btn_zj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rg:
                vipAuth(1);
                break;
            case R.id.btn_zj:
                vipAuth(2);
                break;
        }
    }

    private int vipLevel = 0;
    private JSONObject jsonObject = new JSONObject();

    @Override
    public int getResouceId() {
        return R.layout.activity_wish_report_enter;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "志愿报告", 0);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vipLevel = LoginBean.getInstance().getVipLevel();
    }

    private void initData() {
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        generateReportStatus(YXXConstants.INVOKE_API_DEFAULT_TIME);
       // generateReportStatus(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    private void vipAuth(int type) {
        if (vipLevel <= 1) {
            showDialog();
        } else {//银卡和金卡用户才有这个权限，此刻需要进行是否已经生成报告来判断是否应该生成
            Intent intent = new Intent();
            intent.putExtra("type", type);
            openNewActivityByIntent(WishReportResultActivity.class, intent);
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

    private void generateReportStatus(int time) {
        jsonObject.put("reportType", time);
        PresenterManager.getInstance().setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).generateWishReport(jsonObject.toJSONString()))
                .request(time);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        WishResultBean wishResultBean = JSON.parseObject(commonBean.getData().toString(), WishResultBean.class);
        String status = wishResultBean.getReportStatus();
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                showDifferentMsg(status);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                showDifferentMsg(status);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void showDifferentMsg(String status) {
        if (status == null || "".equals(status)) {
            normalVip();
            return;
        }
        vipMsgRg(status);
    }

    private void normalVip() {
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

    private void vipMsgRg(String status) {
        if (vipLevel <= 1) {
            tvWishReport.setText("会员等级低，没有权限");
            btnRg.setText("马上升级");
            tvYhLevelLow.setText("会员等级低，没有权限");
            btnZj.setText("马上升级");
            return;
        }
        if (vipLevel == 2) {
            if (status.equals("1")) {
                tvWishReport.setText("未生成");
                btnRg.setText("立即生成");
            } else {
                tvWishReport.setText("已生成");
                btnRg.setText("立即查看");
            }
            tvYhLevelLow.setText("会员等级低，没有权限");
            btnZj.setText("马上升级");
            return;
        }

        if (vipLevel == 3) {
            if (status.equals("1")) {
                tvWishReport.setText("未生成");
                btnRg.setText("立即生成");

                tvYhLevelLow.setText("未生成");
                btnZj.setText("立即生成");

            } else {
                tvWishReport.setText("已生成");
                btnRg.setText("立即查看");

                tvYhLevelLow.setText("已生成");
                btnZj.setText("立即查看");
            }
            return;
        }
    }
}
