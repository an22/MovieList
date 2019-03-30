package com.movielist.presenter.activity_presenters;

import android.content.Context;

import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.User;
import com.movielist.model.model_interfaces.CatalogModel;
import com.movielist.presenter.model_listeners.GuestListener;
import com.movielist.view.view_interfaces.CatalogView;

import java.io.Serializable;

public class CatalogPresenter {

    private CatalogModel model;
    private CatalogView view;

    private boolean loaded = false;
    private boolean isGuest = false;

    private GuestListener mListener;

    public CatalogPresenter(CatalogView view,CatalogModel model) {
        this.model = model;
        this.view = view;
        this.mListener = new GuestListener() {

            private int count = 0;

            @Override
            public void onGuest() {
                isGuest = true;
                onLoaded();
            }

            @Override
            public void onLoaded() {
                count++;
                loaded = true;
                if(count == 2) {
                    view.saveLanguage();
                    view.hideProgress();
                    view.runHome(model.getConfig());
                }
            }

            @Override
            public void onStart() {
                view.showProgress();
            }

            @Override
            public void onError(String error) {
                view.onError(error);
            }

        };
    }

    public void loadUser(String session, Context context){
        model.loadUser(session,mListener,context);
    }

    public void loadConfig(){
        model.loadConfig(mListener);
    }

    public Configuration getConfiguration() {
        return model.getConfig();
    }

    public void setConfiguration(Serializable configuration) {
        model.setConfig((Configuration)configuration);
    }

    public void setUser(Serializable user){
        model.setUser((User)user);
    }

    public boolean isGuest() {
        return isGuest;
    }

    public Serializable getUser(){
        if(loaded&&!isGuest) {
            return model.getUser();
        }else if(loaded){
            return new User();
        }
        return null;
    }

    public void onDestroy(){
        view = null;
        model = null;
    }
}
