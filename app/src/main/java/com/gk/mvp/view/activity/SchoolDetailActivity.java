package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.mvp.view.fragment.SchoolDetailBriefFragment;
import com.gk.mvp.view.fragment.SchoolDetailLqDataFragment;
import com.gk.mvp.view.fragment.SchoolDetailZsPlanFragment;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolDetailActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.iv_query_item)
    CircleImageView ivQueryItem;
    @BindView(R.id.tv_school_name)
    TextView tvSchoolName;
    @BindView(R.id.tv_school_mark_0)
    TextView tvSchoolMark0;
    @BindView(R.id.tv_school_mark_1)
    TextView tvSchoolMark1;
    @BindView(R.id.tv_school_mark_2)
    TextView tvSchoolMark2;
    @BindView(R.id.ll_shool_mark)
    LinearLayout llShoolMark;
    @BindView(R.id.tv_school_type)
    RichText tvSchoolType;
    @BindView(R.id.tv_school_city)
    RichText tvSchoolCity;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.tv_luqu_data)
    TextView tvLuquData;
    @BindView(R.id.tv_zs_plan)
    TextView tvZsPlan;

    private FragmentManager fragmentManager;
    private SchoolDetailBriefFragment schoolDetailBriefFragment = null;
    private SchoolDetailLqDataFragment schoolDetailLqDataFragment = null;
    private SchoolDetailZsPlanFragment schoolDetailZsPlanFragment = null;
    private int index = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_school_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "高校详情", 0);
        initFragments();
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        changeFragment(index);//显示主页
    }

    private void changeFragment(int indexTmp) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (indexTmp) {
            case 0:
                if (null == schoolDetailBriefFragment) {
                    schoolDetailBriefFragment = new SchoolDetailBriefFragment();
                    transaction.add(R.id.ll_fragment_container, schoolDetailBriefFragment);
                } else {
                    transaction.show(schoolDetailBriefFragment);
                }

                break;
            case 1:
                if (null == schoolDetailLqDataFragment) {
                    schoolDetailLqDataFragment = new SchoolDetailLqDataFragment();
                    transaction.add(R.id.ll_fragment_container, schoolDetailLqDataFragment);
                } else {
                    transaction.show(schoolDetailLqDataFragment);
                }
                break;
            case 2:
                if (null == schoolDetailZsPlanFragment) {
                    schoolDetailZsPlanFragment = new SchoolDetailZsPlanFragment();
                    transaction.add(R.id.ll_fragment_container, schoolDetailZsPlanFragment);
                } else {
                    transaction.show(schoolDetailZsPlanFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != schoolDetailBriefFragment) {
            transaction.hide(schoolDetailBriefFragment);
        }
        if (null != schoolDetailLqDataFragment) {
            transaction.hide(schoolDetailLqDataFragment);
        }
        if (null != schoolDetailZsPlanFragment) {
            transaction.hide(schoolDetailZsPlanFragment);
        }
    }
}
