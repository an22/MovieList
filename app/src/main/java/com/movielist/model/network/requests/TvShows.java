package com.movielist.model.network.requests;

import com.movielist.model.entity.catalog.TvResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShows {

    @GET("/3/search/tv")
    Call<TvResult> search(@Query("api_key") String key, @Query("language") String language, @Query("query")String query, @Query("page") int page);
}
