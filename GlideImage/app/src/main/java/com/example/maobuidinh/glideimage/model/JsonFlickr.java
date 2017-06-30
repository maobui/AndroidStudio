package com.example.maobuidinh.glideimage.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by maobuidinh on 6/30/2017.
 */

public class JsonFlickr {

    private String title;

    private String link;

    private String description;

    private String modified;

    private String generator;

    @SerializedName("items")
    private ArrayList<ImageFlickr> mImageFlickrs;

    public ArrayList<ImageFlickr> getImageFlickrs() {
        return mImageFlickrs;
    }

}
