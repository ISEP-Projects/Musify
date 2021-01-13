package com.isep.musify.ui.library;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.isep.musify.CustomCallback;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Artist;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.LibraryItem;
import com.isep.musify.ui.ArtistsAdapter;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.TracksAdapter;

import java.util.ArrayList;
import java.util.List;


public class LibraryFragment extends Fragment implements TracksAdapter.TrackClickListener,ArtistsAdapter.ArtistClickListener {
    private String []sTitle = new String[]{"Playlists","Artists","Albums"};
    private TabLayout mTabLayout;
    private RecyclerView recyclerView;
    private TextView textView;
    private TracksAdapter tracksAdapter;
    private ArtistsAdapter artistsAdapter;
    private DataViewModel dataViewModel;
    private List<Item> playlistsItems, artistsItems, albumsItems;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        Log.i("Musify" , "Access Token received in library Fragment: " + dataViewModel.getAccessToken());

        View root = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerView = root.findViewById(R.id.playlistRecyclerView);
        mTabLayout = root.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));

        initialData();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("tab","onTabSelected:"+tab.getText());
                CharSequence text = tab.getText();
                if ("Playlists".equals(text)) {
                    updateList(playlistsItems);
                } else if ("Artists".equals(text)) {
                    updateArtistList(artistsItems);
                } else if ("Albums".equals(text)) {
                    updateList(albumsItems);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        return root;
    }

    public void playlistsSpotifyAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.playlistsApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("library", "onSuccess response: " + value);

                List<LibraryItem> playlists = value.getLibraryItems();

                for(int i = 0; i < playlists.size(); i++){
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(i).getOwner().getDisplay_name();
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    playlistsItems.add(item);
                }
                updateList(playlistsItems);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void artistsSpotifyAPI(){
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.artistsApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("Artists", "onSuccess response: " + value.getArtistsList().getArtists().size());
                List<Artist> artistsList = value.getArtistsList().getArtists();

                for(int i = 0; i < artistsList.size(); i++){
                    String name = artistsList.get(i).getName();
                    String description = "Artist ";
                    List<Image> images = artistsList.get(i).getImages();
                    String href = artistsList.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    artistsItems.add(item);
                }
            }

            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void albumsSpotifyAPI(){
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.albumsApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                //Log.d("Artists", "onSuccess response: " + value.getArtistsList().getArtists().size());
                List<LibraryItem> albumsList = value.getLibraryItems();

                for(int i = 0; i < albumsList.size(); i++){
                    String name = albumsList.get(i).getAlbum().getName();
                    String description = albumsList.get(i).getAddedAt();
                    List<Image> images = albumsList.get(i).getAlbum().getImages();
                    String href = albumsList.get(i).getAlbum().getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    albumsItems.add(item);
                }
            }

            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateList(List<Item> list){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tracksAdapter = new TracksAdapter(list);
        tracksAdapter.setClickListener(this);
        tracksAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tracksAdapter);
    }

    public void updateArtistList(List<Item> list){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("musify" , String.valueOf(list.size()));
        artistsAdapter = new ArtistsAdapter(list);

        artistsAdapter.setClickListener(this::onArtistClick);
        artistsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(artistsAdapter);
    }

    public void initialData(){
        playlistsItems = new ArrayList<>();
        artistsItems = new ArrayList<>();
        albumsItems = new ArrayList<>();
        playlistsSpotifyAPI();
        artistsSpotifyAPI();
        albumsSpotifyAPI();
    }

    @Override
    public void onTrackClick(View view, int position) {
        Toast.makeText(getContext().getApplicationContext(), "Clicked " + view.toString() + " at position " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onArtistClick(View view, int position) {

    }
}