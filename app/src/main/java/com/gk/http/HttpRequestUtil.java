package com.gk.http;

import com.alibaba.fastjson.JSON;

import retrofit2.Call;

/**
 * Created by JDRY_SJM on 2017/4/18.
 */

public class HttpRequestUtil {
    public static String getLoginUrl(String cls, String method, String param) {
        return "access.app?" + "clsname=" + cls + "&" + "methodname=" + method + "&data=" + param;
    }

    public static String getUrl(String cls, String method, String token, String param) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("access.app?").append("clsname=").append(cls).append("&").append("methodname=").append(method).append("&token=").append(token);
        if (null != param && !"".equals(param)) {
            stringBuffer.append("&data=").append(param);
        }
        return stringBuffer.toString();
    }

    public static Call<Object> commonHttpRequest(String cls, String method, String token, Object map) {
        String url = getUrl(cls, method, token, map == null ? null : JSON.toJSONString(map));
        RetrofitUtil retrofitUtil = RetrofitUtil.getInstance();
        IService iService = retrofitUtil.createReq(IService.class);
        Call<Object> call = iService.httpRequest(url);
        return call;
    }
}
