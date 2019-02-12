package com.movielist.model.entity.catalog;

import android.util.Log;

import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.model_interfaces.CatalogModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.GetUserDetails;
import com.movielist.presenter.model_listeners.NetworkListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogData implements CatalogModel {

    private static final String TAG = "CATALOG_DATA";
    private static final String USER_TAG = "USER";

    private User user;

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void loadUser(String session, NetworkListener listener) {

        GetUserDetails userDetails = RetrofitSingleton.getInstance().getRetrofit().create(GetUserDetails.class);
        userDetails.getUser(TmdbConstants.keyV3,session).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                User user = response.body();
                if(user != null) {
                    Log.v(USER_TAG, user.toString());
                    CatalogData.this.user = user;
                    listener.onLoaded();
                }
                else if(response.code() == 401){
                    listener.onGuest();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {
                listener.onError(Error.NETWORK_ERROR);
                Log.e(TAG,Error.NETWORK_ERROR);
            }
        });

    }
}
