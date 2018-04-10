package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ProfessionalParentAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.tools.JdryPersistence;
import com.gk.tools.YxxUtils;

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

    private List<List<MajorBean.DataBean.NodesBeanXX>> listList = new ArrayList<>();
    private List<MajorBean.DataBean.NodesBeanXX> list = new ArrayList<>();
    private List<MajorQueryBean.DataBean> listQuery = new ArrayList<>();

    private MajorPresenter majorPresenter;
    private int pageType = 1;//默认当前页面是本科页面 2 为专科页面
    private String majorJson = null;
    private MajorBean majorBean;
    private DownTask asyncTask = new DownTask();

    @Override
    public int getResouceId() {
        return R.layout.activity_professional_query;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initQueryData();
        //initView();
        majorJson = JdryPersistence.getObjectByAppContext(YXXConstants.MAJOR_JSON_SERIALIZE_KEY);
        majorPresenter = new MajorPresenter(this);
        if (null == majorJson || "".equals(majorJson)) {
            showProgress();
            majorPresenter.getMajorTypeList();
            return;
        }
        //取出存储的数据，直接显示
        asyncTask.execute("execute");
        setSearchViewText(searchView);
    }

    private void destroyAsyncTask() {
        if (asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
        }
        asyncTask = null;
    }

    /**
     * 取出本地数据，主要是数据量比较大，需要异步操作，避免UI线程阻塞
     */
    public class DownTask extends AsyncTask<String, Void, MajorBean> {
        @Override
        //在界面上显示进度条
        protected void onPreExecute() {
            showProgress();
        }

        protected MajorBean doInBackground(String... params) {  //三个点，代表可变参数
            //majorJson = JdryPersistence.getObjectByAppContext(YXXConstants.MAJOR_JSON_SERIALIZE_KEY);
            majorBean = JSON.parseObject(majorJson, MajorBean.class);
            return majorBean;
        }

        //主要是更新UI
        @Override
        protected void onPostExecute(MajorBean result) {
            super.onPostExecute(result);
            handleData();//更新UI
            hideProgress();
        }
    }

    private void handleData() {
        List<MajorBean.DataBean> bzTypeList = majorBean.getData();
        for (MajorBean.DataBean dataBean : bzTypeList) {
            String name = dataBean.getName();
            if (name.equals("本科")) {
                listList.add(0, dataBean.getNodes());
                continue;
            }
            if (name.equals("专科")) {
                listList.add(1, dataBean.getNodes());
                continue;
            }
        }
        list = listList.get(0);//默认是本科
        initView();
        //mAdapter.notifyDataSetChanged();
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
                //mAdapter.notifyDataSetChanged();
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                listQuery = (List<MajorQueryBean.DataBean>) t;
                adapter.setItems(listQuery);
                break;
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
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
                //mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_level_2:
                tvLevel2Click();
                if (listList.size() <= 1) {
                    return;
                }
                list = listList.get(1);
                pageType = 2;
                initView();
                //mAdapter.notifyDataSetChanged();
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
        adapter = new CommonAdapter<MajorQueryBean.DataBean>(this, listQuery, R.layout.professional_query_item) {
            @Override
            public void convert(ViewHolder viewHolder, MajorQueryBean.DataBean item) {
                viewHolder.setText(R.id.tv_zy_name, item.getMajorName());
            }
        };
        listView.setAdapter(adapter);
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
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
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
                // 得到输入管理对象
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
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
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(250);
        llSearch.setAnimation(mHiddenAction);
        llSearch.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyAsyncTask();
    }
}
