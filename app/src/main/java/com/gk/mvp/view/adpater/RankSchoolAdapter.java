package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.SchoolRankBean;
import com.gk.beans.UniversityAreaBean;
import com.gk.beans.UniversityAreaBeanDao;
import com.gk.global.YXXApplication;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDRY-SJM on 2018/1/16.
 */

public class RankSchoolAdapter extends JdryBaseAdapter {


    public RankSchoolAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null != convertView) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.school_rank_list_item, null);
            viewHolder.tv_school_mark_0 = convertView.findViewById(R.id.tv_school_mark_0);
            viewHolder.tv_school_mark_1 = convertView.findViewById(R.id.tv_school_mark_1);
            viewHolder.tv_school_mark_2 = convertView.findViewById(R.id.tv_school_mark_2);
            viewHolder.tv_school_name = convertView.findViewById(R.id.tv_school_name);
            viewHolder.tv_school_type = convertView.findViewById(R.id.tv_school_type);
            viewHolder.tv_school_level = convertView.findViewById(R.id.tv_school_level);
            viewHolder.tv_school_address = convertView.findViewById(R.id.tv_school_address);
            viewHolder.tv_school_rank_0 = convertView.findViewById(R.id.tv_school_rank_0);
            viewHolder.tv_school_rank_1 = convertView.findViewById(R.id.tv_school_rank_1);
            viewHolder.iv_query_item = convertView.findViewById(R.id.iv_query_item);
            convertView.setTag(viewHolder);
        }

        SchoolRankBean dataBean = (SchoolRankBean) list.get(position);
        String isDoubleTop = dataBean.getIsDoubleTop();
        String isNef = dataBean.getIsNef();
        String isToo = dataBean.getIsToo();

        if (isNef != null && !"".equals(isNef) && "1".equals(isNef)) {
            viewHolder.tv_school_mark_0.setVisibility(View.VISIBLE);
            viewHolder.tv_school_mark_0.setText("985");
        }
        if (isDoubleTop != null && !"".equals(isDoubleTop) && "1".equals(isDoubleTop)) {
            viewHolder.tv_school_mark_1.setVisibility(View.VISIBLE);
            viewHolder.tv_school_mark_1.setText("211");
        }
        if (isToo != null && !"".equals(isToo) && "1".equals(isToo)) {
            viewHolder.tv_school_mark_2.setVisibility(View.VISIBLE);
            viewHolder.tv_school_mark_2.setText("双一流");
        }

        GlideImageLoader.displayByImgRes(mContext, dataBean.getSchoolLogo(), viewHolder.iv_query_item, R.drawable.gaoxiaozhanweitu);

        viewHolder.tv_school_name.setText(dataBean.getSchoolName());
        viewHolder.tv_school_type.setText(getPici(dataBean.getSchoolBatch()));
        viewHolder.tv_school_level.setText("1".equals(dataBean.getSchoolCategory()) ? "综合类" : "教育类");
        UniversityAreaBean universityAreaBean = YXXApplication.getDaoSession().getUniversityAreaBeanDao().queryBuilder().where(UniversityAreaBeanDao.Properties.Index.eq(dataBean.getSchoolArea())).unique();
        if (null != universityAreaBean) {
            YxxUtils.setViewData(viewHolder.tv_school_address, universityAreaBean.getName());
        } else {
            viewHolder.tv_school_address.setText("");
        }
        List<TextView> viewList = new ArrayList<>();
        viewList.add(viewHolder.tv_school_rank_0);
        viewList.add(viewHolder.tv_school_rank_1);

        List<SchoolRankBean.RankingsBean> rankList = dataBean.getRankings();

        if (rankList != null && rankList.size() > 0) {
            for (int i = 0; i < rankList.size(); i++) {
                if (i <= 1) {
                    viewList.get(i).setVisibility(View.VISIBLE);
                    viewList.get(i).setText(getRankInfo(rankList.get(i).getUniYear(), rankList.get(i).getUniRanking()));
                }
            }
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

        TextView tv_school_rank_0;
        TextView tv_school_rank_1;

        ImageView iv_query_item;

    }

    private String getRankInfo(String year, String rank) {
        return year + "年：" + rank + " 名";
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
