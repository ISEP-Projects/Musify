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
import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.SongAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtistActivity  extends AppCompatActivity {
    private String accessToken, artistId, imageHref, artistName, followers;
    private DataViewModel dataViewModel;
    private List<Item> songslistItems;
    private ImageView image;
    private Button playButton;
    private TextView nameView, followersView;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        image = findViewById(R.id.imageView);
        playButton = findViewById(R.id.play_button);
        nameView = findViewById(R.id.name_textView);
        followersView = findViewById(R.id.followers_textView);

        recyclerView = findViewById(R.id.songlistRecyclerView);
        accessToken = getIntent().getStringExtra("AccessToken");
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.setAccessToken(accessToken);
        //Log.d("Musify", "Access Token received in Main Activity: " + accessToken);
        songslistItems = new ArrayList<>();
        artistId = getIntent().getStringExtra("ArtistId");
        imageHref = getIntent().getStringExtra("imageHref");
        artistName = getIntent().getStringExtra("ArtistName");
        followers = getIntent().getStringExtra("Followers");
        followersView.setText("Followers: " + followers);
        Picasso.get()
                .load(imageHref)
                .into(image);
        nameView.setText(artistName);
        playlistSpotifyAPI();


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void playlistSpotifyAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.myArtistApiRequest(dataViewModel.getAccessToken(), artistId, new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {

            }

            @Override
            public void onSuccessForPlaylist(PlaylistResponse value) {

            }

            @Override
            public void onSuccessForArtist(ArtistTrackResponse value) {
                Log.d("library", "onSuccess response: " + value);

                List<Track> songslist = value.getTracks();

                for(int i = 0; i < songslist.size(); i++){
                    String name = songslist.get(i).getName();
                    String artistName = songslist.get(i).getArtists().get(0).getName();
                    List<Image> images = songslist.get(i).getAlbum().getImages();
                    Item item = new Item(name, artistName, images.get(images.size()-1));
                    songslistItems.add(item);
                }
                Log.d("songslist",songslistItems.toString());
                updateSonglist(songslistItems);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateSonglist(List<Item> list){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songAdapter = new SongAdapter(list);
        songAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(songAdapter);
    }

}
