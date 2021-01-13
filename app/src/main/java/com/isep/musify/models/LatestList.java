package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestList {
    @SerializedName("items")
    List<SimplePlaylist> featuredList;

    public LatestList(List<SimplePlaylist> featuredList) {
        this.featuredList = featuredList;
    }

    public List<SimplePlaylist> getFeaturedLists() {
        return featuredList;
    }

}
