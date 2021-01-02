package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//Object where data retrieved from api is saved into
public class ApiResponse {
    @SerializedName("tracks")
    private TracksResponseObject tracksList;
    @SerializedName("artists")
    private ArtistsResponseObject artistsList;
    @SerializedName("albums")
    private AlbumsResponseObject albumsList;
    @SerializedName("items")
    private List<Playlist> playList;


    public ApiResponse(TracksResponseObject tracksList, ArtistsResponseObject artistsList, AlbumsResponseObject albumsList, List<Playlist> playList) {
        this.tracksList = tracksList;
        this.artistsList = artistsList;
        this.albumsList = albumsList;
        this.playList = playList;
    }

    public TracksResponseObject getTracksList() {
        return tracksList;
    }
    public ArtistsResponseObject getArtistsList() {
        return artistsList;
    }
    public AlbumsResponseObject getAlbumsList() {
        return albumsList;
    }
    public List<Playlist> getPlayList() { return playList; }


}
