package com.movielist.model.network.requests;

import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.RequestToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostAccessToken {

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("/4/auth/access_token")
    Call<AccessToken> getAccessToken(@Header("Authorization") String v4_token, @Body RequestToken request_token);

}
