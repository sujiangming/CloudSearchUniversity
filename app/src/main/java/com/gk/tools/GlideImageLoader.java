package com.gk.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gk.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by JDRY-SJM on 2017/9/6.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.ic_gf_default_photo)
                .dontAnimate()
                .error(R.drawable.ic_gf_default_photo)
                .priority( Priority.HIGH)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }
}
