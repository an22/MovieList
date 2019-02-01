package com.movielist.model.network.requests;

import com.movielist.model.entity.Redirect;
import com.movielist.model.entity.auth.RequestToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostRequestToken {

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("/4/auth/request_token")
    Call<RequestToken> getRequestToken(@Header("Authorization") String token, @Body Redirect redirect_to);

}
