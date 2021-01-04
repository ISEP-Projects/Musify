package com.isep.musify.models;

import java.util.List;

public class Profile {

    private String display_name;
    private String id;

    public List<Image> getImages() {
        return images;
    }

    private List<Image> images;


    public Profile(String display_name, String id, List<Image> images) {
        this.display_name = display_name;
        this.id = id;
        this.images = images;
    }
    public String getDisplay_name() {
        return display_name;
    }

    public String getId() {
        return id;
    }


}
