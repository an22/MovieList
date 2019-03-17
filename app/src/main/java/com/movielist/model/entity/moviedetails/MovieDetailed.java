package com.movielist.model.entity.moviedetails;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.model_interfaces.MovieModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.Movies;
import com.movielist.presenter.model_listeners.UINetworkListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailed implements MovieModel {

    public static final String TAG = "MovieDetailed";

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String description;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("images")
    private MovieImages mImages;

    @SerializedName("genres")
    private Genre[] mGenres;

    @SerializedName("runtime")
    private int runtime;

    public MovieDetailed(){

    }

    @Override
    public void loadMovie(String movieID, UINetworkListener listener) {
        listener.onStart();
        Movies movies = RetrofitSingleton.getInstance().getRetrofit().create(Movies.class);
        movies.getDetails(movieID, TmdbConstants.keyV3,"images","en").enqueue(new Callback<MovieDetailed>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailed> call,@NonNull Response<MovieDetailed> response) {
                MovieDetailed movieDetailed = response.body();
                Log.i(TAG,response.toString());
                if(movieDetailed != null){
                    posterPath = movieDetailed.posterPath;
                    id = movieDetailed.id;
                    title = movieDetailed.title;
                    description = movieDetailed.description;
                    rating = movieDetailed.rating;
                    mImages = movieDetailed.mImages;
                    mGenres = movieDetailed.mGenres;
                    runtime = movieDetailed.runtime;
                    listener.onLoaded();
                }
                else listener.onError(Error.ACCESS_ERROR);
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailed> call,@NonNull Throwable t) {
                Log.e(TAG,t.toString());
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public String getImagePath() {
        return posterPath;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public Genre[] getGenres() {
        return mGenres;
    }

    @Override
    public MovieImages getImages() {
        return mImages;
    }

    @Override
    public int getRuntime(){
        return runtime;
    }

}
