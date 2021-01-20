package com.isep.musify;

import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.TracksResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// http://service.com/movies/list?movie_lang=hindi
//@GET("http://service.com/movies/list")
//Single<JsonElement> getMovieList(@Query("movie_lang") String userLanguage);

public interface SpotifyApiService {
    @GET("search?type=track%2Cartist%2Calbum&market=IN&limit=3")
    Call<ApiResponse> searchAPI(@Query("q") String searchQuery);

    @GET("search?type=artist&market=IN&limit=4")
    Call<ApiResponse> getArtists(@Query("q") Character searchQuery);

    @GET("me/playlists")
    Call<ApiResponse> myPlaylists();

    @GET("me/following?type=artist")
    Call<ApiResponse> myFollowingArtist();

    @GET("me/albums")
    Call<ApiResponse> mySavedAlbums();

    @GET("me/player?market=IN")
    Call<ApiResponse> getPlayback();
}
