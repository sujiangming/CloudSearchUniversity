package com.gk.mvp.view.activity;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.CommentVideoBean;
import com.gk.beans.CommonBean;
import com.gk.beans.LiveBean;
import com.gk.beans.LoginBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.listener.SjmStandardVideoAllCallBackListener;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.mvp.view.adpater.CommonAdapter;
import com.gk.mvp.view.adpater.ViewHolder;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.mvp.view.custom.SjmListView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.JdryTime;
import com.gk.tools.YxxUtils;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/23.
 */

public class LiveVideoDetailActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {
    @BindView(R.id.video_player_live_detail)
    StandardGSYVideoPlayer videoPlayerLiveDetail;

    @BindView(R.id.tv_zan)
    TextView tvZan;

    @BindView(R.id.iv_zan)
    ImageView ivZan;

    @BindView(R.id.tv_comment)
    TextView tvComment;

    @BindView(R.id.btn_comment)
    Button btnComment;

    @BindView(R.id.lv_comment)
    SjmListView lvComment;

    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.tv_video_count)
    TextView tvVideoCount;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
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

    private boolean isTvZan = false;
    private boolean isIvZan = false;

    private int keyHeight = 0;//软件盘弹起后所占高度阀值


    @OnClick({R.id.tv_zan, R.id.btn_comment, R.id.iv_zan, R.id.tv_submit, R.id.tv_cancel})
    public void tvZanClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zan:
                if (!isTvZan) {
                    isTvZan = true;
                    addZanInter();
                    zanChangeStyle();
                } else {
                    toast("您已经点过赞了");
                }
                break;
            case R.id.btn_comment:
                includeComment.setVisibility(View.VISIBLE);
                etComment.requestFocus();
                YxxUtils.showSoftInputFromWindow(etComment);
                break;
            case R.id.iv_zan:
                if (!isIvZan) {
                    ivZan.setImageResource(R.drawable.zan_press3x);
                    isIvZan = true;
                    addZanInter();
                    String[] textArray = tvZan.getText().toString().split("  ");
                    int count = Integer.valueOf(textArray[1]) + 1;
                    tvZan.setText("点赞:  " + count);
                } else {
                    toast("您已经点过赞了");
                }
                break;
            case R.id.tv_submit:
                addCommentInter();
                break;
            case R.id.tv_cancel:
                includeComment.setVisibility(View.GONE);
                YxxUtils.hideSoftInputKeyboard(etComment);
                break;
        }
    }

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private LiveBean liveBean;
    private List<CommentVideoBean> commentVideoBeans = new ArrayList<>();
    private PresenterManager presenterManager = new PresenterManager().setmIView(this);
    private CommonAdapter adapter;

    @Override
    public int getResouceId() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        liveBean = (LiveBean) getIntent().getSerializableExtra("videoId");
        initKeyBoardParameter();
        initData();
        initVideo();
        initLvComment();
        addFocusInter();
        getCommentInter();
        rootView.addOnLayoutChangeListener(this);//添加layout大小发生改变监听器
    }

    private void initData() {
        tvVideoCount.setText("播放次数：" + liveBean.getAttentionNum());
        tvZan.setText("点赞:  " + liveBean.getZanNum());
        tvVideoName.setText(liveBean.getVideoName());
        tvBrief.setText(liveBean.getVideoSummary() == null ? "无简介" : liveBean.getVideoSummary());
    }

    private void initKeyBoardParameter() {
        //获取屏幕高度
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
       if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            includeComment.setVisibility(View.GONE);
            etComment.clearFocus();
            etComment.setHint("我来说两句");
            etComment.setText("");
        }
    }

    private void getCommentInter() {
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("videoId", liveBean.getId());
        String json = jsonObject.toJSONString();
        presenterManager.setCall(RetrofitUtil.getInstance().createReq(IService.class).getVideoCommentList(json))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);
    }

    private void addCommentInter() {
        String edit = etComment.getText().toString();
        if (TextUtils.isEmpty(edit)) {
            toast("请输入评论内容");
            return;
        }
        showProgress();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("videoId", liveBean.getId());
        jsonObject.put("username", LoginBean.getInstance().getUsername());
        jsonObject.put("content", YxxUtils.URLEncode(edit));
        presenterManager
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).addVideoComment(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_SECOND_TIME);
    }

    private void addZanInter() {
        showProgress();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("videoId", liveBean.getId());
        presenterManager
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).addVideoZan(jsonObject1.toJSONString()))
                .request(YXXConstants.INVOKE_API_THREE_TIME);
    }

    private void zanChangeStyle() {
        Drawable drawable = getResources().getDrawable(R.drawable.zan_press3x);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvZan.setCompoundDrawables(drawable, null, null, null);
        String[] textArray = tvZan.getText().toString().split("  ");
        int count = Integer.valueOf(textArray[1]) + 1;
        tvZan.setText("点赞:  " + count);
    }

    private void addFocusInter() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("videoId", liveBean.getId());
        presenterManager
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).addVideoAttention(jsonObject1.toJSONString()))
                .request(YXXConstants.INVOKE_API_FORTH_TIME);
    }

    private void initLvComment() {
        adapter = new CommonAdapter<CommentVideoBean>(this, commentVideoBeans, R.layout.video_comment) {
            @Override
            public void convert(ViewHolder viewHolder, CommentVideoBean item) {
                CircleImageView imageView = viewHolder.getView(R.id.iv_user_icon);
                viewHolder.setText(R.id.tv_user, item.getNickName());
                viewHolder.setText(R.id.tv_time, JdryTime.format(JdryTime.getFullDate(JdryTime.getFullTimeBySec(item.getCreateTime()))));
                viewHolder.setText(R.id.tv_comment_content, item.getContent());
                int tmp = commentVideoBeans.indexOf(item) % 2;
                if (tmp == 0) { //偶数
                    viewHolder.setBackgroundColor(R.id.rl_comment_root, 0xFFF2F2F2);
                }
                if (!TextUtils.isEmpty(item.getHeadImg())) {
                    GlideImageLoader.displayByImgRes(LiveVideoDetailActivity.this, item.getHeadImg(), imageView, R.drawable.my);
                } else {
                    imageView.setImageResource(R.drawable.my);
                }
            }
        };
        lvComment.setAdapter(adapter);
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        hideProgress();
        final CommonBean commonBean = (CommonBean) t;
        assert commonBean != null;
        assert null != commonBean.getData();
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                commentVideoBeans = JSON.parseArray(commonBean.getData().toString(), CommentVideoBean.class);
                tvComment.setText("评论:  " + commentVideoBeans.size());
                adapter.setItems(commentVideoBeans);
                lvComment.smoothScrollToPosition(lvComment.getFirstVisiblePosition());
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                toast(commonBean.getMessage());
                getCommentInter();
                includeComment.setVisibility(View.GONE);
                YxxUtils.hideSoftInputKeyboard(etComment);
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                toast(commonBean.getMessage());
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
    }

    private void initVideo() {
        //String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        String url = liveBean.getVideoUrl();
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideImageLoader.displayImage(this, liveBean.getVideoLogo(), imageView);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayerLiveDetail);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle(liveBean.getVideoName())
                .setCacheWithPlay(true)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setNeedShowWifiTip(false)
                .setSeekRatio(1)
                .setStandardVideoAllCallBack(new SjmStandardVideoAllCallBackListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .build(videoPlayerLiveDetail);

        videoPlayerLiveDetail.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayerLiveDetail.startWindowFullscreen(LiveVideoDetailActivity.this, true, true);
            }
        });

        videoPlayerLiveDetail.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
        videoPlayerLiveDetail.getTitleTextView().setGravity(Gravity.CENTER);
        videoPlayerLiveDetail.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity(LiveVideoDetailActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null) orientationUtils.releaseListener();
        if (null != presenterManager && null != presenterManager.getCall()) {
            presenterManager.getCall().cancel();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayerLiveDetail.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }

//    public void setTitleTextView() {
//        //实现头部标题跑马灯效果
//        videoPlayerLiveDetail.getTitleTextView().setSelected(true);
//        videoPlayerLiveDetail.getTitleTextView().setFocusable(true);
//        videoPlayerLiveDetail.getTitleTextView().setFocusableInTouchMode(true);
//        videoPlayerLiveDetail.getTitleTextView().setSingleLine();
//        videoPlayerLiveDetail.getTitleTextView().setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        videoPlayerLiveDetail.getTitleTextView().setMarqueeRepeatLimit(-1);//等价于在xml配置成android:marqueeRepeatLimit ="marquee_forever"
//    }
}
