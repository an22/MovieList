package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status_message")
    private String statusMessage;

    public String getToken() {
        return access_token;
    }
}
