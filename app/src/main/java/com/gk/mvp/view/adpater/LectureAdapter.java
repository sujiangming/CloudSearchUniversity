package com.gk.mvp.view.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.mvp.view.custom.RichText;


/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class LectureAdapter extends JdryBaseAdapter {

    public LectureAdapter(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else{
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_lecture_item,null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_lecture_title);
            viewHolder.tvNum = (RichText) convertView.findViewById(R.id.rt_lecture);
            viewHolder.ivSke = (ImageView) convertView.findViewById(R.id.iv_lecture_ske);

            convertView.setTag(viewHolder);
        }

        viewHolder.tvTitle.setText(list.get(position).toString());
        viewHolder.tvNum.setText(String.valueOf(position + 10));

        return convertView;
    }

    public static class ViewHolder{
        RichText tvNum;
        TextView tvTitle;
        ImageView ivSke;
    }
}
