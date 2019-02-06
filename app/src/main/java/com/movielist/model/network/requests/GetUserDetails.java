package com.movielist.model.network.requests;

import com.movielist.model.entity.catalog.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUserDetails {


    @GET("/3/account")
    Call<User> getUser(@Query("api_key")String api_key,@Query("session_id") String sessionId);
}
