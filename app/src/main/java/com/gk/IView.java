package com.gk;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

/**
 * Created by JDRY_SJM on 2017/4/17.
 */

public interface IView {
    void showProgress();

    void hideProgress();

    void openNewActivity(Class<?> cls, Bundle bundle);

    void openNewActivityByIntent(Class<?> cls, Intent intent);

    void openNewActivity(Class<?> cls);

    void closeActivity();

    <T> void fillWithNoData(T t);

    <T> void fillWithData(List<T> list);
}
