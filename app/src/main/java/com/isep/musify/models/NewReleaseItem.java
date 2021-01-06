package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewReleaseItem {
    @SerializedName("items")
    List<NewReleases>albums;

    public NewReleaseItem(List<NewReleases> albums) {
        this.albums = albums;
    }

    public List<NewReleases> getAlbums() {
        return albums;
    }




}
