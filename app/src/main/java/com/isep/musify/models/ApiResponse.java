package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

//Object where data retrieved from api is saved into
public class ApiResponse {
    @SerializedName("tracks")
    private TracksResponseObject tracksList;
    @SerializedName("artists")
    private ArtistsResponseObject artistsList;
    @SerializedName("albums")
    private AlbumsResponseObject albumsList;

    public ApiResponse(TracksResponseObject tracksList, ArtistsResponseObject artistsList, AlbumsResponseObject albumsList) {
        this.tracksList = tracksList;
        this.artistsList = artistsList;
        this.albumsList = albumsList;
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


}
