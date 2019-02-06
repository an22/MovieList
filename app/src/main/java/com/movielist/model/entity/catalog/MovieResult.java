package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.TmdbConstants;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.GetPopular;
import com.movielist.model.network.requests.GetTopRated;
import com.movielist.model.network.requests.GetUpcoming;

import java.util.ArrayList;
import java.util.Observable;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieResult extends Observable {

    @SerializedName("page")
    private int currentPage;

    @SerializedName("results")
    private ArrayList<MovieShort> mShorts;

    @SerializedName("total_pages")
    private int pages;



    public void loadUpcoming(User user,int page) {
        GetUpcoming upcomingReq = RetrofitSingleton.getInstance().getRetrofit().create(GetUpcoming.class);
        upcomingReq.get(TmdbConstants.keyV3, user.getLanguage() + "-" + user.getCountry(), page).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                System.out.println(response.toString());
                if (movieResult != null) {
                    add(movieResult);
                    setChanged();
                    notifyObservers();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    public void loadTopRated(User user,int page) {
        GetTopRated topRatedReq = RetrofitSingleton.getInstance().getRetrofit().create(GetTopRated.class);
        topRatedReq.get(TmdbConstants.keyV3,user.getLanguage() + "-" + user.getCountry(),page).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                System.out.println(response.toString());
                if(movieResult != null){
                    add(movieResult);
                    setChanged();
                    notifyObservers();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });

    }

    public void loadPopular(User user,int page) {
        GetPopular popularReq = RetrofitSingleton.getInstance().getRetrofit().create(GetPopular.class);
        popularReq.get(TmdbConstants.keyV3,user.getLanguage() + "-" + user.getCountry(),page).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                System.out.println(response.toString());
                if(movieResult != null){
                    add(movieResult);
                    setChanged();
                    notifyObservers();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    private void add(MovieResult result){
        mShorts.addAll(result.getShorts());
        pages = result.getPages();
        currentPage = result.getPage();
    }

    public MovieResult() {
        mShorts = new ArrayList<>();
    }

    public int getPage() {
        return currentPage;
    }

    public ArrayList<MovieShort> getShorts() {
        return mShorts;
    }

    public int getPages() {
        return pages;
    }
}
