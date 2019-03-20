package com.movielist.model;

public enum ResultTypes {
    MOVIE("Movies"),
    TV("TV"),
    PEOPLE("People");

    private String name;

    ResultTypes(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
