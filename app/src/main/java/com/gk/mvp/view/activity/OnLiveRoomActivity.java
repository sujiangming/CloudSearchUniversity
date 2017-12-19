package com.gk.mvp.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gk.R;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import tyrantgit.widget.HeartLayout;

/**
 * Created by JDRY-SJM on 2017/12/19.
 */

public class OnLiveRoomActivity extends SjmBaseActivity implements View.OnLayoutChangeListener {

    @BindView(R.id.video_player_live_detail)
    StandardGSYVideoPlayer videoPlayerLiveDetail;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.et_reply)
    EditText et_reply;
    @BindView(R.id.heart_layout)
    HeartLayout heartLayout;
    @BindView(R.id.root_view)
    View rootView;

    @Override
    public int getResouceId() {
        return R.layout.activity_on_live_overly;
    }

    private OrientationUtils orientationUtils;
    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private TimerTask timerTask;

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        setScreenHeight();
        initVideo();
        //添加layout大小发生改变监听器
        rootView.addOnLayoutChangeListener(this);

    }

    private void initVideo() {
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //String url = liveBean.getVideoUrl();
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ym);
//        GlideImageLoader glideImageLoader = new GlideImageLoader();
//        glideImageLoader.displayImage(this, liveBean.getVideoLogo(), imageView);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPlayerLiveDetail);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setNeedShowWifiTip(false)
                .setSeekRatio(1)
                .build(videoPlayerLiveDetail);
        videoPlayerLiveDetail.getTitleTextView().setVisibility(View.GONE);
        videoPlayerLiveDetail.getBackButton().setVisibility(View.GONE);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    private void showHeartLayout() {
        clearTimeTask();
        mTimer.scheduleAtFixedRate(timerTask = new TimerTask() {
            @Override
            public void run() {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addHeart(randomColor());
                    }
                });
            }
        }, 500, 200);
    }

    public class LongTimeTask extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //更新UI的操作，这里面的内容是在UI线程里面执行的
            clearTimeTask();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                //线程睡眠5秒，模拟耗时操作，这里面的内容Android系统会自动为你启动一个新的线程执行
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }
    }

    private void clearTimeTask() {
        if (mTimer != null) {
            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    private void setScreenHeight() {
        //获取屏幕高度
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            //Toast.makeText(this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            llComment.setVisibility(View.GONE);
            et_reply.clearFocus();
            et_reply.setText("");
            //Toast.makeText(this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.civ_comment, R.id.civ_cancel, R.id.civ_send_hua, R.id.civ_shoucang, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_comment:
                llComment.setVisibility(View.VISIBLE);
                et_reply.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) et_reply.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_reply, 0);
                break;
            case R.id.civ_cancel:
                clearTimeTask();
                closeActivity(this);
                break;
            case R.id.civ_send_hua:
                break;
            case R.id.civ_shoucang:
                showHeartLayout();
                new LongTimeTask().execute("执行……");
                break;
            case R.id.tv_send:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
        closeActivity(this);
        if (mTimer != null) {
            mTimer.cancel();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
