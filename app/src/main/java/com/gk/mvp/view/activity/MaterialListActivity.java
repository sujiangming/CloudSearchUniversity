package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.MaterialItemBean;
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
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/11/26.
 */

public class MaterialListActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_material)
    ListView lvMaterial;
    @BindView(R.id.smart_material)
    SmartRefreshLayout smartMaterial;

    List<MaterialItemBean.DataBean> list = new ArrayList<>();

    private GlideImageLoader imageLoader = new GlideImageLoader();
    private int type;
    private int page = 0;
    private String course;
    private boolean isLoadMore = false;
    private JSONObject jsonObject = new JSONObject();

    @Override
    public int getResouceId() {
        return R.layout.activity_material_list;
    }


    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartMaterial, true);
        type = getIntent().getIntExtra("type", 0);
        course = getIntent().getStringExtra("course");
        setTitleByType();
        showProgress();
        invoke();

    }

    private void setTitleByType() {
        if (type == 1) {
            setTopBar(topBar, "名师讲堂", 0);
        } else if (type == 2) {
            setTopBar(topBar, "历史真题", 0);
        } else {
            setTopBar(topBar, "模拟试卷", 0);
        }
    }

    private void invoke() {
        jsonObject.put("page", page);
        jsonObject.put("type", type);
        jsonObject.put("course", course);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getMaterialsByType(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<MaterialItemBean.DataBean> beanList = JSON.parseArray(commonBean.getData().toString(), MaterialItemBean.DataBean.class);
        if (beanList == null) {
            return;
        }
        if (isLoadMore) {
            list.addAll(beanList);
        } else {
            //beanList.removeAll(list);
            list.addAll(beanList);
            removeDuplicate(list);
        }
        lvMaterial.setAdapter(new CommonAdapter<MaterialItemBean.DataBean>(this, R.layout.material_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, MaterialItemBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_live_title, item.getName());
                viewHolder.setText(R.id.tv_time_content, JdryTime.getFullTimeBySec(item.getUploadTime()));
                switch (type) {
                    case 1:
                        viewHolder.setText(R.id.tv_type_content, "名师讲堂");
                        break;
                    case 2:
                        viewHolder.setText(R.id.tv_type_content, "历史真题");
                        break;
                    case 3:
                        viewHolder.setText(R.id.tv_type_content, "模拟试卷");
                        break;
                }
                imageLoader.displayImage(MaterialListActivity.this, item.getLogo(), (ImageView) viewHolder.getView(R.id.iv_item));
            }
        });
        hideProgress();
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
    }

    @Override
    public void refresh() {
        page = 0;
        isLoadMore = false;
        invoke();
    }

    @Override
    public void loadMore() {
        page++;
        isLoadMore = true;
        invoke();
    }

    public List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

}
