package com.movielist.model.entity.tvdetails;

import com.google.gson.annotations.SerializedName;

public class CreatedBy {

    @SerializedName("id")
    private int id;

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private int gender;

    @SerializedName("profile_path")
    private String profilePath;

    public int getId() {
        return id;
    }

    public String getCreditId() {
        return creditId;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
