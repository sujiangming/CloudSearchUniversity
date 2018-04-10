package com.gk.mvp.model;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.gk.beans.MaterialItemBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.IPresenterCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/26.
 */

public class MaterialModel {
    private JSONObject jsonObject;
    private IPresenterCallback iPresenterCallback;
    private MaterialItemBean materialItemBean;

    public MaterialModel(IPresenterCallback iPresenterCallback) {
        this.iPresenterCallback = iPresenterCallback;
        jsonObject = new JSONObject();
        materialItemBean = new MaterialItemBean();
    }

    /**
     * 获取资料类型列表（分页查询）
     *
     * @param page   分页
     * @param course 课程名称 比如语文
     */
    public void httpRequestMaterialsByCourse(int page, String course) {
        jsonObject.put("page", page);
        jsonObject.put("course", course);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getMaterialsByCourse(jsonObject.toJSONString())
                .enqueue(new Callback<MaterialItemBean>() {
                    @Override
                    public void onResponse(@NonNull Call<MaterialItemBean> call, @NonNull Response<MaterialItemBean> response) {
                        if (response.isSuccessful()) {
                            materialItemBean = response.body();
                            iPresenterCallback.httpRequestSuccess(materialItemBean, YXXConstants.INVOKE_API_DEFAULT_TIME);
                        } else {
                            materialItemBean.setMessage(response.message());
                            iPresenterCallback.httpRequestFailure(materialItemBean, YXXConstants.INVOKE_API_DEFAULT_TIME);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MaterialItemBean> call, @NonNull Throwable t) {
                        materialItemBean.setMessage(t.getMessage());
                        iPresenterCallback.httpRequestFailure(materialItemBean, YXXConstants.INVOKE_API_DEFAULT_TIME);
                    }
                });
    }
}
