package com.gk.mvp.view.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.OnLiveBean;
import com.gk.beans.OnLiveRoomInfo;
import com.gk.global.YXXConstants;
import com.gk.mvp.presenter.OnLiveRoomPresenter;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.tools.GlideImageLoader;
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
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.civ_header)
    CircleImageView circleImageView;

    @BindView(R.id.tv_nick_name)
    TextView tvNickName;

    @BindView(R.id.ll_images)
    LinearLayout llImages;

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
                if (TextUtils.isEmpty(et_reply.getText())) {
                    toast("请输入内容");
                    return;
                }
                showProgress();
                roomPresenter.fansLiveRoomsSpeak(YxxUtils.URLEncode(et_reply.getText().toString()));
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

    private List<OnLiveRoomInfo.FansSpeakBean> fanSpeakBeanList = new ArrayList<>();
    private CommonAdapter<OnLiveRoomInfo.FansSpeakBean> adapter;
    private OnLiveRoomPresenter roomPresenter;
    private OnLiveBean onLiveBean;
    private OnLiveRoomInfo onLiveRoomInfo;
    private int onlyOneTime = 0;
    private GlideImageLoader imageLoader = new GlideImageLoader();

    private int screenHeight = 0;//屏幕高度初始值
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    private final int TIME_INTERVAL = 15000;//1秒执行一次
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME_INTERVAL);
            //stringList.add("测试添加信息" + Math.random());
            Log.e("runnable：", "1秒执行一次添加操作");
            roomPresenter.getLiveRoomsInfo();

        }
    };

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        setScreenHeight();
        onLiveBean = (OnLiveBean) getIntent().getSerializableExtra("bean");
        initVideo();
        roomPresenter = new OnLiveRoomPresenter(this, onLiveBean);
        initListView();
        handler.postDelayed(runnable, TIME_INTERVAL); // 在初始化方法里.
        rootView.addOnLayoutChangeListener(this);//添加layout大小发生改变监听器
    }

    @Override
    public <T> void fillWithData(T t, int order) {
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                OnLiveRoomInfo.FansSpeakBean fanSpeakBean = (OnLiveRoomInfo.FansSpeakBean) t;
                if (fanSpeakBean == null) {
                    return;
                }
                fanSpeakBeanList.add(fanSpeakBean);
                adapter.notifyDataSetChanged();
                YxxUtils.hideSoftInputKeyboard(et_reply);
                llComment.setVisibility(View.GONE);
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                onLiveRoomInfo = (OnLiveRoomInfo) t;
                initRoomInfo(onLiveRoomInfo);
                break;
        }
    }

    @Override
    public <T> void fillWithNoData(T t, int order) {
        switch (order) {
            case YXXConstants.INVOKE_API_DEFAULT_TIME:
                break;
            case YXXConstants.INVOKE_API_SECOND_TIME:
                break;
            case YXXConstants.INVOKE_API_THREE_TIME:
                break;
            case YXXConstants.INVOKE_API_FORTH_TIME:
                break;
        }
    }

    private void initRoomInfo(OnLiveRoomInfo roomInfo) {
        if (onlyOneTime == 0) {
            onlyOneTime = 1;
            if (roomInfo.getHeadImg() != null && !"".equals(roomInfo.getHeadImg())) {
                imageLoader.displayImage(this, roomInfo.getHeadImg(), circleImageView);
            }
            if (roomInfo.getNickName() != null && !"".equals(roomInfo.getNickName())) {
                tvNickName.setText(roomInfo.getNickName());
            }
            List<OnLiveRoomInfo.Fans> fans = roomInfo.getFans();
            if (fans != null && fans.size() > 0) {
                for (int i = 0; i < fans.size(); i++) {
                    View view = View.inflate(this, R.layout.on_live_images, null);
                    CircleImageView circleImageView = view.findViewById(R.id.civ_image);
                    imageLoader.displayImage(this, fans.get(i).getHeadImg(), circleImageView);
                    llImages.addView(view);
                }
            }
            initRoomFanSpeak(roomInfo);
            return;
        }
        initRoomFanSpeak(roomInfo);
    }

    private void initRoomFanSpeak(OnLiveRoomInfo roomInfo) {
        List<OnLiveRoomInfo.FansSpeakBean> speakBeanList = roomInfo.getFansSpeak();
        if (speakBeanList != null && speakBeanList.size() > 0) {
            fanSpeakBeanList.addAll(speakBeanList);
            fanSpeakBeanList = removeDuplicate(fanSpeakBeanList);
            adapter.notifyDataSetChanged();
        }
    }

    public List<OnLiveRoomInfo.FansSpeakBean> removeDuplicate(List<OnLiveRoomInfo.FansSpeakBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private void initVideo() {
        if (onLiveBean == null) {
            return;
        }
        String url = onLiveBean.getLivePullUrl();
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(this, onLiveBean.getLiveVerticalLogo(), imageView);
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
        listView.setAdapter(adapter = new CommonAdapter<OnLiveRoomInfo.FansSpeakBean>(this, R.layout.on_live_detail_chat_item, fanSpeakBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, OnLiveRoomInfo.FansSpeakBean item, int position) {
                viewHolder.setText(R.id.tv_reply_content, item.getFansSpeak());
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
        roomPresenter.fansExitLiveRooms();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        stopTimer();
        stopHandler();
        roomPresenter.fansExitLiveRooms();
    }
}
