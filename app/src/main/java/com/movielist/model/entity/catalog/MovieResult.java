package com.movielist.model.entity.catalog;

import android.util.Log;

import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.DownloadTypes;
import com.movielist.model.entity.Result;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.Movies;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieResult extends Result {


    private DownloadTypes type;

    private Movies movieRequests;


    public MovieResult(String language,String region) {
        super(language,region);
        movieRequests = RetrofitSingleton.getInstance().getRetrofit().create(Movies.class);
    }

    public MovieResult(String language,String region,DownloadTypes type) {
        super(language,region);
        movieRequests = RetrofitSingleton.getInstance().getRetrofit().create(Movies.class);
        this.type = type;
    }

    public void setType(DownloadTypes type) {
        this.type = type;
    }

    public void loadMore(){
        if(!loading && canLoadPage()) {
            switch (type) {
                case UPCOMING: {
                    loading = true;
                    loadUpcoming();
                    break;
                }
                case TOP_RATED: {
                    loading = true;
                    loadTopRated();
                    break;
                }
                case POPULAR: {
                    loading = true;
                    loadPopular();
                    break;
                }
                case QUERY:{
                    loading = true;
                    loadFromQuery(currentQuery);
                    break;
                }
                default:
                    break;
            }
        }

    }

    private void loadTopRated()  {
            movieRequests.getTopRated(TmdbConstants.keyV3, language, region, currentPage).enqueue(new Callback<MovieResult>() {
                @Override
                public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                    MovieResult movieResult = response.body();
                    Log.i(NETWORK_TAG, response.toString());
                    if (movieResult != null) {
                        add(movieResult);
                        loading = false;
                        listener.onLoaded();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                    listener.onError(Error.NETWORK_ERROR);
                    Log.e(NETWORK_TAG, t.toString());
                }
            });
    }

    private void loadPopular() {
        movieRequests.getPopular(TmdbConstants.keyV3, language,region, currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    listener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                listener.onError(Error.NETWORK_ERROR);
                Log.e(NETWORK_TAG,t.toString());
            }
        });
    }

    private void loadUpcoming() {
        movieRequests.getUpcoming(TmdbConstants.keyV3, language,region, currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    listener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                listener.onError(Error.NETWORK_ERROR);
                Log.e(NETWORK_TAG,t.toString());
            }
        });
    }

    @Override
    public void loadFromQuery(String query){
        listener.onStart();
        checkQuery(query);
        movieRequests.search(TmdbConstants.keyV3,language,region,query,currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    listener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                listener.onError(Error.NETWORK_ERROR);
                Log.e(NETWORK_TAG, t.toString());
            }
        });
    }


    public void setQuery(String query) {
        this.currentQuery = query;
    }

}
