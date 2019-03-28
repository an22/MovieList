package com.movielist.model.entity;

import com.google.gson.annotations.SerializedName;

public class ImagePaths {

    public static final String TAG = "IMAGES";

    @SerializedName("backdrops")
    private Image[] backdrops;

    @SerializedName("posters")
    private Image[] posters;

    public ImagePaths(){

    }

    public String[] toBackdropsStringArray(Configuration configuration){
        String[] images = new String[getBackdrops().length + 2];
        images[0] = configuration.getImageConfig().getSecureBaseUrl();
        images[1] = configuration.getImageConfig().getPosterSizes()[configuration.getImageConfig().getPosterSizes().length - 1];

        for (int i = 2; i < images.length; i++){
            images[i] = backdrops[i - 2].getPosterPath();
        }

        return images;
    }

    public String[] toPostersStringArray(Configuration configuration){
        String[] images = new String[getPosters().length + 2];
        images[0] = configuration.getImageConfig().getSecureBaseUrl();
        images[1] = configuration.getImageConfig().getPosterSizes()[configuration.getImageConfig().getPosterSizes().length - 1];

        for (int i = 2; i < images.length; i++){
            images[i] = posters[i - 2].getPosterPath();
        }

        return images;
    }

    public Image[] getBackdrops() {
        return backdrops;
    }

    public Image[] getPosters() {
        return posters;
    }

}
