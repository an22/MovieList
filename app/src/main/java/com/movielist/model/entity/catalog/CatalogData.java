package com.movielist.model.entity.catalog;

import com.movielist.model.model_interfaces.CatalogModel;
import com.movielist.model.network.callbacks.GetUserAsync;

import java.util.concurrent.ExecutionException;

public class CatalogData implements CatalogModel {

    private User user;

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean loadUser(String session) {

        try {
            user = new GetUserAsync().execute(session).get();
            if(user != null){
                System.out.println(user.toString());
                return true;
            }else return false;
        } catch (ExecutionException e) {

            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
