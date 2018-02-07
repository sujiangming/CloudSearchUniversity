package com.gk.mvp.view.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LoginBean;
import com.gk.beans.OnLiveBean;
import com.gk.beans.OnLiveRoomInfo;
import com.gk.global.YXXConstants;
import com.gk.mvp.presenter.OnLiveRoomPresenter;
import com.gk.mvp.view.custom.CircleImageView;
import com.gk.tools.GlideImageLoader;
import com.gk.tools.YxxUtils;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

    @BindView(R.id.heart_layout_flower)
    HeartLayout heartLayoutFlower;

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
                clearHeartTask();
                closeActivity(this);
                break;
            case R.id.civ_send_hua:
                toast("送花成功！");
                dianzanAndSendFlower(flower);
                break;
            case R.id.civ_shoucang:
                showHeartLayout();
                dianzanAndSendFlower(like);
                new LongTimeTask().execute("执行……");
                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(et_reply.getText())) {
                    toast("请输入内容");
                    return;
                }
                roomPresenter.fansLiveRoomsSpeak(YxxUtils.URLEncode(et_reply.getText().toString()));
                break;
        }
    }

    private void dianzanAndSendFlower(String value) {
        OnLiveRoomInfo.FansSpeakBean fanSpeakBean = new OnLiveRoomInfo.FansSpeakBean();
        fanSpeakBean.setFansSpeak(value);
        fanSpeakBean.setNickName(LoginBean.getInstance().getNickName());
        fanSpeakBean.setUsername(LoginBean.getInstance().getUsername());
        fanSpeakBean.setId(value);
        fanSpeakBeanList.add(fanSpeakBean);
        adapter.notifyDataSetChanged();
        listView.smoothScrollByOffset(adapter.getCount());
    }

    @Override
    public int getResouceId() {
        return R.layout.activity_on_live_overly;
    }

    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private TimerTask timerHeartTask;

    private List<OnLiveRoomInfo.FansSpeakBean> fanSpeakBeanList = new ArrayList<>();
    private CommonAdapter<OnLiveRoomInfo.FansSpeakBean> adapter;
    private OnLiveRoomPresenter roomPresenter;
    private OnLiveBean onLiveBean;
    private OnLiveRoomInfo onLiveRoomInfo;
    private int onlyOneTime = 0;
    private GlideImageLoader imageLoader = new GlideImageLoader();

    private int screenHeight = 0;//屏幕高度初始值
    private int keyHeight = 0;//软件盘弹起后所占高度阀值

    private String flower = "/flower";
    private String like = "/like";

    private final int TIME_INTERVAL = 15000;//1秒执行一次
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME_INTERVAL);
            roomPresenter.getLiveRoomsInfo();
        }
    };

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        setScreenHeight();
        onLiveBean = (OnLiveBean) getIntent().getSerializableExtra("bean");
        initVideo();
        showDialogByStatus();
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
                listView.smoothScrollByOffset(adapter.getCount());
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
        toast(YXXConstants.ERROR_INFO);
        hideProgress();
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
                imageLoader.displayByImgRes(this, roomInfo.getHeadImg(), circleImageView,R.drawable.my);
            }
            if (roomInfo.getNickName() != null && !"".equals(roomInfo.getNickName())) {
                tvNickName.setText(roomInfo.getNickName());
            }
            llImages.removeAllViews();
            List<OnLiveRoomInfo.Fans> fans = roomInfo.getFans();
            if (fans != null && fans.size() > 0) {
                Set<String> stringSet = new HashSet<>();
                for (int i = 0; i < fans.size(); i++) {
                    stringSet.add(fans.get(i).getHeadImg());
                }
                for (String url : stringSet) {
                    View view = View.inflate(this, R.layout.on_live_images, null);
                    CircleImageView circleImageView = view.findViewById(R.id.civ_image);
                    imageLoader.displayByImgRes(this, url, circleImageView,R.drawable.my);
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
            listView.smoothScrollByOffset(adapter.getCount());
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

        videoPlayerLiveDetail.getTitleTextView().setVisibility(View.GONE);
        videoPlayerLiveDetail.getBackButton().setVisibility(View.GONE);
        new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setNeedShowWifiTip(true)
                .setSeekRatio(1)
                .build(videoPlayerLiveDetail);
    }

    private void showDialogByStatus() {
        //0未直播1直播中2直播结束3已录制
        int status = onLiveBean.getLiveStatus();
        switch (status) {
            case 0:
                showCommonTipeDialog("直播未开始");
                break;
            case 1:
                autoPlay();
                break;
            case 2:
                showCommonTipeDialog("直播结束");
                break;
            case 3:
                showContinue();
                break;
        }
    }

    private void autoPlay() {
        videoPlayerLiveDetail.getFullscreenButton().setVisibility(View.GONE);
        videoPlayerLiveDetail.startPlayLogic();
    }

    private void showCommonTipeDialog(String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("温馨提示");
        builder.setCancelable(false);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });
        builder.setMessage(status);
        builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                closeActivity(OnLiveRoomActivity.this);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showContinue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("温馨提示");
        builder.setMessage("当前将要播放的为录制视频！");
        builder.setPositiveButton("继续播放", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                autoPlay();
            }
        });
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                closeActivity(OnLiveRoomActivity.this);
            }
        });
        builder.setCancelable(false);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initListView() {
        listView.setAdapter(adapter = new CommonAdapter<OnLiveRoomInfo.FansSpeakBean>(this, R.layout.on_live_detail_chat_item, fanSpeakBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, OnLiveRoomInfo.FansSpeakBean item, int position) {
                viewHolder.setText(R.id.tv_replier, (item.getNickName() == null ? "游客：" : (item.getNickName() + "：")));
                if (item.getFansSpeak().equals(flower)) {
                    viewHolder.setVisible(R.id.iv_zan, true);
                    viewHolder.setImageResource(R.id.iv_zan, R.drawable.sendhua);
                    viewHolder.setVisible(R.id.tv_reply_content, false);
                } else if (item.getFansSpeak().equals(like)) {
                    viewHolder.setVisible(R.id.iv_zan, true);
                    viewHolder.setImageResource(R.id.iv_zan, R.drawable.dianzan);
                    viewHolder.setVisible(R.id.tv_reply_content, false);
                } else {
                    viewHolder.setText(R.id.tv_reply_content, item.getFansSpeak());
                    viewHolder.setVisible(R.id.tv_reply_content, true);
                    viewHolder.setVisible(R.id.iv_zan, false);
                }
            }
        });
        listView.smoothScrollByOffset(adapter.getCount());
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    private void showHeartLayout() {
        clearHeartTask();
        mTimer.scheduleAtFixedRate(timerHeartTask = new TimerTask() {
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
            clearHeartTask();
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

    public void clearHeartTask() {
        if (mTimer != null) {
            if (timerHeartTask != null) {
                timerHeartTask.cancel();
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
        clearHeartTask();
        stopTimer();
        stopHandler();
        roomPresenter.fansExitLiveRooms();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        clearHeartTask();
        stopTimer();
        stopHandler();
        roomPresenter.fansExitLiveRooms();
    }
}
