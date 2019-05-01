package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Session;

public class GuestSession implements Session {

    @SerializedName("success")
    private boolean success;

    @SerializedName("guest_session_id")
    private String sessionId;

    @SerializedName("expires_at")
    private String expireDate;

    public String getSessionID(){
        return sessionId;
    }
}
