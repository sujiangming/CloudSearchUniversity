package com.gk.mvp.view;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public interface IView {
    void showProgress();

    void hideProgress();

    <T> void fillWithNoData(T t, int order);

    <T> void fillWithData(T t, int order);
}
