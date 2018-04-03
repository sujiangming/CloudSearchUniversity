package com.gk.mvp.model;

import com.gk.beans.CommonBean;
import com.gk.mvp.model.inter.IModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by JDRY-SJM on 2017/11/1.
 */

public class ModelManager implements IModel<CommonBean> {

    public Call<CommonBean> miBeanCall;
    public Call<ResponseBody> miBeanCallForResponseBody;

    public ModelManager setCall(Call<CommonBean> call, Call<ResponseBody> callForResponseBody) {
        miBeanCall = call;
        miBeanCallForResponseBody = callForResponseBody;
        return this;
    }

    @Override
    public void getServerData(Callback<CommonBean> callback) {
        miBeanCall.enqueue(callback);
    }

    public void getServerDataForResponseBody(Callback<ResponseBody> callback) {
        miBeanCallForResponseBody.enqueue(callback);
    }
}
