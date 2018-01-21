package com.gk.mvp.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.beans.MajorBean;
import com.gk.beans.MajorQueryBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.IPresenterCallback;
import com.gk.tools.JdryPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public class MajorModel {

    private MajorBean majorBean;
    private String errorMsg;
    private MajorQueryBean majorQueryBean;

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
                        JdryPersistence.saveByAppContext(body, YXXConstants.MAJOR_JSON_SERIALIZE_KEY);//存储起来
                        majorBean = JSON.parseObject(body, MajorBean.class);
                        handleData(iPresenterCallback);
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

    private void handleData(final IPresenterCallback iPresenterCallback) {
        if (majorBean == null) {
            return;
        }
        List<List<MajorBean.DataBean.NodesBeanXX>> listList = new ArrayList<>();
        List<MajorBean.DataBean> bzTypeList = majorBean.getData();
        for (MajorBean.DataBean dataBean : bzTypeList) {
            String name = dataBean.getName();
            if (name.equals("本科")) {
                listList.add(0, dataBean.getNodes());
                continue;
            } else if (name.equals("专科")) {
                listList.add(1, dataBean.getNodes());
                continue;
            } else {
                continue;
            }
        }
        iPresenterCallback.httpRequestSuccess(listList, 1);
    }

    public void httpQueryRequest(String key, int type, final IPresenterCallback iPresenterCallback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("majorName", key);
        jsonObject.put("type", "");
        RetrofitUtil.getInstance().createReq(IService.class).getMajorListByName(jsonObject.toJSONString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String body = response.body().string();
                        Log.e("body", body);
                        majorQueryBean = JSON.parseObject(body, MajorQueryBean.class);
                        iPresenterCallback.httpRequestSuccess(majorQueryBean.getData(), 2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iPresenterCallback.httpRequestFailure(t, 2);
            }
        });
    }
}
