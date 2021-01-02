package com.isep.musify.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private final MutableLiveData<String> accessToken = new MutableLiveData<String>();

    public void setAccessToken(String AccessToken) {
        accessToken.setValue(AccessToken);
    }

    public String getAccessToken() {
        return accessToken.getValue();
    }
}