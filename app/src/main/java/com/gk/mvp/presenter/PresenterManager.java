package com.gk.mvp.presenter;

import android.content.Context;

import com.gk.beans.CommonBean;
import com.gk.mvp.model.ModelManager;
import com.gk.mvp.model.inter.IModel;
import com.gk.mvp.view.IView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/11/2.
 */

public class PresenterManager {
    private IModel mModel;
    private IView mIView;
    private Context mContext;
    private static PresenterManager mInstance;
    private Call mCall;
    private int mOrder = 1;//默认每次只是调用一次接口

    private PresenterManager() {

    }

    public static PresenterManager getInstance() {
        if (mInstance == null) {
            synchronized (PresenterManager.class) {
                mInstance = new PresenterManager();
            }
        }
        return mInstance;
    }

    public IModel getmModel() {
        return mModel;
    }

    public PresenterManager setmModel(IModel mModel) {
        this.mModel = mModel;
        return mInstance;
    }

    public IView getmIView() {
        return mIView;
    }

    public PresenterManager setmIView(IView mIView) {
        this.mIView = mIView;
        return mInstance;
    }

    public Context getmContext() {
        return mContext;
    }

    public PresenterManager setmContext(Context mContext) {
        this.mContext = mContext;
        return mInstance;
    }

    public Call getCall() {
        return mCall;
    }

    public PresenterManager setCall(Call mCall) {
        this.mCall = mCall;
        return mInstance;
    }

    public PresenterManager isShowProgress(boolean isShow) {
        if (isShow) {
            mIView.showProgress();
        }
        return mInstance;
    }

    public PresenterManager setInvokeOrder(int order) {
        this.mOrder = order;
        return mInstance;
    }

    public PresenterManager request() {
        ModelManager.getInstance(mCall).getServerData(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                CommonBean commonBean = response.body();
                setNoDataByBean(commonBean);
                mIView.hideProgress();
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable t) {
                mIView.fillWithNoData(t.getMessage(), mOrder);
                mIView.hideProgress();
            }
        });
        return mInstance;
    }

    private void setNoDataByBean(CommonBean commonBean) {
        if (commonBean == null) {
            mIView.fillWithNoData("网络请求错误", mOrder);
            return;
        }
        if (commonBean.getStatus() == 1) {
            mIView.fillWithData(commonBean, mOrder);
        } else {
            mIView.fillWithNoData(commonBean.getMessage(), mOrder);
        }
    }
}
