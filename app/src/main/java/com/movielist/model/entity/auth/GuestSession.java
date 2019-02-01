package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;

public class GuestSession {

    @SerializedName("success")
    private boolean success;

    @SerializedName("guest_session_id")
    private String sessionId;

    @SerializedName("expires_at")
    private String expireDate;

    public String getSessionId(){
        return sessionId;
    }
}
