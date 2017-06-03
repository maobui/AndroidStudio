package com.example.maobuidinh.recyclerviewgmail.network;

import com.example.maobuidinh.recyclerviewgmail.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maobuidinh on 6/2/2017.
 */

public interface ApiInterface {
    @GET("inbox.json")
    Call<List<Message>> getInbox();
}
