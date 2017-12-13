package com.gk.http.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ljd on 3/29/16.
 */
public interface DownloadApi {
    //@GET("/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk")
    @GET
    Call<ResponseBody> retrofitDownload(@Url String url);
}
