package com.isep.musify.models;

import java.util.List;

public class Song {
    private String name;
    private List<Artist> artists;
    private Album album;

    public Album getAlbum() {
        return album;
    }

    public String getName() {
        return name;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Song(String name, List<Artist> artist, Album album) {
        this.name = name;
        this.artists = artist;
        this.album = album;
    }
}
