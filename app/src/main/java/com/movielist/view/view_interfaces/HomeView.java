package com.movielist.view.view_interfaces;

import com.movielist.model.entity.catalog.MovieResult;

import java.util.ArrayList;

public interface HomeView {

    void setUpRecyclerView(ArrayList<MovieResult> results);
}
