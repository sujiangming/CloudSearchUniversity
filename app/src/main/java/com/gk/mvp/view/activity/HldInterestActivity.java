package com.gk.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.gk.R;
import com.gk.beans.AuthResult;
import com.gk.beans.CommonBean;
import com.gk.beans.HldReportBean;
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
import com.gk.mvp.view.custom.TopBarView;
import com.gk.mvp.view.custom.actionsheet.ActionSheet;
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

public class HldInterestActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.btn_mbti_test)
    Button btnMbtiTest;
    @BindView(R.id.btn_mbti_query)
    Button btnMbtiQuery;

    @OnClick({R.id.btn_mbti_test, R.id.btn_mbti_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mbti_test:
                showUpgradeDialog();
                break;
            case R.id.btn_mbti_query:
                openResultWin();
                break;
        }
    }

    private int vipLevel = 0;
    private HldReportBean hldReportBean = null;

    @Override
    public int getResouceId() {
        return R.layout.activity_interest_test;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "霍兰德性格测试", 0);
        httpReqest();
        getVipLevelAmount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserRechargeTimes();
    }

    private void openResultWin() {
        Intent intent = new Intent();
        intent.putExtra("flag", 1);
        intent.putExtra("bean", hldReportBean);
        openNewActivityByIntent(HLDTestResultActivity.class, intent);
    }

    private UserRechargeTimes.Data rechargeTimesData = null;

    private void getUserRechargeTimes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        RetrofitUtil.getInstance().createReq(IService.class)
                .getUserRechargeTimes(jsonObject.toJSONString())
                .enqueue(new Callback<UserRechargeTimes>() {
                    @Override
                    public void onResponse(@NonNull Call<UserRechargeTimes> call, @NonNull Response<UserRechargeTimes> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }

                        if (null == response.body()) {
                            return;
                        }

                        UserRechargeTimes rechargeTimes = response.body();

                        if (null == rechargeTimes) {
                            return;
                        }

                        rechargeTimesData = rechargeTimes.getData();
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserRechargeTimes> call, @NonNull Throwable t) {

                    }
                });
    }

    private PresenterManager presenterManager = new PresenterManager();

    private void httpReqest() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getHldTestReportByUser(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    private VIPPriceBean priceBean = null;

    private void getVipLevelAmount() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getVipLevelAmount()
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }

                        if (null == response.body()) {
                            return;
                        }

                        CommonBean commonBean = response.body();

                        if (null == (commonBean != null ? commonBean.getData() : null)) {
                            return;
                        }

                        priceBean = JSON.parseObject(commonBean.getData().toString(), VIPPriceBean.class);

                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        toast(t.getMessage());
                    }
                });
    }

    private void showUpgradeDialog() {
        int vip = LoginBean.getInstance().getVipLevel();
        if (vip <= 1) {
            if (null == rechargeTimesData) {
                showDialog();
                return;
            }
            if ("0".equals(rechargeTimesData.getHeartTestNum())) {
                showDialog();
                return;
            }
            //普通会员已经付费了，可以直接进行测试
            openNewActivity(HLDTestDetailActivity.class);
            return;
        }
        //VIP会员免费使用，直接进行测试
        openNewActivity(HLDTestDetailActivity.class);
    }

    private void showDialog() {
        String tip = "非VIP会员进行测试，需要付费";
        if (null != priceBean && !TextUtils.isEmpty(priceBean.getHeartTestAmount())) {
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
                showPayWay(11);//心理测试 传参是 11
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
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if ((commonBean != null ? commonBean.getData() : null) == null) {
                                toast("获取订单信息失败");
                                return;
                            }

                            WeiXinPay weiXinPay = JSON.parseObject(commonBean.getData().toString(), WeiXinPay.class);
                            payForWeiXin(weiXinPay);
                            hideProgress();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
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

    private void submitOrder(int level) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("vipLevel", level);
        presenterManager.setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .addUserOrder(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                hldReportBean = JSON.parseObject(commonBean.getData().toString(), HldReportBean.class);
                if (hldReportBean == null || hldReportBean.getId() == null) {
                    return;
                }
                btnMbtiTest.setText("重做一遍");
                btnMbtiQuery.setVisibility(View.VISIBLE);
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                VipOrderBean vipOrderBean = JSON.parseObject(commonBean.getData().toString(), VipOrderBean.class);
                String sign = vipOrderBean.getSign();
                LoginBean.getInstance().setOrderNo(vipOrderBean.getOrderNo());
                payV2(sign);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        if (order == YXXConstants.INVOKE_API_SECOND_TIME) {
            toast(YXXConstants.ERROR_INFO);
        }
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
                PayTask alipay = new PayTask(HldInterestActivity.this);
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
                        int vipType = LoginBean.getInstance().getVipLevel();
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
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            toast(commonBean != null ? commonBean.getMessage() : response.message());
                        } else {
                            toast(response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }
}
