package com.isep.musify.models;

//Object to hold necessary data to display in the Recyclerview's list
public class Item {
    Image icon;
    String name;
    String description;
    String href;

    public Item(Image icon, String name, String description, String href) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.href = href;
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
