package com.movielist.model.entity.moviedetails;

import com.google.gson.annotations.SerializedName;

public class MovieImages {

    @SerializedName("backdrops")
    private Image[] backdrops;

    @SerializedName("posters")
    private Image[] posters;

    public MovieImages(){

    }

    public Image[] getBackdrops() {
        return backdrops;
    }

    public Image[] getPosters() {
        return posters;
    }

}
