package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.entity.auth.AccessToken;

public class User {

    @SerializedName("avatar")
    private Avatar avatar;

    @SerializedName("id")
    private int id;

    private AccessToken accessToken;

    @SerializedName("name")
    private String name;

    @SerializedName("include_adult")
    private  boolean includeAdult;

    @SerializedName("username")
    private String username;



    //-----------------------------------Getter-----------------------------------------------


    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public String getUsername() {
        return username;
    }
}
