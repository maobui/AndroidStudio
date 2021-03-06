package com.example.maobuidinh.recyclerviewgmail.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maobuidinh on 6/2/2017.
 */

public class ApiClient {
//    public static final String BASE_URL = "http://api.androidhive.info/json/";
//    public static final String BASE_URL = "https://github.com/maobui/AndroidStudio/blob/master/RecyclerViewGmail/json/";
    public static final String BASE_URL = "https://raw.githubusercontent.com/maobui/AndroidStudio/master/RecyclerViewGmail/json/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
