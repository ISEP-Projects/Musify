package com.isep.musify.models;

//Object to hold necessary data to display in the Recyclerview's list
public class Item {
    Image icon;
    String name;
    String description;
    String href;
    String id;
    String artistName;

    public String getArtistName() {
        return artistName;
    }

    public Item(Image icon, String name, String description, String href) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.href = href;
    }

    public Item(Image image, String name, String description, String href, String id) {
        this.icon = image;
        this.name = name;
        this.description = description;
        this.href = href;
        this.id = id;
    }

    public Item(String name, String artistName, Image image) {
        this.name = name;
        this.artistName = artistName;
        this.icon = image;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        return "Item{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
