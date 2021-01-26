package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponseObject {

    private String username;

    @SerializedName("profile")
    private String name;


    public String getName() {
        return name;
    }
}