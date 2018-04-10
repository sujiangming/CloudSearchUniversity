package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.gk.beans.LoginBean;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
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

    @BindView(R.id.ll_hld_item)
    LinearLayout layout;

    @OnClick(R.id.btn_next)
    public void nextBtnClick() {
        if (!isSelected()) {
            toast("您还有题没有答，请您答完！");
            return;
        }
        currentPage++;
        if (currentPage > pageCount) {
            if (list.size() == 0) {
                toast("没有数据，不能做题！");
                return;
            }
            toast("已作答完毕");
            openResultWin();
            closeActivity(this);
            LoginBean.getInstance().setIsHeartTest("1");
            LoginBean.getInstance().save();
            return;
        }
        if (currentPage == pageCount) {
            btnNext.setText("提  交");
        }
        updatePage();
        tableList = getFiveRec();
        createUI();
    }

    private void openResultWin() {
        Intent intent = new Intent();
        intent.putExtra("flag", 2);
        openNewActivityByIntent(HLDTestResultActivity.class, intent);
    }

    private List<RadioGroup> radioGroups = new ArrayList<>();
    private List<HLDTestBean> list = new ArrayList<>();
    private List<HLDTable> tableList = new ArrayList<>();
    private HLDTableDao hldTableDao = YXXApplication.getDaoSession().getHLDTableDao();
    private int pageCount = 0;
    private int currentPage = 1;
    private int tmpSum = 0;


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
        httpRequest();
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void httpRequest() {
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getCareerTestHld())
                .request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenterManager && null != presenterManager.getCall()){
            presenterManager.getCall().cancel();
        }
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
        hldTableDao.deleteAll();
        for (HLDTestBean h : list) {
            HLDTable hldTable = new HLDTable();
            hldTable.setHldId(h.getId());
            hldTable.setInterestType(h.getInterestType());
            hldTable.setTitle(h.getTitle());
            hldTable.setIsSelected(false);
            hldTable.setClicked(false);
            hldTableDao.insertOrReplace(hldTable);
        }
        int tmp = list.size() % 5;
        if (tmp == 0) {
            pageCount = list.size() / 5;
        } else {
            pageCount = list.size() / 5 + 1;
        }
        updatePage();
        tableList = getFiveRec();
        createUI();
        progressBar.setMax(list.size());
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void createUI() {

        radioGroups.clear();
        layout.removeAllViews();

        for (int i = 0; i < tableList.size(); i++) {
            HLDTable hldTable = tableList.get(i);
            View view = View.inflate(this, R.layout.hld_item, null);
            TextView textView = view.findViewById(R.id.tv_ti_mu_content_0);
            RadioGroup radioGroup = view.findViewById(R.id.radio_group_0);
            radioGroup.setTag(hldTable.getHldId());
            textView.setText(hldTable.getTitle());
            btnChange(radioGroup, i);
            radioGroups.add(radioGroup);
            layout.addView(view);

        }
    }

    private void btnChange(RadioGroup radioGroup, final int pos) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                HLDTable hldTable = tableList.get(pos);
                if (!hldTable.isClicked()) {
                    updateProgressBar();
                }
                switch (checkId) {
                    case R.id.btn_ti_mu_no_0:
                        selectRadioButton(pos, false);
                        break;
                    case R.id.btn_ti_mu_yes_0:
                        selectRadioButton(pos, true);
                        break;
                }
            }
        });
    }

    private void updatePage() {
        tvTiMu.setText("一共" + list.size() + "道题，共" + pageCount + "页，" + "当前是第" + currentPage + "页，答题进度如下");
    }

    public List<HLDTable> getFiveRec() {
        List<HLDTable> listMsg = hldTableDao.queryBuilder()
                .offset((currentPage - 1) * 5).limit(5).list();
        return listMsg;
    }

    public List<HLDTable> getFiveRecList() {
        List<HLDTable> listMsg = hldTableDao.loadAll();
        Log.e("List<HLDTable> size:", "总数：" + listMsg.size());
        return listMsg;
    }

    private boolean isSelected() {
        for (RadioGroup radio : radioGroups) {
            RadioButton radioButton = findViewById(radio.getCheckedRadioButtonId());
            if (radioButton == null) {
                return false;
            }
        }
        return true;
    }

    private void selectRadioButton(int pos, boolean isSelect) {
        HLDTable hldTable = tableList.get(pos);

        Log.e("before hldTable :", JSON.toJSONString(hldTable));

        hldTable.setIsSelected(isSelect);
        hldTable.setClicked(true);//被选择了一次

        hldTableDao.update(hldTable);

        Log.e("after hldTable :", JSON.toJSONString(tableList.get(pos)));
    }

    private void updateProgressBar() {
        tmpSum++;
        Log.e("tmpSum", tmpSum + "");
        progressBar.setProgress(tmpSum);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            showDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

}
