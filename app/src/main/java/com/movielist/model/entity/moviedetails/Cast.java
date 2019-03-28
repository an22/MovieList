package com.movielist.model.entity.moviedetails;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Credit;

public class Cast implements Credit {

    @SerializedName("cast_id")
    private int castId;

    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;


    public int getCastId() {
        return castId;
    }

    public String getCreditId() {
        return creditId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return character;
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }
}
