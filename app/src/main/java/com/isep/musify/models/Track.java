package com.isep.musify.models;

import java.util.ArrayList;
import java.util.List;

public class Track {

    private String id;
    private List<Artist> artists;
    private Album album;
    private String href;
    private String name;
    private String popularity;
    private String uri;

    public Track(String id, List<Artist> artists, Album album, String href, String name, String popularity, String uri) {
        this.id = id;
        this.artists = artists;
        this.album = album;
        this.href = href;
        this.name = name;
        this.popularity = popularity;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopularity() {
        return this.popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", artists=" + artists +
                ", album=" + album +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", popularity='" + popularity + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
