package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.YxxUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class LqRiskActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_level_1)
    TextView tvLevel1;
    @BindView(R.id.tv_level_2)
    TextView tvLevel2;
    @BindView(R.id.iv_lq)
    ImageView imageView;
    @BindView(R.id.tv_student_score)
    RichText tvStudentScore;
    @BindView(R.id.tv_student_mb)
    RichText tvStudentMb;
    @BindView(R.id.tv_wen_li_desc)
    TextView tv_wen_li_desc;

    @BindView(R.id.tv_test_desc)
    TextView tv_test_desc;

    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.et_reply)
    EditText et_reply;

    private int faultLevel = 1; //默认显示高校
    private LoginBean loginBean;
    private String score;
    private String rank;
    private String weli;
    private String address;

    @Override
    public int getResouceId() {
        return R.layout.activity_lq_risk_test;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "录取测试", 0);
        setScreenHeight();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        loginBean = LoginBean.getInstance();
        score = loginBean.getScore();
        rank = loginBean.getRanking();
        weli = loginBean.getWlDesc();
        address = loginBean.getAddress();
        YxxUtils.setViewData(tvStudentScore, score);
        if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(weli)) {
            tv_wen_li_desc.setText(LoginBean.getInstance().getAddress() + "|" + LoginBean.getInstance().getWlDesc());
        }
    }

    @OnClick({R.id.tv_level_1,
            R.id.tv_level_2,
            R.id.ll_aim,
            R.id.btn_lq_risk_test,
            R.id.ll_score,
            R.id.tv_send, R.id.tv_wen_li_desc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_level_1:
                tvLevel1Click();
                break;
            case R.id.tv_level_2:
                tvLevel2Click();
                break;
            case R.id.ll_aim://进入高校和专业查询的页面
                openWin();
                break;
            case R.id.btn_lq_risk_test://立即测试
                needZDCkeck();
                break;
            case R.id.tv_student_score:
                if (TextUtils.isEmpty(et_reply.getText())) {
                    llComment.setVisibility(View.VISIBLE);
                    YxxUtils.showSoftInputFromWindow(et_reply);
                    return;
                }
                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(et_reply.getText())) {
                    toast("请输入内容");
                    return;
                }
                updateUserScore();
                break;
            case R.id.ll_score:
                if (TextUtils.isEmpty(tvStudentScore.getText())) {
                    llComment.setVisibility(View.VISIBLE);
                    YxxUtils.showSoftInputFromWindow(et_reply);
                    return;
                }
                break;
            case R.id.tv_wen_li_desc:
                if (TextUtils.isEmpty(tv_wen_li_desc.getText())) {
                    toast("请完善个人资料");
                    openNewActivity(PersonInfoActivity.class);
                }
                break;
        }
    }

    private void updateUserScore() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginBean.getUsername());
        jsonObject.put("score", et_reply.getText().toString());
        RetrofitUtil.getInstance().createReq(IService.class).updateUserInfo(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            toast(commonBean.getMessage());
                            if (commonBean.getStatus() == 1) {
                                tvStudentScore.setText(et_reply.getText());
                                llComment.setVisibility(View.GONE);
                                YxxUtils.hideSoftInputKeyboard(et_reply);
                                LoginBean.getInstance().setScore(et_reply.getText().toString());
                                LoginBean.getInstance().save();
                            }
                        }
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast(t.getMessage());
                        hideProgress();
                    }
                });
    }

    private void needZDCkeck() {
        if (TextUtils.isEmpty(score)) {
            toast("请完善您的个人信息-分数还未填写");
            return;
        }
        if (TextUtils.isEmpty(rank)) {
            toast("请完善您的个人信息-排名还未填写");
            return;
        }
        if (TextUtils.isEmpty(weli)) {
            toast("请完善您的个人信息-文理科还未填写");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            toast("请完善您的个人信息-生源地还未填写");
            return;
        }
        rightNowTest();
    }


    private void tvLevel1Click() {
        imageView.setImageResource(R.drawable.lq_yx3x);
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_press);
        tvLevel1.setTextColor(0xFFFFFFFF);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_normal);
        tvLevel2.setTextColor(0xFF555555);
        faultLevel = 1;
        tv_test_desc.setText("目标高校");
        tvStudentMb.setHint("请选择目标高校");
    }

    private void tvLevel2Click() {
        imageView.setImageResource(R.drawable.lq_zy3x);
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_normal);
        tvLevel1.setTextColor(0xFF555555);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_press);
        tvLevel2.setTextColor(0xFFFFFFFF);
        faultLevel = 2;
        tv_test_desc.setText("目标专业");
        tvStudentMb.setHint("请选择目标专业");
    }

    private void openWin() {
        Intent intent = new Intent();
        if (faultLevel == 1) { //进入高校查询页面
            intent.setClass(this, LqRiskChooseSchoolActivity.class);
            startActivityForResult(intent, 110);
        } else {//进入专业查询页面
            intent.setClass(this, LqRiskQueryMajorActivity.class);
            startActivityForResult(intent, 119);
        }
    }

    private void rightNowTest() {
        if (TextUtils.isEmpty(tvStudentMb.getText())) {
            toast("请选择目标学校或目标专业");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("flag", faultLevel);
        intent.putExtra("aim", tvStudentMb.getText().toString());
        openNewActivityByIntent(LqRiskTestResultActivity.class, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (resultCode == 110) {//高校选择返回结果
            String schoolName = data.getStringExtra("schoolName");
            tvStudentMb.setText(schoolName);
        } else {
            String schoolName = data.getStringExtra("schoolName");
            tvStudentMb.setText(schoolName);
        }
    }

    private int screenHeight = 0;//屏幕高度初始值
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    private void setScreenHeight() {
        //获取屏幕高度
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            //Toast.makeText(this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            llComment.setVisibility(View.GONE);
            et_reply.clearFocus();
            et_reply.setText("");
            YxxUtils.hideSoftInputKeyboard(et_reply);
        }
    }
}
