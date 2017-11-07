package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.gk.R;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.RichText;
import com.gk.mvp.view.custom.TopBarView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/10/31.
 */

public class PersonInfoActivity extends SjmBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.iv_user_head)
    CircleImageView ivUserHead;
    @BindView(R.id.tv_user_cname)
    RichText tvUserCname;
    @BindView(R.id.tv_vip_level)
    RichText tvVipLevel;
    @BindView(R.id.tv_student_source)
    RichText tvStudentSource;
    @BindView(R.id.tv_student_score)
    RichText tvStudentScore;
    @BindView(R.id.tv_student_rank)
    RichText tvStudentRank;
    @BindView(R.id.tv_wen_li_ke)
    RichText tvWenLiKe;

    @Override
    public int getResouceId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "个人信息", 0);
    }


    @OnClick({R.id.iv_user_head, R.id.tv_user_cname, R.id.tv_vip_level, R.id.tv_student_source, R.id.tv_student_score, R.id.tv_student_rank, R.id.tv_wen_li_ke})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:
                break;
            case R.id.tv_user_cname:
                break;
            case R.id.tv_vip_level:
                break;
            case R.id.tv_student_source:
                break;
            case R.id.tv_student_score:
                break;
            case R.id.tv_student_rank:
                break;
            case R.id.tv_wen_li_ke:
                break;
        }
    }
}
