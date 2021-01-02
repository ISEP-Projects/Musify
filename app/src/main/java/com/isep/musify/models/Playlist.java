package com.isep.musify.models;


import java.util.List;

public class Playlist {
    private String id;
    private String name;
    private Owner owner;
    private boolean collaborative;
    private String description;
    private List<Image> images;
    private String href;

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public boolean isCollaborative() {
        return collaborative;
    }


    public String getDescription() {
        return description;
    }

    public List<Image> getImages() {
        return images;
    }

    public Playlist(String id, String name, Owner owner, boolean collaborative, String description, List<Image> images, String href) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.collaborative = collaborative;
        this.description = description;
        this.images = images;
        this.href = href;
    }
}
