package com.gk.mvp.view.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gk.mvp.view.fragment.LoginLeftFragment;
import com.gk.mvp.view.fragment.LoginRightFragment;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/9/19.
 */

public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> list;
    private int mEnterFlag;

    public LoginFragmentPagerAdapter(FragmentManager fm, List<String> list, int enterFlag) {
        super(fm);
        this.list = list;
        this.mEnterFlag = enterFlag;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LoginLeftFragment.newInstance(mEnterFlag);
        } else {
            return LoginRightFragment.newInstance(mEnterFlag);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
