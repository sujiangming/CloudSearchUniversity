package com.gk.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LectureBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.activity.CourseListActivity;
import com.gk.mvp.view.activity.MaterialListActivity;
import com.gk.mvp.view.activity.MaterialQueryActivity;
import com.gk.mvp.view.custom.RichText;
import com.gk.tools.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY_SJM on 2017/4/20.
 */

public class LectureFragment extends SjmBaseFragment {

    @BindView(R.id.top_bar_live)
    TextView topBarLive;
    @BindView(R.id.rtv_all)
    RichText rtvAll;
    @BindView(R.id.rtv_yuwen)
    RichText rtvYuwen;
    @BindView(R.id.rtv_shuxue)
    RichText rtvShuxue;
    @BindView(R.id.rtv_english)
    RichText rtvEnglish;
    @BindView(R.id.rtv_wz)
    RichText rtvWz;
    @BindView(R.id.rtv_lz)
    RichText rtvLz;
    @BindView(R.id.rtv_wuli)
    RichText rtvWuli;
    @BindView(R.id.rtv_huaxue)
    RichText rtvHuaxue;
    @BindView(R.id.rtv_shengwu)
    RichText rtvShengwu;
    @BindView(R.id.rtv_dili)
    RichText rtvDili;
    @BindView(R.id.rtv_lish)
    RichText rtvLish;
    @BindView(R.id.rtv_zhengzhi)
    RichText rtvZhengzhi;
    @BindView(R.id.ll_msjt)
    LinearLayout llMsjt;
    @BindView(R.id.ll_lszt)
    LinearLayout llLszt;
    @BindView(R.id.ll_mnsj)
    LinearLayout llMnsj;
    @BindView(R.id.iv_msjt_1)
    ImageView ivMsjt1;
    @BindView(R.id.tv_msjz_1)
    TextView tvMsjz1;
    @BindView(R.id.ll_msjt_1)
    LinearLayout llMsjt1;
    @BindView(R.id.iv_msjt_2)
    ImageView ivMsjt2;
    @BindView(R.id.tv_msjt_2)
    TextView tvMsjt2;
    @BindView(R.id.ll_msjt_2)
    LinearLayout llMsjt2;
    @BindView(R.id.ll_msjt_part_1)
    LinearLayout llMsjtPart1;
    @BindView(R.id.ll_msjt_3)
    LinearLayout llMsjt3;
    @BindView(R.id.iv_msjt_3)
    ImageView ivMsjt3;
    @BindView(R.id.tv_msjt_3)
    TextView tvMsjt3;
    @BindView(R.id.iv_msjt_4)
    ImageView ivMsjt4;
    @BindView(R.id.tv_msjt_4)
    TextView tvMsjt4;
    @BindView(R.id.ll_msjt_4)
    LinearLayout llMsjt4;
    @BindView(R.id.ll_msjt_part_2)
    LinearLayout llMsjtPart2;
    @BindView(R.id.iv_lszt_1)
    ImageView ivLszt1;
    @BindView(R.id.tv_lszt_1)
    TextView tvLszt1;
    @BindView(R.id.ll_lszt_1)
    LinearLayout llLszt1;
    @BindView(R.id.iv_lszt_2)
    ImageView ivLszt2;
    @BindView(R.id.tv_lszt_2)
    TextView tvLszt2;
    @BindView(R.id.ll_lszt_2)
    LinearLayout llLszt2;
    @BindView(R.id.ll_lszt_part_1)
    LinearLayout llLsztPart1;
    @BindView(R.id.iv_lszt_3)
    ImageView ivLszt3;
    @BindView(R.id.tv_lszt_3)
    TextView tvLszt3;
    @BindView(R.id.ll_lszt_3)
    LinearLayout llLszt3;
    @BindView(R.id.iv_lszt_4)
    ImageView ivLszt4;
    @BindView(R.id.tv_lszt_4)
    TextView tvLszt4;
    @BindView(R.id.ll_lszt_4)
    LinearLayout llLszt4;
    @BindView(R.id.ll_lszt_part_2)
    LinearLayout llLsztPart2;
    @BindView(R.id.iv_mnsj_1)
    ImageView ivMnsj1;
    @BindView(R.id.tv_mnsj_1)
    TextView tvMnsj1;
    @BindView(R.id.ll_mnsj_1)
    LinearLayout llMnsj1;
    @BindView(R.id.iv_mnsj_2)
    ImageView ivMnsj2;
    @BindView(R.id.tv_mnsj_2)
    TextView tvMnsj2;
    @BindView(R.id.ll_mnsj_2)
    LinearLayout llMnsj2;
    @BindView(R.id.ll_mnsj_part_1)
    LinearLayout llMnsjPart1;
    @BindView(R.id.iv_mnsj_3)
    ImageView ivMnsj3;
    @BindView(R.id.tv_mnsj_3)
    TextView tvMnsj3;
    @BindView(R.id.ll_mnsj_3)
    LinearLayout llMnsj3;
    @BindView(R.id.iv_mnsj_4)
    ImageView ivMnsj4;
    @BindView(R.id.tv_mnsj_4)
    TextView tvMnsj4;
    @BindView(R.id.ll_mnsj_4)
    LinearLayout llMnsj4;
    @BindView(R.id.ll_mnsj_part_2)
    LinearLayout llMnsjPart2;

    private List<LectureBean.LnztBean> lnzt;
    private List<LectureBean.MnsjBean> mnsj;
    private List<LectureBean.MsjtBean> msjt;

    private LinearLayout[] llMsjzList;
    private ImageView[] ivMsjtList;
    private TextView[] tvMsjzList;

    private LinearLayout[] llLiztList;
    private ImageView[] ivLiztList;
    private TextView[] tvLiztList;

    private LinearLayout[] llMnsjList;
    private ImageView[] ivMnsjList;
    private TextView[] tvMnsjList;

    @Override
    public int getResourceId() {
        return R.layout.fragment_lecture;
    }

    private JSONObject jsonObject = new JSONObject();
    private LectureBean lectureBean;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        invoke(0);
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        llMsjzList = new LinearLayout[]{llMsjt1, llMsjt2, llMsjt3, llMsjt4};
        ivMsjtList = new ImageView[]{ivMsjt1, ivMsjt2, ivMsjt3, ivMsjt4};
        tvMsjzList = new TextView[]{tvMsjz1, tvMsjt2, tvMsjt3, tvMsjt4};

        llLiztList = new LinearLayout[]{llLszt1, llLszt2, llLszt3, llLszt4};
        ivLiztList = new ImageView[]{ivLszt1, ivLszt2, ivLszt3, ivLszt4};
        tvLiztList = new TextView[]{tvLszt1, tvLszt2, tvLszt3, tvLszt4};

        llMnsjList = new LinearLayout[]{llMnsj1, llMnsj2, llMnsj3, llMnsj4};
        ivMnsjList = new ImageView[]{ivMnsj1, ivMnsj2, ivMnsj3, ivMnsj4};
        tvMnsjList = new TextView[]{tvMnsj1, tvMnsj2, tvMnsj3, tvMnsj4};
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            invoke(0);
//        }
//    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void invoke(int page) {
        jsonObject.put("page", page);
        jsonObject.put("course", "");
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsList(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_FORTH_TIME);
    }


    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                lectureBean = JSON.parseObject(commonBean.getData().toString(), LectureBean.class);
                initMsjt();
                initLszt();
                initMnsj();
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }

    private void initMsjt() {
        msjt = lectureBean.getMsjt();
        if (msjt == null) {
            return;
        }
        int msjtLen = msjt.size();
        if (msjtLen == 1) {
            llMsjtPart1.setVisibility(View.VISIBLE);
            llMsjzList[0].setVisibility(View.VISIBLE);
            String path = msjt.get(0).getLogo();
            GlideImageLoader.displayImage(getContext(), path, ivMsjtList[0]);
            tvMsjzList[0].setText(msjt.get(0).getName());
        } else if (msjtLen == 2) {
            for (int i = 0; i < msjtLen; ++i) {
                LectureBean.MsjtBean msjtBean = msjt.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivMsjtList[i]);
                tvMsjzList[i].setText(msjtBean.getName());
                llMsjzList[i].setVisibility(View.VISIBLE);
            }
            llMsjtPart1.setVisibility(View.VISIBLE);
        } else if (msjtLen == 3) {
            for (int i = 0; i < msjtLen; ++i) {
                LectureBean.MsjtBean msjtBean = msjt.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivMsjtList[i]);
                tvMsjzList[i].setText(msjtBean.getName());
                llMsjzList[i].setVisibility(View.VISIBLE);
            }
            llMsjtPart1.setVisibility(View.VISIBLE);
            llMsjtPart2.setVisibility(View.VISIBLE);
        } else if (msjtLen >= 4) {
            for (int i = 0; i < 4; ++i) {
                LectureBean.MsjtBean msjtBean = msjt.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivMsjtList[i]);
                tvMsjzList[i].setText(msjtBean.getName());
                llMsjzList[i].setVisibility(View.VISIBLE);
            }
            llMsjtPart1.setVisibility(View.VISIBLE);
            llMnsjPart2.setVisibility(View.VISIBLE);
        }
    }

    private void initLszt() {
        lnzt = lectureBean.getLnzt();
        if (lnzt == null) {
            return;
        }
        int msjtLen = lnzt.size();
        if (msjtLen == 1) {
            llLsztPart1.setVisibility(View.VISIBLE);
            llLiztList[0].setVisibility(View.VISIBLE);
            String path = lnzt.get(0).getLogo();
            GlideImageLoader.displayImage(getContext(), path, ivLiztList[0]);
            tvLiztList[0].setText(lnzt.get(0).getName());
        } else if (msjtLen == 2) {
            for (int i = 0; i < msjtLen; ++i) {
                LectureBean.LnztBean msjtBean = lnzt.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivLiztList[i]);
                tvLiztList[i].setText(msjtBean.getName());
                llLiztList[i].setVisibility(View.VISIBLE);
            }
            llLsztPart1.setVisibility(View.VISIBLE);
        } else if (msjtLen == 3) {
            for (int i = 0; i < msjtLen; ++i) {
                LectureBean.LnztBean msjtBean = lnzt.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivLiztList[i]);
                tvLiztList[i].setText(msjtBean.getName());
                llLiztList[i].setVisibility(View.VISIBLE);
            }
            llLsztPart1.setVisibility(View.VISIBLE);
            llLsztPart2.setVisibility(View.VISIBLE);
        } else if (msjtLen >= 4) {
            for (int i = 0; i < 4; ++i) {
                LectureBean.LnztBean msjtBean = lnzt.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivLiztList[i]);
                tvLiztList[i].setText(msjtBean.getName());
                llLiztList[i].setVisibility(View.VISIBLE);
            }
            llLsztPart1.setVisibility(View.VISIBLE);
            llLsztPart2.setVisibility(View.VISIBLE);
        }
    }

    private void initMnsj() {
        mnsj = lectureBean.getMnsj();
        if (mnsj == null) {
            return;
        }
        int msjtLen = mnsj.size();
        if (msjtLen == 1) {
            llMnsjPart1.setVisibility(View.VISIBLE);
            llMnsjList[0].setVisibility(View.VISIBLE);
            String path = mnsj.get(0).getLogo();
            GlideImageLoader.displayImage(getContext(), path, ivMnsjList[0]);
            tvMnsjList[0].setText(mnsj.get(0).getName());
        } else if (msjtLen == 2) {
            for (int i = 0; i < msjtLen; ++i) {
                LectureBean.MnsjBean msjtBean = mnsj.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivMnsjList[i]);
                tvMnsjList[i].setText(msjtBean.getName());
                llMnsjList[i].setVisibility(View.VISIBLE);
            }
            llMnsjPart1.setVisibility(View.VISIBLE);
        } else if (msjtLen == 3) {
            for (int i = 0; i < msjtLen; ++i) {
                LectureBean.MnsjBean msjtBean = mnsj.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivMnsjList[i]);
                tvMnsjList[i].setText(msjtBean.getName());
                llMnsjList[i].setVisibility(View.VISIBLE);
            }
            llMnsjPart1.setVisibility(View.VISIBLE);
            llMnsjPart2.setVisibility(View.VISIBLE);
        } else if (msjtLen >= 4) {
            for (int i = 0; i < 4; ++i) {
                LectureBean.MnsjBean msjtBean = mnsj.get(i);
                String path = msjtBean.getLogo();
                GlideImageLoader.displayImage(getContext(), path, ivMnsjList[i]);
                tvMnsjList[i].setText(msjtBean.getName());
                llMnsjList[i].setVisibility(View.VISIBLE);
            }
            llMnsjPart1.setVisibility(View.VISIBLE);
            llMnsjPart2.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ll_search, R.id.rtv_all, R.id.rtv_yuwen,
            R.id.rtv_shuxue, R.id.rtv_english, R.id.rtv_wz,
            R.id.rtv_lz, R.id.rtv_wuli, R.id.rtv_huaxue,
            R.id.rtv_shengwu, R.id.rtv_dili, R.id.rtv_lish,
            R.id.rtv_zhengzhi, R.id.ll_msjt, R.id.ll_msjt_part_1, R.id.ll_msjt_part_2,
            R.id.ll_lszt, R.id.ll_lszt_part_1, R.id.ll_lszt_part_2,
            R.id.ll_mnsj, R.id.ll_mnsj_part_1, R.id.ll_mnsj_part_2})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_search:
                openNewActivity(MaterialQueryActivity.class);
                break;
            case R.id.rtv_all:
                intent.putExtra("course", "all");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_yuwen:
                intent.putExtra("course", "yuwen");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_shuxue:
                intent.putExtra("course", "shuxue");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_english:
                intent.putExtra("course", "yingyu");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_wz:
                intent.putExtra("course", "wenzong");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_lz:
                intent.putExtra("course", "lizong");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_wuli:
                intent.putExtra("course", "wuli");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_huaxue:
                intent.putExtra("course", "huaxue");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_shengwu:
                intent.putExtra("course", "shengwu");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_dili:
                intent.putExtra("course", "dili");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_lish:
                intent.putExtra("course", "lishi");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.rtv_zhengzhi:
                intent.putExtra("course", "zhengzhi");
                openNewActivityByIntent(CourseListActivity.class, intent);
                break;
            case R.id.ll_msjt:
            case R.id.ll_msjt_part_1:
            case R.id.ll_msjt_part_2:
                intent.putExtra("type", 1);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case R.id.ll_lszt:
            case R.id.ll_lszt_part_1:
            case R.id.ll_lszt_part_2:
                intent.putExtra("type", 2);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
            case R.id.ll_mnsj:
            case R.id.ll_mnsj_part_1:
            case R.id.ll_mnsj_part_2:
                intent.putExtra("type", 3);
                intent.putExtra("course", "");
                openNewActivityByIntent(MaterialListActivity.class, intent);
                break;
        }
    }
}
