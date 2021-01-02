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
    @GET("search?type=track%2Cartist%2Calbum&market=FR&limit=3")
    Call<ApiResponse> searchTracks(@Query("q") String searchQuery);

    //Call<List<Track>> searchTracks(@Query("q") String searchQuery, @Header("Authorization: Bearer") String accessToken);

    //@GET("?q={track}&type=track")
    //Call<Track> getSongById(@Path("songId") String songId);

    //@GET("songs/random/{number}")
    //Call<List<Song>> randomSongs(@Path("number") int number);
    @GET("me/playlists")
    Call<ApiResponse> myPlaylists();
}
