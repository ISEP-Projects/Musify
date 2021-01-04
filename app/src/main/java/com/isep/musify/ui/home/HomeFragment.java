package com.isep.musify.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isep.musify.CustomCallback;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.Album;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Artist;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.LibraryItem;
import com.isep.musify.models.Profile;
import com.isep.musify.models.SimplePlaylist;
import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.TracksAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView textView,profile;
    ImageView imageView;
  //  private HomeViewModel homeViewModel;
    private DataViewModel dataViewModel;
    private List<Item> playlistsItems;
    private RecyclerView recyclerView;
    private TracksAdapter tracksAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        //homeViewModel =new ViewModelProvider(this).get(HomeViewModel.class);
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
         recyclerView = root.findViewById(R.id.RV_playlist);
        profile=root.findViewById(R.id.profileName);
        imageView=root.findViewById(R.id.imageView);

       //textView = root.findViewById(R.id.text_home);
        init();
        updateList(playlistsItems);
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

    public void init(){
        playlistsItems = new ArrayList<>();
        playlistsSpotifyAPI();

    }


    public void updateList(List<Item> list){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        tracksAdapter= new TracksAdapter(list);
        tracksAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tracksAdapter);
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

