package com.gk.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.baoyz.actionsheet.ActionSheet;
import com.gk.R;
import com.gk.beans.AuthResult;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.PayResult;
import com.gk.beans.VIPPriceBean;
import com.gk.beans.VipOrderBean;
import com.gk.beans.WeiXinPay;
import com.gk.global.YXXApplication;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/10/23.
 */

public class VIPActivity extends SjmBaseActivity {

    @BindView(R.id.tv_top_bar)
    TopBarView tvTopBar;

    @BindView(R.id.ll_gold)
    LinearLayout llGold;

    @BindView(R.id.ll_silve)
    LinearLayout llSilve;

    @BindView(R.id.rtv_gold_price)
    RichText rtv_gold_price;

    @BindView(R.id.rtv_silver_price)
    RichText rtv_silver_price;

    private String form = null;
    private LoginBean loginBean;
    private int vipType = 0;
    private int vipLevel = 0;
    private String goldTip = "您确定升级为金卡会员吗？";
    private String silverTip = "您确定升级为银卡会员吗？";


    @OnClick({R.id.tv_open_gold, R.id.tv_open_silver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open_gold:
                if (vipType < 3) {
                    showPayWay(3);
                    vipLevel = 3;
                } else {
                    toast("您已经是金卡用户了");
                }
                break;
            case R.id.tv_open_silver:
                if (vipType < 2) {
                    showPayWay(2);
                    vipLevel = 2;
                } else if (vipType == 2) {
                    toast("您已经是银卡用户了，您可要升级为金卡用户哦");
                } else {
                    toast("您已经是金卡用户了");
                }
                break;
        }
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(tvTopBar, "VIP服务", 0);
        initUIByForm();
        getVipLevelAmount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        loginBean = LoginBean.getInstance();
        vipType = LoginBean.getInstance().getVipLevel();
    }

    private void initUIByForm() {
        Intent intent = getIntent();
        if (intent != null) {
            form = intent.getStringExtra("form");
            if (form != null) {
                if (form.equals("vip_choose")) {
                    llGold.setVisibility(View.GONE);
                } else {
                    llSilve.setVisibility(View.GONE);
                }
            }
        }
    }

    private void getVipLevelAmount() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getVipLevelAmount()
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            VIPPriceBean priceBean = JSON.parseObject(commonBean.getData().toString(), VIPPriceBean.class);
                            if (priceBean != null) {
                                rtv_gold_price.setText("金卡会员服务 ￥" + priceBean.getVipLevel2Amount() + "元");
                                rtv_silver_price.setText("银卡会员服务 ￥" + priceBean.getVipLevel1Amount() + "元");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast(t.getMessage());
                    }
                });
    }

    private void showPayWay(final int vipLevel) {
        if (null == loginBean.getUsername()) {
            toast("您还没有登录，请您先登录！");
            return;
        }
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

    private void submitOrder(int level) {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("vipLevel", level);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .addUserOrder(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        VipOrderBean vipOrderBean = JSON.parseObject(commonBean.getData().toString(), VipOrderBean.class);
        String sign = vipOrderBean.getSign();
        LoginBean.getInstance().setOrderNo(vipOrderBean.getOrderNo());
        payV2(sign);
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    /**
     * 支付宝支付业务
     *
     * @param info
     */
    public void payV2(String info) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        final String orderInfo = info;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(VIPActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

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
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
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
                            if (commonBean.getStatus() == 1) {
                                toast(commonBean.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {

                    }
                });
    }
}
