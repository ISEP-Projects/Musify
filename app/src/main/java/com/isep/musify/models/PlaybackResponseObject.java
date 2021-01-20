package com.isep.musify.models;

import java.util.List;

public class PlaybackResponseObject {

    private Album album;
    private List<Artist> artists;
    private String id;
    private String uri;
    private String name;

    public PlaybackResponseObject(Album album, List<Artist> artists, String id, String uri, String name) {
        this.album = album;
        this.artists = artists;
        this.id = id;
        this.uri = uri;
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "PlaybackResponseObject{" +
                "album=" + album +
                ", artists=" + artists +
                ", id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
