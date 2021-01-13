package com.isep.musify;

import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.PlaylistResponse;
import com.isep.musify.models.Track;

import java.util.List;

public interface CustomCallback {

    void onSuccess(ApiResponse value);
    void onSuccessForPlaylist(PlaylistResponse value);
    void onFailure();

}
