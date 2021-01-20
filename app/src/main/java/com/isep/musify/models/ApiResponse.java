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
    private List<LibraryItem> libraryItems;
    //Playback Response
    @SerializedName("item")
    private PlaybackResponseObject playbackResponse;

    public ApiResponse(TracksResponseObject tracksList, ArtistsResponseObject artistsList, AlbumsResponseObject albumsList, List<LibraryItem> libraryItems, PlaybackResponseObject playbackResponse) {
        this.tracksList = tracksList;
        this.artistsList = artistsList;
        this.albumsList = albumsList;
        this.libraryItems = libraryItems;
        this.playbackResponse = playbackResponse;
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

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "tracksList=" + tracksList +
                ", artistsList=" + artistsList +
                ", albumsList=" + albumsList +
                ", libraryItems=" + libraryItems +
                ", playbackResponse=" + playbackResponse +
                '}';
    }

    public PlaybackResponseObject getPlaybackResponse() {
        return playbackResponse;
    }
}
