package com.gk.mvp.model;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.OnLiveBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.IPresenterCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/12/22.
 */

public class OnLiveRoomModel {
    private IPresenterCallback iPresenterCallback;
    private JSONObject jsonObject = new JSONObject();

    public OnLiveRoomModel(IPresenterCallback iPresenterCallback, OnLiveBean onLiveBean) {
        this.iPresenterCallback = iPresenterCallback;
        OnLiveBean onLiveBean1 = onLiveBean;
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("liveRoomId", onLiveBean.getId());
        fansEnterLiveRooms();
        getLiveRoomsInfo();
    }

    public void fansEnterLiveRooms() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .fansEnterLiveRooms(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if ((commonBean != null ? commonBean.getStatus() : 0) == 1) {
                                iPresenterCallback.httpRequestSuccess(commonBean, YXXConstants.INVOKE_API_DEFAULT_TIME);
                            } else {
                                iPresenterCallback.httpRequestFailure(commonBean.getMessage(), YXXConstants.INVOKE_API_DEFAULT_TIME);
                            }
                            return;
                        }
                        iPresenterCallback.httpRequestFailure(response.message(), YXXConstants.INVOKE_API_DEFAULT_TIME);
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        iPresenterCallback.httpRequestFailure(t.getMessage(), YXXConstants.INVOKE_API_DEFAULT_TIME);
                    }
                });
    }

    public void fansExitLiveRooms() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .fansExitLiveRooms(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if ((commonBean != null ? commonBean.getStatus() : 0) == 1) {
                                iPresenterCallback.httpRequestSuccess(commonBean, YXXConstants.INVOKE_API_SECOND_TIME);
                            } else {
                                iPresenterCallback.httpRequestFailure(commonBean.getMessage(), YXXConstants.INVOKE_API_SECOND_TIME);
                            }
                            return;
                        }
                        iPresenterCallback.httpRequestFailure(response.message(), YXXConstants.INVOKE_API_SECOND_TIME);
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        iPresenterCallback.httpRequestFailure(t.getMessage(), YXXConstants.INVOKE_API_SECOND_TIME);
                    }
                });
    }

    public void fansLiveRoomsSpeak(String fansSpeak) {
        jsonObject.put("fansSpeak", fansSpeak);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .fansLiveRoomsSpeak(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if ((commonBean != null ? commonBean.getStatus() : 0) == 1) {
                                iPresenterCallback.httpRequestSuccess(commonBean, YXXConstants.INVOKE_API_THREE_TIME);
                            } else {
                                iPresenterCallback.httpRequestFailure(commonBean.getMessage(), YXXConstants.INVOKE_API_THREE_TIME);
                            }
                            return;
                        }
                        iPresenterCallback.httpRequestFailure(response.message(), YXXConstants.INVOKE_API_THREE_TIME);
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        iPresenterCallback.httpRequestFailure(t.getMessage(), YXXConstants.INVOKE_API_THREE_TIME);
                    }
                });
    }

    public void getLiveRoomsInfo() {
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getLiveRoomsInfo(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if ((commonBean != null ? commonBean.getStatus() : 0) == 1) {
                                iPresenterCallback.httpRequestSuccess(commonBean, YXXConstants.INVOKE_API_FORTH_TIME);
                            } else {
                                iPresenterCallback.httpRequestFailure(commonBean.getMessage(), YXXConstants.INVOKE_API_FORTH_TIME);
                            }
                            return;
                        }
                        iPresenterCallback.httpRequestFailure(response.message(), YXXConstants.INVOKE_API_FORTH_TIME);
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                        iPresenterCallback.httpRequestFailure(t.getMessage(), YXXConstants.INVOKE_API_FORTH_TIME);
                    }
                });
    }

}
