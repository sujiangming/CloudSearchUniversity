package com.gk.mvp.view.adpater.rv;

import android.content.Context;

import com.gk.beans.ChatMessageBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessageBean>
{
    public ChatAdapterForRv(Context context, List<ChatMessageBean> datas)
    {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelagate(context));
        addItemViewDelegate(new MsgComingItemDelagate(context));
    }
}
