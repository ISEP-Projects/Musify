package com.isep.musify;

import android.util.Log;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.Profile;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.isep.musify.models.ArtistTrackResponse;
import com.isep.musify.models.ArtistsResponseObject;
import com.isep.musify.models.PlaylistResponse;
import com.isep.musify.models.Track;
import com.isep.musify.ui.search.SearchFragment;

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

    public void searchApiRequest(String AccessToken, String searchQuery, CustomCallbackSuccess customCallbackSuccess) {

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
                    customCallbackSuccess.onSuccess(response.body());
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

    public void getRandomArtists(String AccessToken, Character searchQuery, CustomCallbackSuccess customCallback){

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

    public void playlistsApiRequest(String AccessToken, CustomCallbackSuccess customCallbackSuccess) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.myPlaylists();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getLibraryItems().size());
                    customCallbackSuccess.onSuccess(response.body());
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

    public void artistsApiRequest(String AccessToken, CustomCallbackSuccess customCallbackSuccess) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.myFollowingArtist();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getArtistsList().getArtists().size());;
                    customCallbackSuccess.onSuccess(response.body());
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

    public void albumsApiRequest(String AccessToken, CustomCallbackSuccess customCallbackSuccess) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.mySavedAlbums();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Log.d("Musify Call", call.request().toString());
                if (response.code() == 200) {
                    customCallbackSuccess.onSuccess(response.body());
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
                Log.d("Musify Error", t.getMessage());

            }
        });
    }

    public void currentUserApiRequest(String AccessToken, CustomCallbackProfile customCallbackSuccess) {

        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        //Interface instance
        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<Profile> call = spotifyApiService.currentUser();

        //Custom callback to send the data retrieved(JSON) back to the function as an object
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.d("Musify Call", call.request().toString());

                if (response.code() == 200) {
                    Log.d("Musify", "Response " + response.body().toString());

                    Log.d("Musify", "Data response from API received");
                    customCallbackSuccess.onProfileSuccess(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "Response " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("Musify Error", t.getMessage());
            }
        });
    }

    public void NewReleasesApiRequest(String AccessToken, CustomCallback_Album_Release customCallbackSuccess) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponseNewAlbums> call = spotifyApiService.getLatestAlbums();

        call.enqueue(new Callback<ApiResponseNewAlbums>() {
            @Override
            public void onResponse(Call<ApiResponseNewAlbums> call, Response<ApiResponseNewAlbums> response) {
                Log.d("Musify Call", call.request().toString());
                if (response.code() == 200) {
                    customCallbackSuccess.onNewReleaseAlbum(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "albumsApiRequest Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponseNewAlbums> call, Throwable t) {
            }
        });
    }

    public void LatestPlaylist(String AccessToken, CustomCallbackSuccess customCallbackSuccess) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ApiResponse> call = spotifyApiService.getFeaturedLists();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    //Log.d("Musify", "Response " + response.body().getLibraryItems().size());
                    customCallbackSuccess.onSuccess(response.body());
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


    public void myPlaylistApiRequest(String AccessToken, String playlistId, CustomCallback customCallback) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<PlaylistResponse> call = spotifyApiService.myPlaylist(playlistId);

        call.enqueue(new Callback<PlaylistResponse>() {
            @Override
            public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                Log.d("Musify Call", call.request().toString());
                if (response.code() == 200) {
                    customCallback.onSuccessForPlaylist(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "my Playlist Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaylistResponse> call, Throwable t) {
            }
        });
    }


    public void myArtistApiRequest(String AccessToken, String artistId, CustomCallback customCallback) {
        Retrofit retrofit = getRetrofitBuilder(AccessToken);

        SpotifyApiService spotifyApiService = retrofit.create(SpotifyApiService.class);
        Call<ArtistTrackResponse> call = spotifyApiService.myArtist(artistId);

        call.enqueue(new Callback<ArtistTrackResponse>() {
            @Override
            public void onResponse(Call<ArtistTrackResponse> call, Response<ArtistTrackResponse> response) {
                Log.d("Musify Call", call.request().toString());
                if (response.code() == 200) {
                    customCallback.onSuccessForArtist(response.body());
                } else {
                    try {
                        Log.d("Musify Body Error", "my Playlist Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArtistTrackResponse> call, Throwable t) {
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