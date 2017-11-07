package com.gk.http;

import com.gk.beans.CommonBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public interface IService {
    @FormUrlEncoded
    @POST("app/getSalt")
    Call<CommonBean> getSaltValue(@Field("data") String jsonParameter);

    @FormUrlEncoded
    @POST("app/login")
    Call<CommonBean> login(@Field("data") String jsonParameter);

}
