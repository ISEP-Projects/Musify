package com.isep.musify.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.isep.musify.CustomCallbackSuccess;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.MusicPlayer;
import com.isep.musify.models.Album;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Artist;
import com.isep.musify.models.ArtistTrackResponse;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;

import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.ItemsAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements ItemsAdapter.ItemClickListener {

    EditText searchInput;
    Button button;
    private DataViewModel dataViewModel;

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Item> itemsList;
    private List<Track> tracksList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = root.findViewById(R.id.tracksRecyclerView);
        searchInput = root.findViewById(R.id.searchInput);
        button = root.findViewById(R.id.btnSearch);

        Log.i("Musify" , "Access Token received in Search Fragment: " + dataViewModel.getAccessToken());

        //RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemsAdapter(null);
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
        apiConnection.searchApiRequest(dataViewModel.getAccessToken(), input, new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {
                //Saving objects of different types into a unified list to display in Recyclerview
                itemsList = new ArrayList<>();
                Log.d("TAG", "onSuccess: " +  value.getTracksList());

                //Retrieve respective lists from object
                tracksList = value.getTracksList().getTracks();
                List<Album> albumsList = value.getAlbumsList().getAlbums();
                List<Artist> artistsList = value.getArtistsList().getArtists();


               //Saving Tracks as Custom Items
                for(int i = 0; i < tracksList.size(); i++){
                    String id = tracksList.get(i).getId();
                    String name = tracksList.get(i).getName();
                    String description = "Track  |  " + tracksList.get(i).getArtists().get(0).getName();
                    //Log.d("Musify", "Artist for " + name + "\n" + tracksList.get(i).getArtists().get(0).toString());
                    List<Image> images = tracksList.get(i).getAlbum().getImages();
                    String href = tracksList.get(i).getHref();
                    String uri = tracksList.get(i).getUri();
                    Item item = new Item(id, "Track", images.get(images.size()-1), images.get(0), name, description, href, uri, i);
                    //Log.d("Musify", "Track converted to item!\n" + item.toString());
                    itemsList.add(item);
                }

                //Saving Albums as Custom Items
                for(int i = 0; i < albumsList.size(); i++){
                    String id = albumsList.get(i).getId();
                    String name = albumsList.get(i).getName();
                    String description = "Album  |  " + tracksList.get(i).getArtists().get(0).getName();
                    //Log.d("Musify", "Album by " + name + "\n" + tracksList.get(i).getArtists().get(0).toString());
                    List<Image> images = albumsList.get(i).getImages();
                    String href = albumsList.get(i).getHref();
                    Item item = new Item(id, "Album", images.get(images.size()-1), images.get(0), name, description, href);
                    //Log.d("Musify", "Album converted to item!\n" + item.toString());
                    itemsList.add(item);
                }

                //Saving Artists as Custom Items
                for(int i = 0; i < artistsList.size(); i++){
                    String id = artistsList.get(i).getId();
                    String name = artistsList.get(i).getName();
                    String description = "Artist ";
                    List<Image> images = artistsList.get(i).getImages();
                    String href = artistsList.get(i).getHref();
                    Item item = new Item(id, "Artist", images.get(images.size()-1), images.get(0), name, description, href);
                    //Log.d("Musify", "Artist converted to item!\n" + item.toString());
                    itemsList.add(item);
                }

                updateTracksList(itemsList);
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

    public void updateTracksList(List<Item> list){

        //Update RecyclerView
        adapter = new ItemsAdapter(list);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        Log.d("Musify", "Updating Tracks list " + list.size() );
    }


    @Override
    public void onItemClick(View view, int position) {
        //Log.d("Musify", "Position " + position);
        //Log.d("Musify", "Items List " + itemsList.size());
        //Toast.makeText(getContext().getApplicationContext(), "Clicked " + view.toString() + " at position " + position, Toast.LENGTH_LONG).show();
        Item item = itemsList.get(position);
        switch(item.getType()){
            case "Track":
                Intent i = new Intent(getContext(), MusicPlayer.class);
                i.putExtra("AccessToken", dataViewModel.getAccessToken());
                i.putExtra("Track", itemsList.get(position));
                startActivity(i);
                break;
            case "Album":
                break;

            case "Artist":
                break;

            default:
                Log.e("Musify", "Unkown Item type\n" + item.toString());
        }

        /*
        if(item.getDescription().contains("Track")){
            Intent i = new Intent(getContext(), TrackDetails.class);
            i.putExtra("AccessToken", dataViewModel.getAccessToken());
            i.putExtra("Track", (Parcelable) tracksList.get(item.getIndex()));
            startActivity(i);
        }
         */

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