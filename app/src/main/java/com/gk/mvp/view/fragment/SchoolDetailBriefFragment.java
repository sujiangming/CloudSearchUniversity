package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.gk.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;

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
        initData();
    }

    private void initData() {
        ExpandableTextView expandableTextView1 = expand_text_1.findViewById(R.id.expand_text_view);
        ExpandableTextView expandableTextView2 = expand_text_2.findViewById(R.id.expand_text_view);
        expandableTextView1.setText(getString(R.string.test_desc));
        expandableTextView2.setText(getString(R.string.test_desc));
//        expandableTextView1.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//            @Override
//            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
//                Toast.makeText(getActivity(), isExpanded ? "Expanded" : "Collapsed", Toast.LENGTH_SHORT).show();
//            }
//        });
//        expandableTextView2.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//            @Override
//            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
//                Toast.makeText(getActivity(), isExpanded ? "Expanded" : "Collapsed", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
