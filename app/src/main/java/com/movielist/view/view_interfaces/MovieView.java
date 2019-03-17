package com.movielist.view.view_interfaces;


import com.movielist.model.entity.moviedetails.MovieImages;

public interface MovieView {

    void addRating(String rating);
    void addTitle(String title);
    void addDescription(String description);
    void addGenres(String genres);
    void setPoster(String posterPath);
    void onStartLoading();
    void setImages(MovieImages images);
    void setRuntime(String runtime);
    void onError();

}
