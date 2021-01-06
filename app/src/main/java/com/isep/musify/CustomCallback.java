package com.isep.musify;

import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.NewReleaseItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
import com.isep.musify.models.Track;

import java.util.List;

public abstract interface CustomCallback {

     void onSuccess(ApiResponse value);
    void onProfileSuccess(Profile value);
    void onNewReleaseAlbum(ApiResponseNewAlbums value);
    void onFailure();

}
