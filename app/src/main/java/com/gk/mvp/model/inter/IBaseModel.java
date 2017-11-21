package com.gk.mvp.model.inter;

import retrofit2.Callback;

/**
 * Created by JDRY-SJM on 2017/11/21.
 */

public interface IBaseModel<T> {
    /**
     * 请求服务器接口统一方法
     *
     * @param callback
     */
    void httpRequest(Callback<T> callback);

    /**
     * 请求成功回调接口
     * @param t
     * @param order
     */
    void httpRequestSuccess(T t,int order);

    /**
     * 请求失败回调接口
     * @param t
     * @param order
     */
    void httpRequestFailure(T t,int order);

}
