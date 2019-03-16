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
    private ArrayList<T> results;

    @SerializedName("total_pages")
    private int pages;

    //Error and loading listener
    protected UINetworkListener listener;

    protected boolean loading = false;
    protected String language;
    protected String region;
    protected String currentQuery;

    public Result(String language,String region){
        results = new ArrayList<>();
        this.language = language;
        this.region = region;
        this.pages = 1;
        this.currentPage = 0;
    }

    public abstract void loadFromQuery(String query);

    //Generic method
    //We can`t use Result<T> because possible lose of data
    protected <V extends Result<T>> void add( V result){
        results.addAll(result.getResults());
        pages = result.getPages();
    }

    private void resetPage(){
        currentPage = 1;
    }

    protected void checkQuery(String query){
        if(!query.equals(currentQuery)) {
            resetPage();
            results.clear();
            currentQuery = query;
        }
    }

    public void setListener(UINetworkListener listener) {
        this.listener = listener;
    }

    public ArrayList<T> getResults() {
        return results;
    }

    int getPages() {
        return pages;
    }

    protected boolean canLoadPage(){
        if(currentPage < pages){
            currentPage++;
            return true;
        }
        return false;
    }
}
