package com.movielist.view.view_interfaces;

public interface LoginView {

    void onLogin();
    void onGuest();
    void onError(String text);
    void launchNext(String data, String type);
    void checkToken(String url);

}
