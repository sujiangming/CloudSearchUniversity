package com.gk.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.beans.MajorTypeBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.IPresenterCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public class MajorModel {

    private List<MajorTypeBean> list;
    private String errorMsg;

    public MajorModel(IPresenterCallback iPresenterCallback) {
        httpRequest(iPresenterCallback);
    }

    public void httpRequest(final IPresenterCallback iPresenterCallback) {
        RetrofitUtil.getInstance().createReq(IService.class).getMajorTypeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String body = response.body().string();
                        JSONObject json = JSON.parseObject(body);
                        list = JSON.parseArray(json.getString("data"), MajorTypeBean.class);
                        iPresenterCallback.httpRequestSuccess(list, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iPresenterCallback.httpRequestFailure(t, 1);
            }
        });
    }
}
