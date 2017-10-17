package com.gk.model;

import com.gk.model.inter.IModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LectureModelImp implements IModel {

    public List<String> mList;

    public LectureModelImp(){
        setData();
    }

    @Override
    public void getData(Callback callback) {

    }

    public void setData(){
         mList = new ArrayList<>();
        for (int i= 0;i < 10; ++i){
            mList.add("测试" + i);
        }
    }
}
