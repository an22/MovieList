package com.movielist.model.network.callbacks;

import android.os.AsyncTask;

import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.model.network.requests.GetUpcoming;

import java.io.IOException;

public class UpcomingAsync extends AsyncTask<String,Void, MovieResult> {
    @Override
    protected MovieResult doInBackground(String... strings) {
        GetUpcoming upcomingReq = RetrofitSingleton.getInstance().getRetrofit().create(GetUpcoming.class);
        try {
            MovieResult result = upcomingReq.get(TmdbConstants.keyV3, strings[0], Integer.parseInt(strings[1])).execute().body();
            if(result != null) {
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
