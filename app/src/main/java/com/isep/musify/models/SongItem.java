package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

public class SongItem {
    @SerializedName("track")
    private Song song;

    public SongItem(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }
}
