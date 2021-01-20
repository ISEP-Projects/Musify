package com.isep.musify;

import android.util.Log;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isep.musify.models.ApiResponse;
import com.spotify.sdk.android.authentication.AuthenticationRequest;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIConnection {

    static final String BASE_URL = "https://api.spotify.com/v1/";

    public Retrofit getRetrofitBuilder(String AccessToken) {
        //Log.d("Musify", AccessToken);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder()

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + AccessToken)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public void searchApiRequest(String AccessToken, String searchQuery, CustomCallback customCallback){

        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        //Interface instance
        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.searchAPI(searchQuery);

        //Custom callback to send the data retrieved(JSON) back to the function as an object
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Log.d("Musify Call", call.request().toString());

                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getTracksList().getTracks().size());
                    //Log.d("Musify", "Response " + response.body().getAlbumsList().getAlbums().size());
                    //Log.d("Musify", "Response " + response.body().getArtistsList().getArtists().size());
                    Log.d("Musify", "Data response from API received");
                    customCallback.onSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "Response " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Musify Error", t.getMessage());

            }
        });
    }

    public void getRandomArtists(String AccessToken, Character searchQuery, CustomCallback customCallback){

        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        //Interface instance
        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.getArtists(searchQuery);

        //Custom callback to send the data retrieved(JSON) back to the function as an object
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Log.d("Musify Call", call.request().toString());

                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getArtistsList().getArtists().size());
                    //Log.d("Musify", "Data response from API received");
                    customCallback.onSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "Response " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Musify Error", t.getMessage());

            }
        });
    }

    public void playlistsApiRequest(String AccessToken, CustomCallback customCallback){
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.myPlaylists();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getLibraryItems().size());
                    customCallback.onSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "playlistsApiRequest Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Musify Error", t.getMessage());

            }
        });

    }

    public void artistsApiRequest(String AccessToken, CustomCallback customCallback){
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.myFollowingArtist();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getArtistsList().getArtists().size());;
                    customCallback.onSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "artistsApiRequest Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Musify Error", t.getMessage());

            }
        });
    }

    public void albumsApiRequest(String AccessToken, CustomCallback customCallback){
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.mySavedAlbums();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Log.d("Musify Call", call.request().toString());
                if (response.code() == 200) {
                    customCallback.onSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "albumsApiRequest Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Musify Error", t.getMessage());

            }
        });
    }

    public void currentPlayback(String AccessToken, CustomPlaybackCallback playbackCallback){
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.getPlayback();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    //Log.d("Musify", response.raw().toString());
                    playbackCallback.onSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify", "Current Playback error " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Musify Error", t.getMessage());
            }
        });
    }
}