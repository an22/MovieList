package com.movielist.model.model_interfaces;

import com.movielist.model.entity.auth.RequestToken;
import com.movielist.presenter.model_listeners.UINetworkListener;

public interface LoginModel {
    RequestToken getRequestToken();
    Session getSession();
    void loadAccessToken(UINetworkListener listener);
    void loadRequestToken(UINetworkListener listener);
    void loadGuestSession(UINetworkListener listener);
    boolean loadRequestTokenFromDb();
}
