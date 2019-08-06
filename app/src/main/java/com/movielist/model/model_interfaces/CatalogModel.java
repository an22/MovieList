package com.movielist.model.model_interfaces;

import android.content.Context;

import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.model_listeners.GuestListener;
import com.movielist.presenter.model_listeners.UINetworkListener;

public interface CatalogModel {

     User getUser();
     void setUser(User user);
     void loadUser(String session, GuestListener listener, Context context);
     void loadConfig(UINetworkListener listener);
     Configuration getConfig();
     void setConfig(Configuration config);
     void deleteSession();
}
