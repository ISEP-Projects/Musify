package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaylistItems {
    @SerializedName("items")
    private List<TrackItem> trackItemsList;

    public PlaylistItems(List<TrackItem> trackItemsList) {
        this.trackItemsList = trackItemsList;
    }

    public List<TrackItem> getTrackItemsList() {
        return trackItemsList;
    }
}
