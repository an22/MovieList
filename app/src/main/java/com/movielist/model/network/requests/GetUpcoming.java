package com.movielist.model.network.requests;

import com.movielist.model.entity.catalog.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUpcoming {

    @GET("/3/movie/upcoming")
    Call<MovieResult> get(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);
}
