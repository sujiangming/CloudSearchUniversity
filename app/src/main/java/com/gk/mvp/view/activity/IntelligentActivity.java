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
import com.gk.tools.JdryPersistence;

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
    Button btnRg;
    @BindView(R.id.tv_wish_desc_1)
    TextView tvWishDesc1;
    @BindView(R.id.tv_yh_level_low)
    TextView tvYhLevelLow;
    @BindView(R.id.btn_2)
    Button btnZj;

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void btnOnClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                btnClicked(view, btnRg, 1, YXXConstants.INVOKE_API_THREE_TIME);
                break;
            case R.id.btn_2:
                btnClicked(view, btnZj, 2, YXXConstants.INVOKE_API_FORTH_TIME);
                break;
        }
    }

    private int vipLevel = 0;
    private JSONObject jsonObject = new JSONObject();
    private String generating = "生成中";
    private String rightAfterSee = "请稍后查看";
    private WishResultBean wishResultBean = null;

    @Override
    public int getResouceId() {
        return R.layout.activity_intelligent;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "智能海选", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        httpGetData();
        String intel_zj_btn = JdryPersistence.getObject(this, "intel_zj_btn");
    }

    private void initData() {
        LoginBean loginBean = LoginBean.getInstance();
        vipLevel = loginBean.getVipLevel();
        initTextViewAndBtn(btnRg, tvWishReport);
        initTextViewAndBtn(btnZj, tvYhLevelLow);
        String address = loginBean.getAddress();
        String wenli = loginBean.getWlDesc();
        String score = loginBean.getScore();
        tvSourceAddress.setText("" + (address == null ? "----" : address) + "  " + (wenli == null ? "----" : wenli));
        tvStudentScore.setText("" + (score == null ? "----" : score) + "分");
        tvSWantCity.setText("" + (loginBean.getWishProvince() == null ? "未知" : loginBean.getWishProvince()));
        tvZy.setText("" + (loginBean.getWishUniversity() == null ? "未知" : loginBean.getWishUniversity()));
    }

    private void httpGetData() {
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        queryUserVolunteerReport(YXXConstants.INVOKE_API_DEFAULT_TIME);
        queryUserVolunteerReport(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void queryUserVolunteerReport(int time) {
        showProgress();
        jsonObject.put("reportType", time);
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).queryUserVolunteerReport(jsonObject.toJSONString()))
                .request(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != presenterManager && null != presenterManager.getCall()){
            presenterManager.getCall().cancel();
        }
    }

    private void generateReport(int type, int order) {
        showProgress();
        jsonObject.put("reportType", type);
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).generateWishReport(jsonObject.toJSONString()))
                .request(order);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                setWishResultBean(commonBean);
                setTextViewAndBtn(btnRg, tvWishReport, commonBean);
                tvSWantCity.setText("" + (wishResultBean.getIntentArea() == null ? "未知" : wishResultBean.getIntentArea()));
                tvZy.setText("" + (wishResultBean.getIntentSch() == null ? "未知" : wishResultBean.getIntentSch()));
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                setWishResultBean(commonBean);
                setTextViewAndBtn(btnZj, tvYhLevelLow, commonBean);
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                btnRg.setText(rightAfterSee);
                tvWishReport.setText(generating);
                toast(commonBean.getMessage());
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                btnZj.setText(rightAfterSee);
                tvYhLevelLow.setText(generating);
                toast(commonBean.getMessage());
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void initTextViewAndBtn(Button button, TextView textView) {
        if (vipLevel <= 2) {
            String lowLevel = "会员等级低，不能生成";
            textView.setText(lowLevel);
            String tipUpdate = "立即升级";
            button.setText(tipUpdate);
        }
    }

    private void btnClicked(View view, Button button, int type, int order) {
        if (vipLevel <= 2) {
            showDialog();
            return;
        }
        Integer tag = (Integer) view.getTag();
        switch (tag.intValue()) {
            case 0:
                generateReport(type, order);
                break;
            case 1:
                String generatingTip = "正在生成，请稍后进来查看";
                toast(generatingTip);
                break;
            case 2:
                goResultWin(type);
                break;
        }
    }

    private void setWishResultBean(CommonBean commonBean) {
        if (null != commonBean.getData()) {
            wishResultBean = JSON.parseObject(commonBean.getData().toString(), WishResultBean.class);
        }
    }

    private void setTextViewAndBtn(Button button, TextView textView, CommonBean commonBean) {
        button.setTag(commonBean.getFlag());
        switch (commonBean.getFlag()) {
            case 0:
                String noGenerate = "未生成";
                textView.setText(noGenerate);
                String rightNowGen = "立即生成";
                button.setText(rightNowGen);
                break;
            case 1:
                textView.setText(generating);
                button.setText(rightAfterSee);
                break;
            case 2:
                String generated = "已生成";
                textView.setText(generated);
                String rightNowSee = "立即查看";
                button.setText(rightNowSee);
                break;
        }
    }

    private void goResultWin(int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("bean", wishResultBean);
        openNewActivityByIntent(WishReportResultActivity.class, intent);
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
