package com.isep.musify;

import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Track;

import java.util.List;

public interface CustomCallback {

    void onSuccess(ApiResponse value);
    void onFailure();

}
