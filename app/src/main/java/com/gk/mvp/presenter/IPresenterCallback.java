package com.gk.mvp.presenter;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public interface IPresenterCallback<T> {
    /**
     * 请求成功回调接口
     *
     * @param t
     * @param order
     */
    void httpRequestSuccess(T t, int order);

    /**
     * 请求失败回调接口
     *
     * @param t
     * @param order
     */
    void httpRequestFailure(T t, int order);
}
