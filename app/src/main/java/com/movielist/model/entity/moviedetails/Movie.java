package com.movielist.model.entity.moviedetails;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.Error;
import com.movielist.model.ResultTypes;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.ImagePaths;
import com.movielist.model.model_interfaces.MovieModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.bodies.FavouriteBody;
import com.movielist.model.network.bodies.Rating;
import com.movielist.model.network.bodies.WatchlistBody;
import com.movielist.model.network.requests.Movies;
import com.movielist.presenter.model_listeners.UINetworkListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Movie implements MovieModel {

    public static final String TAG = "Movie";

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
    private ImagePaths mImages;

    @SerializedName("genres")
    private Genre[] mGenres;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("credits")
    private Credits mCredits;

    private Movies mMovieRequests;

    public Movie(){
        mMovieRequests = RetrofitSingleton.getInstance().getRetrofit().create(Movies.class);
    }

    @Override
    public void loadMovie(String movieID, UINetworkListener listener) {
        listener.onStart();
        mMovieRequests.getDetails(movieID, TmdbConstants.keyV3,"images,credits","en").enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                Movie movie = response.body();
                Log.i(TAG,response.toString());
                if(movie != null){
                    posterPath = movie.posterPath;
                    id = movie.id;
                    title = movie.title;
                    description = movie.description;
                    rating = movie.rating;
                    mImages = movie.mImages;
                    mGenres = movie.mGenres;
                    runtime = movie.runtime;
                    mCredits = movie.mCredits;
                    listener.onLoaded();
                }
                else listener.onError(Error.ACCESS_ERROR);
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Log.e(TAG,t.toString());
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public Credits getCredits() {
        return mCredits;
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
    public ImagePaths getImages() {
        return mImages;
    }

    @Override
    public int getRuntime(){
        return runtime;
    }

    @Override
    public void addToFavourites(int userID,String session) {
        mMovieRequests.markAsFavourite(String.valueOf(userID),TmdbConstants.keyV3,session,new FavouriteBody(ResultTypes.MOVIE,id)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                Log.i(TAG,response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    @Override
    public void addToWatchlist(int userID,String session) {
        mMovieRequests.addToWatchList(String.valueOf(userID),TmdbConstants.keyV3,session, new WatchlistBody(ResultTypes.MOVIE,id)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                Log.i(TAG,response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    @Override
    public void deleteRating(String session) {
        mMovieRequests.deleteRating(String.valueOf(id),TmdbConstants.keyV3,session).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                Log.i(TAG,response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    @Override
    public void rate(int rating,String session) {
        mMovieRequests.rate(String.valueOf(id),TmdbConstants.keyV3,session,new Rating(rating)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                Log.i(TAG, response.toString());
                if(response.code()!= 201){
                    Log.e(TAG, Error.BAD_RESPONSE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void rateGuest(int rating, String session) {
        mMovieRequests.rateGuest(String.valueOf(id),TmdbConstants.keyV3,session,new Rating(rating)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                Log.i(TAG, response.toString());
                if(response.code()!= 201){
                    Log.e(TAG, Error.BAD_RESPONSE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void deleteRatingGuest(String session) {
        mMovieRequests.deleteRating(String.valueOf(id),TmdbConstants.keyV3,session).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> response) {
                Log.i(TAG,response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,@NonNull Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

}
