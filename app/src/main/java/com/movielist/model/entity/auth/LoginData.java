package com.movielist.model.entity.auth;

import com.movielist.model.model_interfaces.LoginModel;

public class LoginData implements LoginModel {

    private RequestToken mRequestToken;
    private AccessToken mAccessToken;

    @Override
    public void setRequestToken(RequestToken requestToken) {
        mRequestToken = requestToken;
    }

    @Override
    public void setAccessToken(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    @Override
    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    @Override
    public RequestToken getRequestToken() {

        return mRequestToken;
    }
}
