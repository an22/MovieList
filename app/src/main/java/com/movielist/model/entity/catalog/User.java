package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.entity.auth.AccessToken;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class User implements Serializable {

    public static final String USER = "USER";

    private boolean isGuest;

    @SerializedName("id")
    private int id;

    @SerializedName("iso_639_1")
    private String language;

    @SerializedName("iso_3166_1")
    private String country;

    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("include_adult")
    private boolean adult;

    private AccessToken mAccessToken;

    //Default user is Guest
    public User(){
        language = "en";
        country = "US";
        name = "Guest";
        id = -1;
        adult = false;
        isGuest = true;
    }


    //----------------------------------------------------------------------------------


    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    void setAccessToken(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isGuest() {
        return isGuest;
    }

    void setGuest(boolean guest) {
        isGuest = guest;
    }

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdult() {
        return adult;
    }

    @Override
    @NonNull
    public String toString(){
        return name + " " + username + " " + language;
    }
}
