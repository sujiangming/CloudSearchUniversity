package com.gk.http.download;

import com.gk.global.YXXConstants;
import com.gk.http.FastJsonConverterFactory;
import com.gk.http.RetrofitUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by JDRY-SJM on 2017/11/28.
 */

public class RetrofitDownloadUtils {
    public static final int DEFAULT_TIMEOUT = 15;
    public Retrofit mRetrofit;
    private static final String TAG = RetrofitDownloadUtils.class.getName();

    private static RetrofitDownloadUtils mInstance;

    private static ProgressListener progressListener;

    public ProgressListener getProgressListener() {
        return progressListener;
    }

    public RetrofitDownloadUtils setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
        return mInstance;
    }

    /**
     * 私有构造方法
     */
    private RetrofitDownloadUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response orginalResponse = chain.proceed(chain.request());
                return orginalResponse.newBuilder()
                        .body(new ProgressResponseBody(orginalResponse.body(),progressListener)).build();
            }
        });
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(YXXConstants.HOST)
                //在此处声明使用FastJsonConverter做为转换器
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public static RetrofitDownloadUtils getInstance(ProgressListener listener) {
        progressListener = listener;
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitDownloadUtils();
            }
        }
        return mInstance;
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
