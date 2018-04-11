package com.gk.mvp.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.gk.R;
import com.gk.mvp.view.adpater.LoginFragmentPagerAdapter;
import com.gk.mvp.view.custom.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;

/**
 * Created by JDRY-SJM on 2017/10/9.
 */

public class LoginActivity extends SjmBaseActivity implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.top_bar_login)
    TopBarView topBarLogin;

    private List<String> list;

    @Override
    public int getResouceId() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        app.onTerminate();
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        initTopBar();
        initData();
        int mEnterFlag = 0;
        pager.setAdapter(new LoginFragmentPagerAdapter(getSupportFragmentManager(), list, mEnterFlag));
        tabLayout.setupWithViewPager(pager);
    }

    private void initTopBar() {
        topBarLogin.getTitleView().setText("登录");
        topBarLogin.getBackView().setVisibility(View.GONE);
    }

    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        list.add("验证码登录");
        list.add("密码登录");
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
                Manifest.permission.CALL_PHONE,
                Manifest.permission_group.STORAGE
        };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要打开相机和打电话的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
}
