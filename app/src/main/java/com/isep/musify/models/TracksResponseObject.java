package com.isep.musify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TracksResponseObject {

    private String href;

    @SerializedName("items")
    private List<Track> tracks;
    private String next;

    public TracksResponseObject(){};

    public TracksResponseObject(String href, List<Track> tracks, String next) {
        this.href = href;
        this.tracks = tracks;
        this.next = next;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "ApiTracksResponse{" +
                "href='" + href + '\'' +
                ", tracks=" + tracks +
                ", next='" + next + '\'' +
                '}';
    }
}
