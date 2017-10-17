package com.gk.presenter;

import android.content.Context;

import com.gk.IView;
import com.gk.model.LiveVideoModelImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LiveVideoPresenter {
    public LiveVideoModelImp waitModel;
    public IView iView;
    public Context context;
    public int pageNum;
    public List<String> mListData;

    public LiveVideoPresenter(Context context, IView view) {
        this.context = context;
        this.iView = view;
        this.waitModel = new LiveVideoModelImp(pageNum);
        initData();
    }

    public void getData(int pageNum) {
        this.waitModel.pageCount = pageNum;
        //this.iView.showProgress();
        iView.fillWithData(mListData);
//        this.waitModel.getData(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                iView.hideProgress();
//                iView.fillWithData(mListData);
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                iView.fillWithNoData(false);
//                iView.hideProgress();
//            }
//        });
    }

    public List<String> initData(){
        List<String> stringList = new ArrayList<>();
        for(int i=0; i < 10; ++i){
            stringList.add("测试" + i);
        }
        mListData = stringList;
        return mListData;
    }
}
