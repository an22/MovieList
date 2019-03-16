package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.TmdbConstants;
import com.movielist.model.model_interfaces.Describable;
import com.movielist.model.model_interfaces.MovieModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.Movies;
import com.movielist.presenter.model_listeners.UINetworkListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Movie implements Describable {

    public static final String TAG = "Movie";

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @Override
    public String getImagePath() {
        return posterPath;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }


}
