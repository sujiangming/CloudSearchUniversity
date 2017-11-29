package com.gk.mvp.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.global.YXXConstants;
import com.gk.mvp.view.fragment.HomeFragment;
import com.gk.mvp.view.fragment.LectureFragment;
import com.gk.mvp.view.fragment.LiveVideoFragment;
import com.gk.mvp.view.fragment.UserFragment;
import com.gk.mvp.view.fragment.WishFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2017/9/29.
 */

public class MainActivity extends SjmBaseActivity {

    @BindView(R.id.main_frame)
    FrameLayout mainFrame;

    @BindView(R.id.ll_fragment_container)
    LinearLayout llFragmentContainer;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_live)
    ImageView ivLive;
    @BindView(R.id.tv_live)
    TextView tvLive;
    @BindView(R.id.ll_live)
    LinearLayout llLive;
    @BindView(R.id.ll_wish)
    LinearLayout tvWish;
    @BindView(R.id.ll_lesson)
    LinearLayout llLesson;
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.iv_tab_lecture)
    ImageView ivTabLecture;
    @BindView(R.id.iv_tab_user)
    ImageView ivTabUser;
    @BindView(R.id.iv_tab_wish)
    ImageView ivTabWish;
    @BindView(R.id.welcome)
    View welcomeView;

    @OnClick({R.id.ll_home, R.id.ll_live, R.id.ll_wish, R.id.ll_lesson, R.id.ll_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                changeNavStyle(view);
                break;
            case R.id.ll_live:
                changeNavStyle(view);
                break;
            case R.id.ll_wish:
                changeNavStyle(view);
                break;
            case R.id.ll_lesson:
                changeNavStyle(view);
                break;
            case R.id.ll_user:
                changeNavStyle(view);
                break;
        }
    }

    public LinearLayout getLlLesson() {
        return llLesson;
    }

    private FragmentManager fragmentManager;
    private LectureFragment lectureFragment = null;
    private HomeFragment homeFragment = null;
    private UserFragment userFragment = null;
    private LiveVideoFragment liveVideoFragment = null;
    private WishFragment wishFragment = null;
    private ImageView[] imageViews = new ImageView[5];
    private int[] imageViewNormalRes = {R.drawable.shouye, R.drawable.zhibo_95, R.drawable.gaokaozhiyuan, R.drawable.gaokao_74, R.drawable.my};
    private int[] imageViewChangeRes = {R.drawable.shouye_press, R.drawable.zhibo_press, R.drawable.gaokaozhiyuan_selected3x, R.drawable.gaokao_press, R.drawable.my_press};

    private int index = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarTransparent();
        initView();
    }

    private void initView() {
        showMainView();
        initFragments();
    }

    private void showMainView() {
        showWelcome();
        new LongTimeTask().execute("welcome");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == YXXConstants.LOGIN_SET_RESULT) {
            showMainView();
        }
    }

    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;

    private void showWelcome() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        welcomeView.setAnimation(mShowAction);
        welcomeView.setVisibility(View.VISIBLE);
    }

    private void hideWelcome() {
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(500);
        welcomeView.setAnimation(mHiddenAction);
        welcomeView.setVisibility(View.GONE);
    }

    public class LongTimeTask extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //更新UI的操作，这里面的内容是在UI线程里面执行的
            //btnStart.setText(result);
            hideWelcome();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                //线程睡眠5秒，模拟耗时操作，这里面的内容Android系统会自动为你启动一个新的线程执行
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        initImageViews(); //初始化imageview数组
        changeFragment(index);//显示主页
    }

    private void initImageViews() {
        imageViews[0] = ivHome;
        imageViews[1] = ivLive;
        imageViews[2] = ivTabWish;
        imageViews[3] = ivTabLecture;
        imageViews[4] = ivTabUser;
    }

    private void changeFragment(int indexTmp) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (indexTmp) {
            case 0:
                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.ll_fragment_container, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }

                break;
            case 1:
                if (null == liveVideoFragment) {
                    liveVideoFragment = new LiveVideoFragment();
                    transaction.add(R.id.ll_fragment_container, liveVideoFragment);
                } else {
                    transaction.show(liveVideoFragment);
                }
                break;
            case 2:
                if (null == wishFragment) {
                    wishFragment = new WishFragment();
                    transaction.add(R.id.ll_fragment_container, wishFragment);
                } else {
                    transaction.show(wishFragment);
                }
                break;
            case 3:
                if (null == lectureFragment) {
                    lectureFragment = new LectureFragment();
                    transaction.add(R.id.ll_fragment_container, lectureFragment);
                } else {
                    transaction.show(lectureFragment);
                }
                break;
            case 4:
                if (null == userFragment) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.ll_fragment_container, userFragment);
                } else {
                    transaction.show(userFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != homeFragment) {
            transaction.hide(homeFragment);
        }
        if (null != liveVideoFragment) {
            transaction.hide(liveVideoFragment);
        }
        if (null != wishFragment) {
            transaction.hide(wishFragment);
        }
        if (null != lectureFragment) {
            transaction.hide(lectureFragment);
        }
        if (null != userFragment) {
            transaction.hide(userFragment);
        }
    }

    public void changeNavStyle(View view) {
        String tag = (String) view.getTag();
        index = Integer.parseInt(tag);
        changeFragment(index);
        changeImageViewRes(index);
    }

    private void changeImageViewRes(int index) {
        imageViews[index].setImageResource(imageViewChangeRes[index]);
        int len = imageViews.length;
        for (int i = 0; i < len; ++i) {
            if (i != index) {
                imageViews[i].setImageResource(imageViewNormalRes[i]);
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                appManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
