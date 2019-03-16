package com.movielist.model.model_interfaces;

import android.content.Context;

import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.model_listeners.GuestListener;

public interface CatalogModel {

     User getUser();
     void setUser(User user);
     void loadUser(String session, GuestListener listener, Context context);
}
