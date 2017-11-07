package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
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
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.adpater.ProfessionalParentAdapter;
import com.gk.beans.FirstBean;
import com.gk.beans.SecondBean;
import com.gk.beans.ThirdBean;
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

    private ArrayList<FirstBean> mDatas = new ArrayList<FirstBean>();
    private ProfessionalParentAdapter mAdapter;
    private String faultLevel = "01"; //默认为本科
    private List<String> stringList = new ArrayList<>();
    private CommonAdapter<String> adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_professional_query;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initQueryData() {
        for (int i = 0; i < 20; ++i) {
            stringList.add("电气工程及其自动化" + i);
        }
        listView.setAdapter(adapter = new CommonAdapter<String>(this, R.layout.professional_query_item, stringList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_zy_name, item);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("当前点击第" + i + "个专业：" + stringList.get(i));
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_search, R.id.tv_level_1, R.id.tv_level_2, R.id.back_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeActivity();
                break;
            case R.id.tv_level_1:
                tvLevel1Click();
                break;
            case R.id.tv_level_2:
                tvLevel2Click();
                break;
            case R.id.iv_search:
                showSearch();
                break;
            case R.id.back_image:
                hideSearch();
                break;

        }
    }

    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;

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
            private String TAG = getClass().getSimpleName();

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit = " + s);
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
                Log.d(TAG, "onQueryTextChange = " + s);
                initQueryData();
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                stringList.clear();
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

    private void tvLevel1Click() {
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_press);
        tvLevel1.setTextColor(0xFFFFFFFF);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_normal);
        tvLevel2.setTextColor(0xFF555555);
        faultLevel = "01";
    }

    private void tvLevel2Click() {
        tvLevel1.setBackgroundResource(R.drawable.fault_level_left_normal);
        tvLevel1.setTextColor(0xFF555555);
        tvLevel2.setBackgroundResource(R.drawable.fault_level_right_press);
        tvLevel2.setTextColor(0xFFFFFFFF);
        faultLevel = "02";
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            FirstBean firstBean = new FirstBean();
            ArrayList<SecondBean> mArrlistSecondBean = new ArrayList<SecondBean>();
            if (i == 0) {
                firstBean.setScore("80分");
                firstBean.setTitle("KPI  关键能力");
            } else if (i == 1) {
                firstBean.setScore("10分");
                firstBean.setTitle("API  工作态度");
            } else if (i == 2) {
                firstBean.setScore("10分");
                firstBean.setTitle("LPI  团队建设");
            } else if (i == 3) {
                firstBean.setScore("5分");
                firstBean.setTitle("WPI  特殊事件");
            }
            for (int j = 0; j < 3; j++) {
                SecondBean secondBean = new SecondBean();
                secondBean.setTitle("第" + i + "个二级标题");
                ArrayList<ThirdBean> mArrlistBean = new ArrayList<ThirdBean>();
                for (int k = 0; k < 2; k++) {
                    ThirdBean thirdBean = new ThirdBean();
                    thirdBean.setTitle("第" + k + "个三级标题");
                    mArrlistBean.add(thirdBean);
                }
                secondBean.setSecondBean(mArrlistBean);
                mArrlistSecondBean.add(secondBean);
            }
            firstBean.setFirstData(mArrlistSecondBean);
            mDatas.add(firstBean);

            Log.e("xxx", mDatas.get(i).getTitle());
        }
    }

    private void initView() {
        mAdapter = new ProfessionalParentAdapter(this, mDatas);
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
        Log.e("xxx", "onGroupExpand>>" + groupPosition);
        for (int i = 0; i < mDatas.size(); i++) {
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
        Log.e("xxx", "点了" + "parentPosition>>" + "childPosition>>" + childPosition +
                "childIndex>>" + childIndex);
    }

}
