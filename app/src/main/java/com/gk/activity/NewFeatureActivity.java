package com.gk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gk.R;
import com.gk.YXXApplication;
import com.gk.YXXConstants;
import com.gk.adpater.SjmFragmentPagerAdapter;
import com.gk.beans.UserBean;
import com.gk.beans.UserBeanDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2017/10/23.
 */

public class NewFeatureActivity extends SjmBaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_new_feature)
    ViewPager vpNewFeature;

    @BindView(R.id.ll_indication)
    LinearLayout llIndication;

    //最后一页的按钮
    @BindView(R.id.ll_enter_app)
    LinearLayout llEnterApp;

    @BindView(R.id.guide_ib_start)
    TextView ib_start;

    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合

    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;


    @Override
    public int getResouceId() {
        return R.layout.activity_new_feature;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setStatusBarTransparent();
        initViewPager();
        initPoint();
        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        UserBeanDao userBeanDao = YXXApplication.getDaoSession().getUserBeanDao();
        List<UserBean> userBeans = userBeanDao.loadAll();
        if (userBeans != null && userBeans.size() > 0) {
            openNewActivity(MainActivity.class);
        } else {
            Intent intent = new Intent();
            intent.putExtra(YXXConstants.ENTER_LOGIN_PAGE_FLAG, YXXConstants.FROM_SPLASH_FLAG);
            openNewActivityByIntent(LoginActivity.class, intent);
        }
        closeActivity();
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.setMargins(12, 0, 0, 0);
            iv_point.setLayoutParams(layoutParams);
            iv_point.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.circle_indcation_change);
            } else {
                iv_point.setBackgroundResource(R.drawable.circle_indcation);
            }
            //将数组中的ImageView加入到ViewGroup
            llIndication.addView(ivPointArray[i]);
        }
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.timg_0, R.drawable.timg_1, R.drawable.timg_2};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        //View集合初始化好后，设置Adapter
        vpNewFeature.setAdapter(new SjmFragmentPagerAdapter(viewList));
        //设置滑动监听
        vpNewFeature.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0; i < length; i++) {
            ivPointArray[position].setBackgroundResource(R.drawable.circle_indcation_change);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.circle_indcation);
            }
        }
        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            llEnterApp.setVisibility(View.VISIBLE);
        } else {
            llEnterApp.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
