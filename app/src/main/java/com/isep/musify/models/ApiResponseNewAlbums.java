package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseNewAlbums {
    @SerializedName("albums")
    private NewReleaseItem releases;

    public ApiResponseNewAlbums(NewReleaseItem releases) {
        this.releases = releases;
    }

    public NewReleaseItem getReleases() {
        return releases;
    }
}
