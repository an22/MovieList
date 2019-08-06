package com.movielist.model.network.bodies;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.ResultTypes;

public class WatchlistBody {

    @SerializedName("media_type")
    private String mType;

    @SerializedName("media_id")
    private int id;

    @SerializedName("watchlist")
    boolean watchlist = true;

    public WatchlistBody(ResultTypes type, int mediaId){
        if(type == ResultTypes.PEOPLE) throw new IllegalArgumentException("Allowed values: "
                + ResultTypes.MOVIE.getName()
                + ", "
                + ResultTypes.TV.getName());
        this.mType = type.getName();
        this.id = mediaId;
    }
}
