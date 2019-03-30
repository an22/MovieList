package com.movielist.model;

public enum ResultTypes {
    MOVIE("movie"),
    TV("tv"),
    PEOPLE("people");

    private String name;

    ResultTypes(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
