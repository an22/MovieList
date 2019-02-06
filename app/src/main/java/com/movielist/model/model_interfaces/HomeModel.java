package com.movielist.model.model_interfaces;

import com.movielist.model.entity.catalog.MovieResult;

import java.util.ArrayList;

public interface HomeModel {

    void loadUpcoming();
    void loadTopRated();
    void loadPopular();
    void incUpcoming();
    void incTopRated();
    void incPopular();
    ArrayList<MovieResult> getResult();
}
