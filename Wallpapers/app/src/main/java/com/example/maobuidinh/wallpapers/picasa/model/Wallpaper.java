package com.example.maobuidinh.wallpapers.picasa.model;

import java.io.Serializable;

/**
 * Created by mao.buidinh on 7/25/2017.
 */

public class Wallpaper implements Serializable{
    private static final long serialVersionUID = 1L;

    private String url, photoJson;
    private int width, height;

    public Wallpaper() {
    }

    public Wallpaper(String url, String photoJson, int width, int height) {
        this.url = url;
        this.photoJson = photoJson;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoJson() {
        return photoJson;
    }

    public void setPhotoJson(String photoJson) {
        this.photoJson = photoJson;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
