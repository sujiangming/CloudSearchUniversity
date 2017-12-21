package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
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
import butterknife.OnClick;

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


    @OnClick({R.id.tv_brief, R.id.tv_luqu_data, R.id.tv_zs_plan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_brief:
                changeNavStyle(view);
                break;
            case R.id.tv_luqu_data:
                changeNavStyle(view);
                break;
            case R.id.tv_zs_plan:
                changeNavStyle(view);
                break;
        }
    }

    private FragmentManager fragmentManager;
    private SchoolDetailBriefFragment schoolDetailBriefFragment = null;
    private SchoolDetailLqDataFragment schoolDetailLqDataFragment = null;
    private SchoolDetailZsPlanFragment schoolDetailZsPlanFragment = null;
    private int index = 0;
    private TextView[] textViews;

    @Override
    public int getResouceId() {
        return R.layout.activity_school_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "高校详情", 0);
        textViews = new TextView[]{tvBrief, tvLuquData, tvZsPlan};
        initFragments();
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        changeFragment(index);//显示主页
    }

    public void changeNavStyle(View view) {
        String tag = (String) view.getTag();
        index = Integer.parseInt(tag);
        changeFragment(index);
        changeTvColor(index);
    }

    private void changeTvColor(int index) {
        textViews[index].setTextColor(0xFF3AB1D6);
        for (int i = 0; i < textViews.length; i++) {
            if (i != index) {
                textViews[i].setTextColor(0xFF808080);
            }
        }
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
