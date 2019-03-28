package com.movielist.model.entity.moviedetails;

import com.google.gson.annotations.SerializedName;
import com.movielist.model.model_interfaces.Credit;

public class Crew implements Credit {

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("department")
    private String department;

    @SerializedName("id")
    private int id;

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    public String getCreditId() {
        return creditId;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return department;
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }
}
