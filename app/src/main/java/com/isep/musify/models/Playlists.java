package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Playlists {
    @SerializedName("items")
    private List<SongItem> songList;

    public Playlists(List<SongItem> songList) {
        this.songList = songList;
    }

    public List<SongItem> getSongList() {
        return songList;
    }
}
