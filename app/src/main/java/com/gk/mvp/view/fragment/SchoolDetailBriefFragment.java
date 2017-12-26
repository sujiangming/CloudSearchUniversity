package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolDetailBriefFragment extends SjmBaseFragment {

    @BindView(R.id.expand_text_1)
    View expand_text_1;

    @BindView(R.id.expand_text_2)
    View expand_text_2;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_detail_brief;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        String uniName = getArguments().getString("uniName");
        Log.e(SchoolDetailBriefFragment.class.getName(), uniName);
        initData();
    }

    private void getUniversityInfoByName(String uniName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uniName", uniName);
        RetrofitUtil.getInstance()
                .createReq(IService.class)
                .getUniversityInfoByName(jsonObject.toJSONString())
                .enqueue(new Callback<CommonBean>() {
                    @Override
                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                        if (response.isSuccessful()) {
                            CommonBean commonBean = response.body();
                            if (commonBean.getStatus() == 1) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonBean> call, Throwable t) {

                    }
                });
    }

    private void initData() {
        ExpandableTextView expandableTextView1 = expand_text_1.findViewById(R.id.expand_text_view);
        ExpandableTextView expandableTextView2 = expand_text_2.findViewById(R.id.expand_text_view);
        expandableTextView1.setText(getString(R.string.test_desc));
        expandableTextView2.setText(getString(R.string.test_desc));
    }
}
