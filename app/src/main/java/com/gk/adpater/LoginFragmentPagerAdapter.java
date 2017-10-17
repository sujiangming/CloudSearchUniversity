package com.gk.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gk.fragment.LoginLeftFragment;
import com.gk.fragment.LoginRightFragment;

import java.util.List;

/**
 * Created by JDRY-SJM on 2017/9/19.
 */

public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> list;

    public LoginFragmentPagerAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LoginLeftFragment.newInstance();
        } else {
            return LoginRightFragment.newInstance();
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
