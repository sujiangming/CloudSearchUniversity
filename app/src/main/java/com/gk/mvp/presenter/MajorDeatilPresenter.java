package com.gk.mvp.presenter;

import com.gk.beans.MajorInfoBean;
import com.gk.global.YXXConstants;
import com.gk.mvp.model.MajorDetailModel;
import com.gk.mvp.view.IView;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/11/27.
 */

public class MajorDeatilPresenter<T> implements IPresenterCallback<T> {
    private IView iView;
    private MajorDetailModel majorDetailModel;

    public MajorDeatilPresenter(IView iView) {
        this.iView = iView;
        majorDetailModel = new MajorDetailModel();
    }

    public void httpRequest(String pid) {
        majorDetailModel.httpRequest(this, pid);
    }

    @Override
    public void httpRequestSuccess(T o, int order) {
        List<MajorInfoBean.DataBean> bzTypeList = (List<MajorInfoBean.DataBean>) o;
        this.iView.fillWithData(bzTypeList, YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public void httpRequestFailure(T o, int order) {
        this.iView.fillWithNoData(o, YXXConstants.INVOKE_API_DEFAULT_TIME);
    }
}
