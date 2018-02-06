package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.SchoolZSBean;

import java.util.List;

/**
 * Created by JDRY-SJM on 2018/1/16.
 */

public class SchoolZSPlanAdapter extends JdryBaseAdapter {

    public SchoolZSPlanAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null != convertView) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.school_zhaosheng_list_item, null);
            viewHolder.tv_school_mark_0 = convertView.findViewById(R.id.tv_school_plan);
            viewHolder.tv_school_mark_1 = convertView.findViewById(R.id.tv_school_address);
            viewHolder.tv_school_mark_2 = convertView.findViewById(R.id.tv_school_name);
            viewHolder.tv_bk_num = convertView.findViewById(R.id.tv_bk_num);
            viewHolder.tv_zk_num = convertView.findViewById(R.id.tv_zk_num);
            viewHolder.tv_tiqian = convertView.findViewById(R.id.tv_tiqian);
            convertView.setTag(viewHolder);
        }

        SchoolZSBean item = (SchoolZSBean) list.get(position);

        viewHolder.tv_school_mark_0.setText("2017年招生计划");
        viewHolder.tv_school_mark_1.setText(item.getSchoolName());
        viewHolder.tv_school_mark_2.setText(item.getSchoolName() == null ? "未知" : item.getSchoolName());

        List<SchoolZSBean.RecruitPlan> recruitPlanList = item.getRecruitPlan();
        if (null != recruitPlanList && recruitPlanList.size() > 0) {
            for (int i = 0; i < recruitPlanList.size(); i++) {
                switch (i) {
                    case 0:
                        viewHolder.tv_bk_num.setText(item.getRecruitPlan().get(i).getPlanNum() + "");
                        break;
                    case 1:
                        viewHolder.tv_zk_num.setText(item.getRecruitPlan().get(i).getPlanNum() + "");
                        break;
                    case 2:
                        viewHolder.tv_tiqian.setText(item.getRecruitPlan().get(i).getPlanNum() + "");
                        break;
                }
            }
        } else {
            viewHolder.tv_bk_num.setText("0");
            viewHolder.tv_zk_num.setText("0");
            viewHolder.tv_tiqian.setText("0");
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_school_mark_0;
        TextView tv_school_mark_1;
        TextView tv_school_mark_2;

        TextView tv_bk_num;
        TextView tv_zk_num;
        TextView tv_tiqian;

    }
}
