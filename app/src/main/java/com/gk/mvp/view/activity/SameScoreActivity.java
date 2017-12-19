package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.SameScoreItem;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_weli)
    TextView tvWeli;
    @BindView(R.id.tv_score)
    TextView tvScore;

    private List<SameScoreItem> list = new ArrayList<>();
    CommonAdapter adapter = null;
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;

    @OnClick(R.id.tv_more_data)
    public void tvMoreDataClick() {
        tvSameTop10.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        lvSameScore.setPadding(0, 0, 0, 0);
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
        initSmartRefreshLayout(smartRefreshLayout, true);

        LoginBean loginBean = LoginBean.getInstance();
        tvAddress.setText(loginBean.getAddress() == null ? "" : loginBean.getAddress());
        tvWeli.setText(loginBean.getWlDesc());
        tvScore.setText(loginBean.getScore());

        getSameScoreDirection();

        lvSameScore.setAdapter(adapter = new CommonAdapter<SameScoreItem>(this, R.layout.same_score_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, SameScoreItem item, int position) {
                viewHolder.setText(R.id.tv_same_score_name, item.getSchoolName());
                viewHolder.setText(R.id.tv_score_start, item.getLowestScore() + "");
                viewHolder.setText(R.id.tv_score_end, item.getHighestScore() + "");

                int max = item.getHighestScore();
                int min = item.getLowestScore();

                int len = max - min;

                TextView endTextView = viewHolder.getView(R.id.tv_score_end);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) endTextView.getLayoutParams();
                layoutParams.setMargins(len, 0, 0, 0);

                ProgressBar progressBar = viewHolder.getView(R.id.progressBar1);
                progressBar.setMax((max * 2));
                for (int i = min; i < (max * 2); i++) {
                    progressBar.setProgress(i);
                }
            }
        });
        lvSameScore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("当前点击的是:" + i + " 名称：" + list.get(i).getSchoolName());
            }
        });
    }

    private void getSameScoreDirection() {
        jsonObject.put("page", mPage);
        jsonObject.put("area", "");//地区
        jsonObject.put("subject", LoginBean.getInstance().getSubjectType());
        jsonObject.put("score", LoginBean.getInstance().getScore());
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getSameScoreDirection(jsonObject.toJSONString()))
                .request();
    }

    private void handleData(List<SameScoreItem> sameScoreItems) {
        if (sameScoreItems == null || sameScoreItems.size() == 0) {
            toast("没有数据");
            return;
        }
        if (!isLoadMore) {//刷新
            list.clear();
            list.addAll(sameScoreItems);
            adapter.notifyDataSetChanged();
            return;
        }
        list.addAll(sameScoreItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<SameScoreItem> sameScoreItems = JSON.parseArray(commonBean.getData().toString(), SameScoreItem.class);
        handleData(sameScoreItems);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        getSameScoreDirection();
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        getSameScoreDirection();
    }

}
