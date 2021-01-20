package com.isep.musify.models;

import android.os.Parcel;
import android.os.Parcelable;

//Object to hold necessary data to display in the Recyclerview's list
public class Item implements Parcelable {
    Image icon;
    String iconUrl;
    Image cover;
    String coverUrl;
    String name;
    String description;
    String href;
    String uri;
    int index;  //Index of item in original list

    public Item(Image icon, Image cover, String name, String description, String href) {
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.href = href;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
    }

    public Item(Image icon, Image cover, String name, String description, String href, String uri, int index) {
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.href = href;
        this.uri = uri;
        this.index = index;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
    }

    public Item(Image icon, Image cover, String name, String description, String href, int index) {
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.href = href;
        this.index = index;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
    }

    protected Item(Parcel in) {
        name = in.readString();
        description = in.readString();
        uri = in.readString();
        href = in.readString();
        index = in.readInt();
        iconUrl = in.readString();
        coverUrl = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    public String getUri() {
        return uri;
    }

    public String getHref() {
        return href;
    }

    public int getIndex() {
        return index;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    @Override
    public String toString() {
        return "Item{" +
                "iconUrl='" + iconUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", href='" + href + '\'' +
                ", index=" + index +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(uri);
        parcel.writeString(href);
        parcel.writeInt(index);
        parcel.writeString(iconUrl);
        parcel.writeString(coverUrl);
    }
}
