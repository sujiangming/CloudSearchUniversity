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

    private int vipLevel = 0;
    private JSONObject jsonObject = new JSONObject();
    private String tipUpdate = "立即升级";
    private String lowLevel = "会员等级低，不能生成";
    private String noGenerate = "未生成";
    private String generated = "已生成";
    private String rightNowGen = "立即生成";
    private String rightNowSee = "立即查看";

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
        generateReportStatus(YXXConstants.INVOKE_API_SECOND_TIME);
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
                showDifferentMsg(status, order);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                showDifferentMsg(status, order);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void showDifferentMsg(String status, int flag) {
        if (status == null || "".equals(status)) {
            normalVip();
            return;
        }
        vipMsgRg(status, flag);
    }

    private void normalVip() {
        if (vipLevel <= 1) {
            tvWishReport.setText(lowLevel);
            btnRg.setText(tipUpdate);
            tvYhLevelLow.setText(lowLevel);
            btnZj.setText(tipUpdate);
        } else if (vipLevel == 2) {
            tvYhLevelLow.setText(lowLevel);
            btnZj.setText(tipUpdate);
        }
    }

    private void vipMsgRg(String status, int flag) {
        if (vipLevel <= 1) {
            tvWishReport.setText(lowLevel);
            btnRg.setText(tipUpdate);
            tvYhLevelLow.setText(lowLevel);
            btnZj.setText(tipUpdate);

            btnRg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
            btnZj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
            return;
        }
        if (vipLevel == 2) {
            if (flag == YXXConstants.INVOKE_API_DEFAULT_TIME) {
                if (status.equals("1")) {
                    tvWishReport.setText(noGenerate);
                    btnRg.setText(rightNowGen);
                } else {
                    tvWishReport.setText(generated);
                    btnRg.setText(rightNowSee);
                }
                btnRg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("type", 1);
                        openNewActivityByIntent(WishReportResultActivity.class, intent);
                    }
                });
            }
            tvYhLevelLow.setText(lowLevel);
            btnZj.setText(tipUpdate);
            btnZj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
            return;
        }

        if (vipLevel == 3) {
            if (flag == YXXConstants.INVOKE_API_DEFAULT_TIME) {
                if (status.equals("1")) {
                    tvWishReport.setText(noGenerate);
                    btnRg.setText(rightNowGen);
                } else {
                    tvWishReport.setText(generated);
                    btnRg.setText(rightNowSee);
                }
            } else {
                if (status.equals("1")) {
                    tvYhLevelLow.setText(noGenerate);
                    btnZj.setText(rightNowGen);
                } else {
                    tvYhLevelLow.setText(generated);
                    btnZj.setText(rightNowSee);
                }
            }

            btnRg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("type", 1);
                    openNewActivityByIntent(WishReportResultActivity.class, intent);
                }
            });

            btnZj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("type", 2);
                    openNewActivityByIntent(WishReportResultActivity.class, intent);
                }
            });
            return;
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
