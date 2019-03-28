package com.movielist.model.model_interfaces;

import com.movielist.model.entity.moviedetails.Credits;
import com.movielist.model.entity.moviedetails.Genre;
import com.movielist.model.entity.ImagePaths;
import com.movielist.presenter.model_listeners.UINetworkListener;

public interface MovieModel {

    void loadMovie(String movieID, UINetworkListener listener);
    String getImagePath();
    String getTitle();
    String getDescription();
    double getRating();
    Genre[] getGenres();
    ImagePaths getImages();
    Credits getCredits();
    int getRuntime();
    void rate(int rating, String session);


}
