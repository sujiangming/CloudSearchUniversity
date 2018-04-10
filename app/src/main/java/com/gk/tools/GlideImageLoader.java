package com.gk.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.Util;
import com.gk.R;
import com.gk.mvp.view.custom.GlideCircleTransform;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class GlideImageLoader {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        if (!Util.isOnMainThread()) {
            return;
        }
        if (null == context) {
            return;
        }
        Activity activity = (Activity) context;
        if (activity.isDestroyed()) {
            return;
        }
        Glide.with(context)
                .load(path)
                .crossFade()
                .placeholder(R.drawable.zhanweitu)
                .dontAnimate()
                .error(R.drawable.zhanweitu)
                .priority(Priority.NORMAL)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void displayCircleRadius(Context context, Object path, ImageView imageView, int radius) {
        if (!Util.isOnMainThread()) {
            return;
        }
        if (null == context) {
            return;
        }

        Activity activity = (Activity) context;
        if (activity.isDestroyed()) {
            return;
        }

        Glide.with(context)
                .load(path)
                .crossFade()
                .transform(new GlideCircleTransform(context, radius))
                .placeholder(R.drawable.zhanweitu)
                .dontAnimate()
                .error(R.drawable.zhanweitu)
                .priority(Priority.NORMAL)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void displayByImgRes(Context context, Object path, ImageView imageView, int imgRes) {
        if (!Util.isOnMainThread()) {
            return;
        }
        if (null == context) {
            return;
        }

        Activity activity = (Activity) context;
        if (activity.isDestroyed()) {
            return;
        }

        Glide.with(context)
                .load(path)
                .crossFade()
                .placeholder(imgRes)
                .error(imgRes)
                .priority(Priority.NORMAL)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public static void stopLoad(Context context) {
        if (Util.isOnMainThread()) {
            Glide.with(context).pauseRequests();
        }
    }
}
