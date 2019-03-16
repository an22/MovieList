package com.movielist.presenter.activity_presenters;

import android.content.Context;
import android.util.Log;

import com.movielist.model.Error;
import com.movielist.model.TmdbConstants;
import com.movielist.model.entity.Configuration;
import com.movielist.model.entity.catalog.User;
import com.movielist.model.model_interfaces.CatalogModel;
import com.movielist.model.network.RetrofitSingleton;
import com.movielist.model.network.requests.GetImageCfg;
import com.movielist.presenter.model_listeners.GuestListener;
import com.movielist.view.view_interfaces.CatalogView;

import java.io.Serializable;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogPresenter {

    private final String TAG = "CATALOG_PRESENTER";
    private final String NETWORK_TAG = "Network";

    private CatalogModel model;
    private CatalogView view;

    private boolean loaded = false;
    private boolean isGuest = false;

    private GuestListener mListener;
    private Configuration mConfiguration;

    public CatalogPresenter(CatalogView view,CatalogModel model) {
        this.model = model;
        this.view = view;
        this.mListener = new GuestListener() {
            @Override
            public void onGuest() {
                isGuest = true;
                onLoaded();
            }

            @Override
            public void onLoaded() {
                loaded = true;
                view.saveLanguage();
                if(mConfiguration != null) {
                    view.hideProgress();
                    view.runHome(mConfiguration);
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
        loadConfig();
    }

    public void loadUser(String session, Context context){
        model.loadUser(session,mListener,context);
    }

    private void loadConfig(){
        GetImageCfg cfg = RetrofitSingleton.getInstance().getRetrofit().create(GetImageCfg.class);
        cfg.getCfg(TmdbConstants.keyV3).enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(@NonNull Call<Configuration> call, @NonNull Response<Configuration> response) {
                Configuration config = response.body();
                Log.i(NETWORK_TAG,response.toString());
                if(config != null){
                    mConfiguration = config;
                    if(getUser()!=null){
                        view.runHome(mConfiguration);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Configuration> call,@NonNull Throwable t) {
                mListener.onError(t.getMessage());
                Log.e(TAG, Error.NETWORK_ERROR);
            }
        });
    }

    public Configuration getConfiguration() {
        return mConfiguration;
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
