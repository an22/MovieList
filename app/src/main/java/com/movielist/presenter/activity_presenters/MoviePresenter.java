package com.movielist.presenter.activity_presenters;

import com.movielist.model.entity.moviedetails.Genre;
import com.movielist.model.model_interfaces.MovieModel;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.view_interfaces.MovieView;

import java.text.DecimalFormat;

public class MoviePresenter {

    private MovieView mMovieView;
    private MovieModel mMovieModel;

    public MoviePresenter(String movieID,MovieView view, MovieModel model){
        mMovieModel = model;
        mMovieView = view;

        UINetworkListener listener = new UINetworkListener() {
            @Override
            public void onLoaded() {
                view.addTitle(model.getTitle());
                view.addDescription(model.getDescription());
                view.addGenres(formatGenres());

                DecimalFormat formater = new DecimalFormat("#.#");

                view.addRating(formater.format(model.getRating()));
                view.setPoster(getPath());
                view.setRuntime(formatRuntime());

            }

            @Override
            public void onStart() {
                view.onStartLoading();
            }

            @Override
            public void onError(String error) {
                view.onError();
            }
        };

        model.loadMovie(movieID, listener);
    }

    private String formatGenres(){
        StringBuilder genres = new StringBuilder();

        for (Genre genre:mMovieModel.getGenres()) {
            genres.append(genre.getName());
            genres.append(", ");
        }
        genres.deleteCharAt(genres.length() - 1);
        return genres.toString();
    }

    private String getPath(){
        if(mMovieModel.getImages().getBackdrops().length != 0) {
            return mMovieModel.getImages().getBackdrops()[0].getPosterPath();
        }
        return mMovieModel.getImagePath();
    }

    private String formatRuntime(){
       int runtime =  mMovieModel.getRuntime();
       String formatted = "";

       int hours = runtime /60;
       if(hours > 0) formatted = String.valueOf(hours) + " h. ";

       int min = (runtime%60)*100;
       formatted += String.valueOf(min) + " m.";

       return formatted;
    }

    public void onDestroy(){
        mMovieView = null;
        mMovieModel = null;
    }


}
