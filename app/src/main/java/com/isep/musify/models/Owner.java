package com.isep.musify.models;

public class Owner {
    private String display_name;
    private String id;

    public Owner(String display_name, String id) {
        this.display_name = display_name;
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getId() {
        return id;
    }

}
