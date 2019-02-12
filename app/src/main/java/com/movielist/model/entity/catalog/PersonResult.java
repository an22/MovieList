package com.movielist.model.entity.catalog;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Loadable;

import java.util.ArrayList;

public class PersonResult implements Loadable {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Person> results;

    @SerializedName("total_pages")
    private int pages;

    @SerializedName("total_results")
    private int totalResults;

    @Override
    public void loadMore() {

    }
}
