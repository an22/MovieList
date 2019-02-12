package com.movielist.model.network.requests;

import com.movielist.model.entity.catalog.PersonResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Peoples {

    @GET
    Call<PersonResult> search(@Query("api_key") String key, @Query("language") String language, @Query("region") String region, @Query("query")String query, @Query("page") int page);
}
