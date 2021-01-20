package com.isep.musify;

import com.isep.musify.models.Profile;

public interface CustomCallbackProfile {
    void onProfileSuccess(Profile value);
    void onFailure();
}
