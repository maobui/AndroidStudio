package com.example.maobuidinh.glideimage.model;

import java.io.Serializable;

/**
 * Created by maobuidinh on 6/10/2017.
 */

public class Image extends ImageTemplate implements Serializable {

    private String name;
    private ImageUrl url;
    private String timestamp;

    public ImageUrl getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getSmall() {
        return getUrl().getSmall();
    }

    @Override
    public String getMedium() {
        return getUrl().getMedium();
    }

    @Override
    public String getLarge() {
        return getUrl().getLarge();
    }

    @Override
    public String getTitle() {
        return this.getName();
    }

    @Override
    public String getPublished() {
        return this.getTimestamp();
    }

    public String getTimestamp() {
        return timestamp;
    }

}
