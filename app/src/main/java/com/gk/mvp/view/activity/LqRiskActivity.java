package com.gk.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.baoyz.actionsheet.ActionSheet;
import com.gk.R;
import com.gk.beans.AuthResult;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.PayResult;
import com.gk.beans.UserRechargeTimes;
import com.gk.beans.VIPPriceBean;
import com.gk.beans.VipOrderBean;
import com.gk.beans.WeiXinPay;
import com.gk.global.YXXApplication;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.CommonTipDialog;
import com.gk.mvp.view.custom.CommonTipDialog.onNoOnclickListener;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.YxxUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class LqRiskActivity extends SjmBaseActivity {
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
    @BindView(R.id.tv_user_address)
    TextView tv_user_address;
    @BindView(R.id.tv_risk_score)
    TextView tv_risk_score;

    private int faultLevel = 1; //默认显示高校
    private LoginBean loginBean;
    private String score;
    private String rank;
    private String weli;
    private String address;
    private String schoolName;
    private CommonTipDialog selfDialog = null;

    @Override
    public int getResouceId() {
        return R.layout.activity_lq_risk_test;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "录取测试", 0);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vipLevel = LoginBean.getInstance().getVipLevel();
        getUserRechargeTimes();
        getVipLevelAmount();
    }

    private void initData() {
        loginBean = LoginBean.getInstance();
        score = loginBean.getScore();
        rank = loginBean.getRanking();
        weli = loginBean.getWlDesc();
        address = loginBean.getAddress();
        YxxUtils.setViewData(tvStudentScore, score);
        if (!TextUtils.isEmpty(weli)) {
            tv_wen_li_desc.setText(LoginBean.getInstance().getWlDesc());
        }
        if (!TextUtils.isEmpty(address)) {
            tv_user_address.setText(LoginBean.getInstance().getAddress());
        }
        if (!TextUtils.isEmpty(score)) {
            tv_risk_score.setText(score);
        }
    }

    private void showUpdateScoreDialog() {
        selfDialog = new CommonTipDialog(this);
        selfDialog.setTitle("温馨提示");
        selfDialog.setYesOnclickListener("确定", new CommonTipDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                String reScore = selfDialog.getMessageTv().getText().toString();
                if (TextUtils.isEmpty(reScore)) {
                    toast("请输入您的分数");
                    return;
                }
                tv_risk_score.setText(reScore);
                tvStudentScore.setText(reScore);
                score = reScore;
                selfDialog.dismiss();
            }
        });
        selfDialog.setNoOnclickListener("取消", new onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }

    @OnClick({R.id.tv_level_1,
            R.id.tv_level_2,
            R.id.ll_aim,
            R.id.btn_lq_risk_test,
            R.id.ll_score,
            R.id.tv_wen_li_desc,
            R.id.tv_risk_score,
            R.id.tv_user_address})
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
            case R.id.ll_score:
                if (TextUtils.isEmpty(tvStudentScore.getText())) {
                    showVipDialog("请完善个人资料");
                }
                break;
            case R.id.tv_wen_li_desc:
                if (TextUtils.isEmpty(tv_wen_li_desc.getText())) {
                    showVipDialog("请完善个人资料");
                }
                break;
            case R.id.tv_user_address:
                if (TextUtils.isEmpty(tv_wen_li_desc.getText())) {
                    showVipDialog("请完善个人资料");
                }
                break;
            case R.id.tv_risk_score:
                showUpdateScoreDialog();
                break;
        }
    }

    private void needZDCkeck() {
        if (TextUtils.isEmpty(tvStudentScore.getText())) {
            showVipDialog("请完善您的个人信息-分数还未填写");
            return;
        }
        if (TextUtils.isEmpty(rank)) {
            showVipDialog("请完善您的个人信息-排名还未填写");
            return;
        }
        if (TextUtils.isEmpty(weli)) {
            showVipDialog("请完善您的个人信息-文理科还未填写");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            showVipDialog("请完善您的个人信息-生源地还未填写");
            return;
        }
        showUpgradeDialog();
    }

    private void showVipDialog(String tip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("温馨提示");
        builder.setMessage(tip);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                openNewActivity(PersonInfoActivity.class);
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

    private void tvLevel1Click() {
        imageView.setImageResource(R.drawable.yuanxiao);
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_press);
        tvLevel1.setTextColor(0xFFFFFFFF);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_normal);
        tvLevel2.setTextColor(0xFF555555);
        faultLevel = 1;
        tv_test_desc.setText("目标高校");
        tvStudentMb.setText("");
        tvStudentMb.setHint("请选择目标高校");
    }

    private void tvLevel2Click() {
        imageView.setImageResource(R.drawable.zhuanyebg);
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_normal);
        tvLevel1.setTextColor(0xFF555555);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_press);
        tvLevel2.setTextColor(0xFFFFFFFF);
        faultLevel = 2;
        tv_test_desc.setText("目标专业");
        tvStudentMb.setText("");
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
        if (faultLevel == 2) {
            intent.putExtra("schoolName", schoolName);
        }
        openNewActivityByIntent(LqRiskTestResultActivity.class, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (resultCode){
            case 110:
                String returnSchoolName = data.getStringExtra("schoolName");
                setTextViewValues(tvStudentMb,returnSchoolName);
                break;
            case 119:
                schoolName = data.getStringExtra("schoolName");
                String majorName = data.getStringExtra("majorName");
                setTextViewValues(tvStudentMb,majorName);
                break;
        }
    }

    private int vipLevel = 0;
    private int vipType = 0;

    private UserRechargeTimes.Data rechargeTimesData = null;

    private void getUserRechargeTimes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        RetrofitUtil.getInstance().createReq(IService.class)
                .getUserRechargeTimes(jsonObject.toJSONString())
                .enqueue(new Callback<UserRechargeTimes>() {
                    @Override
                    public void onResponse(Call<UserRechargeTimes> call, Response<UserRechargeTimes> response) {
                        if (response.isSuccessful()) {
                            UserRechargeTimes rechargeTimes = response.body();
                            rechargeTimesData = rechargeTimes.getData();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRechargeTimes> call, Throwable t) {

                    }
                });
    }

    private void showUpgradeDialog() {
        int vip = LoginBean.getInstance().getVipLevel();
        if (vip <= 1) { //针对普通会员
            if (null == rechargeTimesData) {
                showDialog();
                return;
            }
            if ("0".equals(rechargeTimesData.getAdmissionRiskNum())) {
                showDialog();
                return;
            }
            //普通会员已经付费，立即测试
            rightNowTest();

            return;
        }
        //VIP会员免费，立即测试
        rightNowTest();
    }

    private VIPPriceBean priceBean = null;

    private void getVipLevelAmount() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getVipLevelAmount()
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            priceBean = JSON.parseObject(commonBean.getData().toString(), VIPPriceBean.class);
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast(t.getMessage());
                    }
                });
    }

    private void showDialog() {
        String tip = "非VIP会员进行测试，需要付费";
        if (null != priceBean && !TextUtils.isEmpty(priceBean.getAdmissionRiskAmount())) {
            tip += "￥" + priceBean.getHeartTestAmount();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("温馨提示");
        builder.setMessage(tip);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showPayWay(12);//12 录取风险
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPayWay(final int vipLevel) {
        new ActionSheet.Builder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("支付宝支付", "微信支付")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                submitOrder(vipLevel);
                                break;
                            case 1:
                                weiXinPay(vipLevel);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    private void weiXinPay(int level) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("vipLevel", level);
        RetrofitUtil.getInstance().createReq(IService.class)
                .prepay(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if (commonBean.getData() == null) {
                                toast("获取订单信息失败");
                                return;
                            }
                            WeiXinPay weiXinPay = JSON.parseObject(commonBean.getData().toString(), WeiXinPay.class);
                            payForWeiXin(weiXinPay);
                            hideProgress();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast(t.getMessage());
                        hideProgress();
                    }
                });
    }

    private void payForWeiXin(WeiXinPay weiXinPay) {
        PayReq req = new PayReq();
        req.appId = weiXinPay.getAppid();
        req.partnerId = weiXinPay.getPartnerid();
        req.prepayId = weiXinPay.getPrepayid();
        req.nonceStr = weiXinPay.getNoncestr();
        req.timeStamp = weiXinPay.getTimestamp();
        req.packageValue = weiXinPay.getPackAge();
        req.sign = weiXinPay.getSign();
        toast("这在调起微信支付……");

        LoginBean.getInstance().setVipLevelTmp(vipLevel);
        LoginBean.getInstance().setOrderNo(weiXinPay.getOrderNo());

        YXXApplication.payApi.sendReq(req);
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void submitOrder(int level) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("vipLevel", level);
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .addUserOrder(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_THREE_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_THREE_TIME:
                VipOrderBean vipOrderBean = JSON.parseObject(commonBean.getData().toString(), VipOrderBean.class);
                String sign = vipOrderBean.getSign();
                LoginBean.getInstance().setOrderNo(vipOrderBean.getOrderNo());
                payV2(sign);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    /**
     * 支付宝支付业务
     *
     * @param info
     */
    public void payV2(String info) {
        final String orderInfo = info;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(LqRiskActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        toast("支付成功");
                        LoginBean.getInstance().setVipLevel(vipLevel);
                        LoginBean.getInstance().save();
                        LoginBean.getInstance().setVipLevelTmp(vipLevel);
                        vipType = LoginBean.getInstance().getVipLevel();
                        tempOrderPaySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        toast("支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        toast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        toast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void tempOrderPaySuccess() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderNo", LoginBean.getInstance().getOrderNo());
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .tempOrderPaySuccess(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            toast(commonBean.getMessage());
                            rightNowTest();//立即进行测试
                        } else {
                            toast("支付失败，请重新支付！");
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast("支付失败，请重新支付！");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
