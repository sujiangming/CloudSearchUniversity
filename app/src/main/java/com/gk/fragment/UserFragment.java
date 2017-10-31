package com.gk.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gk.R;
import com.gk.activity.HelpCenterActivity;
import com.gk.activity.PersonInfoActivity;
import com.gk.activity.SetActivity;
import com.gk.activity.WishReportActivity;
import com.gk.custom.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by JDRY_SJM on 2017/4/20.
 */

public class UserFragment extends SjmBaseFragment {

    @BindView(R.id.iv_user_head)
    CircleImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    public int getResourceId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {

    }

    @OnClick({R.id.rl_info, R.id.ll_wish_report, R.id.ll_vip_choose, R.id.ll_zj, R.id.ll_help_center, R.id.ll_contact_kf, R.id.ll_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_info:
                openNewActivity(PersonInfoActivity.class);
                break;
            case R.id.ll_wish_report:
                openNewActivity(WishReportActivity.class);
                break;
            case R.id.ll_vip_choose:
                break;
            case R.id.ll_zj:
                break;
            case R.id.ll_help_center:
                openNewActivity(HelpCenterActivity.class);
                break;
            case R.id.ll_contact_kf:
                break;
            case R.id.ll_set:
                openNewActivity(SetActivity.class);
                break;
        }
    }
}
