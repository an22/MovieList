package com.movielist.model.entity.catalog;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.model_interfaces.Loadable;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.Movies;
import com.movielist.presenter.model_listeners.UINetworkListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieResult implements Loadable {

    @SerializedName("page")
    private int currentPage;

    @SerializedName("results")
    private ArrayList<Movie> mShorts;

    @SerializedName("total_pages")
    private int pages;

    private DownloadTypes type;

    private boolean loading = false;

    private String currentQuery;

    private Movies movieRequests;

    private String language;

    private String region;

    private UINetworkListener mListener;

    private static String TAG = "Network";


    public MovieResult(String language,String region) {
        mShorts = new ArrayList<>();
        movieRequests = RetrofitSingleton.getInstance().getRetrofit().create(Movies.class);
        pages = 1;
        currentPage = 0;
        this.language = language;
        this.region = region;
    }

    public void setListener(UINetworkListener listener) {
        mListener = listener;
    }

    public void setType(DownloadTypes type) {
        this.type = type;
    }

    public void loadMore(){
        if(!loading&& currentPage < pages) {
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

    private void loadTopRated() {
        movieRequests.getTopRated(TmdbConstants.keyV3, language,region, ++currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    mListener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                mListener.onError(Error.NETWORK_ERROR);
                Log.e(TAG,Error.NETWORK_ERROR);
            }
        });
    }

    private void loadPopular() {
        movieRequests.getPopular(TmdbConstants.keyV3, language,region, ++currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    mListener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                mListener.onError(Error.NETWORK_ERROR);
                Log.e(TAG,Error.NETWORK_ERROR);
            }
        });
    }

    private void loadUpcoming() {
        movieRequests.getUpcoming(TmdbConstants.keyV3, language,region, ++currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    mListener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                mListener.onError(Error.NETWORK_ERROR);
                Log.e(TAG,Error.NETWORK_ERROR);
            }
        });
    }

    public void loadFromQuery(String query){
        if(!query.equals(currentQuery)) {
            resetPage();
            mShorts.clear();
        }
        currentQuery = query;
        loading = true;
        movieRequests.search(TmdbConstants.keyV3,language,region,query,++currentPage).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                Log.i(TAG,response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    loading = false;
                    mListener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                mListener.onError(Error.NETWORK_ERROR);
                Log.e(TAG, Error.NETWORK_ERROR);
            }
        });
    }

    private void add(MovieResult result){
        mShorts.addAll(result.getShorts());
        pages = result.getPages();
    }

    /*private void replace(MovieResult result){
        mShorts.clear();
        mShorts.addAll(result.getShorts());
        pages = result.getPages();
    }*/

    private void resetPage(){
        currentPage = 0;
    }

    public void setQuery(String query) {
        this.currentQuery = query;
    }

    public ArrayList<Movie> getShorts() {
        return mShorts;
    }

    public int getPages() {
        return pages;
    }
}
