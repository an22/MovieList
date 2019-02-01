package com.movielist.model.model_interfaces;

import com.movielist.model.entity.auth.AccessToken;
import com.movielist.model.entity.auth.RequestToken;

public interface LoginModel {
    void setRequestToken(RequestToken requestToken);
    void setAccessToken(AccessToken accessToken);
    AccessToken getAccessToken();
    RequestToken getRequestToken();
    boolean saveDataToDb(String column,String data);
    boolean loadRequestTokenFromDb();



}
