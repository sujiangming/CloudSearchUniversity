package com.gk.mvp.view.adpater.rv;

import android.content.Context;
import android.text.TextUtils;

import com.gk.R;
import com.gk.beans.ChatMessageBean;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.tools.GlideImageLoader;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessageBean> {
    Context context;

    public MsgComingItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ChatMessageBean item, int position) {
        return TextUtils.isEmpty(item.getFromUser());//item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessageBean chatMessage, int position) {
        holder.setText(R.id.chat_from_content, chatMessage.getMessage());
        holder.setText(R.id.chat_from_name, chatMessage.getToUserName());
        if(TextUtils.isEmpty(chatMessage.getToUserImg())){
            holder.setImageResource(R.id.chat_from_icon,R.drawable.ym);
        }else{
            GlideImageLoader.displayImage(context, chatMessage.getToUserImg(), (CircleImageView) holder.getView(R.id.chat_from_icon));
        }
    }
}
