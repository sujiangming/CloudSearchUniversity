package com.gk.mvp.model.inter;

import retrofit2.Callback;

/**
 * Created by JDRY-SJM on 2017/8/3.
 */

public interface IModel<Object> {
    void getServerData(Callback<Object> callback);
}
