package com.gk.mvp.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.gk.beans.SameScoreItem;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/10/30.
 */

public class SameScoreActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_same_score)
    ListView lvSameScore;
    @BindView(R.id.tv_same_top_10)
    EditText etSameTop10;
    @BindView(R.id.rl_more_data)
    RelativeLayout relativeLayout;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_weli)
    TextView tvWeli;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_more_data)
    TextView tvMoreData;

    private List<SameScoreItem> list = new ArrayList<>();
    CommonAdapter adapter = null;
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;
    private int vip = 0;
    private String queryMoreStr = "查看更多";
    private String score = "";

    @OnClick(R.id.tv_more_data)
    public void tvMoreDataClick() {
        String tag = tvMoreData.getTag().toString();
        String etValue = etSameTop10.getText().toString();
        if(!TextUtils.isEmpty(etValue) && !score.equals(etValue)){
            score = etValue;
            mPage = 0;
            tvMoreData.setTag("0");
        }
        if ("0".equals(tag)) {
            if (vip > 1) {
                getSameScoreDirection();
            }
            tvMoreData.setTag("1");
            tvMoreData.setText(queryMoreStr);
            return;
        }
        mPage++;
        isLoadMore = true;
        getSameScoreDirection();
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_same_score_direction;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        topBar.getTitleView().setText("同分去向");
        topBar.getTitleView().setTextColor(0xFF030303);
        topBar.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
        initSmartRefreshLayout(smartRefreshLayout, false);

        LoginBean loginBean = LoginBean.getInstance();
        vipLevel = loginBean.getVipLevel();

        setTextViewValues(tvAddress, loginBean.getAddress());
        setTextViewValues(tvWeli, loginBean.getWlDesc());
        setTextViewValues(etSameTop10, loginBean.getScore());

        initListView();
        showUpgradeDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vip = LoginBean.getInstance().getVipLevel();
        getVipLevelAmount();
        getUserRechargeTimes();
    }

    private void initListView() {
        lvSameScore.setAdapter(adapter = new CommonAdapter<SameScoreItem>(this, R.layout.same_score_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, SameScoreItem item, int position) {
                viewHolder.setText(R.id.tv_same_score_name, item.getSchoolName());
                viewHolder.setText(R.id.tv_score_start, item.getLowestScore() + "");
                viewHolder.setText(R.id.tv_score_end, item.getHighestScore() + "");

                int max = item.getHighestScore();
                int min = item.getLowestScore();

                int len = max - min;

                TextView endTextView = viewHolder.getView(R.id.tv_score_end);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) endTextView.getLayoutParams();
                layoutParams.setMargins(len, 0, 0, 0);

                ProgressBar progressBar = viewHolder.getView(R.id.progressBar1);
                progressBar.setMax((max * 2));
                for (int i = min; i < (max * 2); i++) {
                    progressBar.setProgress(i);
                }
            }
        });
    }

    private void getSameScoreDirection() {
        showProgress();
        jsonObject.put("page", mPage);
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("score",score);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getSameScoreDirection(jsonObject.toJSONString()))
                .request();
    }

    private void handleData(List<SameScoreItem> sameScoreItems) {
        if (sameScoreItems == null || sameScoreItems.size() == 0) {
            toast("没有数据");
            return;
        }
        if (lvSameScore.getVisibility() == View.GONE) {
            lvSameScore.setVisibility(View.VISIBLE);
        }
        if (!isLoadMore) {//刷新
            list.clear();
            list.addAll(sameScoreItems);
            adapter.notifyDataSetChanged();
            lvSameScore.smoothScrollToPosition(list.size());
            return;
        }
        list.addAll(sameScoreItems);
        adapter.notifyDataSetChanged();
        lvSameScore.smoothScrollToPosition(list.size());

    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<SameScoreItem> sameScoreItems = JSON.parseArray(commonBean.getData().toString(), SameScoreItem.class);
        handleData(sameScoreItems);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        getSameScoreDirection();
    }

    private int vipLevel = 0;
    private int vipType = 0;

    private void showUpgradeDialog() {
        vip = LoginBean.getInstance().getVipLevel();
        if (vip <= 1) {
            showNormalVipDialog();
        }
    }

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

    private void showNormalVipDialog() {
        if (null == rechargeTimesData || TextUtils.isEmpty(rechargeTimesData.getSameScoreToNum())) {
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
                    showPayWay(13);//13 同分去向
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    closeActivity(SameScoreActivity.this);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            return;
        }

        getSameScoreDirection();
    }

    private void showPayWay(final int vipLevel) {
        new ActionSheet.Builder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("支付宝支付", "微信支付")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                        if (isCancel) {
                            closeActivity(SameScoreActivity.this);
                        }
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
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .addUserOrder(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            VipOrderBean vipOrderBean = JSON.parseObject(commonBean.getData().toString(), VipOrderBean.class);
                            String sign = vipOrderBean.getSign();
                            LoginBean.getInstance().setOrderNo(vipOrderBean.getOrderNo());
                            payV2(sign);
                            return;
                        }
                        toast("支付失败，请重新支付！");
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        toast("支付失败，请重新支付！");
                    }
                });
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
                PayTask alipay = new PayTask(SameScoreActivity.this);
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
                            getSameScoreDirection();
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

}
