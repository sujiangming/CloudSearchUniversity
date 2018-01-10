package com.gk.mvp.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.gk.R;
import com.gk.beans.LoginBean;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.SchoolRankBean;
import com.gk.mvp.view.activity.LqRiskTestResultActivity;
import com.gk.mvp.view.activity.VIPActivity;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/12/20.
 */

public class SchoolDetailBriefFragment extends SjmBaseFragment {

    @BindView(R.id.expand_text_1)
    View expand_text_1;

    @BindView(R.id.expand_text_2)
    View expand_text_2;

    @OnClick(R.id.tv_vip)
    public void vipClicked() {
        showUpgradeDialog();
    }

    private QuerySchoolBean.DataBean schoolBean;
    private String flagStr;
    private SchoolRankBean schoolRankBean;
    private String schoolName;

    @Override
    public int getResourceId() {
        return R.layout.fragment_school_detail_brief;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        ExpandableTextView expandableTextView1 = expand_text_1.findViewById(R.id.expand_text_view);
        ExpandableTextView expandableTextView2 = expand_text_2.findViewById(R.id.expand_text_view);
        flagStr = getArguments().getString("flag");
        if (flagStr != null && "query".equals(flagStr)) {
            schoolBean = (QuerySchoolBean.DataBean) getArguments().getSerializable("schoolBean");
            expandableTextView1.setText(schoolBean.getStuRecruitBrochure());
            expandableTextView2.setText(schoolBean.getSchoolProfile());
            schoolName = schoolBean.getSchoolName();
        } else {
            schoolRankBean = (SchoolRankBean) getArguments().getSerializable("schoolBean");
            //expandableTextView1.setText(schoolRankBean.getStuRecruitBrochure());
            expandableTextView2.setText(schoolRankBean.getSchoolProfile());
            schoolName = schoolRankBean.getSchoolName();
        }
    }

    private void showUpgradeDialog() {
        int vip = LoginBean.getInstance().getVipLevel();
        if (vip <= 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle("温馨提示");
            builder.setMessage("VIP会员才能使用，您想成为VIP会员吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    openNewActivity(VIPActivity.class);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("flag", 1);//高校
            intent.putExtra("aim", schoolName);
            openNewActivityByIntent(LqRiskTestResultActivity.class, intent);
        }
    }
}
