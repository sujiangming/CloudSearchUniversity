package com.gk.mvp.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.gk.R;
import com.gk.beans.SchoolDetailTest;
import com.gk.mvp.view.custom.RichText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolDetailLqDataFragment extends SjmBaseFragment {
    @BindView(R.id.rtv_data)
    RichText rtvData;
    @BindView(R.id.lv_score)
    ListView lvScore;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_detail_lq_data;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        String uniName = getArguments().getString("uniName");
        Log.e(SchoolDetailLqDataFragment.class.getName(), uniName);
        initData();
    }

    private void initData() {
        List<SchoolDetailTest> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SchoolDetailTest detailTest = new SchoolDetailTest();
            int year = 2010;
            year += i;
            int score = 600;
            score = (int) (score + i + Math.random());
            int scoreLower = 380;
            scoreLower = (int) (scoreLower + i + Math.random());
            detailTest.setValue0(String.valueOf(year));
            detailTest.setValue1("理科");
            detailTest.setValue2(String.valueOf(score));
            detailTest.setValue3(String.valueOf(scoreLower));
            detailTest.setValue4("450");
            list.add(detailTest);
        }
        lvScore.setAdapter(new CommonAdapter<SchoolDetailTest>(getContext(), R.layout.school_detail_list_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, SchoolDetailTest item, int position) {
                viewHolder.setText(R.id.tv_year, item.getValue0());
                viewHolder.setText(R.id.tv_type, item.getValue1());
                viewHolder.setText(R.id.tv_score_hight, item.getValue2());
                viewHolder.setText(R.id.tv_score_lower, item.getValue3());
                viewHolder.setText(R.id.tv_score_control, item.getValue4());
            }
        });
    }

}
