package com.movielist.model.entity.moviedetails;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class MovieImages {

    @SerializedName("backdrops")
    private Image[] backdrops;

    @SerializedName("posters")
    private Image[] posters;



    public Image[] getBackdrops() {
        return backdrops;
    }

    public Image[] getPosters() {
        return posters;
    }

}
