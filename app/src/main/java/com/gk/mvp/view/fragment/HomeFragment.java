package com.gk.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gk.R;
import com.gk.beans.AdsBean;
import com.gk.mvp.view.activity.HldInterestActivity;
import com.gk.mvp.view.activity.IntelligentActivity;
import com.gk.mvp.view.activity.LqRiskActivity;
import com.gk.mvp.view.activity.MBTIActivity;
import com.gk.mvp.view.activity.MainActivity;
import com.gk.mvp.view.activity.MaterialListActivity;
import com.gk.mvp.view.activity.OnLiveRoomActivity;
import com.gk.mvp.view.activity.ProfessionalQueryActivity;
import com.gk.mvp.view.activity.QWActivity;
import com.gk.mvp.view.activity.QuerySchoolActivity;
import com.gk.mvp.view.activity.SameScoreActivity;
import com.gk.mvp.view.activity.SchoolRankActivity;
import com.gk.mvp.view.activity.SchoolZSPlanActivity;
import com.gk.mvp.view.activity.VIPActivity;
import com.gk.mvp.view.activity.WishReportEnterActivity;
import com.gk.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

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

    @Override
    public int getResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initBanner();
    }

    private void initBanner() {
        List<AdsBean.MDataBean> mDataBeans = AdsBean.getInstance().getShouYeAds();
        if (mDataBeans == null || mDataBeans.size() == 0) {
            return;
        }
        final List<String> imageList = new ArrayList<>();
        final List<String> imageNameList = new ArrayList<>();
        final List<String> imageRedirectUrlList = new ArrayList<>();
        for (int i = 0; i < mDataBeans.size(); i++) {
            imageList.add(mDataBeans.get(i).getUrl());
            imageNameList.add(mDataBeans.get(i).getName());
            imageRedirectUrlList.add(mDataBeans.get(i).getRedirectUrl());
        }
        banner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                goActivityByRedirectUrl(imageRedirectUrlList.get(position));
            }
        });
    }

    private void goActivityByRedirectUrl(String redirectUr) {
        Intent intent = new Intent();
        switch (redirectUr) {
            case "schoolList"://大学列表
                openNewActivityByIntent(QuerySchoolActivity.class, intent);
                break;
            case "schoolVideoList"://视频列表
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeNavStyle(mainActivity.getLlLesson());
                break;
            case "pastQuestion"://历史真题
                intent.putExtra("type", 2);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case "simulationQuestion"://模拟试卷
                intent.putExtra("type", 3);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case "teacherRoom"://名师讲堂
                intent.putExtra("type", 1);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case "applicationPaper"://志愿报告
                openNewActivity(WishReportEnterActivity.class);
                break;
        }
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

    @OnClick({R.id.rtv_school_query, R.id.rtv_score_query, R.id.rtv_choose_school, R.id.rtv_rank_school,
            R.id.rtv_vip, R.id.rtv_same_score, R.id.rtv_same_rank, R.id.rtv_wish_report,
            R.id.rtv_famous_teacher, R.id.rtv_interest, R.id.rtv_lq_risk, R.id.rtv_qw, R.id.rtv_gaokao_tiku, R.id.rtv_on_live})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rtv_school_query:
                openNewActivity(QuerySchoolActivity.class);
                break;
            case R.id.rtv_score_query:
                openNewActivity(ProfessionalQueryActivity.class);
                break;
            case R.id.rtv_choose_school:
                openNewActivity(SchoolZSPlanActivity.class);
                break;
            case R.id.rtv_rank_school:
                openNewActivity(SchoolRankActivity.class);
                break;
            case R.id.rtv_vip:
                openNewActivity(VIPActivity.class);
                break;
            case R.id.rtv_wish_report:
                openNewActivity(WishReportEnterActivity.class);
                break;
            case R.id.rtv_same_rank:
                openNewActivity(IntelligentActivity.class);
                break;
            case R.id.rtv_same_score:
                openNewActivity(SameScoreActivity.class);
                break;
            case R.id.rtv_famous_teacher:
                openNewActivity(MBTIActivity.class);
                break;
            case R.id.rtv_interest:
                openNewActivity(HldInterestActivity.class);
                break;
            case R.id.rtv_lq_risk:
                openNewActivity(LqRiskActivity.class);
                break;
            case R.id.rtv_qw:
                openNewActivity(QWActivity.class);
                break;
            case R.id.rtv_gaokao_tiku:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeNavStyle(mainActivity.getLlLesson());
                break;
            case R.id.rtv_on_live:
                openNewActivity(OnLiveRoomActivity.class);
                break;

        }
    }
}
