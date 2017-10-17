package com.gk.fragment;

import android.os.Bundle;

import com.gk.R;
import com.gk.activity.LoginActivity;

import butterknife.OnClick;


/**
 * Created by JDRY_SJM on 2017/4/20.
 */

public class UserFragment extends SjmBaseFragment {

    @Override
    public int getResourceId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ll_set)
    public void onViewClicked() {
        openNewActivity(LoginActivity.class);
    }
}
