package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistsResponseObject {

    private String href;

    @SerializedName("items")
    private List<Artist> artists;
    private String next;

    public ArtistsResponseObject(String href, List<Artist> artists, String next) {
        this.href = href;
        this.artists = artists;
        this.next = next;
    }

    public String getHref() {
        return href;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public String getNext() {
        return next;
    }
}
