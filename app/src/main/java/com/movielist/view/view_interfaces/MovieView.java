package com.movielist.view.view_interfaces;


public interface MovieView {

    void addRating(String rating);
    void addTitle(String title);
    void addDescription(String description);
    void addGenres(String genres);
    void setPoster(String posterPath);
    void onStartLoading();
    void setRuntime(String runtime);
    void onError();

}
