package com.movielist.model.network.requests;

import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.UserSession;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostCreateSession {


    @POST("/3/authentication/session/convert/4")
    Call<UserSession> getSession(@Query("api_key") String keyV3, @Body AccessToken accessToken);
}
