package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Configuration implements Serializable {

    public static final String TAG = "Configuration";

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
