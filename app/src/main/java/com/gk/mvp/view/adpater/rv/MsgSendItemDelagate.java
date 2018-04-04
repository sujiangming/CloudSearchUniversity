package com.gk.mvp.view.adpater.rv;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.gk.R;
import com.gk.beans.ChatMessageBean;
import com.gk.beans.LoginBean;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.tools.GlideImageLoader;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessageBean> {

    Context context;

    public MsgSendItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(ChatMessageBean item, int position) {
        return !TextUtils.isEmpty(item.getFromUser());//!item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessageBean chatMessage, int position) {
        holder.setText(R.id.chat_send_content, chatMessage.getMessage());
        holder.setText(R.id.chat_send_name, LoginBean.getInstance().getCname() == null ? LoginBean.getInstance().getUsername() : LoginBean.getInstance().getCname());
        GlideImageLoader.displayImage(context, chatMessage.getFromUserImg(), (ImageView) holder.getView(R.id.chat_send_icon));
        if (TextUtils.isEmpty(chatMessage.getFromUserImg())) {
            holder.setImageResource(R.id.chat_from_icon, R.drawable.ic_zhuanjia3x);
        } else {
            GlideImageLoader.displayImage(context, LoginBean.getInstance().getHeadImg(), (CircleImageView) holder.getView(R.id.chat_send_icon));
        }
    }
}
