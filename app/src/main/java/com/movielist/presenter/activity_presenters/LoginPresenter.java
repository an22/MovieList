package com.movielist.presenter.activity_presenters;

import android.util.Log;

import com.movielist.database.KeyDbHelper;
import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.GuestSession;
import com.movielist.model.entity.auth.LoginData;
import com.movielist.model.entity.auth.Redirect;
import com.movielist.model.entity.auth.RequestToken;
import com.movielist.model.entity.auth.UserSession;
import com.movielist.model.model_interfaces.LoginModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.Authorization;
import com.movielist.view.view_interfaces.LoginView;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    private static final String TAG = "LOGIN_PRESENTER";

    private LoginView loginActivity;
    private LoginModel loginModel;

    private static final String NETWORK_TAG = "Network";

    private Authorization mAuthorization;

    public LoginPresenter(LoginView loginActivity, LoginData mLoginData) {

        this.loginActivity = loginActivity;
        this.loginModel = mLoginData;
        mAuthorization = RetrofitSingleton.getInstance().getRetrofit().create(Authorization.class);

    }


    public void getRequestToken(){

        mAuthorization.getRequestToken("Bearer " + TmdbConstants.keyV4,new Redirect("com.movielist://token_callback")).enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(@NonNull Call<RequestToken> call, @NonNull Response<RequestToken> response) {
                loginModel.setRequestToken(response.body());
                Log.i(NETWORK_TAG,response.toString());
                if (loginModel.getRequestToken() != null) {
                    loginModel.saveDataToDb(KeyDbHelper.REQUEST_TOKEN,loginModel.getRequestToken().getToken());
                    String url = TmdbConstants.host + "/auth/access?request_token=" + loginModel.getRequestToken().getToken();
                    loginActivity.checkToken(url);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestToken> call, @NonNull Throwable t) {
                loginActivity.onError(Error.LOGIN_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }

    public void getGuestSession() {
        mAuthorization.getGuestSession(TmdbConstants.keyV3).enqueue(new Callback<GuestSession>() {
            @Override
            public void onResponse(@NonNull Call<GuestSession> call,@NonNull Response<GuestSession> response) {

                GuestSession guestSession = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if(guestSession != null){

                    if(loginModel.saveDataToDb(KeyDbHelper.GUEST_SESSION,guestSession.getSessionId())){

                        loginActivity.launchNext(guestSession.getSessionId(),KeyDbHelper.GUEST_SESSION);

                    }
                    else {
                        loginActivity.onError(Error.DATABASE_ERROR);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GuestSession> call,@NonNull Throwable t) {

                loginActivity.onError(Error.GUEST_ERROR);
                Log.e(TAG,t.toString());
            }
        });


    }

    public void getAccessToken(){
        if(loginModel.loadRequestTokenFromDb()) {

            mAuthorization.getAccessToken("Bearer " + TmdbConstants.keyV4, loginModel.getRequestToken()).enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
                    Log.i(NETWORK_TAG,response.toString());
                    if (loginModel.getAccessToken() != null) {
                        getSession();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                    loginActivity.onError(Error.ACCESS_ERROR);
                    Log.e(TAG,t.toString());
                }
            });

        }else {

            loginActivity.onError(Error.DATABASE_ERROR);

        }
    }

    private void getSession(){

        mAuthorization.getSession(TmdbConstants.keyV3,loginModel.getAccessToken()).enqueue(new Callback<UserSession>() {
            @Override
            public void onResponse(@NonNull Call<UserSession> call, @NonNull Response<UserSession> response) {
                UserSession session = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if(session != null){
                    loginModel.saveDataToDb(KeyDbHelper.SESSION,session.getId());
                    loginActivity.launchNext(session.getId(),KeyDbHelper.SESSION);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSession> call, @NonNull Throwable t) {
                loginActivity.onError(Error.SESSION_ERROR);
                Log.e(TAG,t.toString());
            }
        });
    }


}
