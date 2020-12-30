package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumsResponseObject {

    private String href;

    @SerializedName("items")
    private List<Album> albums;
    private String next;

    public String getHref() {
        return href;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public String getNext() {
        return next;
    }
}
