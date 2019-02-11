package com.movielist.model.model_interfaces;

import com.movielist.model.entity.catalog.User;
import com.movielist.presenter.model_listeners.NetworkListener;

public interface CatalogModel {

     User getUser();
     void setUser(User user);
     void loadUser(String session, NetworkListener listener);
}
