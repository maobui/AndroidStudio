package com.example.maobuidinh.youtubeex2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.maobuidinh.youtubeex2.fragment.YouTubeFragment;

public class YouTubeFragmentActivity extends ActionBarActivity {

    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_fragment);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey(KEY_VIDEO_ID)) {
            final String videoId = bundle.getString(KEY_VIDEO_ID);
            final YouTubeFragment fragment = (YouTubeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_youtube);
            fragment.setVideoId(videoId);
        }

    }
}
