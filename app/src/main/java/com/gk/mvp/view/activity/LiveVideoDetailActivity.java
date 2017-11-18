package com.gk.mvp.view.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gk.R;
import com.gk.beans.LiveBean;
import com.gk.global.YXXConstants;
import com.gk.http.IService;
import com.gk.http.RetrofitUtil;
import com.gk.listener.SjmStandardVideoAllCallBackListener;
import com.gk.mvp.presenter.PresenterManager;
import com.gk.tools.SjmDensityUtil;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/23.
 */

public class LiveVideoDetailActivity extends SjmBaseActivity {
    @BindView(R.id.video_player_live_detail)
    StandardGSYVideoPlayer videoPlayerLiveDetail;

    @BindView(R.id.tv_zan)
    TextView tvZan;

    @BindView(R.id.tv_comment)
    TextView tvComment;

    @OnClick(R.id.tv_zan)
    public void tvZanClicked() {
        Drawable drawable = getResources().getDrawable(R.drawable.zan_press3x);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvZan.setCompoundDrawables(drawable, null, null, null);
        String[] textArray = tvZan.getText().toString().split("  ");
        int count = Integer.valueOf(textArray[1]) + 1;
        tvZan.setText(String.valueOf(count));
    }

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    public int getResouceId() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarTransparent();
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ym);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayerLiveDetail);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
//        videoPlayerLiveDetail.setUp(url,true,null,"视频标题");
//        videoPlayerLiveDetail.setThumbImageView(imageView);
//        videoPlayerLiveDetail.setIsTouchWiget(true);
//        videoPlayerLiveDetail.setRotateViewAuto(false);
//        videoPlayerLiveDetail.getTitleTextView().setVisibility(View.GONE);
//        videoPlayerLiveDetail.getBackButton().setVisibility(View.GONE);
//        videoPlayerLiveDetail.startPlayLogic();
        videoPlayerLiveDetail.getTitleTextView().setVisibility(View.GONE);
        videoPlayerLiveDetail.getBackButton().setVisibility(View.GONE);
        new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setIsTouchWiget(true)
                .setVideoTitle("视频")
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
        //setTitleTextView();
        videoPlayerLiveDetail.getTitleTextView().setGravity(Gravity.CENTER);
        videoPlayerLiveDetail.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });


        LiveBean liveBean = (LiveBean) getIntent().getSerializableExtra("videoId");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("videoId", liveBean.getId());
        PresenterManager.getInstance()
                .setmContext(this)
                .setmIView(this)
                .setCall(RetrofitUtil.getInstance().createReq(IService.class).getVideoCommentList(jsonObject.toJSONString()))
                .request(YXXConstants.INVOKE_API_DEFAULT_TIME);

    }

    @Override
    public <T> void fillWithData(T t, int order) {

    }

    @Override
    public <T> void fillWithNoData(T t, int order) {

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
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayerLiveDetail.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }

    public void setTitleTextView() {
        //实现头部标题跑马灯效果
        videoPlayerLiveDetail.getTitleTextView().setSelected(true);
        videoPlayerLiveDetail.getTitleTextView().setFocusable(true);
        videoPlayerLiveDetail.getTitleTextView().setFocusableInTouchMode(true);
        videoPlayerLiveDetail.getTitleTextView().setSingleLine();
        videoPlayerLiveDetail.getTitleTextView().setEllipsize(TextUtils.TruncateAt.MARQUEE);
        videoPlayerLiveDetail.getTitleTextView().setMarqueeRepeatLimit(-1);//等价于在xml配置成android:marqueeRepeatLimit ="marquee_forever"
    }

    public int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            Log.e("statusBarHeight:", "" + statusBarHeight);
            statusBarHeight = SjmDensityUtil.px2dip(this, statusBarHeight);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
