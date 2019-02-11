package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageConfiguration implements Serializable {

    @SerializedName("base_url")
    private String baseUrl;

    @SerializedName("secure_base_url")
    private String secureBaseUrl;

    @SerializedName("backdrop_sizes")
    private String[] backdropSizes;

    @SerializedName("logo_sizes")
    private String[] logoSizes;

    @SerializedName("poster_sizes")
    private String[] posterSizes;

    @SerializedName("profile_sizes")
    private String[] profileSizes;

    @SerializedName("still_sizes")
    private String[] stillSizes;


    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public String[] getBackdropSizes() {
        return backdropSizes;
    }

    public String[] getLogoSizes() {
        return logoSizes;
    }

    public String[] getPosterSizes() {
        return posterSizes;
    }

    public String[] getProfileSizes() {
        return profileSizes;
    }

    public String[] getStillSizes() {
        return stillSizes;
    }
}
