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
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryTime;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
        adapter = new CommonAdapter<QWListBean>(this, list, R.layout.qw_list_item) {
            @Override
            public void convert(ViewHolder viewHolder, QWListBean item) {
                if (item == null) {
                    return;
                }
                if (list.indexOf(item) == 0) {
                    viewHolder.setBackgroundColor(R.id.tv_top_line, 0x00000000);
                }
                viewHolder.setText(R.id.tv_title, item.getQueTitle());
                GlideImageLoader.displayImage(QWActivity.this, item.getHeadImg(), (ImageView) viewHolder.getView(R.id.civ_header));
                viewHolder.setText(R.id.tv_nick_name, item.getNickName());
                viewHolder.setText(R.id.tv_time_right, JdryTime.getFullTimeBySec(item.getQueTime()));
                viewHolder.setText(R.id.tv_content, item.getQueContent());
                viewHolder.setText(R.id.tv_attention, "关注 " + item.getAttentionTimes());
                viewHolder.setText(R.id.tv_reply_count, "回复 " + item.getCommentTimes());
                viewHolder.setText(R.id.tv_scan_count, "浏览 " + item.getViewTimes());
            }
        };
        lvQw.setAdapter(adapter);
        lvQw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("bean", list.get(i));
                openNewActivityByIntent(QWDetailActivity.class, intent);
            }
        });
    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void invoke() {
        showProgress();
        jsonObject.put("page", mPage);
        presenterManager.setCall(RetrofitUtil.getInstance()
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
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        CommonBean commonBean = (CommonBean) t;
        assert null != commonBean;
        assert null != commonBean.getData();
        List<QWListBean> qwBeans = JSON.parseArray(commonBean.getData().toString(), QWListBean.class);
        handleData(qwBeans);
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
            list.clear();
            list.addAll(qwBeans);
            adapter.notifyDataSetChanged();
            return;
        }

        list.addAll(qwBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
            presenterManager.setCall(null);
            presenterManager = null;
        }
    }
}
