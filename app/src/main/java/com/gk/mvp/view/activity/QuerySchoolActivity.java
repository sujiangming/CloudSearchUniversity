package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.UniversityAreaEnum;
import com.gk.beans.UniversityFeatureEnum;
import com.gk.beans.UniversityLevelEnum;
import com.gk.beans.UniversityTypeEnum;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.GridViewChooseAdapter;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/27.
 */

public class QuerySchoolActivity extends SjmBaseActivity {
    @BindView(R.id.spinner1)
    TextView spinner1;
    @BindView(R.id.spinner2)
    TextView spinner2;
    @BindView(R.id.spinner3)
    TextView spinner3;
    @BindView(R.id.spinner4)
    TextView spinner4;
    @BindView(R.id.lv_query_school)
    ListView lvQuerySchool;
    @BindView(R.id.smart_rf_query_school)
    SmartRefreshLayout smartRfQuerySchool;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.gv_channel)
    GridView gvChannel;
    @BindView(R.id.btn_choose)
    Button btnChoose;
    @BindView(R.id.rl_choose)
    LinearLayout rlChoose;

    @OnClick({R.id.back_image, R.id.spinner1, R.id.spinner2,
            R.id.spinner3, R.id.spinner4, R.id.btn_choose, R.id.tv_bg_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                closeActivity(this);
                break;
            case R.id.spinner1:
                rlChoose.setVisibility(View.VISIBLE);
                gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityAreaEnum.getAreaList(), 1);
                initGridViewAdapter();
                type = 1;
                break;
            case R.id.spinner2:
                rlChoose.setVisibility(View.VISIBLE);
                gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityTypeEnum.getUniversityList(), 2);
                initGridViewAdapter();
                type = 2;
                break;
            case R.id.spinner3:
                rlChoose.setVisibility(View.VISIBLE);
                rlChoose.setVisibility(View.VISIBLE);
                gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityFeatureEnum.getUniversityFeatureList(), 3);
                initGridViewAdapter();
                type = 3;
                break;
            case R.id.spinner4:
                rlChoose.setVisibility(View.VISIBLE);
                gridViewChooseAdapter = new GridViewChooseAdapter(this, UniversityLevelEnum.getUniversityLevelList(), 4);
                initGridViewAdapter();
                type = 4;
                break;
            case R.id.btn_choose:
                String str = nullStr;
                String strName = nullStr;
                rlChoose.setVisibility(View.GONE);
                queryIndexList = gridViewChooseAdapter.getCheckedArray();
                if (queryIndexList != null) {
                    for (int i = 0; i < queryIndexList.size(); i++) {
                        gridViewChooseAdapter.getIsCheck().set(i, false);
                        if (i == queryIndexList.size() - 1) {
                            str += queryIndexList.get(i);
                            strName += getEnumName(Integer.valueOf(queryIndexList.get(i)));
                        } else {
                            str += queryIndexList.get(i) + ",";
                            strName += getEnumName(Integer.valueOf(queryIndexList.get(i))) + ",";
                        }
                    }
                }
                if (!strName.equals(nullStr)) {
                    setEnumName(strName);
                }
                if (type == 1) {
                    invoke(str, nullStr, nullStr, nullStr, nullStr);
                } else if (type == 2) {
                    invoke(nullStr, str, nullStr, nullStr, nullStr);
                } else if (type == 3) {
                    invoke(nullStr, nullStr, nullStr, str, nullStr);
                } else if (type == 4) {
                    invoke(nullStr, nullStr, nullStr, nullStr, str);
                } else {
                    invoke(nullStr, nullStr, nullStr, nullStr, nullStr);
                }
                break;
            case R.id.tv_bg_click:
                rlChoose.setVisibility(View.GONE);
                break;
        }
    }

    private List<QuerySchoolBean.DataBean> schoolBeanList = new ArrayList<>();
    private GlideImageLoader glideImageLoader = new GlideImageLoader();
    private JSONObject jsonObject = new JSONObject();
    private int mPage = 0;
    private boolean isLoadMore = false;
    private GridViewChooseAdapter gridViewChooseAdapter;
    private List<String> queryIndexList;
    private int type = 1;
    private String nullStr = "";
    private String searchKey = nullStr;


    @Override
    public int getResouceId() {
        return R.layout.activity_query_school;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(smartRfQuerySchool, true);
        invoke(nullStr, nullStr, nullStr, nullStr, nullStr);
        showSearch();
    }

    private String getEnumName(int index) {
        String name = nullStr;
        if (type == 1) {
            name = UniversityAreaEnum.getName(index);
        } else if (type == 2) {
            name = UniversityTypeEnum.getName(index);
        } else if (type == 3) {
            name = UniversityFeatureEnum.getName(index);
        } else {
            name = UniversityLevelEnum.getName(index);
        }
        return name;
    }

    private void setEnumName(String name) {
        if (type == 1) {
            spinner1.setText(name);
        } else if (type == 2) {
            spinner2.setText(name);
        } else if (type == 3) {
            spinner3.setText(name);
        } else {
            spinner4.setText(name);
        }
    }

    private void initGridViewAdapter() {
        gvChannel.setAdapter(gridViewChooseAdapter);
        gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridViewChooseAdapter.choiceState(i, view);
            }
        });
    }

    private void showSearch() {
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private String TAG = getClass().getSimpleName();

            @Override
            public boolean onQueryTextSubmit(String s) {
                invoke(nullStr, nullStr, nullStr, nullStr, YxxUtils.URLEncode(s));
                clearSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //initQueryData(s);
                return true;
            }
        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setQueryHint("请输入关键字");
                hideSoftKey();
                return true;
            }
        });
    }

    private void clearSearch() {
        if (searchview != null) {
            hideSoftKey();
        }
        searchview.clearFocus(); // 不获取焦点
    }

    private void hideSoftKey() {
        // 得到输入管理对象
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
            imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
        }
    }

    private void invoke(String schoolArea, String schoolCategory, String schoolType, String tese, String schoolName) {
        jsonObject.put("page", mPage);
        jsonObject.put("schoolArea", schoolArea);//学校地区
        jsonObject.put("schoolCategory", schoolCategory);//学校类别
        jsonObject.put("schoolType", schoolType);//学校类型（1本科、2专业）
        jsonObject.put("tese", tese);//特色
        jsonObject.put("schoolName", schoolName);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).getUniversityList(jsonObject.toJSONString()))
                .requestForResponseBody(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isLoadMore = false;
        invoke(nullStr, nullStr, nullStr, nullStr, nullStr);

    }

    @Override
    public void loadMore() {
        mPage++;
        isLoadMore = true;
        invoke(nullStr, nullStr, nullStr, nullStr, nullStr);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
        String data = (String) t;
        if (data == null || nullStr.equals(data)) {
            toast("没有相关数据");
            return;
        }
        QuerySchoolBean querySchoolBean = JSON.parseObject(data, QuerySchoolBean.class);
        if (mPage == 0 && !isLoadMore) {
            schoolBeanList = querySchoolBean.getData();
            initListView();
            return;
        }
        if (isLoadMore) {
            stopRefreshLayoutLoadMore();
            List<QuerySchoolBean.DataBean> dataBeans = querySchoolBean.getData();
            if (data == null) {
                toast("没有更多数据了");
                return;
            }
            schoolBeanList.addAll(dataBeans);
            initListView();
            lvQuerySchool.smoothScrollByOffset(lvQuerySchool.getHeight());
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
        stopLayoutRefreshByTag(isLoadMore);
    }

    private void initListView() {
        lvQuerySchool.setAdapter(new CommonAdapter<QuerySchoolBean.DataBean>(this, R.layout.query_school_list_item, schoolBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, QuerySchoolBean.DataBean item, int position) {
                String isDoubleTop = item.getIsDoubleTop();
                String isNef = item.getIsNef();
                String isToo = item.getIsToo();
                viewHolder.getView(R.id.tv_school_mark_0).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_school_mark_1).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_school_mark_2).setVisibility(View.VISIBLE);
                viewHolder.setText(R.id.tv_school_mark_0, "1".equals(isNef) ? "985" : "非985");
                viewHolder.setText(R.id.tv_school_mark_1, "1".equals(isToo) ? "211" : "非211");
                viewHolder.setText(R.id.tv_school_mark_2, isDoubleTop.equals("1") ? "双一流" : "非双一流");
                ImageView imageView = viewHolder.getView(R.id.iv_query_item);
                glideImageLoader.displayByImgRes(QuerySchoolActivity.this, item.getSchoolLogo(), imageView, R.drawable.gaoxiaozhanweitu);
                viewHolder.setText(R.id.tv_school_name, item.getSchoolName());
                viewHolder.setText(R.id.tv_school_type, "1".equals(item.getSchoolType()) ? "本科" : "专科");
                viewHolder.setText(R.id.tv_school_level, "1".equals(item.getSchoolCategory()) ? "综合类" : "教育类");
                viewHolder.setText(R.id.tv_school_address, UniversityAreaEnum.getName(Integer.valueOf(item.getSchoolArea())));
            }
        });
        lvQuerySchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("uniName", schoolBeanList.get(i));
                intent.putExtra("flag","query");
                openNewActivityByIntent(SchoolDetailActivity.class, intent);
            }
        });
    }


}
