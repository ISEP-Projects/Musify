package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistTrackResponse {
    @SerializedName("tracks")
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    public ArtistTrackResponse(List<Track> tracks) {
        this.tracks = tracks;
    }
}
