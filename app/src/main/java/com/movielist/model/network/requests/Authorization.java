package com.movielist.model.network.requests;

import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.GuestSession;
import com.movielist.model.entity.auth.Redirect;
import com.movielist.model.entity.auth.RequestToken;
import com.movielist.model.entity.auth.UserSession;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Authorization {

    @GET("/3/authentication/guest_session/new")
    Call<GuestSession> getGuestSession(@Query("api_key") String key);

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("/4/auth/access_token")
    Call<AccessToken> getAccessToken(@Header("Authorization") String v4_token, @Body RequestToken request_token);

    @POST("/3/authentication/session/convert/4")
    Call<UserSession> getSession(@Query("api_key") String keyV3, @Body AccessToken accessToken);

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("/4/auth/request_token")
    Call<RequestToken> getRequestToken(@Header("Authorization") String token, @Body Redirect redirect_to);

}
