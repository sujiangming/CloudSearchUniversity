package com.gk.mvp.presenter;

import android.content.Context;

import com.gk.mvp.view.IView;
import com.gk.mvp.model.LiveVideoModelImp;

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
        iView.fillWithData(mListData, 1);
    }

    public List<String> initData() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            stringList.add("测试" + i);
        }
        mListData = stringList;
        return mListData;
    }
}
