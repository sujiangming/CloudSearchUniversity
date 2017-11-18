package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.RichText;

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

    @Override
    public int getResourceId() {
        return R.layout.fragment_lecture;
    }

    private JSONObject jsonObject = new JSONObject();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        invoke(0);
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {

    }

    private void invoke(int page) {
        jsonObject.put("page", page);
        jsonObject.put("course", "");
        PresenterManager.getInstance()
                .setmContext(getContext())
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsList(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {

    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }

    @Override
    public void loadMore(int pageNum) {

    }

    @Override
    public void refresh(int pageNum) {

    }

    @OnClick({R.id.rtv_all, R.id.rtv_yuwen, R.id.rtv_shuxue, R.id.rtv_english, R.id.rtv_wz, R.id.rtv_lz, R.id.rtv_wuli, R.id.rtv_huaxue, R.id.rtv_shengwu, R.id.rtv_dili, R.id.rtv_lish, R.id.rtv_zhengzhi, R.id.ll_msjt, R.id.ll_lszt, R.id.ll_mnsj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rtv_all:
                break;
            case R.id.rtv_yuwen:
                break;
            case R.id.rtv_shuxue:
                break;
            case R.id.rtv_english:
                break;
            case R.id.rtv_wz:
                break;
            case R.id.rtv_lz:
                break;
            case R.id.rtv_wuli:
                break;
            case R.id.rtv_huaxue:
                break;
            case R.id.rtv_shengwu:
                break;
            case R.id.rtv_dili:
                break;
            case R.id.rtv_lish:
                break;
            case R.id.rtv_zhengzhi:
                break;
            case R.id.ll_msjt:
                jsonObject.put("type", 1);
                PresenterManager.getInstance()
                        .setmContext(getContext())
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsByType(jsonObject.toJSONString()))
                        .request();
                break;
            case R.id.ll_lszt:
                jsonObject.put("type", 2);
                PresenterManager.getInstance()
                        .setmContext(getContext())
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsByType(jsonObject.toJSONString()))
                        .request();
                break;
            case R.id.ll_mnsj:
                jsonObject.put("type", 3);
                PresenterManager.getInstance()
                        .setmContext(getContext())
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsByType(jsonObject.toJSONString()))
                        .request();
                break;
        }
    }
}
