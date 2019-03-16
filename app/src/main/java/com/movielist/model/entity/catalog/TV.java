package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Describable;

public class TV implements Describable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("poster_path")
    private String posterPath;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getImagePath() {
        return posterPath;
    }

    @Override
    public int getID() {
        return id;
    }
}
