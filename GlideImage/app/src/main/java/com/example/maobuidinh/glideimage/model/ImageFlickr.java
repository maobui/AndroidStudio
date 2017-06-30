package com.example.maobuidinh.glideimage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by maobuidinh on 6/30/2017.
 */

public class ImageFlickr extends ImageTemplate{

    private String title;

    private String link;

    private MediaFlickr media;

    @SerializedName("date_taken")
    private String dateTaken;

    private String description;

    private String published;

    private String author;

    @SerializedName("author_id")
    private String authorId;

    private String tags;

    @Override
    public String getTitle() {
        return title;
    }

    public MediaFlickr getMedia() {
        return media;
    }

    @Override
    public String getSmall(){
        MediaFlickr mediaFlickr = this.getMedia();
        return mediaFlickr.getM();
    }

    @Override
    public String getMedium(){
        MediaFlickr mediaFlickr = this.getMedia();
        String linkMedium = mediaFlickr.getM();
        linkMedium = linkMedium.replace("_m.jpg", "_c.jpg");
        return linkMedium;
    }

    @Override
    public String getLarge(){
        MediaFlickr mediaFlickr = this.getMedia();
        String linkLarge = mediaFlickr.getM();
        linkLarge = linkLarge.replace("_m.jpg", "_b.jpg");
        return linkLarge;
    }

    @Override
    public String getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }
}
