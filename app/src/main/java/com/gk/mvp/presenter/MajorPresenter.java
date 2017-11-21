package com.gk.mvp.presenter;

import com.gk.beans.MajorTypeBean;
import com.gk.mvp.model.MajorModel;
import com.gk.mvp.view.IView;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public class MajorPresenter<T> implements IPresenterCallback<T> {
    private IView iView;
    private MajorModel majorModel;

    public MajorPresenter(IView view) {
        this.iView = view;
        this.iView.showProgress();
        majorModel = new MajorModel(this);
    }

    @Override
    public void httpRequestSuccess(T t, int order) {
        List<MajorTypeBean> majorTypeBeanList = (List<MajorTypeBean>) t;
        iView.fillWithData(majorTypeBeanList, order);
        iView.hideProgress();
    }

    @Override
    public void httpRequestFailure(T t, int order) {
        iView.hideProgress();
    }
}
