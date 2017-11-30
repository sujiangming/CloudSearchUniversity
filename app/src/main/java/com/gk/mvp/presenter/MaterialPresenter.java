package com.gk.mvp.presenter;

import com.gk.mvp.model.MaterialModel;
import com.gk.mvp.view.IView;

/**
 * Created by JDRY-SJM on 2017/11/26.
 */

public class MaterialPresenter<MaterialItemBean> implements IPresenterCallback<MaterialItemBean> {

    private IView iView;
    private MaterialModel materialModel;

    public MaterialPresenter(IView iView) {
        this.iView = iView;
        this.materialModel = new MaterialModel(this);
    }

    public void httpRequestMaterialsByCourse(int page, String course) {
        this.iView.showProgress();
        materialModel.httpRequestMaterialsByCourse(page, course);
    }

    @Override
    public void httpRequestSuccess(MaterialItemBean o, int order) {
        this.iView.hideProgress();
        iView.fillWithData(o, order);
    }

    @Override
    public void httpRequestFailure(MaterialItemBean o, int order) {
        this.iView.hideProgress();
        iView.fillWithNoData(o, order);
    }
}
