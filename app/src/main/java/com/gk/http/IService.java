package com.gk.http;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public interface IService {
    @POST
    Call<Object> httpRequest(@Url String url);
}
