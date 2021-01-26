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
    String playlistId;
    String artistId;
    String albumId;
    String followers;
    int index;  //Index of item in original list

    //For Artists/Albums
    public Item(int index, String id, String type, Image icon, Image cover, String name, String description, String href) {
        this.index = index;
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

    //For Playlist Library Item
    public Item(Image icon, Image cover, String name, String description, String href, String playlistId) {
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.href = href;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
        this.playlistId = playlistId;
    }

    //Item item = new Item(images.get(images.size()-1), images.get(0), name, description, followers, href, artistId);
    //For Playlist Library Item
    public Item(Image icon, Image cover, String name, String description, String followers, String href, String artistId) {
        this.icon = icon;
        this.cover = cover;
        this.name = name;
        this.description = description;
        this.followers = followers;
        this.href = href;
        this.artistId = artistId;
        this.iconUrl = icon.getUrl();
        this.coverUrl = cover.getUrl();
    }

    //For Playlist Album Item
    public Item(String albumId, Image icon, Image cover, String name, String description, String href) {
        this.albumId = albumId;
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

    //For Playlist Items
    public Item(String name, String artistName, Image image) {
        this.name = name;
        this.artistName = artistName;
        this.icon = image;
    }

    //For Album
    public Item(Image cover, String name, String description, String href) {
        this.cover = cover;
        this.coverUrl = cover.getUrl();
        this.name = name;
        this.description = description;
        this.href = href;
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

    public int getIndex() {
        return index;
    }

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

    public String getUri() {
        return uri;
    }

    public String getHref() {
        return href;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getFollowers() {
        return followers;
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
