package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.SchoolZSBean;
import com.gk.tools.YxxUtils;

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
            viewHolder.tv_school_name = convertView.findViewById(R.id.tv_school_name);
            viewHolder.tv_school_address = convertView.findViewById(R.id.tv_school_address);
            viewHolder.tv_year_1 = convertView.findViewById(R.id.tv_year_1);
            viewHolder.tv_year_2 = convertView.findViewById(R.id.tv_year_2);
            viewHolder.tv_year_3 = convertView.findViewById(R.id.tv_year_3);
            viewHolder.tv_wk_year_1_num = convertView.findViewById(R.id.tv_wk_year_1_num);
            viewHolder.tv_wk_year_2_num = convertView.findViewById(R.id.tv_wk_year_2_num);
            viewHolder.tv_wk_year_3_num = convertView.findViewById(R.id.tv_wk_year_3_num);
            viewHolder.tv_lk_year_1_num = convertView.findViewById(R.id.tv_lk_year_1_num);
            viewHolder.tv_lk_year_2_num = convertView.findViewById(R.id.tv_lk_year_2_num);
            viewHolder.tv_lk_year_3_num = convertView.findViewById(R.id.tv_lk_year_3_num);

            convertView.setTag(viewHolder);
        }

        SchoolZSBean item = (SchoolZSBean) list.get(position);

        YxxUtils.setViewData(viewHolder.tv_school_name, item.getSchoolName());

        List<SchoolZSBean.RecruitPlan1Bean> recruitPlanList = item.getRecruitPlan1();
        if (null != recruitPlanList && recruitPlanList.size() > 0) {
            for (int i = 0; i < recruitPlanList.size(); i++) {
                SchoolZSBean.RecruitPlan1Bean recruitPlan1Bean = recruitPlanList.get(i);
                switch (i) {
                    case 0:
                        YxxUtils.setViewData(viewHolder.tv_year_1, recruitPlan1Bean.getYearStr());
                        YxxUtils.setViewData(viewHolder.tv_wk_year_1_num, recruitPlan1Bean.getPlanNum());
                        break;
                    case 1:
                        YxxUtils.setViewData(viewHolder.tv_year_2, recruitPlan1Bean.getYearStr());
                        YxxUtils.setViewData(viewHolder.tv_wk_year_2_num, recruitPlan1Bean.getPlanNum());
                        break;
                    case 2:
                        YxxUtils.setViewData(viewHolder.tv_year_3, recruitPlan1Bean.getYearStr());
                        YxxUtils.setViewData(viewHolder.tv_wk_year_3_num, recruitPlan1Bean.getPlanNum());
                        break;
                }
            }
        }
        List<SchoolZSBean.RecruitPlan2Bean> recruitPlan2BeanList = item.getRecruitPlan2();
        if (null != recruitPlan2BeanList && recruitPlan2BeanList.size() > 0) {
            for (int i = 0; i < recruitPlan2BeanList.size(); i++) {
                SchoolZSBean.RecruitPlan2Bean recruitPlan2Bean = recruitPlan2BeanList.get(i);
                switch (i) {
                    case 0:
                        YxxUtils.setViewData(viewHolder.tv_lk_year_1_num, recruitPlan2Bean.getPlanNum());
                        break;
                    case 1:
                        YxxUtils.setViewData(viewHolder.tv_lk_year_2_num, recruitPlan2Bean.getPlanNum());
                        break;
                    case 2:
                        YxxUtils.setViewData(viewHolder.tv_lk_year_3_num, recruitPlan2Bean.getPlanNum());
                        break;
                }
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_school_name;
        TextView tv_school_address;

        TextView tv_year_1;
        TextView tv_year_2;
        TextView tv_year_3;

        TextView tv_wk_year_1_num;
        TextView tv_wk_year_2_num;
        TextView tv_wk_year_3_num;

        TextView tv_lk_year_1_num;
        TextView tv_lk_year_2_num;
        TextView tv_lk_year_3_num;

    }
}
