package com.gk.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.gk.R;
import com.gk.beans.AuthResult;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.PayResult;
import com.gk.beans.VipOrderBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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

    private String form = null;
    private int vipType = LoginBean.getInstance().getVipLevel();
    private int vipLevel = 0;
    private String goldTip = "您确定升级为金卡会员吗？";
    private String silverTip = "您确定升级为银卡会员吗？";


    @OnClick({R.id.tv_open_gold, R.id.tv_open_silver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open_gold:
                if (vipType < 3) {
                    showVipDialog(goldTip, 2);
                    vipLevel = 3;
                } else {
                    toast("您已经是金卡用户了");
                }
                break;
            case R.id.tv_open_silver:
                if (vipType < 2) {
                    showVipDialog(silverTip, 1);
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

    private void showVipDialog(String tip, final int vipLevel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("温馨提示");
        builder.setMessage(tip);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitOrder(vipLevel);
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
                        vipType = LoginBean.getInstance().getVipLevel();
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
}
