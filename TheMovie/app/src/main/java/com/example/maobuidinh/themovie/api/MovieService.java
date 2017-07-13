package com.example.maobuidinh.themovie.api;

import com.example.maobuidinh.themovie.model.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by maobuidinh on 7/13/2017.
 */

// Declare all your API calls here
public interface MovieService {
    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );
}
