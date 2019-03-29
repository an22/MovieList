package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.ResultTypes;
import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.model.entity.catalog.PersonResult;
import com.movielist.model.entity.catalog.TvResult;
import com.movielist.model.model_interfaces.ListInformation;
import com.movielist.model.model_interfaces.Loadable;
import com.movielist.presenter.model_listeners.UINetworkListener;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Result implements Loadable, Serializable {

    public static final String TAG = "Result";
    public static final String TYPE = "Type";
    protected static String NETWORK_TAG = "Network";

    @SerializedName("page")
    protected int currentPage;

    @SerializedName("results")
    private ArrayList<ListInformation> results;

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

    protected  void add(Result result){
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

    public ArrayList<ListInformation> getResults() {
        return results;
    }

    private int getPages() {
        return pages;
    }

    protected boolean canLoadPage(){
        if(currentPage < pages){
            currentPage++;
            return true;
        }
        return false;
    }

    //This is example of design pattern 'Factory'
    public static Result createResult(ResultTypes type, String language, String region){
        switch (type){
            case TV:{
                return new TvResult(language,region);
            }
            case MOVIE: {
                return new MovieResult(language, region,DownloadTypes.QUERY);
            }
            case PEOPLE: {
                return new PersonResult(language, region);
            }
            default: throw new IllegalArgumentException("Wrong type:" + type);
        }
    }
}
