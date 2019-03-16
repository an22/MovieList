package com.movielist.model.entity.catalog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.movielist.database.KeyContract;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.model_interfaces.CatalogModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.GetUserDetails;
import com.movielist.presenter.model_listeners.GuestListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogData implements CatalogModel {

    private static final String TAG = "CATALOG_DATA";

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
    public void loadUser(String session, GuestListener listener,Context context) {
        listener.onStart();
        GetUserDetails userDetails = RetrofitSingleton.getInstance().getRetrofit().create(GetUserDetails.class);
        userDetails.getUser(TmdbConstants.keyV3,session).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                User user = response.body();
                Log.i(TAG,response.toString());
                if(user != null) {
                    Log.i(User.USER, user.toString());
                    CatalogData.this.user = user;
                    user.setGuest(false);
                    if(loadAccessTokenFromDb(context)) listener.onLoaded();

                    else listener.onError(Error.DATABASE_ERROR);
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

    private boolean loadAccessTokenFromDb(Context context){
        KeyDbHelper mKeyDbHelper = new KeyDbHelper(context);
        try (SQLiteDatabase db = mKeyDbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM "
                     + KeyContract.KeyColumns.TABLE_NAME + " WHERE "
                     + KeyContract.KeyColumns.COLUMN_NAME_TITLE + "="
                     + "\"" + AccessToken.TAG + "\"", null)) {
            cursor.moveToNext();
            int id = cursor.getColumnIndexOrThrow(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE);
            String data = cursor.getString(id);
            user.setAccessToken(new AccessToken(data));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
