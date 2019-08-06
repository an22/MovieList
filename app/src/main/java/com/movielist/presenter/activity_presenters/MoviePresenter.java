package com.movielist.presenter.activity_presenters;

import com.movielist.model.entity.moviedetails.Genre;
import com.movielist.model.model_interfaces.MovieModel;
import com.movielist.presenter.model_listeners.UINetworkListener;
import com.movielist.view.view_interfaces.MovieView;

import java.text.DecimalFormat;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MoviePresenter implements LifecycleObserver {

    private MovieView mMovieView;
    private MovieModel mMovieModel;
    private String session;
    private int userID;
    private UINetworkListener toolsListener;

    public MoviePresenter(String movieID,MovieView view, MovieModel model, String session,int userID){
        mMovieModel = model;
        mMovieView = view;
        this.session = session;
        this.userID = userID;

        UINetworkListener listener = new UINetworkListener() {
            @Override
            public void onLoaded() {
                view.addTitle(model.getTitle());
                view.addDescription(model.getDescription());
                view.addGenres(formatGenres());

                DecimalFormat formateur = new DecimalFormat("#.#");

                view.addRating(formateur.format(model.getRating()));
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
                view.onError(error);
            }
        };

        toolsListener = new UINetworkListener() {
            @Override
            public void onLoaded() {
                view.showSuccess();
            }

            //In this case onStart means successful rate(Bad decision)
            @Override
            public void onStart() {
                view.showSuccessfullRating();
            }

            @Override
            public void onError(String error) {
                view.onError(error);
            }
        };

        model.loadMovie(movieID, listener);
    }

    protected String formatGenres(){
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

    protected String formatRuntime(){
       int runtime =  mMovieModel.getRuntime();
       String formatted = " ";

       int hours = runtime /60;
       if(hours > 0) formatted = hours + " h. ";

       int min = (runtime%60);
       formatted += min + " m.";

       return formatted;
    }

    public void addToWatchlist(){
        if (userID != -1) mMovieModel.addToWatchlist(userID,session,toolsListener);
        else mMovieView.onGuest();

    }

    public void addToFavourites(){
        if (userID != -1) mMovieModel.addToFavourites(userID,session,toolsListener);
        else mMovieView.onGuest();
    }

    public void deleteRating(){
        if (userID != -1) mMovieModel.deleteRating(session,toolsListener);
        else mMovieModel.deleteRatingGuest(session,toolsListener);
    }
    public void rate(int rating){
        if(userID != -1) mMovieModel.rate(rating, session,toolsListener);
        else mMovieModel.rateGuest(rating,session,toolsListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        mMovieView = null;
        mMovieModel = null;
    }


}
