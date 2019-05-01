package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessToken implements Serializable {

    public static final String TAG = "AccessToken";

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status_message")
    private String statusMessage;

    public AccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getToken() {
        return accessToken;
    }
}
