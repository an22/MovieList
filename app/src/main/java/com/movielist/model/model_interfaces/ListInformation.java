package com.movielist.model.model_interfaces;

import com.google.gson.annotations.SerializedName;

public class ListInformation {
    @SerializedName("id")
    private int id;

    @SerializedName(value = "title", alternate = {"name"})
    private String name;

    @SerializedName(value = "poster_path", alternate = {"profile_path"})
    private String posterPath;


    public String getTitle() {
        return name;
    }


    public String getImagePath() {
        return posterPath;
    }


    public int getID() {
        return id;
    }
}
