package com.movielist.model.network.requests;

import com.movielist.model.entity.Configuration;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetImageCfg {

    @GET("/3/configuration")
    Call<Configuration> getCfg(@Query("api_key") String key);
}
