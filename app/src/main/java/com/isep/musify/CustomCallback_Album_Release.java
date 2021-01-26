package com.isep.musify;

import com.isep.musify.models.ApiResponseNewAlbums;

public interface CustomCallback_Album_Release {
    void onNewReleaseAlbum(ApiResponseNewAlbums value);
    void onFailure();
}
