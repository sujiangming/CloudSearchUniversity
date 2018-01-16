package com.gk.http;


import com.gk.global.YXXConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public class RetrofitUtil {
    public static final int DEFAULT_TIMEOUT = 30;

    public Retrofit mRetrofit;
    private static RetrofitUtil mInstance;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {
        //可以利用okhttp实现缓存
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        //创建retrofit对象
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(YXXConstants.HOST)
                .addConverterFactory(FastJsonConverterFactory.create())//在此处声明使用FastJsonConverter做为转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
