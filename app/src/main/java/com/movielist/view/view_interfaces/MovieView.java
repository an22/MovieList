package com.movielist.view.view_interfaces;


import com.movielist.model.entity.ImagePaths;
import com.movielist.model.entity.moviedetails.Credits;

public interface MovieView {

    void addRating(String rating);
    void addTitle(String title);
    void addDescription(String description);
    void addGenres(String genres);
    void setPoster(String posterPath);
    void onStartLoading();
    void setImages(ImagePaths images);
    void setRuntime(String runtime);
    void setCredits(Credits credits);
    void onGuest();
    void onError();
    void show();

}
