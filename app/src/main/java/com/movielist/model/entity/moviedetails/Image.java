package com.movielist.model.entity.moviedetails;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("file_path")
    private String posterPath;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
