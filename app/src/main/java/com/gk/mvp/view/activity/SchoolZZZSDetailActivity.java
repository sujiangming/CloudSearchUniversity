package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.SchoolZZZsBean;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.mvp.view.fragment.SchoolZZZSDetailBriefFragment;
import com.gk.mvp.view.fragment.SchoolZZZSDetailZsPlanFragment;
import com.gk.tools.GlideImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolZZZSDetailActivity extends SjmBaseActivity {
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
    @BindView(R.id.tv_school_type)
    RichText tvSchoolType;
    @BindView(R.id.tv_school_city)
    RichText tvSchoolCity;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.tv_zs_plan)
    TextView tvZsPlan;

    @OnClick({R.id.tv_brief, R.id.tv_zs_plan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_brief:
                changeNavStyle(view);
                break;
            case R.id.tv_zs_plan:
                changeNavStyle(view);
                break;
        }
    }
    private FragmentManager fragmentManager;
    private SchoolZZZSDetailBriefFragment schoolDetailBriefFragment = null;
    private SchoolZZZSDetailZsPlanFragment schoolDetailZsPlanFragment = null;
    private int index = 0;
    private TextView[] textViews;
    private SchoolZZZsBean uniName;
    private Bundle bundle = new Bundle();
    private GlideImageLoader imageLoader = new GlideImageLoader();

    @Override
    public int getResouceId() {
        return R.layout.activity_school_zzzs_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "高校详情", 0);
        uniName = (SchoolZZZsBean) getIntent().getSerializableExtra("uniName");
        bundle.putString("uniName", uniName.getUniversityName());
        bundle.putSerializable("schoolBean", uniName);
        initDataByQuery();
        textViews = new TextView[]{tvBrief, tvZsPlan};
        initFragments();
    }

    private void initDataByQuery() {
        imageLoader.displayImage(this, uniName.getUniversityLogo(), ivQueryItem);
        tvSchoolName.setText(uniName.getUniversityName());
        String area = uniName.getUniversityCity();
        if (area != null && !"".equals(area)) {
            tvSchoolCity.setText(area);
        }
        String schoolCategory = uniName.getUniversityCategory();
        if (schoolCategory != null && !"".equals(schoolCategory)) {
            tvSchoolType.setText(schoolCategory);
        }
        String isNef = uniName.getIsNef();
        if ("是".equals(isNef)) {
            tvSchoolMark0.setVisibility(View.VISIBLE);
        } else {
            tvSchoolMark0.setVisibility(View.GONE);
        }
        String isToo = uniName.getIsToo();
        if ("是".equals(isToo)) {
            tvSchoolMark1.setVisibility(View.VISIBLE);
        } else {
            tvSchoolMark1.setVisibility(View.GONE);
        }
        String isDouble = uniName.getIsDoubleTop();
        if ("是".equals(isDouble)) {
            tvSchoolMark2.setVisibility(View.VISIBLE);
        } else {
            tvSchoolMark2.setVisibility(View.GONE);
        }
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
                    schoolDetailBriefFragment = new SchoolZZZSDetailBriefFragment();
                    schoolDetailBriefFragment.setArguments(bundle);
                    transaction.add(R.id.ll_fragment_container, schoolDetailBriefFragment);
                } else {
                    transaction.show(schoolDetailBriefFragment);
                }

                break;
            case 1:
                if (null == schoolDetailZsPlanFragment) {
                    schoolDetailZsPlanFragment = new SchoolZZZSDetailZsPlanFragment();
                    schoolDetailZsPlanFragment.setArguments(bundle);
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
        if (null != schoolDetailZsPlanFragment) {
            transaction.hide(schoolDetailZsPlanFragment);
        }
    }
}
