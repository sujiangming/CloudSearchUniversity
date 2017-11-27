package com.gk.mvp.presenter;

import com.gk.beans.MaterialItemBean;
import com.gk.mvp.model.MaterialModel;
import com.gk.mvp.view.IView;

/**
 * Created by JDRY-SJM on 2017/11/26.
 */

public class MaterialPresenter implements IPresenterCallback<MaterialItemBean> {

    private IView iView;
    private MaterialModel materialModel;

    public MaterialPresenter(IView iView) {
        this.iView = iView;
        this.materialModel = new MaterialModel(this);
    }

    public void httpRequestMaterialsByType(int page, int type, String course) {
        materialModel.httpRequestMaterialsByType(page, type, course);
    }

    @Override
    public void httpRequestSuccess(MaterialItemBean materialItemBean, int order) {
        iView.fillWithData(materialItemBean.getData(), order);
    }

    @Override
    public void httpRequestFailure(MaterialItemBean materialItemBean, int order) {
        iView.fillWithNoData(materialItemBean, order);
    }


}
