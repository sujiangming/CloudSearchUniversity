package com.gk.mvp.presenter;

import com.gk.beans.MajorBean;
import com.gk.beans.MajorQueryBean;
import com.gk.mvp.model.MajorModel;
import com.gk.mvp.view.IView;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/22.
 */

public class MajorPresenter<T> implements IPresenterCallback<T> {
    private IView iView;
    private MajorModel majorModel;
    public MajorBean majorBean;

    public MajorPresenter(IView view) {
        this.iView = view;
        majorModel = new MajorModel(this);
    }

    public void getMajorTypeList() {
        majorModel.httpRequest();
    }

    public void queryMajorByName(String name, int type) {
        majorModel.httpQueryRequest(name, type, this);
    }

    @Override
    public void httpRequestSuccess(T t, int order) {
        switch (order) {
            case 1:
                List<List<MajorBean.DataBean.NodesBeanXX>> listList = (List<List<MajorBean.DataBean.NodesBeanXX>>) t;
                iView.fillWithData(listList, order);
                break;
            case 2:
                List<MajorQueryBean.DataBean> dataBeans = (List<MajorQueryBean.DataBean>) t;
                iView.fillWithData(dataBeans, order);
                break;
        }
        iView.hideProgress();
    }

    @Override
    public void httpRequestFailure(T t, int order) {
        iView.hideProgress();
    }
}
