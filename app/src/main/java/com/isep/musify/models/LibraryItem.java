package com.isep.musify.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LibraryItem {
    private String id;
    private String name;
    private Profile owner;
    private boolean collaborative;
    private String description;
    private List<Image> images;
    private String href;
    @SerializedName("added_at")
    private String addedAt;
    private Album album;

    public String getAddedAt() {
        return addedAt;
    }

    public Album getAlbum() {
        return album;
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

    public Profile getOwner() {
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

    public LibraryItem(String addedAt, Album album, String id, String name, Profile owner, boolean collaborative, String description, List<Image> images, String href) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.collaborative = collaborative;
        this.description = description;
        this.images = images;
        this.href = href;
        this.addedAt = addedAt;
        this.album = album;
    }
}
