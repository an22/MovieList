package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("success")
    private boolean success;
    @SerializedName("session_id")
    private String session;

    public String getId() {
        return session;
    }
}
