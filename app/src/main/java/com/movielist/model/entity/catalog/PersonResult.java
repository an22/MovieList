package com.movielist.model.entity.catalog;

import android.util.Log;

import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.Result;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.People;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonResult extends Result {

    private People mPeople;

    public PersonResult(String language, String region) {
        super(language, region);
        mPeople = RetrofitSingleton.getInstance().getRetrofit().create(People.class);
    }



    @Override
    public void loadMore() {
        if(!loading&&canLoadPage()) {
            loading = true;
            loadFromQuery(currentQuery);
        }
    }


    @Override
    public void loadFromQuery(String query){
        listener.onStart();
        checkQuery(query);
        mPeople.search(TmdbConstants.keyV3,language,region,query,currentPage).enqueue(new Callback<PersonResult>() {
            @Override
            public void onResponse(@NonNull Call<PersonResult> call,@NonNull Response<PersonResult> response) {
                PersonResult result = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if(result != null){
                    add(result);
                    loading = false;
                    listener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PersonResult> call,@NonNull Throwable t) {
                listener.onError(Error.NETWORK_ERROR);
                Log.e(NETWORK_TAG,t.toString());
            }
        });
    }
}
