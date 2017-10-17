package com.gk.fragment;

import android.os.Bundle;

import com.gk.R;

import java.util.List;

/**
 * Created by JDRY_SJM on 2017/4/20.
 */

public class LectureFragment extends SjmBaseFragment {
//    @BindView(R.id.gv_lecture)
//    GridView gvLecture;
//    @BindView(R.id.smart_rf_lecture)
//    SmartRefreshLayout smartRfLecture;
//
//    private LectureAdapter lectureAdapter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_lecture;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
//        LecturePresenter lecturePresenter = new LecturePresenter(getContext(), this);
//        lectureAdapter = new LectureAdapter(getContext());
//        lecturePresenter.initData();
//        initSmartRefreshLayout(smartRfLecture, true, 0);
//        gvLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //openNewActivity(DetailControlActivity.class);
//            }
//        });
    }

    @Override
    public <T> void fillWithNoData(T t) {

    }

    @Override
    public <T> void fillWithData(List<T> list) {
//        List<String> mlist = (List<String>) list;
//        gvLecture.setAdapter(lectureAdapter);
//        lectureAdapter.update(mlist);
    }
}
