package com.isep.musify.models;

import java.util.List;

public class NewReleases {
    private String albumType;
    private List<Artist> artists = null;
    private List<String> availableMarkets = null;
    private String href;
    private String id;
    private List<Image> images = null;
    private String name;
    private String releaseDate;
    private String releaseDatePrecision;
    private Integer totalTracks;
    private String type;

    public String getAlbumType() {
        return albumType;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public Integer getTotalTracks() {
        return totalTracks;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }

    public NewReleases(String albumType, List<Artist> artists, List<String> availableMarkets, String href, String id, List<Image> images, String name, String releaseDate, String releaseDatePrecision, Integer totalTracks, String type, String uri) {
        this.albumType = albumType;
        this.artists = artists;
        this.availableMarkets = availableMarkets;
        this.href = href;
        this.id = id;
        this.images = images;
        this.name = name;
        this.releaseDate = releaseDate;
        this.releaseDatePrecision = releaseDatePrecision;
        this.totalTracks = totalTracks;
        this.type = type;
        this.uri = uri;
    }

    private String uri;

}
