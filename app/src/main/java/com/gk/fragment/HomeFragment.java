package com.gk.fragment;

import android.os.Bundle;
import android.view.View;

import com.gk.R;
import com.gk.custom.RichText;
import com.gk.tools.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY_SJM on 2017/4/20.
 */

public class HomeFragment extends SjmBaseFragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rtv_school_query)
    RichText rtvSchoolQuery;
    @BindView(R.id.rtv_score_query)
    RichText rtvScoreQuery;
    @BindView(R.id.rtv_choose_school)
    RichText rtvChooseSchool;
    @BindView(R.id.rtv_career_test)
    RichText rtvCareerTest;
    @BindView(R.id.rtv_risk)
    RichText rtvRisk;
    @BindView(R.id.rtv_same_score)
    RichText rtvSameScore;
    @BindView(R.id.rtv_same_rank)
    RichText rtvSameRank;
    @BindView(R.id.rtv_exam_skill)
    RichText rtvExamSkill;
    @BindView(R.id.rtv_famous_teacher)
    RichText rtvFamousTeacher;

//    @BindView(R.id.welcome)
//    LinearLayout welcomeLayout;

    @Override
    public int getResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initBanner();
    }

    private void initBanner() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.banner_buy_vip);
        imageList.add(R.drawable.banner_intelligent_wish);
        imageList.add(R.drawable.bao_zhang);
        banner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @OnClick({R.id.rtv_school_query, R.id.rtv_score_query, R.id.rtv_choose_school, R.id.rtv_career_test, R.id.rtv_risk, R.id.rtv_same_score, R.id.rtv_same_rank, R.id.rtv_exam_skill, R.id.rtv_famous_teacher})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rtv_school_query:
                //openNewActivity(QuerySchoolActivity.class);
                break;
            case R.id.rtv_score_query:
                //openNewActivity(ScoreQueryActivity.class);
                break;
            case R.id.rtv_choose_school:
                break;
            case R.id.rtv_career_test:
                break;
            case R.id.rtv_risk:
                break;
            case R.id.rtv_same_score:
                break;
            case R.id.rtv_same_rank:
                break;
            case R.id.rtv_exam_skill:
                break;
            case R.id.rtv_famous_teacher:
                //openNewActivity(FamousTeacherActivity.class);
                break;

        }
    }
}
