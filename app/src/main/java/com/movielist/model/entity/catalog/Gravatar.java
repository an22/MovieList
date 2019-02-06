package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Gravatar implements Serializable {

    @SerializedName("hash")
    private String hash;

    String getHash() {
        return hash;
    }
}
