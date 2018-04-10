package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gk.R;
import com.gk.beans.MajorQueryBean;
import com.gk.global.YXXConstants;
import com.gk.mvp.presenter.MajorPresenter;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class LqRiskQueryMajorActivity extends SjmBaseActivity {
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.searchview)
    SearchView searchview;

    @OnClick(R.id.back_image)
    public void onViewClicked(View view) {
        closeActivity(this);
    }

    private List<MajorQueryBean.DataBean> listQuery = new ArrayList<>();
    private MajorPresenter majorPresenter;
    private CommonAdapter<MajorQueryBean.DataBean> adapter;
    private String[] majors = {
            "工程造价", "制药工程", "经济学",
            "机械设计制造及自动化", "数学与应用数学",
            "计算机科学与技术", "通信工程", "哲学", "物理",
            "会计学", "统计学", "工商管理", "汉语言文学", "对外汉语",
            "航空航天工程"
    };

    @Override
    public int getResouceId() {
        return R.layout.activity_risk_test_query_major;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        majorPresenter = new MajorPresenter(this);
        initQueryData();
        showSearch();
        setSearchViewText(searchview);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        listQuery = (List<MajorQueryBean.DataBean>) t;
        adapter.setItems(listQuery);
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
    }

    /**
     * 以下为搜索部分
     */
    private void initQueryData() {
        for (int i = 0; i < majors.length; i++) {
            MajorQueryBean.DataBean dataBean = new MajorQueryBean.DataBean();
            dataBean.setMajorName(majors[i]);
            listQuery.add(dataBean);
        }
        adapter = new CommonAdapter<MajorQueryBean.DataBean>(this, listQuery, R.layout.professional_query_item) {
            @Override
            public void convert(ViewHolder viewHolder, MajorQueryBean.DataBean item) {
                viewHolder.setText(R.id.tv_zy_name, item.getMajorName());
            }
        };
        lvQuerySchool.setAdapter(adapter);
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(LqRiskQueryMajorActivity.this, LqRiskTestMajorSchoolActivity.class);
                intent.putExtra("major", listQuery.get(i).getMajorName());
                startActivityForResult(intent, 119);

            }
        });
    }

    private void showSearch() {
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                majorPresenter.queryMajorByName(YxxUtils.URLEncode(s), 1);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                }
                searchview.clearFocus(); // 不获取焦点
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setQueryHint("请输入专业名称");
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 119) {
            this.setResult(119, data);
            closeActivity(this);
        }
    }
}
