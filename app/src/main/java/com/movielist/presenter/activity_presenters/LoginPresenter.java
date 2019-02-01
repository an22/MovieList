package com.movielist.presenter.activity_presenters;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.movielist.database.KeyContract.KeyColumns;
import com.movielist.database.KeyDbHelper;
import com.movielist.model.RetrofitSingletone;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.Redirect;
import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.GuestSession;
import com.movielist.model.entity.auth.LoginData;
import com.movielist.model.entity.auth.RequestToken;
import com.movielist.model.entity.auth.Session;
import com.movielist.model.model_interfaces.LoginModel;
import com.movielist.model.network.requests.GetGuestSession;
import com.movielist.model.network.requests.PostAccessToken;
import com.movielist.model.network.requests.PostCreateSession;
import com.movielist.model.network.requests.PostRequestToken;
import com.movielist.view.view_interfaces.LoginView;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    private LoginView loginActivity;
    private LoginModel mLoginData;
    private KeyDbHelper mHelper;

    public static final String REQUEST_TOKEN = "RequestToken";
    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String GUEST_SESSION = "GuestSession";
    public static final String SESSION = "Session";

    private final String LOGIN_ERROR = "Can`t sign in, try again later";
    private final String GUEST_ERROR = "Can`t create guest session, try again later";
    private final String ACCESS_ERROR = "Can`t access to server";
    private final String DATABASE_ERROR = "Database error, contact us";

    public LoginPresenter(LoginView loginActivity, LoginData mLoginData, KeyDbHelper helper) {

        this.loginActivity = loginActivity;
        this.mLoginData = mLoginData;
        this.mHelper = helper;

    }


    public void getRequestToken(){
        PostRequestToken token = RetrofitSingletone.getInstance().getRetrofit().create(PostRequestToken.class);
        token.getRequestToken("Bearer " + TmdbConstants.keyV4,new Redirect("com.movielist://token_callback")).enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(@NonNull Call<RequestToken> call, @NonNull Response<RequestToken> response) {
                mLoginData.setRequestToken(response.body());
                System.out.println(response.body());
                if (mLoginData.getRequestToken() != null) {
                    saveDataToDb(REQUEST_TOKEN,mLoginData.getRequestToken().getToken());
                    String url = TmdbConstants.host + "/auth/access?request_token=" + mLoginData.getRequestToken().getToken();
                    loginActivity.checkToken(url);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestToken> call, @NonNull Throwable t) {
                loginActivity.onError(LOGIN_ERROR);
            }
        });
    }

    public void getGuestSession() {
        GetGuestSession getGuestSession = RetrofitSingletone.getInstance().getRetrofit().create(GetGuestSession.class);
        getGuestSession.getGuestSession(TmdbConstants.keyV3).enqueue(new Callback<GuestSession>() {
            @Override
            public void onResponse(@NonNull Call<GuestSession> call,@NonNull Response<GuestSession> response) {
                GuestSession guestSession = response.body();
                if(guestSession != null){
                    saveDataToDb(GUEST_SESSION,guestSession.getSessionId());
                    loginActivity.launchNext(guestSession.getSessionId(),GUEST_SESSION);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GuestSession> call,@NonNull Throwable t) {
                loginActivity.onError(GUEST_ERROR);
            }
        });


    }

    public void getAccessToken(){
        loadDataFromDb();
        PostAccessToken postAccessToken = RetrofitSingletone.getInstance().getRetrofit().create(PostAccessToken.class);
        postAccessToken.getAccessToken("Bearer " + TmdbConstants.keyV4, mLoginData.getRequestToken()).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call,@NonNull Response<AccessToken> response) {
                System.out.println(response.toString());
                System.out.println(response.body());
                mLoginData.setAccessToken(response.body());
                if(mLoginData.getAccessToken() != null){
                    System.out.println(mLoginData.getAccessToken().getToken());
                    System.out.println(mLoginData.getRequestToken().getToken());
                    getSession();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                loginActivity.onError(ACCESS_ERROR);
            }
        });
        System.out.println(mLoginData.getRequestToken().getToken());
    }

    private void getSession(){
        PostCreateSession createSession = RetrofitSingletone.getInstance().getRetrofit().create(PostCreateSession.class);
        createSession.getSession(TmdbConstants.keyV3,mLoginData.getAccessToken()).enqueue(new Callback<Session>() {
            @Override
            public void onResponse(@NonNull Call<Session> call,@NonNull Response<Session> response) {
                Session session = response.body();
                if(session != null){
                    saveDataToDb(SESSION,session.getId());
                    loginActivity.launchNext(session.getId(),SESSION);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Session> call,@NonNull Throwable t) {

            }
        });
    }

    private void saveDataToDb(String column,String data){

        try(SQLiteDatabase db = mHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KeyColumns.COLUMN_NAME_TITLE, column);
            values.put(KeyColumns.COLUMN_NAME_SUBTITLE, data);
            db.insert(KeyColumns.TABLE_NAME, null, values);
        }
        catch (Exception e){
            loginActivity.onError(DATABASE_ERROR);
            e.printStackTrace();
        }
    }

    private void loadDataFromDb(){
        try (SQLiteDatabase db = mHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM "
                + KeyColumns.TABLE_NAME + " WHERE "
                + KeyColumns.COLUMN_NAME_TITLE + "="
                + "\"" + REQUEST_TOKEN + "\"", null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getColumnIndexOrThrow(KeyColumns.COLUMN_NAME_SUBTITLE);
                String data = cursor.getString(id);
                mLoginData.setRequestToken(new RequestToken(data));
            }
        } catch (Exception e) {
            loginActivity.onError(DATABASE_ERROR);
            e.printStackTrace();
        }
    }

}
