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
                view.setImages(model.getImages());
                view.setCredits(model.getCredits());
                view.show();

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
        if(genres.length() != 0) {
            genres.delete(genres.length() - 2, genres.length() - 1);
            return "Genres: " + genres.toString();
        }
        return "No genres are set";
    }

    private String getPath(){
        return mMovieModel.getImagePath();
    }

    private String formatRuntime(){
       int runtime =  mMovieModel.getRuntime();
       String formatted = " ";

       int hours = runtime /60;
       if(hours > 0) formatted = String.valueOf(hours) + " h. ";

       int min = (runtime%60);
       formatted += String.valueOf(min) + " m.";

       return formatted;
    }

    public void rate(int rating, String session){
        mMovieModel.rate(rating, session);
    }

    public void onDestroy(){
        mMovieView = null;
        mMovieModel = null;
    }


}
