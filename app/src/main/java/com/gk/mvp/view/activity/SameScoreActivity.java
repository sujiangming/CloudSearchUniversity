package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.SameScoreItem;
import com.gk.mvp.view.custom.TopBarView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/30.
 */

public class SameScoreActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_same_score)
    ListView lvSameScore;
    @BindView(R.id.tv_same_top_10)
    TextView tvSameTop10;
    @BindView(R.id.rl_more_data)
    RelativeLayout relativeLayout;

    private List<SameScoreItem> stringList = new ArrayList<>();
    CommonAdapter adapter = null;

    @OnClick(R.id.tv_more_data)
    public void tvMoreDataClick() {
        tvSameTop10.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        lvSameScore.setPadding(0,0,0,0);
        for (int i = 10; i < 50; ++i) {
            SameScoreItem sameScoreItem = new SameScoreItem();
            sameScoreItem.setName("清华大学" + i);
            sameScoreItem.setMoreData(true);
            stringList.add(sameScoreItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_same_score_direction;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        topBar.getTitleView().setText("同分去向");
        topBar.getTitleView().setTextColor(0xFF030303);
        topBar.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
        for (int i = 0; i < 10; ++i) {
            SameScoreItem sameScoreItem = new SameScoreItem();
            sameScoreItem.setName("贵州大学" + i);
            sameScoreItem.setMoreData(true);
            stringList.add(sameScoreItem);
        }

        lvSameScore.setAdapter(adapter = new CommonAdapter<SameScoreItem>(this, R.layout.same_score_item, stringList) {
            @Override
            protected void convert(ViewHolder viewHolder, SameScoreItem item, int position) {
                viewHolder.setText(R.id.tv_same_score_name, item.getName());
            }
        });
        lvSameScore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("当前点击的是:" + i + " 名称：" + stringList.get(i).getName());
            }
        });
    }
}
