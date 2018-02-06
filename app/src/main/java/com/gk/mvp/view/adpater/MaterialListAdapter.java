package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.CourseTypeEnum;
import com.gk.beans.MaterialItemBean;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryTime;

/**
 * Created by JDRY-SJM on 2018/1/16.
 */

public class MaterialListAdapter extends JdryBaseAdapter {

    private GlideImageLoader glideImageLoader = new GlideImageLoader();
    private int type = 0;

    public MaterialListAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null != convertView) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.material_item, null);
            viewHolder.tv_live_title = convertView.findViewById(R.id.tv_live_title);
            viewHolder.tv_time_content = convertView.findViewById(R.id.tv_time_content);
            viewHolder.tv_km_content = convertView.findViewById(R.id.tv_km_content);
            viewHolder.tv_type_content = convertView.findViewById(R.id.tv_type_content);
            viewHolder.iv_item = convertView.findViewById(R.id.iv_item);
            convertView.setTag(viewHolder);
        }

        MaterialItemBean.DataBean item = (MaterialItemBean.DataBean) list.get(position);

        viewHolder.tv_live_title.setText(item.getName());
        viewHolder.tv_time_content.setText(JdryTime.getFullTimeBySec(item.getUploadTime()));
        viewHolder.tv_km_content.setText(CourseTypeEnum.getSubjectTypeName(item.getCourse()));
        if (0 == type) {
            setTitle(viewHolder, item.getType());
        } else {
            setTitle(viewHolder, type);
        }

        glideImageLoader.displayImage(mContext, item.getLogo(), viewHolder.iv_item);

        return convertView;
    }

    public static class ViewHolder {
        TextView tv_live_title;
        TextView tv_time_content;
        TextView tv_km_content;

        TextView tv_type_content;

        ImageView iv_item;

    }

    private void setTitle(ViewHolder viewHolder, int type) {
        switch (type) {
            case 1:
                viewHolder.tv_type_content.setText("名师讲堂");
                break;
            case 2:
                viewHolder.tv_type_content.setText("历年真题");
                break;
            case 3:
                viewHolder.tv_type_content.setText("模拟试卷");
                break;
        }
    }
}
