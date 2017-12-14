package com.gk.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.VerifyCodeBean;
import com.gk.beans.WeiXin;
import com.gk.beans.WeiXinToken;
import com.gk.beans.WeiXinUserInfo;
import com.gk.global.YXXApplication;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.view.activity.MainActivity;
import com.gk.tools.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 微信登陆分享回调Activity
 *
 * @author 安辉
 * @create time 2015-05-25
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI wxAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxAPI = YXXApplication.sApi;
        wxAPI.handleIntent(getIntent(), this);
    }

    /**
     * 微信组件注册初始化
     *
     * @param context       上下文
     * @param weixin_app_id appid
     * @return 微信组件api对象
     */
    public static IWXAPI initWeiXin(Context context, @NonNull String weixin_app_id) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, weixin_app_id, true);
        api.registerApp(weixin_app_id);
        return api;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxAPI.handleIntent(getIntent(), this);
        Log.d("ansen", "WXEntryActivity onNewIntent");
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        Log.d("ansen", "WXEntryActivity onReq:" + req);
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        Log.e(WXEntryActivity.class.getName(), JSON.toJSONString(resp));
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            Log.d("ansen", "微信分享操作.....");
            WeiXin weiXin = new WeiXin(2, resp.errCode, "");
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
            Log.d("ansen", "微信登录操作.....");
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            //WeiXin weiXin = new WeiXin(1, resp.errCode, authResp.code);
            getAccessToken(authResp.code);
        }
        // finish();
    }

    /**
     * 获取授权口令
     */
    private void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + Constant.WECHAT_APPID +
                "&secret=" + Constant.WECHAT_SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        // 网络请求获取access_token
        RetrofitUtil.getInstance().createReq(IService.class).getAccessToken(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody;
                if (response.isSuccessful()) {
                    responseBody = response.body();
                    try {
                        processGetAccessTokenResult(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //finish();
                } else {
                    responseBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 处理获取的授权信息结果
     *
     * @param response 授权信息结果
     */
    private void processGetAccessTokenResult(String response) {
        // 验证获取授权口令返回的信息是否成功
        if (validateSuccess(response)) {
            // 使用Gson解析返回的授权口令信息
            WeiXinToken tokenInfo = JSON.parseObject(response, WeiXinToken.class);
            Log.e("", tokenInfo.toString());
            // 保存信息到手机本地
            //saveAccessInfotoLocation(tokenInfo);
            // 获取用户信息
            getUserInfo(tokenInfo.getAccess_token(), tokenInfo.getOpenid());
        } else {
            Log.d("error response", response);
        }
    }

    /**
     * 验证是否成功
     *
     * @param response 返回消息
     * @return 是否成功
     */
    private boolean validateSuccess(String response) {
        String errFlag = "errmsg";
        return (errFlag.contains(response) && !"ok".equals(response))
                || (!"errcode".contains(response) && !errFlag.contains(response));
    }

    private void getUserInfo(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token +
                "&openid=" + openid;
        RetrofitUtil.getInstance().createReq(IService.class).getWXUserInfo(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody;
                if (response.isSuccessful()) {
                    responseBody = response.body();
                    if (responseBody != null) {
                        try {
                            String bodyString = responseBody.string();
                            WeiXinUserInfo weiXinUserInfo = JSON.parseObject(bodyString, WeiXinUserInfo.class);
                            //调用微信登录接口
                            weixinLogin(weiXinUserInfo.getOpenid());
                            //将头像和uninID上传服务器
                            if (LoginBean.getInstance().getHeadImg() == null && LoginBean.getInstance().getNickName() == null) {
                                updateUserInfo(weiXinUserInfo.getHeadimgurl(), weiXinUserInfo.getNickname(), weiXinUserInfo.getUnionid());
                            } else if (LoginBean.getInstance().getHeadImg() == null && LoginBean.getInstance().getNickName() != null) {
                                updateUserInfo(weiXinUserInfo.getHeadimgurl(), null, weiXinUserInfo.getUnionid());
                            } else {
                                updateUserInfo(null, weiXinUserInfo.getNickname(), weiXinUserInfo.getUnionid());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    responseBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 微信登录接口
     *
     * @param weixinNo
     */
    private void weixinLogin(final String weixinNo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("weixin", weixinNo);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .weixinLogin(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
                            LoginBean.getInstance().setWeixin(loginBean.getWeixin());
                            LoginBean.getInstance().save();
                            Intent intent = new Intent();
                            intent.setClass(WXEntryActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            getVerityfyCode(weixinNo);
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        ToastUtils.toast(WXEntryActivity.this, t.getMessage());
                    }
                });
    }

    /**
     * 获取验证码接口，获取验证码作为微信绑定登录接口中的参数
     */
    private void getVerityfyCode(final String weixinNo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getVerityfyCode(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            VerifyCodeBean verifyCodeBean = JSON.parseObject(commonBean.getData().toString(), VerifyCodeBean.class);
                            Log.e("微信绑定登录接口获取验证码成功~", verifyCodeBean.getVerifyCode());
                            userBindingWeixin(weixinNo, verifyCodeBean.getVerifyCode());
                        } else {
                            ToastUtils.toast(WXEntryActivity.this, response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        ToastUtils.toast(WXEntryActivity.this, t.getMessage());
                    }
                });
    }

    /**
     * 微信绑定登录接口
     *
     * @param weixinNo
     */
    private void userBindingWeixin(String weixinNo, String verifyCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("weixin", weixinNo);
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("verifyCode", verifyCode);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .weixinLogin(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            LoginBean loginBean = JSON.parseObject(commonBean.getData().toString(), LoginBean.class);
                            LoginBean.getInstance().setWeixin(loginBean.getWeixin());
                            LoginBean.getInstance().save();
                            Intent intent = new Intent();
                            intent.setClass(WXEntryActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtils.toast(WXEntryActivity.this, response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {
                        ToastUtils.toast(WXEntryActivity.this, t.getMessage());
                    }
                });
    }

    /**
     * 保存微信头像和昵称
     *
     * @param imagePath
     * @param nickName
     * @param unionid
     */
    private void updateUserInfo(final String imagePath, final String nickName, final String unionid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        if (imagePath != null) {
            jsonObject.put("headImg", imagePath);
        }
        if (nickName != null) {
            jsonObject.put("nickName", nickName);
        }

        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .updateUserInfo(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            if (imagePath != null) {
                                LoginBean.getInstance().setHeadImg(imagePath);
                            }
                            if (nickName != null) {
                                LoginBean.getInstance().setNickName(nickName);
                            }
                            LoginBean.getInstance().setWeiXinUnionid(unionid);
                            LoginBean.getInstance().save();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {

                    }
                });
    }

}
