package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.MBITTestBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/12/4.
 */

public class MBTITestDetailActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_ti_mu)
    TextView tvTiMu;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.ll_hld_item)
    LinearLayout layout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_select_1)
    TextView tvSelect1;
    @BindView(R.id.tv_select_2)
    TextView tvSelect2;

    private List<MBITTestBean> list = new ArrayList<>();

    @Override
    public int getResouceId() {
        return R.layout.activity_mbti_detail;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showDialog();
    }

    @OnClick({R.id.tv_select_1, R.id.tv_select_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_1:
                break;
            case R.id.tv_select_2:
                break;
        }
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "MBTI测试题", 0);
        httpRequest();
        addListener();
    }

    private void httpRequest() {
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getCareerTestMbti())
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<MBITTestBean> hldTestBeans = JSON.parseArray(commonBean.getData().toString(), MBITTestBean.class);
        if (hldTestBeans == null || hldTestBeans.size() == 0) {
            toast(commonBean.getMessage());
            return;
        }
        list = hldTestBeans;
        updatePage();
        initData(list.get(0));
        progressBar.setMax(list.size());
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void updatePage() {
        tvTiMu.setText("一共" + list.size() + "道题，" + "答题进度如下");
    }

    private void initData(MBITTestBean testBean) {
        tvTitle.setText(testBean.getTitle());
        tvSelect1.setText(testBean.getAnswerA());
        tvSelect2.setText(testBean.getAnswerB());
        tvSelect1.setTag(testBean.getAMaping());
        tvSelect2.setTag(testBean.getBMaping());
    }

    private int page = 1;

    private void addListener() {
        tvSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page == list.size()) {
                    finishDialog();
                    return;
                }
                MBITTestBean mbitTestBean = list.get(page);
                initData(mbitTestBean);
                page++;
                updateProgressBar();
            }
        });
        tvSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page == list.size()) {
                    finishDialog();
                    return;
                }
                MBITTestBean mbitTestBean = list.get(page);
                initData(mbitTestBean);
                page++;
                updateProgressBar();
            }
        });
    }

    private void updateProgressBar() {
        Log.e("tmpSum", page + "");
        progressBar.setProgress(page);
    }

    private void finishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("温馨提示");
        builder.setMessage("您已经答题完毕，可以退出了");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeActivity(MBTITestDetailActivity.this);
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

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("温馨提示");
        builder.setMessage("退出当前页面，之前所做的题全部清空，您确定退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeActivity(MBTITestDetailActivity.this);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            showDialog();
        }
        return super.onKeyDown(keyCode, event);
    }
}
