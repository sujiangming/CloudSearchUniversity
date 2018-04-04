package com.gk.mvp.view.adpater;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.QuerySchoolBean;
import com.gk.beans.UniversityAreaEnum;
import com.gk.beans.UniversityTypeEnum;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;

/**
 * Created by JDRY-SJM on 2018/1/16.
 */

public class QuerySchoolAdapter extends JdryBaseAdapter {

    public QuerySchoolAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null != convertView) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.query_school_list_item, null);
            viewHolder.tv_school_mark_0 = convertView.findViewById(R.id.tv_school_mark_0);
            viewHolder.tv_school_mark_1 = convertView.findViewById(R.id.tv_school_mark_1);
            viewHolder.tv_school_mark_2 = convertView.findViewById(R.id.tv_school_mark_2);
            viewHolder.tv_school_name = convertView.findViewById(R.id.tv_school_name);
            viewHolder.tv_school_type = convertView.findViewById(R.id.tv_school_type);
            viewHolder.tv_school_level = convertView.findViewById(R.id.tv_school_level);
            viewHolder.tv_school_address = convertView.findViewById(R.id.tv_school_address);
            viewHolder.iv_query_item = convertView.findViewById(R.id.iv_query_item);
            convertView.setTag(viewHolder);
        }

        QuerySchoolBean.DataBean dataBean = (QuerySchoolBean.DataBean) list.get(position);
        int isDoubleTop = dataBean.getIsDoubleTop();
        int isNef = dataBean.getIsNef();
        int isToo = dataBean.getIsToo();

        if (isNef == 1) {
            viewHolder.tv_school_mark_0.setVisibility(View.VISIBLE);
            viewHolder.tv_school_mark_0.setText("985");
        } else {
            viewHolder.tv_school_mark_0.setVisibility(View.GONE);
        }
        if (isDoubleTop == 1) {
            viewHolder.tv_school_mark_1.setVisibility(View.VISIBLE);
            viewHolder.tv_school_mark_1.setText("211");
        } else {
            viewHolder.tv_school_mark_1.setVisibility(View.GONE);
        }
        if (isToo == 1) {
            viewHolder.tv_school_mark_2.setVisibility(View.VISIBLE);
            viewHolder.tv_school_mark_2.setText("双一流");
        } else {
            viewHolder.tv_school_mark_2.setVisibility(View.GONE);
        }

        GlideImageLoader.displayByImgRes(mContext, dataBean.getSchoolLogo(), viewHolder.iv_query_item, R.drawable.gaoxiaozhanweitu);

        YxxUtils.setViewData(viewHolder.tv_school_name,dataBean.getSchoolName());
        YxxUtils.setViewData(viewHolder.tv_school_type,getPici(dataBean.getSchoolBatch()));

        if (!TextUtils.isEmpty(dataBean.getSchoolCategory())) {
            int category = Integer.valueOf(dataBean.getSchoolCategory());
            viewHolder.tv_school_level.setText(UniversityTypeEnum.getName(category));
        }

        if(!TextUtils.isEmpty(dataBean.getSchoolArea())){
            viewHolder.tv_school_address.setText(UniversityAreaEnum.getName(Integer.valueOf(dataBean.getSchoolArea())));
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView tv_school_mark_0;
        TextView tv_school_mark_1;
        TextView tv_school_mark_2;

        TextView tv_school_name;
        TextView tv_school_type;
        TextView tv_school_level;
        TextView tv_school_address;

        ImageView iv_query_item;

    }

    private String getPici(String string) {
        String ret = "";
        switch (string) {
            case "1":
                ret = "提前批";
                break;
            case "2":
                ret = "一批";
                break;
            case "3":
                ret = "二批";
                break;
        }
        return ret;
    }
}
