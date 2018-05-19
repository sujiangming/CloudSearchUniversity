package com.gk.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.beans.LoginBean;
import com.gk.global.YXXConstants;
import com.gk.mvp.view.fragment.HomeFragment;
import com.gk.mvp.view.fragment.LectureFragment;
import com.gk.mvp.view.fragment.LiveVideoFragment;
import com.gk.mvp.view.fragment.UserFragment;
import com.gk.mvp.view.fragment.WishFragment;
import com.gk.tools.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;

/**
 * Created by JDRY-SJM on 2017/9/29.
 */

public class MainActivity extends SjmBaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_live)
    ImageView ivLive;
    @BindView(R.id.tv_live)
    TextView tvLive;
    @BindView(R.id.ll_wish)
    LinearLayout tvWish;
    @BindView(R.id.iv_tab_lecture)
    ImageView ivTabLecture;
    @BindView(R.id.iv_tab_user)
    ImageView ivTabUser;
    @BindView(R.id.iv_tab_wish)
    ImageView ivTabWish;
    @BindView(R.id.welcome)
    View welcomeView;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHeader;
    @BindView(R.id.tv_welcome)
    TextView tvWelcome;

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
    private int[] imageViewChangeRes = {R.drawable.shouye_press, R.drawable.zhibo_press, R.drawable.gaokaozhiyuan_selected3x, R.drawable.gaokao_press, R.drawable.my_press};

    private int index = 0;

    @Override
    public int getResouceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initFragments();
        initImageViews();
        if(null != LoginBean.getInstance()){
            GlideImageLoader.displayImage(this, LoginBean.getInstance().getHeadImg(), ivUserHeader);
            String userCname = LoginBean.getInstance().getNickName() == null ? "未填写" : LoginBean.getInstance().getNickName();
            tvWelcome.setText(userCname + ",欢迎回来！");
            showWelcome();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == YXXConstants.LOGIN_SET_RESULT) {
            showWelcome();
        }
    }

    private void showWelcome() {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        welcomeView.setAnimation(mShowAction);
        welcomeView.setVisibility(View.VISIBLE);
        welcomeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideWelcome();
            }
        }, 1500);
    }

    private void hideWelcome() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(500);
        welcomeView.setAnimation(mHiddenAction);
        welcomeView.setVisibility(View.GONE);
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
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
                    //transaction.addToBackStack(homeFragment.getClass().getSimpleName());
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (null == liveVideoFragment) {
                    liveVideoFragment = new LiveVideoFragment();
                    transaction.add(R.id.ll_fragment_container, liveVideoFragment);
                    //transaction.addToBackStack(liveVideoFragment.getClass().getSimpleName());
                } else {
                    transaction.show(liveVideoFragment);
                }
                break;
            case 2:
                if (null == wishFragment) {
                    wishFragment = new WishFragment();
                    transaction.add(R.id.ll_fragment_container, wishFragment);
                    //transaction.addToBackStack(wishFragment.getClass().getSimpleName());
                } else {
                    transaction.show(wishFragment);
                }
                break;
            case 3:
                if (null == lectureFragment) {
                    lectureFragment = new LectureFragment();
                    transaction.add(R.id.ll_fragment_container, lectureFragment);
                    //transaction.addToBackStack(lectureFragment.getClass().getSimpleName());
                } else {
                    transaction.show(lectureFragment);
                }
                break;
            case 4:
                if (null == userFragment) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.ll_fragment_container, userFragment);
                    //transaction.addToBackStack(userFragment.getClass().getSimpleName());
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

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();

    }


    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(List<String> perms) {

    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE
        };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要打开相机和打电话的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
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
                app.onTerminate();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
