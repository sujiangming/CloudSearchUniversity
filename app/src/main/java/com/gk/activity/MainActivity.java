package com.gk.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.YXXApplication;
import com.gk.YXXConstants;
import com.gk.beans.UserBean;
import com.gk.beans.UserBeanDao;
import com.gk.beans.VersionBean;
import com.gk.beans.VersionBeanDao;
import com.gk.fragment.HomeFragment;
import com.gk.fragment.LectureFragment;
import com.gk.fragment.LiveVideoFragment;
import com.gk.fragment.UserFragment;
import com.gk.fragment.WishFragment;
import com.gk.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.banner_main)
    Banner banner;

    @BindView(R.id.ll_enter_app)
    LinearLayout llEnterApp;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    @BindView(R.id.welcome)
    View welcomeView;

    @BindView(R.id.main_view)
    LinearLayout mainView;

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

    private FragmentManager fragmentManager;
    private LectureFragment lectureFragment = null;
    private HomeFragment homeFragment = null;
    private UserFragment userFragment = null;
    private LiveVideoFragment liveVideoFragment = null;
    private WishFragment wishFragment = null;
    private ImageView[] imageViews = new ImageView[5];
    private int[] imageViewNormalRes = {R.drawable.shouye, R.drawable.zhibo_95, R.drawable.gaokaozhiyuan, R.drawable.gaokao_74, R.drawable.my};
    private int[] imageViewChangeRes = {R.drawable.shouye_press, R.drawable.zhibo_press, R.drawable.gaokaozhiyuan, R.drawable.gaokao_press, R.drawable.my_press};

    private int index = 0;
    public int lastPosition = 0;

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
        if (isNewVersionExit(2)) { // 判断当前是否有新版本，有则显示新特性面轮播页；否则，不显示，直接进行用户存在与否的判断
            banner.setVisibility(View.VISIBLE);
            initNewFeature();
        } else {
            hideBanner();
            mainView.setVisibility(View.VISIBLE);
            initFragments();
        }
    }

    private boolean isUserBeanExit() {
        UserBeanDao userBeanDao = YXXApplication.getDaoSession().getUserBeanDao();
        List<UserBean> list = userBeanDao.loadAll();
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }

    private void hideBanner() {
        if (banner.getVisibility() == View.VISIBLE)
            banner.setVisibility(View.GONE);
    }

    private boolean isNewVersionExit(int newVersion) {
        VersionBeanDao versionBeanDao = YXXApplication.getDaoSession().getVersionBeanDao();
        List<VersionBean> list = versionBeanDao.loadAll();
        if (list == null || list.size() == 0) {
            return false;
        }
        int oldVersion = list.get(0).getVersionCode();
        if (newVersion <= oldVersion) {
            return false;
        }
        return true;
    }

    private void initNewFeature() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.timg);
        imageList.add(R.drawable.timg_1);
        imageList.add(R.drawable.timg_2);
        lastPosition = imageList.size() - 1;
        banner.isAutoPlay(false);
        banner.setViewPagerIsScroll(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled->","当前位置=" + position + "\n 偏移量=" + positionOffset + "\n 偏移像素=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(final int position) {
                Log.d("-onPageSelected-", position + "");
                if (lastPosition == position) { //最后一张图片
                    llEnterApp.setVisibility(View.VISIBLE);
                    tvBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isUserBeanExit()) { // 判断用户是否存在，存在则弹出欢迎蒙版，否则需要直接跳到登录页面
                                banner.setVisibility(View.GONE);
                                llEnterApp.setVisibility(View.GONE);
                                mainView.setVisibility(View.VISIBLE);
                                showWelcome();
                                initFragments();
                                new LongTimeTask().execute("New Text");
                            } else {
                                Intent intent = new Intent();
                                intent.putExtra(YXXConstants.ENTER_LOGIN_PAGE_FLAG, YXXConstants.FROM_MAIN_FLAG);
                                openNewActivityByIntent(LoginActivity.class, intent);
                            }
                        }
                    });
                } else {
                    llEnterApp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("onPageScrollSChanged","当前状态=" + state);
            }
        });
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
                Thread.sleep(2000);
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

    private void changeNavStyle(View view) {
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
}
