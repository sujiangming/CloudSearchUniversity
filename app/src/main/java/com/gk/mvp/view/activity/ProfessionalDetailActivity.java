package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.MajorInfoBean;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/11/23.
 */

public class ProfessionalDetailActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.tv_major_name)
    TextView tvMajorName;
    @BindView(R.id.tv_major_code)
    TextView tvMajorCode;
    @BindView(R.id.rl_up)
    RelativeLayout rlUp;
    @BindView(R.id.tv_split_1)
    TextView tvSplit1;
    @BindView(R.id.rtv_major_brief)
    RichText rtvMajorBrief;
    @BindView(R.id.tv_bg_1)
    TextView tvBg1;
    @BindView(R.id.tv_brief_content)
    TextView tvBriefContent;
    @BindView(R.id.tv_split_2)
    TextView tvSplit2;
    @BindView(R.id.rtv_study_desc)
    RichText rtvStudyDesc;
    @BindView(R.id.tv_bg_2)
    TextView tvBg2;
    @BindView(R.id.tv_mb_desc)
    TextView tvMbDesc;
    @BindView(R.id.tv_mb_content)
    TextView tvMbContent;
    @BindView(R.id.tv_bx_desc)
    TextView tvBxDesc;
    @BindView(R.id.tv_bx_content)
    TextView tvBxContent;
    @BindView(R.id.tv_fx_desc)
    TextView tvFxDesc;
    @BindView(R.id.tv_fx_content)
    TextView tvFxContent;
    @BindView(R.id.tv_jn_desc)
    TextView tvJnDesc;
    @BindView(R.id.tv_jn_content)
    TextView tvJnContent;
    @BindView(R.id.tv_split_3)
    TextView tvSplit3;
    @BindView(R.id.rtv_jy_desc)
    RichText rtvJyDesc;
    @BindView(R.id.tv_bg_3)
    TextView tvBg3;
    @BindView(R.id.tv_zs_desc)
    TextView tvZsDesc;
    @BindView(R.id.tv_zs_content)
    TextView tvZsContent;
    @BindView(R.id.tv_by_desc)
    TextView tvByDesc;
    @BindView(R.id.tv_by_content)
    TextView tvByContent;
    @BindView(R.id.tv_xz_desc)
    TextView tvXzDesc;
    @BindView(R.id.tv_xz_content)
    TextView tvXzContent;
    @BindView(R.id.tv_fz_desc)
    TextView tvFzDesc;
    @BindView(R.id.tv_fz_content)
    TextView tvFzContent;
    @BindView(R.id.tv_unit_desc)
    TextView tvUnitDesc;
    @BindView(R.id.tv_unit_content)
    TextView tvUnitContent;
    @BindView(R.id.tv_ability_desc)
    TextView tvAbilityDesc;
    @BindView(R.id.tv_ability_content)
    TextView tvAbilityContent;
    private String pid;

    @Override
    public int getResouceId() {
        return R.layout.activity_profession_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "专业详情", 0);
        pid = getIntent().getStringExtra("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("majorTypeId", pid);
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getMajorInfoList(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        List<MajorInfoBean> list = JSON.parseArray(commonBean.getData().toString(), MajorInfoBean.class);
        if (list == null || list.size() == 0) {
            return;
        }
        initData(list.get(0));
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

    }

    private void initData(MajorInfoBean bean) {
        tvMajorName.setText(bean.getMajorName());
        tvBriefContent.setText(bean.getMajorProfile());
        tvMbContent.setText(bean.getMajorGoal());
        tvBxContent.setText(bean.getRequiredCourse());
        tvFxContent.setText(bean.getMinorCourses());
        tvJnContent.setText(bean.getKnowledgeSkills());
        tvZsContent.setText(bean.getNecessaryCertificates());
        tvByContent.setText(bean.getGraduationTo());
        tvXzContent.setText(bean.getSalary());
        tvFzContent.setText(bean.getDevelopmentProspect());
        //tvUnitContent.setText(bean);
        tvAbilityContent.setText(bean.getCapacityRequirements());

    }
}