package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Avatar implements Serializable {

    @SerializedName("gravatar")
    private Gravatar gravatar;

    String getGravatar() {
        return gravatar.getHash();
    }

}
