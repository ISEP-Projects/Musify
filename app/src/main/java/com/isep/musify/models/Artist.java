package com.isep.musify.models;

import java.util.List;

public class Artist {
    private String href;
    private String id;
    private String name;
    private List<Image> images;
    private String type;
    private String uri;
    private int popularity;
    private Follower followers;

    public Follower getFollowrs() {
        return followers;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }

    public int getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "href='" + href + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
