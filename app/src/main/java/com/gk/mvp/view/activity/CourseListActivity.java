package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CourseTypeEnum;
import com.gk.beans.MaterialItemBean;
import com.gk.mvp.presenter.MaterialPresenter;
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
 * Created by JDRY-SJM on 2017/11/26.
 */

public class CourseListActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.lv_material)
    ListView lvMaterial;
    @BindView(R.id.smart_material)
    SmartRefreshLayout smartMaterial;

    List<MaterialItemBean.DataBean> list = new ArrayList<>();

    private GlideImageLoader imageLoader = new GlideImageLoader();
    private int page = 0;
    private String course;
    private boolean isLoadMore = false;
    private JSONObject jsonObject = new JSONObject();
    private MaterialPresenter<MaterialItemBean> materialPresenter;

    @Override
    public int getResouceId() {
        return R.layout.activity_course_list;
    }


    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartMaterial, true);
        course = getIntent().getStringExtra("course");
        setTitleByType();
        materialPresenter = new MaterialPresenter<MaterialItemBean>(this);
        materialPresenter.httpRequestMaterialsByCourse(page, course);
    }

    private void setTitleByType() {
        switch (course) {
            case "yuwen":
                setTopBar(topBar, "语文资料", 0);
                break;
            case "shuxue":
                setTopBar(topBar, "数学资料", 0);
                break;
            case "yingyu":
                setTopBar(topBar, "英语资料", 0);
                break;
            case "wenzong":
                setTopBar(topBar, "文综资料", 0);
                break;
            case "lizong":
                setTopBar(topBar, "理综资料", 0);
                break;
            case "wuli":
                setTopBar(topBar, "物理资料", 0);
                break;
            case "huaxue":
                setTopBar(topBar, "化学资料", 0);
                break;
            case "shengwu":
                setTopBar(topBar, "生物资料", 0);
                break;
            case "dili":
                setTopBar(topBar, "地理资料", 0);
                break;
            case "lishi":
                setTopBar(topBar, "历史资料", 0);
                break;
            case "zhengzhi":
                setTopBar(topBar, "政治资料", 0);
                break;
            default:
                setTopBar(topBar, "资料列表", 0);
                break;
        }
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
        } else {
            stopRefreshLayout();
        }
        MaterialItemBean commonBean = (MaterialItemBean) t;
        List<MaterialItemBean.DataBean> beanList = commonBean.getData();//JSON.parseArray(commonBean.getData().toString(), MaterialItemBean.DataBean.class);
        if (beanList == null || beanList.size() == 0) {
            toast("已经扯到底啦");
            return;
        }
        if (isLoadMore) {
            list.addAll(beanList);
        } else {
            //beanList.removeAll(list);
            int currentSize = list.size();
            list.addAll(beanList);
            list = removeDuplicate(list);
            int afterSize = list.size();
            if (currentSize == afterSize) {
                return;
            }
        }
        lvMaterial.setAdapter(new CommonAdapter<MaterialItemBean.DataBean>(this, R.layout.material_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, MaterialItemBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_live_title, item.getName());
                viewHolder.setText(R.id.tv_time_content, JdryTime.getFullTimeBySec(item.getUploadTime()));
                viewHolder.setText(R.id.tv_km_content, CourseTypeEnum.getSubjectTypeName(item.getCourse()));
                switch (item.getType()) {
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
                imageLoader.displayImage(CourseListActivity.this, item.getLogo(), (ImageView) viewHolder.getView(R.id.iv_item));
            }
        });
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
        materialPresenter.httpRequestMaterialsByCourse(page, course);
    }

    @Override
    public void loadMore() {
        page++;
        isLoadMore = true;
        materialPresenter.httpRequestMaterialsByCourse(page, course);
    }

    public List<MaterialItemBean.DataBean> removeDuplicate(List<MaterialItemBean.DataBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

}
