package com.example.maobuidinh.wallpapers.adataper;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.maobuidinh.wallpapers.R;
import com.example.maobuidinh.wallpapers.app.AppController;
import com.example.maobuidinh.wallpapers.picasa.model.Wallpaper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mao.buidinh on 7/26/2017.
 */

public class PhotoAdataper extends RecyclerView.Adapter<PhotoAdataper.ItemViewHolder> {
    private static final String TAG = PhotoAdataper.class.getSimpleName();

    private Activity mActivity;
    private List<Wallpaper> mWallpaperList = new ArrayList<Wallpaper>();
    private int mImagWidth;
    ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

    public PhotoAdataper(Activity activity, List<Wallpaper> wallpaperList, int imagWidth) {
        mActivity = activity;
        mWallpaperList = wallpaperList;
        mImagWidth = imagWidth;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View converView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ItemViewHolder(converView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Wallpaper wallpaper = mWallpaperList.get(position);

        if (mImageLoader == null) {
            mImageLoader = AppController.getInstance().getImageLoader();
        }

        holder.mNetworkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //holder.mNetworkImageView.setLayoutParams( new FrameLayout.LayoutParams(mImagWidth, mImagWidth));
        holder.mNetworkImageView.setImageUrl(wallpaper.getUrl(), mImageLoader);
        Log.e(TAG, "onBindViewHolder == url image : " + wallpaper.getUrl());
    }

    @Override
    public int getItemCount() {
        return mWallpaperList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return mWallpaperList.get(position);
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public ImageView mImageView;
        public NetworkImageView mNetworkImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mImageView = (ImageView) itemView.findViewById(R.id.imgLoader);
            mNetworkImageView = (NetworkImageView) itemView.findViewById(R.id.netImgThumbnail);
        }
    }

    /**
     * Add touch listener for Recycler.
     */

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private PhotoAdataper.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final PhotoAdataper.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
