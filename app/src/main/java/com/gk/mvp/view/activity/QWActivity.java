package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.QWListBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryTime;
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

    private int mPage = 0;
    private boolean isLoadMore = false;
    private JSONObject jsonObject = new JSONObject();
    private List<QWListBean> list = new ArrayList<>();
    private CommonAdapter<QWListBean> adapter;
    private GlideImageLoader glideImageLoader = new GlideImageLoader();

    @Override
    public int getResouceId() {
        return R.layout.activity_qw;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "权威解答", 0);
        initSmartRefreshLayout(smartQw, false);
        initAdapter();
        invoke();
    }

    private void initAdapter() {
        lvQw.setAdapter(adapter = new CommonAdapter<QWListBean>(this, R.layout.qw_list_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, QWListBean item, int position) {
                if (item == null) {
                    return;
                }
                if (position == 0) {
                    viewHolder.setBackgroundColor(R.id.tv_top_line, 0x00000000);
                }
                viewHolder.setText(R.id.tv_title, item.getQueTitle());
                glideImageLoader.displayImage(QWActivity.this, item.getHeadImg(), (ImageView) viewHolder.getView(R.id.civ_header));
                viewHolder.setText(R.id.tv_nick_name, item.getNickName());
                viewHolder.setText(R.id.tv_time_right, JdryTime.getFullTimeBySec(item.getQueTime()));
                viewHolder.setText(R.id.tv_content, item.getQueContent());
                viewHolder.setText(R.id.tv_attention, "关注 " + item.getAttentionTimes());
                viewHolder.setText(R.id.tv_reply_count, "回复 " + item.getCommentTimes());
                viewHolder.setText(R.id.tv_scan_count, "浏览 " + item.getViewTimes());
            }
        });
        lvQw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("bean", list.get(i));
                openNewActivityByIntent(QWDetailActivity.class, intent);
            }
        });
    }

    private void invoke() {
        jsonObject.put("page", mPage);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getQuestionList(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke();
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<QWListBean> qwBeans = JSON.parseArray(commonBean.getData().toString(), QWListBean.class);
        handleData(qwBeans);
        stopLayoutRefreshByTag(isLoadMore);
        hideProgress();

    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void handleData(List<QWListBean> qwBeans) {
        if (qwBeans == null || qwBeans.size() == 0) {
            toast("没有数据");
            return;
        }
        if (!isLoadMore) {//刷新
            int beforeSize = list.size();
            list.addAll(qwBeans);
            list = removeDuplicate(list);
            int afterSize = list.size();
            if (beforeSize == afterSize) {
                toast("没有最新数据");
                return;
            }
            adapter.notifyDataSetChanged();
            return;
        }

        list.addAll(qwBeans);
        adapter.notifyDataSetChanged();

    }

    public List<QWListBean> removeDuplicate(List<QWListBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getQueId().equals(list.get(i).getQueId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
