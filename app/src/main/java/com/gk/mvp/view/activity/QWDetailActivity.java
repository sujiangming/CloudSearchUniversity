package com.gk.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommonBean;
import com.gk.beans.LoginBean;
import com.gk.beans.QWAnsBean;
import com.gk.beans.QWListBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.SjmListView;
import com.gk.mvp.view.custom.TopBarView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryTime;
import com.gk.tools.YxxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/11/20.
 */

public class QWDetailActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {
    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.civ_header)
    CircleImageView civHeader;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_care)
    TextView tvCare;
    @BindView(R.id.tv_care_count)
    TextView tvCareCount;
    @BindView(R.id.tv_scan_count)
    TextView tvScanCount;
    @BindView(R.id.tv_view_count)
    TextView tvViewCount;
    @BindView(R.id.lv_qw_jd)
    SjmListView lvQwJd;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_comment)
    EditText etComment;

    @BindView(R.id.include_comment)
    View includeComment;

    @BindView(R.id.root_view)
    View rootView;

    private int keyHeight = 0;//软件盘弹起后所占高度阀值
    private QWListBean qwListBean;
    private List<QWAnsBean> qwAnsBeans = new ArrayList<>();
    private JSONObject jsonObject = new JSONObject();
    private int addAnsFlag = 0;
    private CommonAdapter adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_qw_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "问答详情", 0);
        qwListBean = (QWListBean) getIntent().getSerializableExtra("bean");
        initKeyBoardParameter();
        initAdapter();
        initData();
        rootView.addOnLayoutChangeListener(this);
    }

    private void initData() {
        if (qwListBean == null) {
            return;
        }
        jsonObject.put("queId", qwListBean.getQueId());
        GlideImageLoader.displayImage(this, qwListBean.getHeadImg(), civHeader);
        tvNickName.setText(qwListBean.getNickName() == null ? "未知" : qwListBean.getNickName());
        YxxUtils.setViewData(tvTimeRight, JdryTime.getDayHourMinBySec(qwListBean.getQueTime()));
        YxxUtils.setViewData(tvContent, qwListBean.getQueContent());
        YxxUtils.setViewData(tvTitle, qwListBean.getQueTitle());
        YxxUtils.setViewData(tvScanCount, qwListBean.getViewTimes() + "");
        YxxUtils.setViewData(tvCareCount, qwListBean.getAttentionTimes() + "");
        addViewTimes();
        getAnswerList();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<QWAnsBean>(this, qwAnsBeans, R.layout.qw_detail_item) {
            @Override
            public void convert(ViewHolder viewHolder, QWAnsBean item) {
                if (qwAnsBeans.indexOf(item) == 0) {
                    viewHolder.setBackgroundColor(R.id.tv_top_line, 0x00000000);
                }
                viewHolder.setText(R.id.tv_nick_name, item.getNickName());
                viewHolder.setText(R.id.tv_time_right, JdryTime.getDayHourMinBySec(item.getAnsTime()));
                viewHolder.setText(R.id.tv_content, item.getAnsContent());
                GlideImageLoader.displayImage(QWDetailActivity.this, item.getHeadImg(), (ImageView) viewHolder.getView(R.id.civ_header));
            }
        };
        lvQwJd.setAdapter(adapter);
    }

    private PresenterManager presenterManager = new PresenterManager().setmIView(this);

    private void getAnswerList() {
        showProgress();
        presenterManager.setCall(RetrofitUtil.getInstance()
                .createReq(IService.class).getAnswerList(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }

    private void addViewTimes() {
        presenterManager.setCall(RetrofitUtil.getInstance()
                .createReq(IService.class).addViewTimes(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_THREE_TIME);
    }

    private void addAttentionTimes() {
        showProgress();
        presenterManager.setCall(RetrofitUtil.getInstance()
                .createReq(IService.class).addAttentionTimes(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_FORTH_TIME);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                qwAnsBeans = JSON.parseArray(commonBean.getData().toString(), QWAnsBean.class);
                adapter.setItems(qwAnsBeans);
                if (addAnsFlag == 1) {
                    hideSoftKey();
                }
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                toast(commonBean.getMessage());
                getAnswerList();
                break;
            case YXXConstants.INVOKE_API_THREE_TIME: //增加浏览次数
                tvScanCount.setText((qwListBean.getViewTimes() + 1) + "");
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME: //我的关注接口
                toast(commonBean.getMessage());
                tvCareCount.setText((qwListBean.getAttentionTimes() + 1) + "");
                break;
        }
        hideProgress();
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
        if (order == YXXConstants.INVOKE_API_SECOND_TIME) {
            hideSoftKey();
        }
    }

    /**
     * 初始化软键盘弹出和关闭时的参数
     */
    private void initKeyBoardParameter() {
        //获取屏幕高度
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    private void hideSoftKey() {
        //隐藏软盘
        InputMethodManager imm = (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
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
            includeComment.setVisibility(View.GONE);
            etComment.setText("");
            etComment.clearFocus();
        }
    }

    @OnClick({R.id.btn_comment, R.id.tv_care, R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:
                includeComment.setVisibility(View.VISIBLE);
                etComment.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputManager != null;
                inputManager.showSoftInput(etComment, 0);
                break;
            case R.id.tv_care:
                addAttentionTimes();
                break;
            case R.id.tv_cancel:
                includeComment.setVisibility(View.GONE);
                etComment.clearFocus();
                hideSoftKey();
                break;
            case R.id.tv_submit:
                includeComment.setVisibility(View.GONE);
                if (TextUtils.isEmpty(etComment.getText())) {
                    toast("请输入内容");
                    return;
                }
                showProgress();
                addAnsFlag = 1;
                jsonObject.put("username", LoginBean.getInstance().getUsername());
                jsonObject.put("ansContent", YxxUtils.URLEncode(etComment.getText().toString()));
                presenterManager.setCall(RetrofitUtil.getInstance()
                        .createReq(IService.class).addAnswer(jsonObject.toJSONString()))
                        .request(YXXConstants.INVOKE_API_SECOND_TIME);
                break;
        }
    }
}
