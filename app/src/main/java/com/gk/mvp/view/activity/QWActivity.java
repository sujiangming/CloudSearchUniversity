package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gk.R;
import com.gk.mvp.view.custom.TopBarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/11/20.
 */

public class QWActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_qw)
    ListView lvQw;
    @BindView(R.id.smart_qw)
    SmartRefreshLayout smartQw;

    @Override
    public int getResouceId() {
        return R.layout.activity_qw;
    }

    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "权威解答", 0);
        initSmartRefreshLayout(smartQw, false);
        for (int i = 0; i < 20; i++) {
            stringList.add("权威解答-" + i);
        }
        lvQw.setAdapter(new CommonAdapter<String>(this, R.layout.qw_list_item, stringList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                if (position == 0) {
                    viewHolder.setBackgroundColor(R.id.tv_top_line, 0x00000000);
                }
                viewHolder.setText(R.id.tv_title, item);
            }
        });
        lvQw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("当前点击的是：" + i);
                openNewActivity(QWDetailActivity.class);
            }
        });
    }

    @Override
    public void refresh() {
        getmRefreshLayout().finishRefresh();
    }

    @Override
    public <T> void fillWithData(T t, int order) {

    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }
}
