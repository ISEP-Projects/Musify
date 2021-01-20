package com.isep.musify.models;

import android.os.Parcel;
import android.os.Parcelable;

//Object to hold necessary data to display in the Recyclerview's list
public class Item implements Parcelable {
    String id;
    String type;
    Image icon;
    String iconUrl;
    Image cover;
    String coverUrl;
    String name;
    String description;
    String href;
    String uri;
    String artistName;
    int index;  //Index of item in original list

    //For Artsits/Albums
    public Item(String id, String type, Image icon, Image cover, String name, String description, String href) {
        this.id = id;
        this.type = type;
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.href = href;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
    }

    //For Tracks
    public Item(String id, String type, Image icon, Image cover, String name, String description, String href, String uri, int index) {
        this.id = id;
        this.type = type;
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

    //For Library Items
    public Item(Image icon, Image cover, String name, String description, String href) {
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.href = href;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
    }

    //For Playback
    public Item(String id, String coverUrl, String name, String description, String uri) {
        this.id = id;
        this.coverUrl = coverUrl;
        this.name = name;
        this.description = description;
        this.uri = uri;
    }

    //For Artist?
    public Item(String name, String artistName, Image image) {
        this.name = name;
        this.artistName = artistName;
        this.icon = image;
    }

    protected Item(Parcel in) {
        id = in.readString();
        type = in.readString();
        name = in.readString();
        description = in.readString();
        uri = in.readString();
        href = in.readString();
        index = in.readInt();
        iconUrl = in.readString();
        coverUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(type);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(uri);
        parcel.writeString(href);
        parcel.writeInt(index);
        parcel.writeString(iconUrl);
        parcel.writeString(coverUrl);
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

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Image getIcon() {
        return icon;
    }

    public Image getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
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

    public String getUri() {
        return uri;
    }

    public String getHref() {
        return href;
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
                "id='" + id + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", href='" + href + '\'' +
                ", uri='" + uri + '\'' +
                ", index=" + index +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
