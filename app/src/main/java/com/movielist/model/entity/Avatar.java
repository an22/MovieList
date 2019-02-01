package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;

public class Avatar {
    @SerializedName("gravatar")
    private String gravatar;

    public String getGravatar() {
        return gravatar;
    }
}
