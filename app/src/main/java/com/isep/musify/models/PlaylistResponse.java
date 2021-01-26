package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;


public class PlaylistResponse {
    private String description;
    private Follower followers;
    private String href;
    private String id;
    private String name;
    @SerializedName("tracks")
    private PlaylistItems playlistItems;

    public String getDescription() {
        return description;
    }

    public Follower getFollower() {
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

    public PlaylistItems getPlaylistItems() {
        return playlistItems;
    }

    public PlaylistResponse(String description, Follower followers, String href, String id, String name, PlaylistItems playlistItems) {
        this.description = description;
        this.followers = followers;
        this.href = href;
        this.id = id;
        this.name = name;
        this.playlistItems = playlistItems;
    }
}
