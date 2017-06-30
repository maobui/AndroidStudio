package com.example.maobuidinh.glideimage.config;

/**
 * Created by maobuidinh on 6/22/2017.
 */

public class Config {

    // URL get json.
    //public static final String endpoint = "http://api.androidhive.info/json/glide.json";
    public static final String ENDPOINT = "https://raw.githubusercontent.com/maobui/AndroidStudio/master/GlideImage/glide.json";
    public static final String ENDPOINT_DYNAMIC = "https://api.flickr.com/services/feeds/photos_faves.gne?nsid=38041819@N04&format=json&nojsoncallback=1&page=";
    //public static final String ENDPOINT_DYNAMIC = "http://api.flickr.com/services/feeds/photos_public.gne?nsid=hoangchino_photographer&format=json&nojsoncallback=1";

    // fetch data from Flickr.
    public static  boolean isUseDynamicServer = true;

    // type of get json from server.
    public static  boolean isUseVolley = true;
    public static  boolean isUseAsyncTask = !isUseVolley;

    // use type of scroll listener.
    public static boolean isUseEndlessScrollListenner = true;
    public static boolean isUSePaginationScrollListenner = !isUseEndlessScrollListenner;

    // use only for Hack RecyclerReview with LinearLayoutManager.
    public static boolean isUseHackLinearLayout = false;

    // use for scroll page
    public static final int START_PAGE = 1;
    public static final int END_PAGE = 5;
}
