package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.entity.auth.AccessToken;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class User implements Serializable {

    public static final String USER = "USER";
    public static final String GUEST = "GUEST";

    private boolean isGuest;

    @SerializedName("avatar")
    private Avatar avatar;

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


    //This class is inner cuz without user that class is useless
    class Avatar implements Serializable {

        //For more info about gravatar check out https://gravatar.com
        @SerializedName("gravatar")
        private Gravatar gravatar;

        String getGravatar() {
            return gravatar.getHash();
        }

        class Gravatar implements Serializable {

            @SerializedName("hash")
            private String hash;

            String getHash() {
                return hash;
            }
        }

    }


    //----------------------------------------------------------------------------------


    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar.getGravatar();
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
