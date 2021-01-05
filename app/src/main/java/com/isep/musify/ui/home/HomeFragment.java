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
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.LibraryItem;
import com.isep.musify.models.NewReleaseItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
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
    private List<Item> playlistsItems, newPlaylists,newAlbums;
    private RecyclerView recyclerView_Made_For_You, recyclerView_newAlbums;
    private TracksAdapter tracksAdapter,playlistAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        //homeViewModel =new ViewModelProvider(this).get(HomeViewModel.class);
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
         recyclerView_Made_For_You = root.findViewById(R.id.RV_playlist);
         recyclerView_newAlbums =root.findViewById(R.id.newPlaylists);
        profile=root.findViewById(R.id.profileName);
        imageView=root.findViewById(R.id.imageView);

       //textView = root.findViewById(R.id.text_home);
        init();
        updateList(playlistsItems,0);
        updateList(newAlbums,1);
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
                imageView.setClipToOutline(true);


                Picasso.get()
                        .load(image.get(0).getUrl())
                        .into(imageView);

           Log.d("Profile value = ",""+value);
           // textView.setText((CharSequence) value.getProfile().getName());
            }

            @Override
            public void onNewRelease(NewReleaseItem value) {

            }

            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void newAlbumsAPI(){
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.NewReleasesApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onNewRelease(NewReleaseItem value) {

                List<NewReleases> playlists = value.getAlbums();

                for(int i = 0; i < playlists.size(); i++){
                    Log.d("Content",""+playlists.get(i).toString());
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(i).getArtists().get(i).getName();
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                   System.out.println( "LOOK HERE________\n" + item.toString());

                    newPlaylists.add(item);
                }
                updateList(newAlbums,1);
            }

            @Override
            public void onSuccess(ApiResponse value) {

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


    public void init(){
        playlistsItems = new ArrayList<>();
        newAlbums=new ArrayList<>();
        playlistsSpotifyAPI();
       // latestPlaylistSpotifyAPI();
        newAlbumsAPI();

    }


    public void updateList(List<Item> list, int flag){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
switch(flag) {
    case 0:
        recyclerView_Made_For_You.setHasFixedSize(true);
        recyclerView_Made_For_You.setLayoutManager(layoutManager);
        tracksAdapter = new TracksAdapter(list);
        tracksAdapter.notifyDataSetChanged();
        recyclerView_Made_For_You.setAdapter(tracksAdapter);
        break;
    case 1:
        recyclerView_newAlbums.setHasFixedSize(true);
        recyclerView_newAlbums.setLayoutManager(layoutManager);
        playlistAdapter = new TracksAdapter(list);
        playlistAdapter.notifyDataSetChanged();
        recyclerView_newAlbums.setAdapter(playlistAdapter);
    } }
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
                updateList(playlistsItems,0);
            }

            @Override
            public void onProfileSuccess(Profile value) {

            }

            @Override
            public void onNewRelease(NewReleaseItem value) {

            }


            @Override
            public void onFailure() {
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void latestPlaylistSpotifyAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.LatestPlaylist(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("library", "onSuccess response: " + value);

                List<NewReleases> playlists = value.getReleases();

                for(int i = 0; i < playlists.size(); i++){
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(i).getArtists().get(i).getName();
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                   newPlaylists.add(item);
                }
                updateList(newPlaylists,1);
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

*/

}

