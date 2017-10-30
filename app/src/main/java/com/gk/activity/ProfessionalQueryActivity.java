package com.gk.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.adpater.ProfessionalParentAdapter;
import com.gk.beans.FirstBean;
import com.gk.beans.SecondBean;
import com.gk.beans.ThirdBean;

import java.util.ArrayList;

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

    private ArrayList<FirstBean> mDatas = new ArrayList<FirstBean>();
    private ProfessionalParentAdapter mAdapter;
    private String faultLevel = "01"; //默认为本科

    @Override
    public int getResouceId() {
        return R.layout.activity_professional_query;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initData();
        initView();
    }

    @OnClick({R.id.iv_back, R.id.iv_search, R.id.tv_level_1, R.id.tv_level_2})
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
                break;

        }
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
