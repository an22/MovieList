package com.movielist.model.network.bodies;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("value")
    private int value;

    public Rating(int value){
        this.value = value;
    }
}
