package com.isep.musify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isep.musify.models.Item;
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp;
import com.spotify.android.appremote.api.error.NotLoggedInException;
import com.spotify.android.appremote.api.error.UserNotAuthorizedException;
import com.squareup.picasso.Picasso;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class TrackDetails extends AppCompatActivity {

    Credentials credentials = new Credentials();
    private final String CLIENT_ID = credentials.getClientID();
    private final String REDIRECT_URI = credentials.getRedirectURI();
    private SpotifyAppRemote mSpotifyAppRemote;

    private String accessToken;
    private ImageView trackImage;
    private TextView trackName, trackDescription;
    private Item track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        trackImage = findViewById(R.id.trackImage);
        trackName = findViewById(R.id.trackName);
        trackDescription = findViewById(R.id.trackDescription);

        accessToken = getIntent().getStringExtra("AccessToken");
        Bundle data = getIntent().getExtras();
        track = (Item) data.getParcelable("Track");
        //Log.d("Musify", "URL: " + track.getCoverUrl());
        Picasso.get()
                .load(track.getCoverUrl())
                .into(trackImage);
        trackName.setText(track.getName());
        trackDescription.setText(track.getDescription());

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

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public void play(View view) {

        mSpotifyAppRemote.getPlayerApi().getPlayerState()
                .setResultCallback(playerState -> {
                    Log.d("Musify", playerState.track.name + " by " + playerState.track.artist.name);
                    if (playerState.isPaused && playerState.track.name.equals(track.getName())) {
                        Log.d("Musify", " TRUE ");
                        mSpotifyAppRemote.getPlayerApi().resume();
                    } else {
                        mSpotifyAppRemote.getPlayerApi().play(track.getUri());
                    }
                });
    }

    public void pause(View view) {
        mSpotifyAppRemote.getPlayerApi().pause();
    }

}