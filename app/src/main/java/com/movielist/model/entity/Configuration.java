package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;

public class Configuration {

    @SerializedName("images")
    private ImageConfiguration imageConfig;

    @SerializedName("change_keys")
    private String[] changeKeys;


    public ImageConfiguration getImageConfig() {
        return imageConfig;
    }

    public String[] getChangeKeys() {
        return changeKeys;
    }
}
