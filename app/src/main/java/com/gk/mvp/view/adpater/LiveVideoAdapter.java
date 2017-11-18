package com.gk.mvp.view.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LiveBean;


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
            viewHolder.tvLiveTitle = (TextView) convertView.findViewById(R.id.tv_live_title);
            viewHolder.ivItem = (ImageView) convertView.findViewById(R.id.iv_item);
            viewHolder.tvTeacher = (TextView) convertView.findViewById(R.id.tv_teacher);
            //viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_no_data_tip);
            convertView.setTag(viewHolder);
        }
        LiveBean liveBean = (LiveBean) list.get(position);
        viewHolder.tvLiveTitle.setText(liveBean.getVideoName());
        viewHolder.tvTeacher.setText(liveBean.getSpeaker());
        return convertView;
    }

    public static class ViewHolder {
        ImageView ivItem;
        TextView tvLiveTitle;
        TextView tvTeacher;
        //LinearLayout linearLayout;
    }
}
