package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.SchoolRankBean;
import com.gk.beans.UniversityTypeBean;
import com.gk.beans.UniversityTypeBeanDao;
import com.gk.global.YXXApplication;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.mvp.view.fragment.SchoolDetailBriefFragment;
import com.gk.mvp.view.fragment.SchoolDetailLqDataFragment;
import com.gk.mvp.view.fragment.SchoolDetailZsPlanFragment;
import com.gk.tools.GlideImageLoader;

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
    private QuerySchoolBean.DataBean uniName;
    private Bundle bundle = new Bundle();
    private SchoolRankBean schoolRankBean;
    private SparseArray<String> sparseArray = new SparseArray<>();

    @Override
    public int getResouceId() {
        return R.layout.activity_school_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "高校详情", 0);
        initSparseArray();
        String flagStr = getIntent().getStringExtra("flag");
        if (flagStr != null && "query".equals(flagStr)) {
            uniName = (QuerySchoolBean.DataBean) getIntent().getSerializableExtra("uniName");
            bundle.putString("uniName", uniName.getSchoolName());
            bundle.putSerializable("schoolBean", uniName);
            bundle.putString("flag", flagStr);
            initDataByQuery();
        } else {
            schoolRankBean = (SchoolRankBean) getIntent().getSerializableExtra("uniName");
            bundle.putString("uniName", schoolRankBean.getSchoolName());
            bundle.putSerializable("schoolBean", schoolRankBean);
            bundle.putString("flag", flagStr);
            initDataByRank();
        }
        textViews = new TextView[]{tvBrief, tvLuquData, tvZsPlan};
        initFragments();
    }

    private void initSparseArray() {
        sparseArray.put(0, "不限");
        sparseArray.put(1, "北京");
        sparseArray.put(2, "天津");
        sparseArray.put(3, "上海");
        sparseArray.put(4, "重庆");
        sparseArray.put(5, "河北");
        sparseArray.put(6, "山西");
        sparseArray.put(7, "辽宁");
        sparseArray.put(8, "吉林");
        sparseArray.put(9, "黑龙江");
        sparseArray.put(10, "江苏");
        sparseArray.put(11, "浙江");
        sparseArray.put(12, "安徽");
        sparseArray.put(13, "福建");
        sparseArray.put(14, "江西");
        sparseArray.put(15, "山东");
        sparseArray.put(16, "河南");
        sparseArray.put(17, "湖北");
        sparseArray.put(18, "湖南");
        sparseArray.put(19, "广东");
        sparseArray.put(20, "海南");
        sparseArray.put(21, "四川");
        sparseArray.put(22, "贵州");
        sparseArray.put(23, "云南");
        sparseArray.put(24, "陕西");
        sparseArray.put(25, "甘肃");
        sparseArray.put(26, "青海");
        sparseArray.put(27, "台湾");
        sparseArray.put(28, "蒙古");
        sparseArray.put(29, "广西");
        sparseArray.put(30, "西藏");
        sparseArray.put(31, "宁夏");
        sparseArray.put(32, "新疆");
        sparseArray.put(33, "香港");
        sparseArray.put(34, "澳门");
    }

    private void initDataByQuery() {
        GlideImageLoader.displayImage(this, uniName.getSchoolLogo(), ivQueryItem);
        tvSchoolName.setText(uniName.getSchoolName());
        String area = uniName.getSchoolArea();
        if (area != null && !"".equals(area)) {
            tvSchoolCity.setText(sparseArray.get(Integer.valueOf(area)));
        }
        String schoolCategory = uniName.getSchoolCategory();
        if (!TextUtils.isEmpty(schoolCategory)) {
            UniversityTypeBean universityTypeBean = YXXApplication.getDaoSession().getUniversityTypeBeanDao().queryBuilder().where(UniversityTypeBeanDao.Properties.Index.eq(schoolCategory)).unique();
            if (null != universityTypeBean) {
                tvSchoolType.setText(universityTypeBean.getName());
            }
        }
        int isNef = uniName.getIsNef();
        if (isNef == 1) {
            tvSchoolMark0.setVisibility(View.VISIBLE);
        } else {
            tvSchoolMark0.setVisibility(View.GONE);
        }
        int isToo = uniName.getIsToo();
        if (isToo == 1) {
            tvSchoolMark1.setVisibility(View.VISIBLE);
        } else {
            tvSchoolMark1.setVisibility(View.GONE);
        }
        int isDouble = uniName.getIsDoubleTop();
        if (isDouble == 1) {
            tvSchoolMark2.setVisibility(View.VISIBLE);
        } else {
            tvSchoolMark2.setVisibility(View.GONE);
        }
    }

    private void initDataByRank() {
        GlideImageLoader.displayImage(this, schoolRankBean.getSchoolLogo(), ivQueryItem);
        tvSchoolName.setText(schoolRankBean.getSchoolName());
        String area = schoolRankBean.getSchoolArea();
        if (area != null && !"".equals(area)) {
            tvSchoolCity.setText(sparseArray.get(Integer.valueOf(area)));
        }
        String schoolCategory = schoolRankBean.getSchoolCategory();
        if (schoolCategory != null && !"".equals(schoolCategory)) {
            UniversityTypeBean universityTypeBean = YXXApplication.getDaoSession().getUniversityTypeBeanDao().queryBuilder().where(UniversityTypeBeanDao.Properties.Index.eq(schoolCategory)).unique();
            if (null != universityTypeBean) {
                tvSchoolType.setText(universityTypeBean.getName());
            }
        }
        String isNef = schoolRankBean.getIsNef();
        if (isNef != null && !"".equals(isNef) && "1".equals(isNef)) {
            tvSchoolMark0.setVisibility(View.VISIBLE);
        }
        String isToo = schoolRankBean.getIsToo();
        if (isToo != null && !"".equals(isToo) && "1".equals(isToo)) {
            tvSchoolMark1.setVisibility(View.VISIBLE);
        }
        String isDouble = schoolRankBean.getIsDoubleTop();
        if (isDouble != null && !"".equals(isDouble) && "1".equals(isDouble)) {
            tvSchoolMark2.setVisibility(View.VISIBLE);
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
                    schoolDetailBriefFragment = new SchoolDetailBriefFragment();
                    schoolDetailBriefFragment.setArguments(bundle);
                    transaction.add(R.id.ll_fragment_container, schoolDetailBriefFragment);
                } else {
                    transaction.show(schoolDetailBriefFragment);
                }

                break;
            case 1:
                if (null == schoolDetailLqDataFragment) {
                    schoolDetailLqDataFragment = new SchoolDetailLqDataFragment();
                    schoolDetailLqDataFragment.setArguments(bundle);
                    transaction.add(R.id.ll_fragment_container, schoolDetailLqDataFragment);
                } else {
                    transaction.show(schoolDetailLqDataFragment);
                }
                break;
            case 2:
                if (null == schoolDetailZsPlanFragment) {
                    schoolDetailZsPlanFragment = new SchoolDetailZsPlanFragment();
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
        if (null != schoolDetailLqDataFragment) {
            transaction.hide(schoolDetailLqDataFragment);
        }
        if (null != schoolDetailZsPlanFragment) {
            transaction.hide(schoolDetailZsPlanFragment);
        }
    }

}
