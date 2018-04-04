package com.gk.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gk.R;
import com.gk.mvp.view.custom.GlideCircleTransform;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class GlideImageLoader {

    public static void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .crossFade()
                .placeholder(R.drawable.zhanweitu)
                .dontAnimate()
                .error(R.drawable.zhanweitu)
                .priority(Priority.NORMAL)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void displayCircleRadius(Context context, Object path, ImageView imageView, int radius) {
        Glide.with(context)
                .load(path)
                .crossFade()
                .transform(new GlideCircleTransform(context, radius))
                .placeholder(R.drawable.zhanweitu)
                .dontAnimate()
                .error(R.drawable.zhanweitu)
                .priority(Priority.NORMAL)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void displayByImgRes(Context context, Object path, ImageView imageView, int imgRes) {
        Glide.with(context)
                .load(path)
                .crossFade()
                .placeholder(imgRes)
                .error(imgRes)
                .priority(Priority.NORMAL)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }
}
