package com.gk.mvp.view.activity;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.MaterialItemBean;
import com.gk.listener.SjmStandardVideoAllCallBackListener;
import com.gk.tools.GlideImageLoader;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/23.
 */

public class MsJtDetailActivity extends SjmBaseActivity {
    @BindView(R.id.video_player_live_detail)
    StandardGSYVideoPlayer videoPlayerLiveDetail;

    @BindView(R.id.tv_zan)
    TextView tvZan;

    @BindView(R.id.tv_comment)
    TextView tvComment;

    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.tv_video_count)
    TextView tvVideoCount;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.root_view)
    View rootView;

    private boolean isTvZan = false;
    private boolean isIvZan = false;


    @OnClick(R.id.tv_zan)
    public void tvZanClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zan:
                if (!isTvZan) {
                    isTvZan = true;
                    zanChangeStyle();
                } else {
                    toast("您已经点过赞了");
                }
                break;
        }
    }

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private MaterialItemBean.DataBean liveBean;

    @Override
    public int getResouceId() {
        return R.layout.activity_msjt_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        liveBean = (MaterialItemBean.DataBean) getIntent().getSerializableExtra("bean");
        initData();
        initVideo();
    }

    private void initData() {
        tvVideoCount.setText("播放次数：" + liveBean.getAttentionNum());
        tvZan.setText("点赞:  0");
        tvVideoName.setText("" + liveBean.getName());
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

    private void initVideo() {
        String url = liveBean.getFileUrl();
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(this, liveBean.getLogo(), imageView);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayerLiveDetail);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle(liveBean.getName())
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
                videoPlayerLiveDetail.startWindowFullscreen(MsJtDetailActivity.this, true, true);
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
                closeActivity(MsJtDetailActivity.this);
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
}
