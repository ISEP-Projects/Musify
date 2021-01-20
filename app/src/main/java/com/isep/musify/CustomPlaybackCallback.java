package com.isep.musify;

import com.isep.musify.models.ApiResponse;

public interface CustomPlaybackCallback {

    void onSuccess(ApiResponse value);
    void onFailure();

}
