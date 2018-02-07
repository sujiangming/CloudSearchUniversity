package com.gk.mvp.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.ChatMessageBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.SendMsgBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.rv.ChatAdapterForRv;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MultiItemRvActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {

    @BindView(R.id.top_bar)
    TopBarView topBarView;

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.include_comment)
    View includeComment;

    @BindView(R.id.root_view)
    View rootView;

    @OnClick({R.id.btn_comment, R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:
                includeComment.setVisibility(View.VISIBLE);
                YxxUtils.showSoftInputFromWindow(etComment);
                break;
            case R.id.tv_cancel:
                includeComment.setVisibility(View.GONE);
                YxxUtils.hideSoftInputKeyboard(etComment);
                etComment.setHint("我来说两句");
                etComment.setText("");
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(etComment.getText())) {
                    toast("请输入内容");
                    return;
                }
                showProgress();
                jsonObject.put("fromUser", LoginBean.getInstance().getUsername());
                jsonObject.put("messageType", 1);
                jsonObject.put("message", YxxUtils.URLEncode(etComment.getText().toString()));
                PresenterManager.getInstance()
                        .setmIView(this)
                        .setCall(RetrofitUtil.getInstance()
                                .createReq(IService.class).sendMessage(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_SECOND_TIME);

                includeComment.setVisibility(View.GONE);
                YxxUtils.hideSoftInputKeyboard(etComment);
                etComment.setHint("我来说两句");
                etComment.setText("");

                break;
        }
    }

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    private List<ChatMessageBean> mDatas = new ArrayList<>();

    private JSONObject jsonObject = new JSONObject();
    private ChatAdapterForRv adapter;

    private int onlyOneTime = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBarView, "客服中心", 0);
        initKeyBoardParameter();
        rootView.addOnLayoutChangeListener(this);
        initAdapter();
        delayShowClientInfo();
        delayShowMessageInfo();
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapterForRv(this, mDatas);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.scrollToPosition(adapter.getItemCount());
    }

    private void getMyMessageList() {
        showProgress();
        PresenterManager.getInstance()
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class)
                        .getMyMessageList(jsonObject.toJSONString()))
                .request();
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        hideProgress();
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                List<ChatMessageBean> chatMessageBeans = JSON.parseArray(commonBean.getData().toString(), ChatMessageBean.class);
                if (chatMessageBeans == null || chatMessageBeans.size() == 0) {
                    toast(commonBean.getMessage());
                    return;
                }
                mDatas.addAll(chatMessageBeans);
                mDatas = removeDuplicate(mDatas);
                adapter.notifyDataSetChanged();
                //initAdapter();
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                toast(commonBean.getMessage());
                SendMsgBean sendMsgBean = JSON.parseObject(commonBean.getData().toString(), SendMsgBean.class);
                mDatas.add(addChatMessageUser(sendMsgBean));
                adapter.notifyDataSetChanged();
                if (onlyOneTime == 0) {
                    onlyOneTime = 1;
                    delayShowClientInfo();
                }
                mRecyclerView.scrollToPosition(adapter.getItemCount());
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void delayShowMessageInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jsonObject.put("username", LoginBean.getInstance().getUsername());
                getMyMessageList();
            }
        }, 3000);
    }

    private void delayShowClientInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.add(addChatMessageClient());
                adapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(adapter.getItemCount());
            }
        }, 1500);
    }

    private ChatMessageBean addChatMessageClient() {
        double coming = Math.random() > 100 ? 100 : Math.random();
        int random = (int) coming;
        ChatMessageBean chatMessageBean = new ChatMessageBean();
        chatMessageBean.setCreateTime(new Date().getTime());
        chatMessageBean.setToUser("");
        chatMessageBean.setToUserImg("");
        chatMessageBean.setToUserName("客服" + random);
        chatMessageBean.setMessage("感谢您的提问，稍后将有专家为您解答！");
        return chatMessageBean;
    }

    private ChatMessageBean addChatMessageUser(SendMsgBean sendMsgBean) {
        ChatMessageBean chatMessageBean = new ChatMessageBean();
        chatMessageBean.setCreateTime(sendMsgBean.getCreateTime());
        chatMessageBean.setFromUser(sendMsgBean.getFromUser());
        chatMessageBean.setId(sendMsgBean.getId());
        chatMessageBean.setMessage(sendMsgBean.getMessage());
        chatMessageBean.setIsRead(sendMsgBean.getIsRead());
        chatMessageBean.setMessageType(sendMsgBean.getMessageType());
        chatMessageBean.setToUser(sendMsgBean.getToUser());
        chatMessageBean.setFromUserImg(LoginBean.getInstance().getHeadImg());
        chatMessageBean.setFromUserName(LoginBean.getInstance().getUsername());
        return chatMessageBean;
    }

    public List<ChatMessageBean> removeDuplicate(List<ChatMessageBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            etComment.setHint("我来说两句");
            etComment.setText("");
            includeComment.setVisibility(View.GONE);
        }
    }
}
