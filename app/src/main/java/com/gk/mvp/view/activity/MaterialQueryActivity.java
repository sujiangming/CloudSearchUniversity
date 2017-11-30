package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.CourseTypeEnum;
import com.gk.beans.MaterialItemBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryTime;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/30.
 */

public class MaterialQueryActivity extends SjmBaseActivity {
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.searchview)
    SearchView searchView;
    @BindView(R.id.lv_zy_query)
    ListView lvZyQuery;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartLayout;


    private int mPage = 0;
    private boolean isLoadMore = false;
    private JSONObject jsonObject;
    private String searchKey;
    List<MaterialItemBean.DataBean> list;
    private CommonAdapter<MaterialItemBean.DataBean> adapter;
    private GlideImageLoader imageLoader;

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        closeActivity(this);
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_material_query;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartLayout, true);
        jsonObject = new JSONObject();
        imageLoader = new GlideImageLoader();
        list = new ArrayList<>();
        showSearch();
    }

    private void showSearch() {
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private String TAG = getClass().getSimpleName();

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit = " + s);
                searchKey = s;
                invoke(CourseTypeEnum.getPinYin(s));
                if (searchView != null) {
                    // 得到输入管理对象
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                    }
                }
                searchView.clearFocus(); // 不获取焦点
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //initQueryData(s);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void invoke(String name) {
        jsonObject.put("page", mPage);
        jsonObject.put("name", name);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getMaterialsByName(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke(searchKey);
    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke(searchKey);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<MaterialItemBean.DataBean> beanList = JSON.parseArray(commonBean.getData().toString(), MaterialItemBean.DataBean.class);
        if (beanList == null || beanList.size() == 0) {
            return;
        }
        if (isLoadMore) {
            list.addAll(beanList);
        } else {
            if (beanList == null || beanList.size() == 0) {
                toast("已经扯到底啦");
                return;
            }
            if (isLoadMore) {
                list.addAll(beanList);
            } else {
                int currentSize = list.size();
                list.addAll(beanList);
                list = removeDuplicate(list);
                int afterSize = list.size();
                if (currentSize == afterSize) {
                    return;
                }
            }
        }
        lvZyQuery.setAdapter(adapter = new CommonAdapter<MaterialItemBean.DataBean>(this, R.layout.material_item, list) {
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
                imageLoader.displayImage(MaterialQueryActivity.this, item.getLogo(), (ImageView) viewHolder.getView(R.id.iv_item));
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