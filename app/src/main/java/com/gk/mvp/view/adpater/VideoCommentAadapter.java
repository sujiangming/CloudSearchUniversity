package com.gk.mvp.view.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.CommentVideoBean;
import com.gk.tools.JdryTime;

/**
 * Created by JDRY-SJM on 2017/11/19.
 */

public class VideoCommentAadapter extends JdryBaseAdapter {
    public VideoCommentAadapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.video_comment, null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_user_icon);
            viewHolder.tvUserName = convertView.findViewById(R.id.tv_user);
            viewHolder.tvTime = convertView.findViewById(R.id.tv_time);
            viewHolder.tvComment = convertView.findViewById(R.id.tv_comment_content);
            viewHolder.rootView = convertView.findViewById(R.id.rl_comment_root);
            convertView.setTag(viewHolder);
        }

        CommentVideoBean commentVideoBean = (CommentVideoBean) list.get(position);
        int tmp = position % 2;
        if (tmp == 1) { //奇数
            viewHolder.rootView.setBackgroundColor(0xFFF2F2F2);
        }
        viewHolder.tvUserName.setText(commentVideoBean.getNickName());
        viewHolder.tvTime.setText(JdryTime.format(JdryTime.getFullDate(JdryTime.getFullTimeBySec(commentVideoBean.getCreateTime()))));
        viewHolder.tvComment.setText(commentVideoBean.getContent());

        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout rootView;
        ImageView imageView;
        TextView tvUserName;
        TextView tvTime;
        TextView tvComment;
    }
}
