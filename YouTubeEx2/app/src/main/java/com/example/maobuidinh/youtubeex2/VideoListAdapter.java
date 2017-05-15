package com.example.maobuidinh.youtubeex2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maobuidinh.youtubeex2.content.YouTubeContent;
import com.example.maobuidinh.youtubeex2.content.YouTubeVideo;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mao.buidinh on 5/15/2017.
 */

public class VideoListAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener{

    private Context mContext;
    private Map<View, YouTubeThumbnailLoader> mLoaders;

    public VideoListAdapter(final Context context) {
        mContext = context;
        mLoaders = new HashMap<>();
    }

    @Override
    public int getCount() {
        return YouTubeContent.ITEMS.size();
    }

    @Override
    public Object getItem(int position) {
        return YouTubeContent.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VideoHolder holder;

        //The item at the current position
        final YouTubeVideo item = YouTubeContent.ITEMS.get(position);

        if (convertView == null) {
            //Create the row
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);

            //Create the video holder
            holder = new VideoHolder();

            //Set the title
            holder.title = (TextView) convertView.findViewById(R.id.textView_title);
            holder.title.setText(item.title);

            //Initialise the thumbnail
            holder.thumb = (YouTubeThumbnailView) convertView.findViewById(R.id.imageView_thumbnail);
            holder.thumb.setTag(item.id);
            holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

            convertView.setTag(holder);
        } else {
            //Create it again
            holder = (VideoHolder) convertView.getTag();
            final YouTubeThumbnailLoader loader = mLoaders.get(holder.thumb);

            if (item != null) {
                //Set the title
                holder.title.setText(item.title);

                //Setting the video id can take a while to actually change the image
                //in the meantime the old image is shown.
                //Removing the image will cause the background color to show instead, not ideal
                //but preferable to flickering images.
                holder.thumb.setImageBitmap(null);

                if (loader == null) {
                    //Loader is currently initialising
                    holder.thumb.setTag(item.id);
                } else {
                    //The loader is already initialised
                    //Note that it's possible to get a DeadObjectException here
                    try {
                        loader.setVideo(item.id);
                    } catch (IllegalStateException exception) {
                        //If the Loader has been released then remove it from the map and re-init
                        mLoaders.remove(holder.thumb);
                        holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

                    }
                }

            }
        }
        return convertView;
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        mLoaders.put(youTubeThumbnailView, youTubeThumbnailLoader);
        youTubeThumbnailLoader.setVideo((String) youTubeThumbnailView.getTag());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        final String errorMessage = youTubeInitializationResult.toString();
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    static class VideoHolder {
        YouTubeThumbnailView thumb;
        TextView title;
    }
}
