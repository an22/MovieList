package com.movielist.model.entity.auth;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.movielist.database.KeyContract;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.model_interfaces.LoginModel;
import com.movielist.model.model_interfaces.Session;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.bodies.Redirect;
import com.movielist.model.network.requests.Authorization;
import com.movielist.presenter.model_listeners.UINetworkListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginData implements LoginModel {

    public static final String TAG = "LoginData";

    private RequestToken mRequestToken;
    private AccessToken mAccessToken;
    private KeyDbHelper mKeyDbHelper;

    private Session mSession;

    private Authorization mAuthorization;

    public LoginData(KeyDbHelper keyDbHelper) {
        mKeyDbHelper = keyDbHelper;
        mAuthorization = RetrofitSingleton.getInstance().getRetrofit().create(Authorization.class);
    }

    @Override
    public RequestToken getRequestToken() {

        return mRequestToken;
    }

    @Override
    public Session getSession() {
        return mSession;
    }

    @Override
    public boolean loadRequestTokenFromDb() {
        try (SQLiteDatabase db = mKeyDbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM "
                     + KeyContract.KeyColumns.TABLE_NAME + " WHERE "
                     + KeyContract.KeyColumns.COLUMN_NAME_TITLE + "="
                     + "\"" + KeyDbHelper.REQUEST_TOKEN + "\"", null)) {
            cursor.moveToNext();
            int id = cursor.getColumnIndexOrThrow(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE);
            String data = cursor.getString(id);
            mRequestToken = new RequestToken(data);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void loadAccessToken(UINetworkListener listener) {
        mAuthorization.getAccessToken("Bearer " + TmdbConstants.keyV4, mRequestToken).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                mAccessToken = response.body();
                Log.i(TAG,response.toString());

                if (mAccessToken != null) {
                    saveDataToDb(AccessToken.TAG,mAccessToken.getToken());
                    loadSession(listener);
                }
                else{
                    listener.onError(Error.NETWORK_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                listener.onError(Error.ACCESS_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }

    @Override
    public void loadRequestToken(UINetworkListener listener) {
        mAuthorization.getRequestToken("Bearer " + TmdbConstants.keyV4,new Redirect("com.movielist://token_callback")).enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(@NonNull Call<RequestToken> call, @NonNull Response<RequestToken> response) {
                mRequestToken = response.body();
                Log.i(TAG,response.toString());
                if (mRequestToken != null) {
                    saveDataToDb(KeyDbHelper.REQUEST_TOKEN,mRequestToken.getToken());
                    listener.onLoaded();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestToken> call, @NonNull Throwable t) {
                listener.onError(Error.LOGIN_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }

    @Override
    public void loadGuestSession(UINetworkListener listener) {
        mAuthorization.getGuestSession(TmdbConstants.keyV3).enqueue(new Callback<GuestSession>() {
            @Override
            public void onResponse(@NonNull Call<GuestSession> call,@NonNull Response<GuestSession> response) {

                mSession = response.body();
                Log.i(TAG,response.toString());
                if(mSession != null){

                    if(saveDataToDb(KeyDbHelper.GUEST_SESSION,mSession.getSessionID())){

                        listener.onStart();

                    }
                    else {
                        listener.onError(Error.DATABASE_ERROR);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GuestSession> call,@NonNull Throwable t) {

                listener.onError(Error.GUEST_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }

    private void loadSession(UINetworkListener listener) {
        mAuthorization.getSession(TmdbConstants.keyV3,mAccessToken).enqueue(new Callback<UserSession>() {
            @Override
            public void onResponse(@NonNull Call<UserSession> call, @NonNull Response<UserSession> response) {
                mSession = response.body();
                Log.i(TAG,response.toString());
                if(mSession != null){
                    saveDataToDb(KeyDbHelper.SESSION,mSession.getSessionID());
                    listener.onStart();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSession> call, @NonNull Throwable t) {
                listener.onError(Error.SESSION_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }

    private boolean saveDataToDb(String column, String data) {
        try(SQLiteDatabase db = mKeyDbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KeyContract.KeyColumns.COLUMN_NAME_TITLE, column);
            values.put(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE, data);
            db.insert(KeyContract.KeyColumns.TABLE_NAME, null, values);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }

    private boolean loadAccessTokenFromDb() {
        try (SQLiteDatabase db = mKeyDbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM "
                     + KeyContract.KeyColumns.TABLE_NAME + " WHERE "
                     + KeyContract.KeyColumns.COLUMN_NAME_TITLE + "="
                     + "\"" + AccessToken.TAG + "\"", null)) {
            cursor.moveToNext();
            int id = cursor.getColumnIndexOrThrow(KeyContract.KeyColumns.COLUMN_NAME_SUBTITLE);
            String data = cursor.getString(id);
            mAccessToken = new AccessToken(data);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
