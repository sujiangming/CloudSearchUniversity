package com.gk.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LoginBean;
import com.gk.mvp.view.activity.HelpCenterActivity;
import com.gk.mvp.view.activity.MultiItemRvActivity;
import com.gk.mvp.view.activity.PersonInfoActivity;
import com.gk.mvp.view.activity.SetActivity;
import com.gk.mvp.view.activity.VIPActivity;
import com.gk.mvp.view.activity.WishReportEnterActivity;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.tools.GlideImageLoader;

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
    @BindView(R.id.iv_level)
    ImageView ivLevel;

    @Override
    public int getResourceId() {
        return R.layout.fragment_user;
    }

    private GlideImageLoader glideImageLoader = new GlideImageLoader();

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        glideImageLoader.displayImage(getContext(), LoginBean.getInstance().getHeadImg(), ivUserHead);
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            glideImageLoader.displayImage(getContext(), LoginBean.getInstance().getHeadImg(), ivUserHead);
            initData();
        }
    }

    private void initData() {
        LoginBean loginBean = LoginBean.getInstance();
        tvUserName.setText(loginBean.getNickName() == null ? loginBean.getUsername() : loginBean.getNickName());
        ivLevel.setImageResource(loginBean.getLevelImage());
        setViewData(tvUserScore, loginBean.getScore());
    }

    private void setViewData(TextView tv, String value) {
        if (value != null && !"".equals(value)) {
            tv.setText("高考分数：" + value);
        }
    }

    @OnClick({R.id.rl_info, R.id.ll_wish_report, R.id.ll_vip_choose, R.id.ll_zj, R.id.ll_help_center, R.id.ll_contact_kf, R.id.ll_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_info:
                openNewActivity(PersonInfoActivity.class);
                break;
            case R.id.ll_wish_report:
                openNewActivity(WishReportEnterActivity.class);
                break;
            case R.id.ll_vip_choose:
                Intent intent = new Intent();
                intent.putExtra("form", "vip_choose");
                openNewActivityByIntent(VIPActivity.class, intent);
                break;
            case R.id.ll_zj:
                Intent intent1 = new Intent();
                intent1.putExtra("form", "vip_zj");
                openNewActivityByIntent(VIPActivity.class, intent1);
                break;
            case R.id.ll_help_center:
                openNewActivity(HelpCenterActivity.class);
                break;
            case R.id.ll_contact_kf:
                openNewActivity(MultiItemRvActivity.class);
                break;
            case R.id.ll_set:
                openNewActivity(SetActivity.class);
                break;
        }
    }
}
