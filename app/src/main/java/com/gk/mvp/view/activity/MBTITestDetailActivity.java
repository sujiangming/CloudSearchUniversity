package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.gk.beans.LoginBean;
import com.gk.beans.MBITTbale;
import com.gk.beans.MBITTbaleDao;
import com.gk.beans.MBITTestBean;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "MBTI测试题", 0);
        httpRequest();
        addListener();
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void httpRequest() {
        showProgress();
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getCareerTestMbti())
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
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
        tvSelect1.setVisibility(View.VISIBLE);
        tvSelect2.setVisibility(View.VISIBLE);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        setTvSelectGone(tvSelect1);
        setTvSelectGone(tvSelect2);
    }

    private void setTvSelectGone(TextView tv) {
        if (tv.getVisibility() == View.VISIBLE) {
            tv.setVisibility(View.GONE);
        }
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
                selectItem(1);
            }
        });
        tvSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectItem(2);
            }
        });
    }

    /**
     * type 表示选择是A还是B： 1 选择A 2 选择B
     *
     * @param type
     */
    private void selectItem(int type) {
        if (page == list.size()) {
            finishDialog();
            return;
        }
        if (list.size() == 0) {
            return;
        }
        MBITTestBean mbitTestBean = list.get(page);
        initData(mbitTestBean);
        page++;
        updateProgressBar();
        updateTable(mbitTestBean, type);
    }

    private void updateTable(MBITTestBean mbitTestBean, int type) {
        MBITTbaleDao mbitTbaleDao = YXXApplication.getDaoSession().getMBITTbaleDao();
        MBITTbale table = new MBITTbale();
        table.setMbitId(mbitTestBean.getId());
        if (type == 1) {
            table.setSelectItem(mbitTestBean.getAMaping());
        } else {
            table.setSelectItem(mbitTestBean.getBMaping());
        }
        mbitTbaleDao.insertOrReplace(table);
    }

    private void updateProgressBar() {
        Log.e("tmpSum", page + "");
        progressBar.setProgress(page);
    }

    private void finishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("温馨提示");
        builder.setMessage("您已经答题完毕，是否生成测试报告？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginBean.getInstance().setIsHeartTest("1");
                LoginBean.getInstance().save();
                openResultWin();
                closeActivity(MBTITestDetailActivity.this);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openResultWin() {
        Intent intent = new Intent();
        intent.putExtra("flag", 2);
        openNewActivityByIntent(MBTITestResultActivity.class, intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenterManager && null != presenterManager.getCall()){
            presenterManager.getCall().cancel();
        }
    }
}
