package com.dexter.basemvpwithoutdagger.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dexter.basemvpwithoutdagger.R;

/**
 * Created by Admin on 06-12-2017.
 */

public class ImageUtils {

    public static void setImage(Context context, ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.mipmap.ic_launcher).circleCrop();
        GlideApp.with(context).asBitmap().load(url).apply(requestOptions).error(R.mipmap.ic_launcher).into(imageView);
    }

    public static void setRectangleImage(Context context, ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.mipmap.ic_launcher);
        GlideApp.with(context).asBitmap().load(url).apply(requestOptions).error(R.mipmap.ic_launcher).into(imageView);
    }

    public static String getOriginalPoster(String url) {
        if (!android.text.TextUtils.isEmpty(url)) {
            String[] originalUrl = url.split(Constants.token);
            url = originalUrl[0] + originalUrl[1];
            return url;
        } else {
            return url;
        }
    }
}
