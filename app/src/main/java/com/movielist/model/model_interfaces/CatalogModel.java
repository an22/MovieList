package com.movielist.model.model_interfaces;

import com.movielist.model.entity.catalog.User;

public interface CatalogModel {

     User getUser();
     void setUser(User user);
     boolean loadUser(String session);
}
