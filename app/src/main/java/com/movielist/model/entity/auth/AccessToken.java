package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessToken implements Serializable {

    public static transient final String TAG = "AccessToken";

    @SerializedName("access_token")
    private String accessToken;

    public AccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getToken() {
        return accessToken;
    }
}
