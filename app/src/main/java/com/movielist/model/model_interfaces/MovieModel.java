package com.movielist.model.model_interfaces;

import com.movielist.model.entity.moviedetails.Genre;
import com.movielist.model.entity.moviedetails.MovieImages;
import com.movielist.presenter.model_listeners.UINetworkListener;

public interface MovieModel {

    void loadMovie(String movieID, UINetworkListener listener);
    String getImagePath();
    String getTitle();
    String getDescription();
    double getRating();
    Genre[] getGenres();
    MovieImages getImages();
    int getRuntime();


}
