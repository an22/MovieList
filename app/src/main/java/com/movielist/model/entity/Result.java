package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Describable;
import com.movielist.model.model_interfaces.Loadable;
import com.movielist.presenter.model_listeners.UINetworkListener;

import java.util.ArrayList;

public abstract class Result<T extends Describable> implements Loadable {

    public static final String TAG = "Result";
    protected static String NETWORK_TAG = "Network";

    @SerializedName("page")
    protected int currentPage;

    @SerializedName("results")
    protected ArrayList<T> results;

    @SerializedName("total_pages")
    protected int pages;

    protected UINetworkListener listener;

    protected boolean loading = false;
    protected String language;
    protected String region;

    public Result(String language,String region){
        results = new ArrayList<>();
        this.language = language;
        this.region = region;
        this.pages = 1;
        this.currentPage = 0;
    }

    public abstract void loadFromQuery(String query);

    protected <V extends Result<T>> void add( V result){
        results.addAll(result.getResults());
        pages = result.getPages();
    }

    protected void resetPage(){
        currentPage = 0;
    }

    public void setListener(UINetworkListener listener) {
        this.listener = listener;
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public int getPages() {
        return pages;
    }
}
