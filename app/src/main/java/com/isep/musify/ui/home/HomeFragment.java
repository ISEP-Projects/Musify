package com.isep.musify.ui.home;

import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.amulyakhare.textdrawable.TextDrawable;
import com.isep.musify.CustomCallbackProfile;
import com.isep.musify.CustomCallback_Album_Release;
import com.isep.musify.CustomListAdapter;
import com.isep.musify.PlaylistActivity;
import com.isep.musify.R;
import com.isep.musify.models.ArtistTrackResponse;
import com.isep.musify.ui.DataViewModel;

import com.isep.musify.CustomCallbackSuccess;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.Image;
import com.isep.musify.models.Item;
import com.isep.musify.models.LibraryItem;
import com.isep.musify.models.NewReleases;
import com.isep.musify.models.Profile;
import com.isep.musify.models.SimplePlaylist;
import com.isep.musify.ui.LatestListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment implements LatestListAdapter.GalleryClickListener, CustomListAdapter.CustomListClickListner {
    TextView profile;
    ImageView imageView;
    private HomeViewModel homeViewModel;
    private DataViewModel dataViewModel;
    private List<Item> playlistsItems, newPlaylists, newAlbums;
    private RecyclerView recyclerView_Made_For_You, recyclerView_newAlbums, recyclerView_Featured_Playlist;
    //private TracksAdapter tracksAdapter;
    private LatestListAdapter latestListAdapter;
    private CustomListAdapter tracksAdapter;
    private Timer timer;
    private TimerTask timerTask;
    private int position;
    private LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView_Made_For_You = root.findViewById(R.id.RV_playlist);
        recyclerView_newAlbums = root.findViewById(R.id.newAlbumReleases);
        recyclerView_Featured_Playlist = root.findViewById(R.id.FeaturedLists);


        profile = root.findViewById(R.id.profileName);
        imageView = root.findViewById(R.id.imageView);

        //textView = root.findViewById(R.id.text_home);


        init();
        updateMadeforYouList(playlistsItems);
        updateNewAlbums(newAlbums);
        updateNewPlaylist(newPlaylists);
        currentUserAPI();

        if (recyclerView_Made_For_You.getAdapter().getItemCount() != 0) {
            position = recyclerView_Made_For_You.getAdapter().getItemCount() / 2;
            recyclerView_Made_For_You.scrollToPosition(position / 2);
        }
        if (recyclerView_newAlbums.getAdapter().getItemCount() != 0) {
            position = recyclerView_newAlbums.getAdapter().getItemCount() / 2;
            recyclerView_newAlbums.scrollToPosition(position / 2);
        }
        if (recyclerView_Featured_Playlist.getAdapter().getItemCount() != 0) {
            position = recyclerView_Featured_Playlist.getAdapter().getItemCount() / 2;
            recyclerView_Featured_Playlist.scrollToPosition(position / 2);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        SnapHelper snapHelper1 = new LinearSnapHelper();
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_Made_For_You);
        recyclerView_Made_For_You.smoothScrollBy(5, 0);
        snapHelper1.attachToRecyclerView(recyclerView_newAlbums);
        recyclerView_newAlbums.smoothScrollBy(5, 0);
        snapHelper2.attachToRecyclerView(recyclerView_Featured_Playlist);
        recyclerView_Featured_Playlist.smoothScrollBy(5, 0);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollBanner();
    }

    private void currentUserAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.currentUserApiRequest(dataViewModel.getAccessToken(), new CustomCallbackProfile() {


            @Override
            public void onProfileSuccess(Profile value) {
                //Saving objects of different types into a unified list to display in Recyclerview
                profile.setText("Welcome back! " + value.getDisplay_name());
                List<Image> image = value.getImages();
                imageView.setClipToOutline(true);

                try {
                    Picasso.get()
                            .load(image.get(0).getUrl())
                            .into(imageView);
                } catch (Exception e) {
                    //If no profile image
                    Log.d("No progile image", e.getMessage());
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRect(String.valueOf(value.getDisplay_name().charAt(0)), Color.RED);
                    imageView.setImageDrawable(drawable);
                }
                Log.d("Profile value = ", "" + value);
                // textView.setText((CharSequence) value.getProfile().getName());
            }


            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void newAlbumsAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.NewReleasesApiRequest(dataViewModel.getAccessToken(), new CustomCallback_Album_Release() {
            @Override
            public void onNewReleaseAlbum(ApiResponseNewAlbums value) {

                List<NewReleases> playlists = value.getReleases().getAlbums();

                for (int i = 0; i < playlists.size(); i++) {
                    Log.d("Content", "" + playlists.get(i).toString());
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(0).getArtists().get(0).getName();
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    Item item = new Item(images.get(images.size() - 1), name, description, href);


                    newAlbums.add(item);
                }
                updateNewAlbums(newAlbums);
            }


            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                Toast.makeText(getContext().getApplicationContext(), "Error fetching tracks", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void init() {
        playlistsItems = new ArrayList<>();
        newAlbums = new ArrayList<>();
        newPlaylists = new ArrayList<>();
        playlistsSpotifyAPI();
        latestPlaylistSpotifyAPI();
        newAlbumsAPI();

    }

    public void updateMadeforYouList(List<Item> list) {
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Made_For_You.setHasFixedSize(true);
        recyclerView_Made_For_You.setLayoutManager(layoutManager);
        tracksAdapter = new CustomListAdapter(list);
        tracksAdapter.setClickListener(this::onCustomListClick);
        tracksAdapter.notifyDataSetChanged();
        recyclerView_Made_For_You.setAdapter(tracksAdapter);
        recyclerView_Made_For_You.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = recyclerView_Made_For_You.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemIndex >= totalItemCount) return;
                layoutManager.smoothScrollToPosition(recyclerView_Made_For_You, null, lastVisibleItemIndex + 1);
            }
        });
        recyclerView_Made_For_You.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollBanner();
                } else if (newState == 0) {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });
    }
    public void updateNewAlbums(List<Item> list) {
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_newAlbums.setHasFixedSize(true);
        recyclerView_newAlbums.setLayoutManager(layoutManager);
        tracksAdapter = new CustomListAdapter(list);
        tracksAdapter.notifyDataSetChanged();
        recyclerView_newAlbums.setAdapter(tracksAdapter);

        recyclerView_newAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = recyclerView_newAlbums.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemIndex >= totalItemCount) return;
                layoutManager.smoothScrollToPosition(recyclerView_newAlbums, null, lastVisibleItemIndex + 1);
            }
        });
        recyclerView_newAlbums.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollBanner();
                } else if (newState == 0) {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });

    }
    public void updateNewPlaylist(List<Item> list) {
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Featured_Playlist.setHasFixedSize(true);
        recyclerView_Featured_Playlist.setLayoutManager(layoutManager);
        latestListAdapter = new LatestListAdapter(list);
        latestListAdapter.setClickListener(this::onGalleryClick);
        latestListAdapter.notifyDataSetChanged();
        recyclerView_Featured_Playlist.setAdapter(latestListAdapter);

        recyclerView_Featured_Playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = recyclerView_Featured_Playlist.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemIndex >= totalItemCount) return;
                layoutManager.smoothScrollToPosition(recyclerView_Featured_Playlist, null, lastVisibleItemIndex + 1);
            }
        });
        recyclerView_Featured_Playlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollBanner();
                } else if (newState == 0) {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });
    }

    public void playlistsSpotifyAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.playlistsApiRequest(dataViewModel.getAccessToken(), new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("library", "onSuccess response: " + value);

                List<LibraryItem> playlists = value.getLibraryItems();

                for (int i = 0; i < playlists.size(); i++) {
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(i).getOwner().getDisplay_name();
                    List<Image> images = playlists.get(i).getImages();
                    String href = playlists.get(i).getHref();
                    String playlistId = playlists.get(i).getId();
                    Item item = new Item(images.get(images.size() - 1), name, description, href,playlistId);
                    playlistsItems.add(item);
                }
                updateMadeforYouList(playlistsItems);
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

    public void latestPlaylistSpotifyAPI() {
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.LatestPlaylist(dataViewModel.getAccessToken(), new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {
                Log.d("Featured Playlists", "onSuccess response: " + value);

                List<SimplePlaylist> playlists = value.getFeaturedList().getFeaturedLists();

                for (int i = 0; i < playlists.size(); i++) {
                    String name = playlists.get(i).getName();
                    String description = "by " + playlists.get(i).getDescription();
                    String href = playlists.get(i).getHref();
                    List<Image> images = playlists.get(i).getImages();
                    String playlistId = playlists.get(i).getId();
                    Item item = new Item(images.get(images.size() - 1), name, description, href,playlistId);
                    newPlaylists.add(item);
                }
                updateNewPlaylist(newPlaylists);
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


    private void stopAutoScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = layoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollBanner() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == Integer.MAX_VALUE) {
                        position = Integer.MAX_VALUE / 2;
                        recyclerView_Made_For_You.scrollToPosition(position);
                        recyclerView_Made_For_You.smoothScrollBy(5, 0);
                        recyclerView_newAlbums.scrollToPosition(position);
                        recyclerView_newAlbums.smoothScrollBy(5, 0);
                        recyclerView_Featured_Playlist.scrollToPosition(position);
                        recyclerView_Featured_Playlist.smoothScrollBy(5, 0);
                    } else {
                        position++;
                        recyclerView_Made_For_You.smoothScrollToPosition(position);
                        recyclerView_newAlbums.smoothScrollToPosition(position);
                        recyclerView_Featured_Playlist.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }

    @Override
    public void onGalleryClick(View view, int position) {
        Intent i = new Intent(getActivity(), PlaylistActivity.class);
        i.putExtra("AccessToken", dataViewModel.getAccessToken());
        i.putExtra("PlaylistId", newPlaylists.get(position).getPlaylistId());
        i.putExtra("playlistName", newPlaylists.get(position).getName());
        i.putExtra("playlistDescription", newPlaylists.get(position).getDescription());
        i.putExtra("imageHref",newPlaylists.get(position).getCoverUrl());
        startActivity(i);
    }


    @Override
    public void onCustomListClick(View view, int position) {
        Intent i = new Intent(getActivity(), PlaylistActivity.class);
        i.putExtra("AccessToken", dataViewModel.getAccessToken());
        i.putExtra("PlaylistId", playlistsItems.get(position).getPlaylistId());
        i.putExtra("playlistName", playlistsItems.get(position).getName());
        i.putExtra("playlistDescription", playlistsItems.get(position).getDescription());
        i.putExtra("imageHref",playlistsItems.get(position).getCoverUrl());
        startActivity(i);
    }
}


