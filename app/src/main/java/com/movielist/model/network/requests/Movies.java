package com.movielist.model.network.requests;

import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.model.entity.moviedetails.Movie;
import com.movielist.model.network.bodies.FavouriteBody;
import com.movielist.model.network.bodies.Rating;
import com.movielist.model.network.bodies.WatchlistBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Movies {

    @GET("/3/movie/upcoming")
    Call<MovieResult> getUpcoming(@Query("api_key") String key, @Query("language") String language, @Query("region")String region,@Query("page") int page);

    @GET("/3/movie/top_rated")
    Call<MovieResult> getTopRated(@Query("api_key") String key, @Query("language") String language, @Query("region")String region,@Query("page") int page);

    @GET("/3/movie/popular")
    Call<MovieResult> getPopular(@Query("api_key") String key, @Query("language") String language,@Query("region")String region, @Query("page") int page);

    @GET("/3/search/movie")
    Call<MovieResult> search(@Query("api_key") String key, @Query("language") String language, @Query("region")String region,@Query("query")String query,@Query("page") int page);

    @GET("/3/movie/{movieID}")
    Call<Movie> getDetails(@Path("movieID") String id, @Query("api_key") String key, @Query("append_to_response")String appendToResponse, @Query("language") String language);

    @POST("/3/movie/{movie_id}/rating")
    Call<Void> rate(@Path("movie_id") String id,@Query("api_key") String key,@Query("session_id") String session,@Body Rating value);

    @POST("/3/movie/{movie_id}/rating")
    Call<Void> rateGuest(@Path("movie_id") String id,@Query("api_key") String key,@Query("guest_session_id") String session,@Body Rating value);

    @DELETE("/3/movie/{movie_id}/rating")
    Call<Void> deleteRating(@Path("movie_id") String id,@Query("api_key") String key,@Query("session_id") String session);

    @DELETE("/3/movie/{movie_id}/rating")
    Call<Void> deleteRatingGuest(@Path("movie_id") String id,@Query("api_key") String key,@Query("guest_session_id") String session);

    @POST("/3/account/{account_id}/favorite")
    Call<Void> markAsFavourite(@Path("account_id")String id, @Query("api_key") String key, @Query("session_id") String session, @Body FavouriteBody favouriteItem);

    @POST("/3/account/{account_id}/watchlist")
    Call<Void> addToWatchList(@Path("account_id")String id, @Query("api_key") String key, @Query("session_id") String session, @Body WatchlistBody watchlistItem);
}
