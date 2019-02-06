package com.movielist.presenter.activity_presenters;

import com.movielist.model.entity.catalog.User;
import com.movielist.model.model_interfaces.CatalogModel;
import com.movielist.view.view_interfaces.CatalogView;

public class CatalogPresenter {

    private CatalogModel model;
    private CatalogView view;
    private boolean loaded;
    public CatalogPresenter(CatalogView view,CatalogModel model) {
        this.model = model;
        this.view = view;
        loaded = false;
    }

    public void loadUser(String session){
        if(!model.loadUser(session)){
            view.onError("");
        }
        else loaded = true;
    }

    public User getUser(){
        if(loaded) {
            return model.getUser();
        }
        return null;
    }
}
