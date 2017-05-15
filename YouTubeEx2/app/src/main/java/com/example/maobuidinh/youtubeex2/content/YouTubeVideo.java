package com.example.maobuidinh.youtubeex2.content;

/**
 * Created by mao.buidinh on 5/15/2017.
 */

public class YouTubeVideo {
    public String id;
    public String title;

    public YouTubeVideo(String id, String content) {
        this.id = id;
        this.title = content;
    }

    @Override
    public String toString() {
        return title;
    }
}
