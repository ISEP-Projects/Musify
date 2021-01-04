package com.isep.musify.models;

import java.util.List;

public class NewReleases {
    public String href;
    public List<Item> items;

    public String getHref() {
        return href;
    }

    public List<Item> getItems() {
        return items;
    }

    public NewReleases(String href, List<Item> items) {
        this.href = href;
        this.items = items;
    }
}
