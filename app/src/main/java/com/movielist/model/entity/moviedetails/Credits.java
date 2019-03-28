package com.movielist.model.entity.moviedetails;

import com.google.gson.annotations.SerializedName;

public class Credits {

    @SerializedName("cast")
    public Cast[] mCasts;
    @SerializedName("crew")
    public Crew[] mCrews;

    public Credits(){
        mCasts = new Cast[0];
        mCrews = new Crew[0];
    }
}
