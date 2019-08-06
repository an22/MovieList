package com.movielist.presenter.activity_presenters;

import com.movielist.database.KeyDbHelper;
import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.model_interfaces.LoginModel;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.view_interfaces.LoginView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class LoginPresenter implements LifecycleObserver {

    private LoginView mLoginView;
    private LoginModel loginModel;

    private UINetworkListener mListener;

    public LoginPresenter(LoginView mLoginView, LoginModel mLoginData) {

        this.mLoginView = mLoginView;
        this.loginModel = mLoginData;
        mListener = new UINetworkListener() {
            @Override
            public void onLoaded() {

                String url = TmdbConstants.host + "/auth/access?request_token=" + loginModel.getRequestToken().getToken();
                mLoginView.checkToken(url);
            }

            @Override
            public void onStart() {
                mLoginView.launchNext(mLoginData.getSession().getSessionID(),KeyDbHelper.GUEST_SESSION);
            }

            @Override
            public void onError(String error) {
                mLoginView.onError(error);
            }
        };

    }


    public void getRequestToken(){
        loginModel.loadRequestToken(mListener);
    }

    public void getGuestSession() {
        loginModel.loadGuestSession(mListener);
    }

    public void getAccessToken(){
        if(loginModel.loadRequestTokenFromDb()) {
            loginModel.loadAccessToken(mListener);
        }else {
            mLoginView.onError(Error.DATABASE_ERROR);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        mLoginView = null;
        loginModel = null;
    }

}
