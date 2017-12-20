package com.gk.mvp.view.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gk.R;
import com.gk.global.YXXConstants;
import com.gk.tools.YxxUtils;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;
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

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.root_view)
    View rootView;

    @OnClick({R.id.civ_comment, R.id.civ_cancel, R.id.civ_send_hua, R.id.civ_shoucang, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_comment:
                llComment.setVisibility(View.VISIBLE);
                YxxUtils.showSoftInputFromWindow(et_reply);
                break;
            case R.id.civ_cancel:
                clearTimeTask();
                closeActivity(this);
                break;
            case R.id.civ_send_hua:
                break;
            case R.id.civ_shoucang:
                showHeartLayout();
                new LongTimeTask(YXXConstants.ON_LIVE_SEND_HEART_FLAG).execute("执行……");
                break;
            case R.id.tv_send:
                break;
        }
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_on_live_overly;
    }

    private OrientationUtils orientationUtils;
    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private TimerTask timerTask;

    private List<String> stringList = new ArrayList<>();
    private CommonAdapter<String> adapter;

    private int screenHeight = 0;//屏幕高度初始值
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    private final int TIME_INTERVAL = 1000;//1秒执行一次
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME_INTERVAL);
            stringList.add("测试添加信息" + Math.random());
            Log.e("runnable：", "1秒执行一次添加操作");
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        setScreenHeight();
        initVideo();
        initListView();
        handler.postDelayed(runnable, TIME_INTERVAL); // 在初始化方法里.
        rootView.addOnLayoutChangeListener(this);//添加layout大小发生改变监听器

    }

    private void initVideo() {
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ym);
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

    private void initListView() {
        for (int i = 0; i < 10; i++) {
            stringList.add("测试数据测试数据" + i);
        }
        listView.setAdapter(adapter = new CommonAdapter<String>(this, R.layout.on_live_detail_chat_item, stringList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                if (item == null) {
                    return;
                }
                viewHolder.setText(R.id.tv_reply_content, item);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast(stringList.get(i));
            }
        });
        listView.setSelection(adapter.getCount());
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

        private int index;

        public LongTimeTask(int index) {
            this.index = index;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //更新UI的操作，这里面的内容是在UI线程里面执行的
            if (index == YXXConstants.ON_LIVE_SEND_HEART_FLAG) {
                clearTimeTask();
            } else if (index == YXXConstants.ON_LIVE_SEND_FLOWER_FLAG) {

            } else if (index == YXXConstants.ON_LIVE_SEND_COMMENT_FLAG) {

            }
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

    public void clearTimeTask() {
        if (mTimer != null) {
            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

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
        }
    }

    private void stopHandler() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
        closeActivity(this);
        stopTimer();
        stopHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        stopTimer();
        stopHandler();
    }
}
