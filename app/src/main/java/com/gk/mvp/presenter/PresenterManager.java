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
    private boolean mIsShow = false;
    private String errorInfo = "No Data Return";

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
            this.mIsShow = isShow;
            mIView.showProgress();
        }
        return mInstance;
    }

    public void hideShowProgress() {
        if (this.mIsShow) {
            mIView.hideProgress();
        }
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
                render(commonBean);
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable t) {
                mIView.fillWithNoData(t.getMessage(), mOrder);
                hideShowProgress();
            }
        });
        return mInstance;
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
        ModelManager.getInstance(mCall).getServerData(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                CommonBean commonBean = response.body();
                render(commonBean, invokeFlag);
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable t) {
                mIView.fillWithNoData(t.getMessage(), invokeFlag);
                hideShowProgress();
            }
        });
        return mInstance;
    }

    /**
     * 这个回调方法，针对在一个页面有多次调用不同接口的情况下
     * 需要输入一个调用标识
     *
     * @param commonBean
     * @param flag
     */
    private void render(CommonBean commonBean, int flag) {
        if (commonBean == null) {
            mIView.fillWithNoData(errorInfo, flag);
            return;
        }
        if (commonBean.getStatus() == 1) {
            mIView.fillWithData(commonBean, flag);
        } else {
            mIView.fillWithNoData(commonBean.getMessage(), flag);
        }
    }

    private void render(CommonBean commonBean) {
        if (commonBean == null) {
            mIView.fillWithNoData(errorInfo, mOrder);
            return;
        }
        if (commonBean.getStatus() == 1) {
            mIView.fillWithData(commonBean, mOrder);
        } else {
            mIView.fillWithNoData(commonBean.getMessage(), mOrder);
        }
    }
}