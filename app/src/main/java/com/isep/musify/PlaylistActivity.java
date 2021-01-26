package com.isep.musify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ArtistTrackResponse;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.PlaylistResponse;
import com.isep.musify.models.Track;
import com.isep.musify.models.TrackItem;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.SongAdapter;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp;
import com.spotify.android.appremote.api.error.NotLoggedInException;
import com.spotify.android.appremote.api.error.UserNotAuthorizedException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements SongAdapter.SongClickListener {
    Credentials credentials = new Credentials();
    private final String CLIENT_ID = credentials.getClientID();
    private final String REDIRECT_URI = credentials.getRedirectURI();
    private SpotifyAppRemote mSpotifyAppRemote;
    private String accessToken, playlistId, imageHref, playlisyName;
    private DataViewModel dataViewModel;
    private List<Item> itemsList;
    private List<TrackItem> tracksList;
    private ImageView image;
    private Button playButton;
    private TextView nameView, followersView;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private String followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        image = findViewById(R.id.imageView);
        playButton = findViewById(R.id.play_button);
        nameView = findViewById(R.id.name_textView);
        followersView = findViewById(R.id.followers_textView);

        recyclerView = findViewById(R.id.songlistRecyclerView);
        accessToken = getIntent().getStringExtra("AccessToken");
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.setAccessToken(accessToken);
        itemsList = new ArrayList<>();
        playlistId = getIntent().getStringExtra("PlaylistId");
        imageHref = getIntent().getStringExtra("imageHref");
        playlisyName = getIntent().getStringExtra("playlistName");
        Picasso.get()
                .load(imageHref)
                .into(image);
        nameView.setText(playlisyName);
        playlistSpotifyAPI();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Musify", "Play playlist");
                mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:" + playlistId);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Musify", "Connected! Yay!");
                    }

                    public void onFailure(Throwable error) {
                        Log.e("Musify", error.getMessage(), error);
                        if (error instanceof NotLoggedInException || error instanceof UserNotAuthorizedException) {
                            // Redirect to Login Activity
                        } else if (error instanceof CouldNotFindSpotifyApp) {
                            Toast.makeText(getApplicationContext(), "Please download Spotify App for Playback", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void playlistSpotifyAPI() {
        Log.d("Musify", "Fetching data of Playlist" + playlistId );
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.myPlaylistApiRequest(dataViewModel.getAccessToken(), playlistId, new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("Musify", "onSuccess");
            }

            @Override
            public void onSuccessForPlaylist(PlaylistResponse value) {
                tracksList = value.getPlaylistItems().getTrackItemsList();
                followers = value.getFollower().getTotal();
                followersView.setText("Followers: " + followers);
                Log.d("Musify", value.getFollower().getTotal());

                for(int i = 0; i < tracksList.size(); i++){
                    Track track = tracksList.get(i).getTrack();
                    Log.d("Musify", track.toString());
                    String id = track.getId();
                    String name = track.getName();
                    String description = track.getArtists().get(0).getName();
                    List<Image> images = track.getAlbum().getImages();
                    String href = track.getHref();
                    String uri = track.getUri();
                    Item item = new Item(id, "Track", images.get(images.size()-1), images.get(0), name, description, href, uri, i);
                    //Item item = new Item(name, artistName, images.get(images.size()-1));
                    itemsList.add(item);
                }
                Log.d("Musify", itemsList.toString());
                updateSonglist(itemsList);
            }

            @Override
            public void onSuccessForArtist(ArtistTrackResponse value) {

            }

            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks");
                Toast.makeText(getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateSonglist(List<Item> list){
        Log.d("Musify", "Items List " + String.valueOf(list.size()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songAdapter = new SongAdapter(list);
        songAdapter.notifyDataSetChanged();
        songAdapter.setClickListener(this);
        recyclerView.setAdapter(songAdapter);
    }

    @Override
    public void onSongClick(View view, int position) {
        Log.d("Musify", "Clicked on " + itemsList.get(position).getName());
        Intent i = new Intent(this, MusicPlayer.class);
        i.putExtra("AccessToken", dataViewModel.getAccessToken());
        i.putExtra("Track", itemsList.get(position));
        startActivity(i);

    }
}