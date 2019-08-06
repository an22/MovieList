package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Session;

public class GuestSession implements Session {

    @SerializedName(value = "session_id",alternate = "guest_session_id")
    private String sessionId;

    public String getSessionID(){
        return sessionId;
    }

    public GuestSession(String sessionId){
        this.sessionId = sessionId;
    }
}
