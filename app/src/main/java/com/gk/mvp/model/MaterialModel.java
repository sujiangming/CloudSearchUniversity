package com.gk.mvp.model;

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
    private MaterialItemBean materialItemBean;
    private JSONObject jsonObject;
    private IPresenterCallback<MaterialItemBean> iPresenterCallback;

    public MaterialModel(IPresenterCallback<MaterialItemBean> iPresenterCallback) {
        this.iPresenterCallback = iPresenterCallback;
        jsonObject = new JSONObject();
    }

    /**
     * 获取资料类型列表（分页查询）
     *
     * @param page   分页
     * @param type   1 名师讲堂 2历年真题 3模拟真题
     * @param course 课程名称 比如语文
     */
    public void httpRequestMaterialsByType(int page, int type, String course) {
        jsonObject.put("page", page);
        jsonObject.put("type", type);
        jsonObject.put("course", course);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getMaterialsByType(jsonObject.toJSONString())
                .enqueue(new Callback<MaterialItemBean>() {
                    @Override
                    public void onResponse(Call<MaterialItemBean> call, Response<MaterialItemBean> response) {
                        if (response.isSuccessful()) {
                            materialItemBean = response.body();
                            iPresenterCallback.httpRequestSuccess(materialItemBean, YXXConstants.INVOKE_API_DEFAULT_TIME);
                        }
                    }

                    @Override
                    public void onFailure(Call<MaterialItemBean> call, Throwable t) {
                        MaterialItemBean itemBean = new MaterialItemBean();
                        itemBean.setMessage(t.getMessage());
                        iPresenterCallback.httpRequestFailure(itemBean, YXXConstants.INVOKE_API_DEFAULT_TIME);
                    }
                });
    }
}
