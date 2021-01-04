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
    @SerializedName("profile")
    private ProfileResponseObject profile;
    @SerializedName("items")
    private List<LibraryItem> libraryItems;

    public ApiResponse(TracksResponseObject tracksList, ArtistsResponseObject artistsList, AlbumsResponseObject albumsList) {
        this.tracksList = tracksList;
        this.artistsList = artistsList;
        this.albumsList = albumsList;
    }
    public ApiResponse(ProfileResponseObject profileName){
        this.profile=profileName;
    }
    public ProfileResponseObject getProfile(){return profile;}
    public TracksResponseObject getTracksList() {
        return tracksList;
    }
    public ArtistsResponseObject getArtistsList() {
        return artistsList;
    }
    public AlbumsResponseObject getAlbumsList() {
        return albumsList;
    }
    public List<LibraryItem> getLibraryItems() { return libraryItems; }

}
