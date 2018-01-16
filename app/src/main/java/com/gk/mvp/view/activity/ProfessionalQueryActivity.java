package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gk.R;
import com.gk.beans.MajorBean;
import com.gk.beans.MajorQueryBean;
import com.gk.global.YXXConstants;
import com.gk.mvp.presenter.MajorPresenter;
import com.gk.mvp.view.adpater.ProfessionalParentAdapter;
import com.gk.tools.YxxUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/30.
 */

public class ProfessionalQueryActivity extends SjmBaseActivity implements ExpandableListView.OnGroupExpandListener,
        ProfessionalParentAdapter.OnExpandClickListener {

    @BindView(R.id.expand_list)
    ExpandableListView expandList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_level_1)
    TextView tvLevel1;
    @BindView(R.id.tv_level_2)
    TextView tvLevel2;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.lv_zy_query)
    ListView listView;
    @BindView(R.id.searchview)
    SearchView searchView;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.back_image)
    ImageView backImage;

    private ProfessionalParentAdapter mAdapter;
    private CommonAdapter<MajorQueryBean.DataBean> adapter;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;

    private List<List<MajorBean.DataBean.NodesBeanXX>> listList = new ArrayList<>();
    private List<MajorBean.DataBean.NodesBeanXX> list = new ArrayList<>();
    private List<MajorQueryBean.DataBean> listQuery = new ArrayList<>();

    private MajorPresenter majorPresenter;
    private int pageType = 1;//默认当前页面是本科页面 2 为专科页面

    @Override
    public int getResouceId() {
        return R.layout.activity_professional_query;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        showProgress();
        majorPresenter = new MajorPresenter(this);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                listList = (List<List<MajorBean.DataBean.NodesBeanXX>>) t;
                if (listList == null || listList.size() == 0) {
                    toast("无相关数据");
                    return;
                }
                list = listList.get(0);//默认是本科
                initView();
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                listQuery = (List<MajorQueryBean.DataBean>) t;
                initQueryData();
                break;
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void initView() {
        mAdapter = new ProfessionalParentAdapter(this, list);
        expandList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //设置点击父控件的监听
        expandList.setOnGroupExpandListener(this);
        //点击最里面的菜单的点击事件
        mAdapter.setOnChildListener(this);
    }

    /**
     * 保证listview只展开一项
     *
     * @param groupPosition
     */
    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < list.size(); i++) {
            if (i != groupPosition) {
                expandList.collapseGroup(i);
            }
        }
    }

    /***
     * 点击最次级菜单的点击事件
     * @param parentPosition
     * @param childPosition
     * @param childIndex
     */
    @Override
    public void onclick(int parentPosition, int childPosition, int childIndex) {
        MajorBean.DataBean.NodesBeanXX.NodesBeanX.NodesBean da = list.get(parentPosition).getNodes().get(childIndex).getNodes().get(childIndex);
        Log.e("", JSON.toJSONString(da));
    }

    @OnClick({R.id.iv_back, R.id.iv_search, R.id.tv_level_1, R.id.tv_level_2, R.id.back_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeActivity();
                break;
            case R.id.tv_level_1:
                tvLevel1Click();
                if (listList.size() < 1) {
                    return;
                }
                list = listList.get(0);
                pageType = 1;
                initView();
                break;
            case R.id.tv_level_2:
                tvLevel2Click();
                if (listList.size() <= 1) {
                    return;
                }
                list = listList.get(1);
                pageType = 2;
                initView();
                break;
            case R.id.iv_search:
                showSearch();
                break;
            case R.id.back_image:
                hideSearch();
                break;

        }
    }

    private void tvLevel1Click() {
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_press);
        tvLevel1.setTextColor(0xFFFFFFFF);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_normal);
        tvLevel2.setTextColor(0xFF555555);
    }

    private void tvLevel2Click() {
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_normal);
        tvLevel1.setTextColor(0xFF555555);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_press);
        tvLevel2.setTextColor(0xFFFFFFFF);
    }

    /**
     * 以下为搜索部分
     */

    private void initQueryData() {
        listView.setAdapter(adapter = new CommonAdapter<MajorQueryBean.DataBean>(this, R.layout.professional_query_item, listQuery) {
            @Override
            protected void convert(ViewHolder viewHolder, MajorQueryBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_zy_name, item.getMajorName());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(ProfessionalQueryActivity.this, ProfessionalDetailActivity.class);
                intent.putExtra("id", listQuery.get(i).getMajorId());
                startActivity(intent);

            }
        });
    }

    private void showSearch() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(250);
        llSearch.setAnimation(mShowAction);
        if (llSearch.getVisibility() == View.GONE) {
            llSearch.setVisibility(View.VISIBLE);
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                majorPresenter.queryMajorByName(YxxUtils.URLEncode(s), pageType);
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
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQueryHint("高校排名");
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void hideSearch() {
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(250);
        llSearch.setAnimation(mHiddenAction);
        llSearch.setVisibility(View.GONE);
    }
}
