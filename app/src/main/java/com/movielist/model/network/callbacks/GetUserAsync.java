package com.movielist.model.network.callbacks;

import android.os.AsyncTask;

import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.catalog.User;
import com.movielist.model.network.requests.GetUserDetails;

import java.io.IOException;

public class GetUserAsync extends AsyncTask<String,Void, User> {
    @Override
    protected User doInBackground(String... strings) {

        User user;
        GetUserDetails userDetails = RetrofitSingleton.getInstance().getRetrofit().create(GetUserDetails.class);
        try {
            user = userDetails.getUser(TmdbConstants.keyV3,strings[0]).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }
}
