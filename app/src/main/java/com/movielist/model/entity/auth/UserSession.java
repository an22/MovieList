package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Session;

public class UserSession implements Session {

    @SerializedName("success")
    private boolean success;
    @SerializedName("session_id")
    private String session;

    public String getSessionID() {
        return session;
    }
}
