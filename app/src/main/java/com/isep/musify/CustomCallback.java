package com.isep.musify;

import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.NewReleaseItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
import com.isep.musify.models.Track;

import java.util.List;

public abstract class CustomCallback {

     protected abstract void onSuccess(ApiResponse value);
    protected abstract void onProfileSuccess(Profile value);
    protected abstract void onNewReleaseAlbum(ApiResponseNewAlbums value);
    protected abstract void onFailure();

}
