package com.gk.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gk.beans.CommonBean;
import com.gk.mvp.model.ModelManager;
import com.gk.mvp.view.IView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class PresenterManager {
    private IView mIView;
    private Call mCall;
    private int mOrder = 1;//默认每次只是调用一次接口
    private boolean mIsShow = false;
    private String errorInfo = "请求失败";
    private ModelManager modelManager;

    public PresenterManager() {
        modelManager = new ModelManager();
    }

    public PresenterManager setmIView(IView mIView) {
        this.mIView = mIView;
        return this;
    }

    public Call getCall() {
        return mCall;
    }

    public PresenterManager setCall(Call mCall) {
        this.mCall = mCall;
        return this;
    }

    public void hideShowProgress() {
        if (this.mIsShow) {
            mIView.hideProgress();
        }
    }

    public PresenterManager request() {
        modelManager.setCall(mCall, null).getServerData(new Callback<CommonBean>() {
            @Override
            public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {
                if (!response.isSuccessful()) {
                    mIView.fillWithNoData(errorInfo, mOrder);
                    return;
                }
                if (null == response.body()) {
                    mIView.fillWithNoData(errorInfo, mOrder);
                    return;
                }

                CommonBean commonBean = response.body();

                if (1 != (commonBean != null ? commonBean.getStatus() : 0)) {
                    mIView.fillWithNoData(errorInfo, mOrder);
                    return;
                }

                render(commonBean);
            }

            @Override
            public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                mIView.fillWithNoData(t.getMessage(), mOrder);
                hideShowProgress();
            }
        });
        return this;
    }

    private void render(CommonBean commonBean) {
        mIView.fillWithData(commonBean, mOrder);
    }

    /**
     * 针对在一个页面有多次调用不同接口的情况下（与调用顺序无关）
     * 需要输入一个调用标识
     *
     * @param flag
     * @return
     */
    public PresenterManager request(int flag) {
        final int invokeFlag = flag;
        modelManager.setCall(mCall, null).getServerData(new Callback<CommonBean>() {
            @Override
            public void onResponse(@NonNull Call<CommonBean> call, @NonNull Response<CommonBean> response) {

                if (!response.isSuccessful()) {
                    mIView.fillWithNoData(errorInfo, invokeFlag);
                    return;
                }
                if (null == response.body()) {
                    mIView.fillWithNoData(errorInfo, invokeFlag);
                    return;
                }

                CommonBean commonBean = response.body();

                if (1 != (commonBean != null ? commonBean.getStatus() : 0)) {
                    mIView.fillWithNoData(errorInfo, invokeFlag);
                    return;
                }

                render(commonBean, invokeFlag);
            }

            @Override
            public void onFailure(@NonNull Call<CommonBean> call, @NonNull Throwable t) {
                mIView.fillWithNoData(t.getMessage(), invokeFlag);
                hideShowProgress();
            }
        });
        return this;
    }

    /**
     * 这个回调方法，针对在一个页面有多次调用不同接口的情况下
     * 需要输入一个调用标识
     *
     * @param commonBean
     * @param flag
     */
    private void render(CommonBean commonBean, int flag) {
        mIView.fillWithData(commonBean, flag);
    }

    public PresenterManager requestForResponseBody(int flag) {
        final int invokeFlag = flag;
        modelManager.setCall(null, mCall).getServerDataForResponseBody(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    renderForResponseBody(responseBody, invokeFlag);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mIView.fillWithNoData(t.getMessage(), invokeFlag);
            }
        });
        return this;
    }

    private void renderForResponseBody(ResponseBody responseBody, int flag) {
        if (responseBody == null) {
            mIView.fillWithNoData(errorInfo, flag);
            return;
        }
        try {
            String data = responseBody.string();
            if (TextUtils.isEmpty(data)) {
                mIView.fillWithNoData(errorInfo, flag);
                return;
            }
            mIView.fillWithData(data, flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
