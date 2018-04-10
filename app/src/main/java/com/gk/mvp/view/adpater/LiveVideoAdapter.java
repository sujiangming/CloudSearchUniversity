package com.gk.mvp.view.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LiveBean;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;


/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LiveVideoAdapter extends JdryBaseAdapter {
    public LiveVideoAdapter(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.fragment_live_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvLiveTitle = convertView.findViewById(R.id.tv_live_title);
            viewHolder.ivItem = convertView.findViewById(R.id.iv_item);
            viewHolder.tvTeacher = convertView.findViewById(R.id.tv_teacher);
            convertView.setTag(viewHolder);
        }
        LiveBean liveBean = (LiveBean) list.get(position);
        YxxUtils.setViewData(viewHolder.tvLiveTitle,liveBean.getVideoName());
        YxxUtils.setViewData(viewHolder.tvTeacher,liveBean.getSpeaker());
        GlideImageLoader.displayImage(mContext, liveBean.getVideoLogo(), viewHolder.ivItem);
        return convertView;
    }

    public static class ViewHolder {
        ImageView ivItem;
        TextView tvLiveTitle;
        TextView tvTeacher;
    }
}
