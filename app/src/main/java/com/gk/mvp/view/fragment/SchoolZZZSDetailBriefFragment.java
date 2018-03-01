package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.gk.R;
import com.gk.beans.SchoolZZZsBean;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolZZZSDetailBriefFragment extends SjmBaseFragment {

    @BindView(R.id.expand_text_1)
    View expand_text_1;

    @BindView(R.id.expand_text_2)
    View expand_text_2;

    private SchoolZZZsBean schoolBean;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_zzzs_detail_brief;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        ExpandableTextView expandableTextView1 = expand_text_1.findViewById(R.id.expand_text_view);
        ExpandableTextView expandableTextView2 = expand_text_2.findViewById(R.id.expand_text_view);
        schoolBean = (SchoolZZZsBean) getArguments().getSerializable("schoolBean");
        expandableTextView1.setText(schoolBean.getSelfRecruitBrochure());
        expandableTextView2.setText(schoolBean.getUniversitySummary());
    }
}
