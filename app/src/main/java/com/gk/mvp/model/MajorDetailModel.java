package com.gk.mvp.model;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.beans.MajorInfoBean;
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

public class MajorDetailModel {
    private MajorInfoBean majorInfoBean;
    private JSONObject jsonObject;

    public MajorDetailModel() {
        jsonObject = new JSONObject();
    }

    public void httpRequest(final IPresenterCallback iPresenterCallback, String pid) {
        jsonObject.put("majorTypeId", pid);
        RetrofitUtil.getInstance().createReq(IService.class).getMajorInfoList(jsonObject.toJSONString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String body = response.body().string();
                        majorInfoBean = JSON.parseObject(body, MajorInfoBean.class);
                        handleData(iPresenterCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                iPresenterCallback.httpRequestFailure(t.getMessage(), 1);
            }
        });
    }

    private void handleData(final IPresenterCallback iPresenterCallback) {
        if (majorInfoBean == null) {
            return;
        }
        List<MajorInfoBean.DataBean> bzTypeList = majorInfoBean.getData();
        if(bzTypeList == null){
            iPresenterCallback.httpRequestFailure(majorInfoBean.getMessage(),1);
            return;
        }
        iPresenterCallback.httpRequestSuccess(bzTypeList, 1);
    }
}
