package com.movielist.presenter.activity_presenters;

import com.movielist.database.KeyDbHelper;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.auth.Redirect;
import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.GuestSession;
import com.movielist.model.entity.auth.LoginData;
import com.movielist.model.entity.auth.RequestToken;
import com.movielist.model.entity.auth.UserSession;
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
    private LoginModel loginModel;

    private final String LOGIN_ERROR = "Can`t sign in, try again later";
    private final String GUEST_ERROR = "Can`t create guest session, try again later";
    private final String ACCESS_ERROR = "Can`t access to server";

    public LoginPresenter(LoginView loginActivity, LoginData mLoginData) {

        this.loginActivity = loginActivity;
        this.loginModel = mLoginData;

    }


    public void getRequestToken(){
        PostRequestToken token = RetrofitSingleton.getInstance().getRetrofit().create(PostRequestToken.class);
        token.getRequestToken("Bearer " + TmdbConstants.keyV4,new Redirect("com.movielist://token_callback")).enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(@NonNull Call<RequestToken> call, @NonNull Response<RequestToken> response) {
                loginModel.setRequestToken(response.body());
                System.out.println(response.toString());
                if (loginModel.getRequestToken() != null) {
                    loginModel.saveDataToDb(KeyDbHelper.REQUEST_TOKEN,loginModel.getRequestToken().getToken());
                    String url = TmdbConstants.host + "/auth/access?request_token=" + loginModel.getRequestToken().getToken();
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
        GetGuestSession getGuestSession = RetrofitSingleton.getInstance().getRetrofit().create(GetGuestSession.class);
        getGuestSession.getGuestSession(TmdbConstants.keyV3).enqueue(new Callback<GuestSession>() {
            @Override
            public void onResponse(@NonNull Call<GuestSession> call,@NonNull Response<GuestSession> response) {

                GuestSession guestSession = response.body();
                System.out.println(response.toString());
                if(guestSession != null){

                    if(loginModel.saveDataToDb(KeyDbHelper.GUEST_SESSION,guestSession.getSessionId())){

                        loginActivity.launchNext(guestSession.getSessionId(),KeyDbHelper.GUEST_SESSION);

                    }
                    else {
                        loginActivity.onError(KeyDbHelper.DATABASE_ERROR);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GuestSession> call,@NonNull Throwable t) {

                loginActivity.onError(GUEST_ERROR);

            }
        });


    }

    public void getAccessToken(){
        if(loginModel.loadRequestTokenFromDb()) {

            PostAccessToken postAccessToken = RetrofitSingleton.getInstance().getRetrofit().create(PostAccessToken.class);
            postAccessToken.getAccessToken("Bearer " + TmdbConstants.keyV4, loginModel.getRequestToken()).enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
                    loginModel.setAccessToken(response.body());
                    System.out.println(response.toString());
                    if (loginModel.getAccessToken() != null) {

                        getSession();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                    loginActivity.onError(ACCESS_ERROR);
                }
            });

        }else {

            loginActivity.onError(KeyDbHelper.DATABASE_ERROR);

        }
    }

    private void getSession(){
        PostCreateSession createSession = RetrofitSingleton.getInstance().getRetrofit().create(PostCreateSession.class);
        createSession.getSession(TmdbConstants.keyV3,loginModel.getAccessToken()).enqueue(new Callback<UserSession>() {
            @Override
            public void onResponse(@NonNull Call<UserSession> call, @NonNull Response<UserSession> response) {
                UserSession session = response.body();
                System.out.println(response.toString());
                if(session != null){
                    loginModel.saveDataToDb(KeyDbHelper.SESSION,session.getId());
                    loginActivity.launchNext(session.getId(),KeyDbHelper.SESSION);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserSession> call, @NonNull Throwable t) {

            }
        });
    }


}
