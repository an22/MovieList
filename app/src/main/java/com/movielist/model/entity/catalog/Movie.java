package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Describable;

public class Movie implements Describable {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("vote_average")
    private double average;

    @SerializedName("video")
    private boolean video;

    @Override
    public String getImagePath() {
        return posterPath;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public double getAverage() {
        return average;
    }
}
