package com.me.bui.themoviedatabinding.api;


import com.me.bui.themoviedatabinding.model.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("movie/{id}")
    Call<TopRatedMovies> getMovieDetails(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );
}
