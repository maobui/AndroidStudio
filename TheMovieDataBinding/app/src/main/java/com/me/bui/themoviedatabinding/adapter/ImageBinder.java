package com.me.bui.themoviedatabinding.adapter;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import static android.content.ContentValues.TAG;
import static com.me.bui.themoviedatabinding.AppConstant.POSTER_URL_IMG;

/**
 * Created by mao.buidinh on 10/21/2017.
 */

public class ImageBinder {
    private ImageBinder() {
        // NO-OP
    }

    @BindingAdapter({"imgURL"})
    public static void loadImage (ImageView imageView, String imgUrl) {
        Glide
                .with(imageView.getContext())
                .load(POSTER_URL_IMG + imgUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // TODO: handle failure
//                        mProgress.setVisibility(View.GONE);
                        Log.e(TAG, " loadImage error:  " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // image ready, hide progress now
//                        mProgress.setVisibility(View.GONE);
                        return false;   // return false if you want Glide to handle everything else.
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade()
                //.placeholder(R.drawable.poster) // can also be a drawable first until to show real image.
                .error(android.R.drawable.ic_menu_report_image) // will be displayed if the image cannot be loaded
                .into(imageView);
    }
}
