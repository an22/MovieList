package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Describable;

public class Person implements Describable {

    @SerializedName("profile_path")
    private String profile;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private double popularity;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getImagePath() {
        return profile;
    }

    @Override
    public int getID() {
        return id;
    }
}
