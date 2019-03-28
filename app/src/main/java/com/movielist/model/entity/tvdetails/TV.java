package com.movielist.model.entity.tvdetails;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.entity.ImagePaths;
import com.movielist.model.entity.moviedetails.Genre;
import com.movielist.model.model_interfaces.ListInformation;

import java.util.List;

public class TV {

    public static final String TAG = "TV";

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("created_by")
    private List<CreatedBy> createdBy;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("id")
    private int id;

    @SerializedName("in_production")
    private boolean inProduction;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("name")
    private String name;

    @SerializedName("next_episode_to_air")
    private Object nextEpisodeToAir;

    @SerializedName("networks")
    private List<Network> networks;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("seasons")
    private List<Season> seasons;

    @SerializedName("status")
    private String status;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("similar")
    private List<ListInformation> similar;

    @SerializedName("images")
    private ImagePaths images;

    public List<ListInformation> getSimilar() {
        return similar;
    }

    public ImagePaths getImages() {
        return images;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<CreatedBy> getCreatedBy() {
        return createdBy;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public Object getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public String getStatus() {
        return status;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
