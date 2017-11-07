package com.gk.mvp.presenter;

import android.content.Context;

import com.gk.mvp.view.IView;
import com.gk.mvp.model.LectureModelImp;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LecturePresenter {
    public LectureModelImp miModel;
    public IView miView;
    public Context mContext;

    public LecturePresenter(Context context, IView iView){
        this.mContext = context;
        this.miModel = new LectureModelImp();
        this.miView = iView;
    }

    public void initData(){
        List<String> stringList = miModel.mList;
        miView.showProgress();
        miView.fillWithData(stringList,1);
        miView.hideProgress();
    }
}
