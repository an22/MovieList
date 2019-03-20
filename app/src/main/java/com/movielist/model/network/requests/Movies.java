package com.movielist.model.network.requests;

import com.movielist.model.entity.catalog.MovieResult;
import com.movielist.model.entity.moviedetails.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<Movie> getDetails(@Path("movieID") String ID, @Query("api_key") String key, @Query("append_to_response")String appendToResponse, @Query("language") String language);

}
