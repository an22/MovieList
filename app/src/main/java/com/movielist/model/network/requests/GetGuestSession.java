package com.movielist.model.network.requests;

import com.movielist.model.entity.auth.GuestSession;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetGuestSession {


    @GET("/3/authentication/guest_session/new")
    Call<GuestSession> getGuestSession(@Query("api_key") String key);
}
