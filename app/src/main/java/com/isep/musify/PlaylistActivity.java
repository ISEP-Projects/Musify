package com.isep.musify;

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
import com.isep.musify.models.SongItem;
import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.SongAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements SongAdapter.SongClickListener {

    private String accessToken, playlistId, imageHref, playlisyName;
    private DataViewModel dataViewModel;
    private List<Item> songslistItems;
    private List<Track> tracksList;
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
        songslistItems = new ArrayList<>();
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
                Log.d("Musify", "onSuccess response: " + value);

                List<SongItem> songslist = value.getPlaylists().getSongList();
                followers = value.getFollower().getTotal();
                followersView.setText("Followers: " + followers);
                Log.d("Musify", value.getFollower().getTotal());

                for(int i = 0; i < songslist.size(); i++){
                    String name = songslist.get(i).getSong().getName();
                    String artistName = songslist.get(i).getSong().getArtists().get(0).getName();
                    List<Image> images = songslist.get(i).getSong().getAlbum().getImages();
                    Item item = new Item(name, artistName, images.get(images.size()-1));
                    songslistItems.add(item);
                }
                Log.d("Musify", songslistItems.toString());
                updateSonglist(songslistItems);
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
        recyclerView.setAdapter(songAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("Musify", "Clicked on " + position);

    }
}