package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.HLDTable;
import com.gk.beans.HLDTableDao;
import com.gk.beans.HLDTestBean;
import com.gk.global.YXXApplication;
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

public class HLDTestDetailActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_ti_mu)
    TextView tvTiMu;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_ti_mu_content_0)
    TextView tvTiMuContent0;
    @BindView(R.id.btn_ti_mu_no_0)
    RadioButton btnTiMuNo0;
    @BindView(R.id.btn_ti_mu_yes_0)
    RadioButton btnTiMuYes0;
    @BindView(R.id.radio_group_0)
    RadioGroup radioGroup0;
    @BindView(R.id.tv_ti_mu_content_1)
    TextView tvTiMuContent1;
    @BindView(R.id.btn_ti_mu_no_1)
    RadioButton btnTiMuNo1;
    @BindView(R.id.btn_ti_mu_yes_1)
    RadioButton btnTiMuYes1;
    @BindView(R.id.radio_group_1)
    RadioGroup radioGroup1;
    @BindView(R.id.tv_ti_mu_content_2)
    TextView tvTiMuContent2;
    @BindView(R.id.btn_ti_mu_no_2)
    RadioButton btnTiMuNo2;
    @BindView(R.id.btn_ti_mu_yes_2)
    RadioButton btnTiMuYes2;
    @BindView(R.id.radio_group_2)
    RadioGroup radioGroup2;
    @BindView(R.id.tv_ti_mu_content_3)
    TextView tvTiMuContent3;
    @BindView(R.id.btn_ti_mu_no_3)
    RadioButton btnTiMuNo3;
    @BindView(R.id.btn_ti_mu_yes_3)
    RadioButton btnTiMuYes3;
    @BindView(R.id.radio_group_3)
    RadioGroup radioGroup3;
    @BindView(R.id.tv_ti_mu_content_4)
    TextView tvTiMuContent4;
    @BindView(R.id.btn_ti_mu_no_4)
    RadioButton btnTiMuNo4;
    @BindView(R.id.btn_ti_mu_yes_4)
    RadioButton btnTiMuYes4;
    @BindView(R.id.radio_group_4)
    RadioGroup radioGroup4;

    @OnClick(R.id.btn_next)
    public void nextBtnClick() {
        if (!isSelected()) {
            toast("您还有题没有答，请您答完！");
            return;
        }
        if (currentPage > pageCount) {
            toast("已作答完毕");
            return;
        }
        ++currentPage;
        clearSelected();
        currentShowPage = currentPage + 1;
        updatePage();
        initData(tableList = getFiveRec());
    }

    private TextView[] textViews;
    private RadioGroup[] radioGroups;
    private List<HLDTestBean> list = new ArrayList<>();
    private List<HLDTable> tableList = new ArrayList<>();
    private HLDTableDao hldTableDao = YXXApplication.getDaoSession().getHLDTableDao();
    private int pageCount = 0;
    private int currentPage = 0;
    private int currentFinishNum = -1;
    private int currentShowPage = 1;


    @Override
    public int getResouceId() {
        return R.layout.activity_hld_test;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showDialog();
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "霍兰德心里测试题", 0);
        textViews = new TextView[]{tvTiMuContent0, tvTiMuContent1, tvTiMuContent2, tvTiMuContent3, tvTiMuContent4};
        radioGroups = new RadioGroup[]{radioGroup0, radioGroup1, radioGroup2, radioGroup3, radioGroup4};
        httpRequest();
        btnChange(radioGroup0);
        btnChange(radioGroup1);
        btnChange(radioGroup2);
        btnChange(radioGroup3);
        btnChange(radioGroup4);

    }

    private void httpRequest() {
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getCareerTestHld())
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<HLDTestBean> hldTestBeans = JSON.parseArray(commonBean.getData().toString(), HLDTestBean.class);
        if (hldTestBeans == null || hldTestBeans.size() == 0) {
            toast(commonBean.getMessage());
            return;
        }
        list = hldTestBeans;
        for (HLDTestBean h : list) {
            HLDTable hldTable = new HLDTable();
            hldTable.setHldId(h.getId());
            hldTable.setInterestType(h.getInterestType());
            hldTable.setTitle(h.getTitle());
            hldTable.setIsSelected(false);
            hldTableDao.insertOrReplace(hldTable);
        }
        int tmp = list.size() % 5;
        if (tmp == 0) {
            pageCount = list.size() / 5;
        } else {
            pageCount = list.size() / 5 + 1;
        }
        updatePage();
        initData(tableList = getFiveRec());
        progressBar.setMax(list.size() - 2);
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void updatePage() {
        tvTiMu.setText("一共" + list.size() + "道题，共" + pageCount + "页，" + "当前是第" + currentShowPage + "页，答题进度如下");
    }

    public List<HLDTable> getFiveRec() {
        List<HLDTable> listMsg = hldTableDao.queryBuilder()
                .offset(currentPage * 5).limit(5).list();
        return listMsg;
    }

    private void initData(List<HLDTable> listMsg) {
        int size = listMsg.size();
        for (int i = 0; i < size; i++) {
            textViews[i].setText(listMsg.get(i).getTitle());
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("温馨提示");
        builder.setMessage("退出当前页面，之前所做的题全部清空，您确定退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeActivity(HLDTestDetailActivity.this);
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

    private void btnChange(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.btn_ti_mu_no_0:
                        selectRadioButton(0, false);
                        break;
                    case R.id.btn_ti_mu_yes_0:
                        selectRadioButton(0, true);
                        break;
                    case R.id.btn_ti_mu_no_1:
                        selectRadioButton(1, false);
                        break;
                    case R.id.btn_ti_mu_yes_1:
                        selectRadioButton(1, true);
                        break;
                    case R.id.btn_ti_mu_no_2:
                        selectRadioButton(2, false);
                        break;
                    case R.id.btn_ti_mu_yes_2:
                        selectRadioButton(2, true);
                        break;
                    case R.id.btn_ti_mu_no_3:
                        selectRadioButton(3, false);
                        break;
                    case R.id.btn_ti_mu_yes_3:
                        selectRadioButton(3, true);
                        break;
                    case R.id.btn_ti_mu_no_4:
                        selectRadioButton(4, false);
                        break;
                    case R.id.btn_ti_mu_yes_4:
                        selectRadioButton(4, true);
                        break;
                }
            }
        });
    }

    private void clearSelected() {
        int tmp = 0;
        for (RadioGroup radio : radioGroups) {
            radio.clearCheck();
            updateProgressBar(tmp);
            tmp++;
        }
    }

    private boolean isSelected() {
        for (RadioGroup radio : radioGroups) {
            RadioButton radioButton = (RadioButton) findViewById(radio.getCheckedRadioButtonId());
            if (radioButton == null) {
                return false;
            }
        }
        return true;
    }

    private void selectRadioButton(int pos, boolean isSelect) {
        HLDTable hldTable = tableList.get(pos);
        hldTable.setIsSelected(isSelect);
        hldTableDao.insertOrReplace(hldTable);
        countFinishTiMu(pos);
    }

    private void countFinishTiMu(int pos) {
        if (pos != currentFinishNum) {
            currentFinishNum++;
            Log.e("currentFinishNum", currentFinishNum + "");
        }
        updateProgressBar(currentFinishNum);
    }

    private void updateProgressBar(int num) {
        progressBar.setProgress(num);
    }

}
