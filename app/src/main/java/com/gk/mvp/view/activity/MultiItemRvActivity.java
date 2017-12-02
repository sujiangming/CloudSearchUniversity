package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

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

    private LoadMoreWrapper mLoadMoreWrapper;
    //private List<ChatMessage> mDatas = new ArrayList<>();
    private List<ChatMessageBean> mDatas = new ArrayList<>();

    private JSONObject jsonObject = new JSONObject();
    private ChatAdapterForRv adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBarView, "客服中心", 0);
        initKeyBoardParameter();

        jsonObject.put("username", LoginBean.getInstance().getUsername());

        getMyMessageList();
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapterForRv(this, mDatas);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MultiItemRvActivity.this, "Click:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MultiItemRvActivity.this, "LongClick:" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mRecyclerView.setAdapter(adapter);
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
                initAdapter();
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                toast(commonBean.getMessage());
                SendMsgBean sendMsgBean = JSON.parseObject(commonBean.getData().toString(), SendMsgBean.class);
                mDatas.add(addChatMessageUser(sendMsgBean));
                adapter.notifyDataSetChanged();
                delayShowClientInfo();
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast((String) t);
        hideProgress();
    }

    private void delayShowClientInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.add(addChatMessageClient());
                adapter.notifyDataSetChanged();
                mRecyclerView.scrollTo(0, mRecyclerView.getHeight());
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

    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.include_comment)
    View includeComment;

    @OnClick({R.id.btn_comment, R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:
                includeComment.setVisibility(View.VISIBLE);
                etComment.setFocusable(true);
                etComment.setFocusableInTouchMode(true);
                break;
            case R.id.tv_cancel:
                includeComment.setVisibility(View.GONE);
                hideSoftKey();
                break;
            case R.id.tv_submit:
                includeComment.setVisibility(View.GONE);
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
                break;
        }
    }

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    private void hideSoftKey() {
        //隐藏软盘
        InputMethodManager imm = (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //editText失去焦点
        etComment.clearFocus();
        //清空数据
        etComment.setHint("我来说两句");
        etComment.setText("");
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            etComment.setHint("我来说两句");
            etComment.setText("");
        }
    }
}
