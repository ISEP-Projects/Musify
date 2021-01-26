package com.isep.musify.ui.library;

import android.content.Intent;
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
import com.isep.musify.CustomCallbackSuccess;
import com.isep.musify.ArtistActivity;
import com.isep.musify.CustomCallback;
import com.isep.musify.PlaylistActivity;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.Artist;
import com.isep.musify.models.ArtistTrackResponse;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.LibraryItem;

import com.isep.musify.models.Profile;

import com.isep.musify.models.PlaylistResponse;
import com.isep.musify.ui.ArtistsAdapter;

import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.PlaylistAdapter;
import com.isep.musify.ui.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;


public class LibraryFragment extends Fragment implements ItemsAdapter.ItemClickListener,ArtistsAdapter.ArtistClickListener, PlaylistAdapter.PlaylistClickListener {
    private String []sTitle = new String[]{"Playlists","Artists","Albums"};
    private TabLayout mTabLayout;
    private RecyclerView recyclerView;
    private TextView textView;
    private ItemsAdapter adapter;
    private ArtistsAdapter artistsAdapter;
    private PlaylistAdapter playlistAdapter;

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
                    updatePlaylist(playlistsItems);
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
        apiConnection.playlistsApiRequest(dataViewModel.getAccessToken(), new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("library", "onSuccess response: " + value.getLibraryItems().size());

                List<LibraryItem> playlists = value.getLibraryItems();
                Log.d("playlists", playlists.toString());

                for(int i = 0; i < playlists.size(); i++){
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(i).getOwner().getDisplay_name();
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    String id = playlists.get(i).getId();
                    //Item item = new Item(images.get(images.size()-1), name, description, href, id);
                    Item item = new Item(images.get(images.size()-1), images.get(0), name, description, href);
                    playlistsItems.add(item);
                }
                Log.d("playlist",playlistsItems.toString());
                updatePlaylist(playlistsItems);
            }


            @Override
            public void onSuccessForArtist(ArtistTrackResponse value) {

            }



            @Override
            public void onFailure() {
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void artistsSpotifyAPI(){
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.artistsApiRequest(dataViewModel.getAccessToken(), new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("Artists", "onSuccess response: " + value.getArtistsList().getArtists().size());
                List<Artist> artistsList = value.getArtistsList().getArtists();

                for(int i = 0; i < artistsList.size(); i++){
                    String name = artistsList.get(i).getName();
                    String followers = artistsList.get(i).getFollowrs().getTotal();
                    List<Image> images = artistsList.get(i).getImages();
                    String href = artistsList.get(i).getHref();
                    String id = artistsList.get(i).getId();
                    //Item item = new Item(images.get(images.size()-1), name, followers, href, id);
                    String description = "Artist";
                    Item item = new Item(images.get(images.size()-1), images.get(0), name, description, href);
                    artistsItems.add(item);
                }
            }

            @Override
            public void onSuccessForArtist(ArtistTrackResponse value) {

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
        apiConnection.albumsApiRequest(dataViewModel.getAccessToken(), new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {
                //Log.d("Artists", "onSuccess response: " + value.getArtistsList().getArtists().size());
                List<LibraryItem> albumsList = value.getLibraryItems();

                for(int i = 0; i < albumsList.size(); i++){
                    String name = albumsList.get(i).getAlbum().getName();
                    String description = albumsList.get(i).getAddedAt();
                    List<Image> images = albumsList.get(i).getAlbum().getImages();
                    String href = albumsList.get(i).getAlbum().getHref();
                    Item item = new Item(images.get(images.size()-1), images.get(0), name, description, href);
                    String id = albumsList.get(i).getId();
                    //Item item = new Item(images.get(images.size()-1), name, description, href,id);
                    albumsItems.add(item);
                }
            }

            @Override
            public void onSuccessForArtist(ArtistTrackResponse value) {

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
        adapter = new ItemsAdapter(list);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void updatePlaylist(List<Item> list){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playlistAdapter = new PlaylistAdapter(list);
        playlistAdapter.setClickListener(this::onPlaylistClick);
        playlistAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(playlistAdapter);
    }
    public void updateArtistList(List<Item> list){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void onPlaylistClick(View view, int position) {
        Intent i = new Intent(getActivity(), PlaylistActivity.class);
        i.putExtra("AccessToken", dataViewModel.getAccessToken());
        i.putExtra("PlaylistId", playlistsItems.get(position).getId());
        i.putExtra("playlistName", playlistsItems.get(position).getName());
        i.putExtra("playlistDescription", playlistsItems.get(position).getDescription());
        i.putExtra("imageHref",playlistsItems.get(position).getIcon().getUrl());
        Log.d("musify",playlistsItems.get(position).getId());
        startActivity(i);
    }
    @Override
    public void onArtistClick(View view, int position) {
        Intent i = new Intent(getActivity(), ArtistActivity.class);
        i.putExtra("AccessToken", dataViewModel.getAccessToken());
        i.putExtra("ArtistId", artistsItems.get(position).getId());
        i.putExtra("ArtistName", artistsItems.get(position).getName());
        i.putExtra("Followers", artistsItems.get(position).getDescription());
        i.putExtra("imageHref",artistsItems.get(position).getIcon().getUrl());
        startActivity(i);
    }
}