package com.gk.mvp.model;

import com.gk.mvp.model.inter.IModel;

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
    public void getServerData(Callback callback) {
//        Map<String,String> map = new HashMap<>();
//        map.put("page",String.valueOf(pageCount));
//        Call<Object> call = HttpRequestUtil.commonHttpRequest(Config.FORUM_CLS,Config.FORUM_METHOD, null,map);
//        call.enqueue(callback);
    }
}
