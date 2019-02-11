package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class User implements Serializable {

    public static final String USER = "USER";

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


    public User(){
        language = "en";
        country = "US";
        name = "Guest";
        adult = false;
        isGuest = true;
    }

    //-----------------------------------Getter-----------------------------------------------


    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isGuest() {
        return isGuest;
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
        return name + " " + username;
    }
}
