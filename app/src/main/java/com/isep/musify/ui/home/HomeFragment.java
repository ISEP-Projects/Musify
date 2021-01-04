package com.isep.musify.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isep.musify.CustomCallback;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.Album;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.LibraryItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.TracksAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView textView,profile;
    ImageView imageView;
  //  private HomeViewModel homeViewModel;
    private DataViewModel dataViewModel;
    private List<Item> playlistsItems, newAlbums;
    private RecyclerView recyclerView_Made_For_You,recyclerView_New_Albums;
    private TracksAdapter tracksAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        //homeViewModel =new ViewModelProvider(this).get(HomeViewModel.class);
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
         recyclerView_Made_For_You = root.findViewById(R.id.RV_playlist);
     //    recyclerView_New_Albums=root.findViewById(R.id.newAlbums);
        profile=root.findViewById(R.id.profileName);
        imageView=root.findViewById(R.id.imageView);

       //textView = root.findViewById(R.id.text_home);
        init();
        updateList(playlistsItems);
        updateList(newAlbums);
        currentUserAPI();
        return root;
    }

    private void currentUserAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.currentUserApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {

            }

            @Override
            public void onProfileSuccess(Profile value) {
                //Saving objects of different types into a unified list to display in Recyclerview
           profile.setText("Welcome back! "+value.getDisplay_name());
                List<Image> image = value.getImages();
                Picasso.get()
                        .load(image.get(0).getUrl())
                        .into(imageView);
           Log.d("Profile value = ",""+value);
           // textView.setText((CharSequence) value.getProfile().getName());
            }

            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }
/*
    private void newAlbumsAPI(){
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.NewReleasesApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                //Log.d("Artists", "onSuccess response: " + value.getArtistsList().getArtists().size());
                List<NewReleases> albumsList = value.getAlbumsList().getAlbums();
                List<Track> tracksList = value.getTracksList().getTracks();

                for(int i = 0; i < albumsList.size(); i++){
                    String name = albumsList.get(i).getName();
                    String description = "Album  |  " + tracksList.get(i).getArtists().get(0).getName();
                    //Log.d("Musify", "Album by " + name + "\n" + tracksList.get(i).getArtists().get(0).toString());
                    List<Image> images = albumsList.get(i).getImages();
                    String href = albumsList.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    Log.d("Musify", "Album converted to item!\n" + item.toString());
                    newAlbums.add(item);
                }
                updateList(newAlbums,1);
            }

            @Override
            public void onProfileSuccess(Profile value) {

            }

            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

*/
    public void init(){
        playlistsItems = new ArrayList<>();
        newAlbums=new ArrayList<>();
        playlistsSpotifyAPI();
        //newAlbumsAPI();

    }


    public void updateList(List<Item> list){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

                       recyclerView_Made_For_You.setHasFixedSize(true);
                recyclerView_Made_For_You.setLayoutManager(layoutManager);
                tracksAdapter= new TracksAdapter(list);
                tracksAdapter.notifyDataSetChanged();
                recyclerView_Made_For_You.setAdapter(tracksAdapter);

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
            public void onProfileSuccess(Profile value) {

            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }





}

