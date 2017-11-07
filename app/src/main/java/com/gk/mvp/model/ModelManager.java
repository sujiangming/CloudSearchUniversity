package com.gk.mvp.model;

import com.gk.beans.CommonBean;
import com.gk.mvp.model.inter.IModel;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by JDRY-SJM on 2017/11/1.
 */

public class ModelManager implements IModel<CommonBean> {

    public static Call<CommonBean> miBeanCall;
    public static ModelManager mDataManager;

    private ModelManager() {

    }

    public static ModelManager getInstance(Call<CommonBean> call) {
        if (mDataManager == null) {
            synchronized (ModelManager.class) {
                mDataManager = new ModelManager();
            }
        }
        miBeanCall = call;
        return mDataManager;
    }

    @Override
    public void getServerData(Callback<CommonBean> callback) {
        miBeanCall.enqueue(callback);
    }
}
