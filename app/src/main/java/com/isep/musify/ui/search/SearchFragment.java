package com.isep.musify.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.Artist;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.NewReleaseItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.TracksAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements TracksAdapter.TrackClickListener {

    EditText searchInput;
    Button button;
    private DataViewModel dataViewModel;

    private RecyclerView recyclerView;
    private TracksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //private List<Track> tracksList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = root.findViewById(R.id.tracksRecyclerView);
        searchInput = root.findViewById(R.id.searchInput);
        button = root.findViewById(R.id.btnSearch);

        //Log.d("Musify" , "Access Token received in Search Fragment: " + dataViewModel.getAccessToken());

        //RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TracksAdapter(null);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String input = String.valueOf(searchInput.getText());
                Toast.makeText(getContext().getApplicationContext(), "Text entered: " + input, Toast.LENGTH_LONG).show();
                searchSpotifyAPI(input);
            }
        });

        return root;
    }


    public void searchSpotifyAPI(String input) {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.searchApiRequest(dataViewModel.getAccessToken(), input, new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                //Saving objects of different types into a unified list to display in Recyclerview
                List<Item> itemsList = new ArrayList<>();

                //Retrieve respective lists from object
                List<Track> tracksList = value.getTracksList().getTracks();
                List<Album> albumsList = value.getAlbumsList().getAlbums();
                List<Artist> artistsList = value.getArtistsList().getArtists();


               //Saving Tracks as Custom Items
                for(int i = 0; i < tracksList.size(); i++){
                    String name = tracksList.get(i).getName();
                    String description = "Track  |  " + tracksList.get(i).getArtists().get(0).getName();
                    Log.d("Musify", "Artist for " + name + "\n" + tracksList.get(i).getArtists().get(0).toString());
                    List<Image> images = tracksList.get(i).getAlbum().getImages();
                    String href = tracksList.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    Log.d("Musify", "Track converted to item!\n" + item.toString());
                    itemsList.add(item);
                }

                //Saving Albums as Custom Items
                for(int i = 0; i < albumsList.size(); i++){
                    String name = albumsList.get(i).getName();
                    String description = "Album  |  " + tracksList.get(i).getArtists().get(0).getName();
                    //Log.d("Musify", "Album by " + name + "\n" + tracksList.get(i).getArtists().get(0).toString());
                    List<Image> images = albumsList.get(i).getImages();
                    String href = albumsList.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    Log.d("Musify", "Album converted to item!\n" + item.toString());
                    itemsList.add(item);
                }

                //Saving Artists as Custom Items
                for(int i = 0; i < artistsList.size(); i++){
                    String name = artistsList.get(i).getName();
                    String description = "Artist ";
                    List<Image> images = artistsList.get(i).getImages();
                    String href = artistsList.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, description, href);
                    Log.d("Musify", "Artist converted to item!\n" + item.toString());
                    itemsList.add(item);
                }

                updateTracksList(itemsList);
            }

            @Override
            public void onProfileSuccess(Profile value) {

            }

            @Override
            public void onNewReleaseAlbum(ApiResponseNewAlbums value) {

            }



            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateTracksList(List<Item> list){

        //Update RecyclerView
        adapter = new TracksAdapter(list);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        Log.d("Musify", "Updating Tracks list " + list.size() );
    }


    @Override
    public void onTrackClick(View view, int position) {
        Toast.makeText(getContext().getApplicationContext(), "Clicked " + view.toString() + " at position " + position, Toast.LENGTH_LONG).show();
    }
}


/*
        String url = "https://api.spotify.com/v1/search?query=bruno+mars&type=artist&offset=0&limit=20";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object = response.getJSONObject("rates");


                            Log.d("Volley Response object: ", object.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley response", "An error occurred.\n" + error);
            }
        });
        queue.add(request);
        */