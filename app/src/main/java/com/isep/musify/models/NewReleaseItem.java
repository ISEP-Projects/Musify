package com.isep.musify.models;

import java.util.List;

public class NewReleaseItem {
    public List<NewReleases> getAlbums() {
        return albums;
    }

    public NewReleaseItem(List<NewReleases> albums) {
        this.albums = albums;
    }

    List<NewReleases>albums;

}
