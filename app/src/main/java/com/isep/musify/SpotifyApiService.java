package com.isep.musify;

import com.isep.musify.models.ApiResponse;

import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.NewReleaseItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
import com.isep.musify.models.ProfileResponseObject;
import com.isep.musify.models.TracksResponseObject;

import com.isep.musify.models.ArtistTrackResponse;
import com.isep.musify.models.ArtistsResponseObject;
import com.isep.musify.models.PlaylistResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// http://service.com/movies/list?movie_lang=hindi
//@GET("http://service.com/movies/list")
//Single<JsonElement> getMovieList(@Query("movie_lang") String userLanguage);

public interface SpotifyApiService {
    @GET("search?type=track%2Cartist%2Calbum&market=FR&limit=3")
    Call<ApiResponse> searchAPI(@Query("q") String searchQuery);


    @GET("search?type=artist&market=FR&limit=4")
    Call<ApiResponse> getArtists(@Query("q") Character searchQuery);

    @GET("me")
    Call<Profile> currentUser();

    @GET("browse/new-releases?country=FR")
    Call<ApiResponseNewAlbums> getLatestAlbums();

    @GET("browse/featured-playlists")
    Call<ApiResponse> getFeaturedLists();
    @GET("me/playlists")
    Call<ApiResponse> myPlaylists();

    @GET("playlists/{playlist_id}")
    Call<PlaylistResponse> myPlaylist(@Path("playlist_id")  String playlist_id);

    @GET("artists/{id}/top-tracks?market=FR")
    Call<ArtistTrackResponse> myArtist(@Path("id")  String artist_id);

    @GET("me/following?type=artist")
    Call<ApiResponse> myFollowingArtist();

    @GET("me/albums")
    Call<ApiResponse> mySavedAlbums();
}
