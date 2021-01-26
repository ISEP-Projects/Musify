package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

public class TrackItem {
    @SerializedName("track")
    private Track track;

    public TrackItem(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }
}
