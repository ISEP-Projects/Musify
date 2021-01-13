package com.isep.musify.models;

import java.util.List;

public class SimplePlaylist {

    private String description;
    private String id;
    private List<Image> images = null;
    private String name;
    private Profile owner;

    public String getDescription() {
        return description;
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

    public Profile getOwner() {
        return owner;
    }

    public Track getTracks() {
        return tracks;
    }

    public SimplePlaylist(String description, String id, List<Image> images, String name, Profile owner, Track tracks) {
        this.description = description;
        this.id = id;
        this.images = images;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    private Track tracks;
}
