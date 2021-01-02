package com.isep.musify.models;

import java.util.List;

public class Album {
    private  String id;
    private List<Artist> artists;
    private List<Image> images;
    private String href;
    private String name;
    private String uri;

    public Album(String id, List<Artist> artists, List<Image> images, String href, String name, String uri) {
        this.id = id;
        this.artists = artists;
        this.images = images;
        this.href = href;
        this.name = name;
        this.uri = uri;
    }


    public String getId() {
        return id;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
