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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.isep.musify.CustomCallback;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.Album;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.Artist;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.Playlist;
import com.isep.musify.models.Track;
import com.isep.musify.ui.DataViewModel;
import com.isep.musify.ui.LibraryFragmentAdapter;
import com.isep.musify.ui.TracksAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LibraryFragment extends Fragment {
    private String []sTitle = new String[]{"Playlists","Artists","Albums"};
    private TabLayout mTabLayout;
    private RecyclerView recyclerView;
    private TextView textView;
    private TracksAdapter adapter;
    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        //final TextView textView = root.findViewById(R.id.text_library);
        mTabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        Log.i("Musify" , "Access Token received in library Fragment: " + dataViewModel.getAccessToken());
        recyclerView = (RecyclerView) root.findViewById(R.id.playlistRecyclerView);
        //textView = root.findViewById(R.id.textView);

        librarySpotifyAPI();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("tab","onTabSelected:"+tab.getText());
                textView.setText(tab.getText());
                //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
                //adapter = new TracksAdapter(null);
                //recyclerView.setAdapter(adapter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    public void librarySpotifyAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.libraryApiRequest(dataViewModel.getAccessToken(), new CustomCallback() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("library", "onSuccess response: " + value);
                List<Item> itemsList = new ArrayList<>();
                List<Playlist> playlists = value.getPlayList();

                for(int i = 0; i < playlists.size(); i++){
                    String name = playlists.get(i).getName();
                    //String description = playlists.get(i).getDescription();
                    String owner = "By " + playlists.get(i).getOwner().getDisplay_name();
                    Log.d("Musify", "Playlist: " + name + "\n " + owner);
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    Item item = new Item(images.get(images.size()-1), name, owner, href);
                    Log.d("Musify", "Playlist converted to item!\n" + item.toString());
                    itemsList.add(item);
                }
                updateList(itemsList);

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
        adapter = new TracksAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}