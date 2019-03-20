package com.movielist.model.entity.catalog;

import android.util.Log;

import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.Result;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.TvShows;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvResult extends Result {

    private static final String TAG = "TV_RESULT";
    private TvShows mTvShows;

    public TvResult(String language, String region) {
        super(language, region);
        mTvShows = RetrofitSingleton.getInstance().getRetrofit().create(TvShows.class);
    }

    @Override
    public void loadFromQuery(String query) {
        listener.onStart();
        checkQuery(query);
        mTvShows.search(TmdbConstants.keyV3,language,query,currentPage).enqueue(new Callback<TvResult>() {
            @Override
            public void onResponse(@NonNull Call<TvResult> call,@NonNull Response<TvResult> response) {
                TvResult result = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if(result != null){
                    add(result);
                    loading = false;
                    listener.onLoaded();

                }
            }

            @Override
            public void onFailure(@NonNull Call<TvResult> call,@NonNull Throwable t) {
                listener.onError(Error.NETWORK_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }

    @Override
    public void loadMore() {
        if (!loading&&canLoadPage()){
            loadFromQuery(currentQuery);
        }
    }
}
