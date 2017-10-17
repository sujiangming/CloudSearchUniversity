package com.gk.model;

import com.gk.config.Config;
import com.gk.http.HttpRequestUtil;
import com.gk.model.inter.IModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LiveVideoModelImp implements IModel {
    public int pageCount;
    public LiveVideoModelImp(int pageNum){
        this.pageCount = pageNum;
    }
    @Override
    public void getData(Callback callback) {
        Map<String,String> map = new HashMap<>();
        map.put("page",String.valueOf(pageCount));
        Call<Object> call = HttpRequestUtil.commonHttpRequest(Config.FORUM_CLS,Config.FORUM_METHOD, null,map);
        call.enqueue(callback);
    }
}
