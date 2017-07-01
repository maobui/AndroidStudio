package com.example.maobuidinh.glideimage.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by maobuidinh on 6/30/2017.
 */

public class Item extends ImageTemplate{

    private String title;

    private String link;

    private Media media;

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

    public Media getMedia() {
        return media;
    }

    @Override
    public String getSmall(){
        Media media = this.getMedia();
        return media.getM();
    }

    @Override
    public String getMedium(){
        Media media = this.getMedia();
        String linkMedium = media.getM();
        linkMedium = linkMedium.replace("_m.jpg", "_c.jpg");
        return linkMedium;
    }

    @Override
    public String getLarge(){
        Media media = this.getMedia();
        String linkLarge = media.getM();
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
