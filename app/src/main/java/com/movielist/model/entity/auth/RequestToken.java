package com.movielist.model.entity.auth;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class RequestToken {


    @SerializedName("request_token")
    private String requestToken;


    public RequestToken(String requestToken) {
        this.requestToken = requestToken;
    }


    public String getToken() {
        return requestToken;
    }


    @Override
    @NonNull
    public String toString(){
        return requestToken;
    }


}

