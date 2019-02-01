package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;

public class Redirect {

    @SerializedName("redirect_to")
    private String redirect_to;

    public Redirect(String redirect_to){
        this.redirect_to = redirect_to;
    }
}
