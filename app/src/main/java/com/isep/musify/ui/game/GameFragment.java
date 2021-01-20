package com.isep.musify.ui.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.isep.musify.CustomCallbackSuccess;
import com.isep.musify.MainActivity;
import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.models.ApiResponse;
import com.isep.musify.models.ApiResponseNewAlbums;
import com.isep.musify.models.Artist;
import com.isep.musify.models.Profile;
import com.isep.musify.ui.DataViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameFragment extends Fragment implements View.OnClickListener {

    private GameViewModel gameViewModel;
    private DataViewModel dataViewModel;
    private String accessToken;
    private List<Artist> artistsList;

    private TextView leftName, rightName, counter, score;
    private ImageView leftImage, rightImage;
    private Button btnLeft, btnRight;

    private int counterValue = 10, scoreValue = 0;
    private int popularityLeft, popularityRight;    //Save artist's popularity to compare
    AlertDialog.Builder builder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                new ViewModelProvider(this).get(GameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_game, container, false);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        accessToken = dataViewModel.getAccessToken();

        leftName = root.findViewById(R.id.leftArtistName);
        rightName = root.findViewById(R.id.rightArtistName);
        counter = root.findViewById(R.id.counter);
        score = root.findViewById(R.id.score);
        leftImage = root.findViewById(R.id.imageLeft);
        rightImage = root.findViewById(R.id.imageRight);
        btnLeft = root.findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(this);
        btnRight = root.findViewById(R.id.btnRight);
        btnRight.setOnClickListener(this);

        artistsList = new ArrayList<>();
        generateList();

        return root;
    }


    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        //Log.d("Musify", "Button clicked " + btn.getText());

        if (btn.getText().toString().equals("LEFT")) {
            Log.d("Musify", "Left");
            if (popularityLeft >= popularityRight) {
                Toast.makeText(getContext().getApplicationContext(), "Correct Answer!", Toast.LENGTH_SHORT).show();
                scoreValue = Integer.parseInt(String.valueOf(score.getText()));
                scoreValue++;
                score.setText(String.valueOf(scoreValue));
            } else {
                Toast.makeText(getContext().getApplicationContext(), "Incorrect Answer!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (popularityRight >= popularityLeft) {
                Toast.makeText(getContext().getApplicationContext(), "Correct Answer!", Toast.LENGTH_SHORT).show();
                scoreValue = Integer.parseInt(String.valueOf(score.getText()));
                scoreValue++;
                score.setText(String.valueOf(scoreValue));
            } else {
                Toast.makeText(getContext().getApplicationContext(), "Incorrect Answer!", Toast.LENGTH_SHORT).show();
            }
        }
        Log.d("Musify", "Counter before decrement = " + String.valueOf(counterValue));
        counterValue--;
        counter.setText(String.valueOf(counterValue));
        if (counterValue == 0) {
            Toast.makeText(getContext().getApplicationContext(), "Game Over!\nYour Score is " + scoreValue, Toast.LENGTH_LONG).show();
            promptAlertDialog();
        } else {
            loadArtists();
        }
    }

    public void generateList() {
        //REF: https://www.techiedelight.com/convert-string-list-characters-java/
        String letters = "abcdefghijklmnopqrstuvwxyz";
        List<Character> characterList = letters.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList());
        Collections.shuffle(characterList);

        Random ran = new Random();
        for (int i = 0; i < 10; i++) {
            int index = ran.nextInt(characterList.size() - 1);
            //Search for artists with a random character
            getArtists(characterList.get(index));
            //Remove character from options afterwards to prevent repetition
            characterList.remove(index);
        }
    }

    public void getArtists(char c) {
        //Random rnd = new Random();
        //char c = (char) (rnd.nextInt(26) + 'a');
        Log.d("Musify", "Request random artist search with char = " + c);
        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.getRandomArtists(accessToken, c, new CustomCallbackSuccess() {
            @Override
            public void onSuccess(ApiResponse value) {

                List<Artist> tempList = value.getArtistsList().getArtists();

                //Add retrieved artists to list
                for (int i = 0; i < tempList.size(); i++) {
                    //Ensure no duplicates
                    if (!artistsList.contains(tempList.get(i))) {
                        artistsList.add(tempList.get(i));
                    }
                }
                //Shuffle list after each addition
                Collections.shuffle(artistsList, new Random());
                Log.d("Musify", "ArtistsList Size " + artistsList.size());

                //Load artists when half the required artists are fetched from the api
                //Checks for if popularityLeft initialized to prevent loading multiple times
                //Ensure results are random and app doesn't have to wait for all the artists to be retrieved
                if (artistsList.size() >= 10 && popularityLeft == 0) {
                    loadArtists();
                }
            }



            @Override
            public void onFailure() {
                Log.d("Musify", "Error fetching tracks from api");
                //Toast.makeText(this, "Error fetching tracks", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadArtists() {
        int turn = 10 - counterValue;
        Log.d("Musify", "Loading data for turn " + turn + " counter = " + counterValue);
        Artist left = artistsList.get(0);
        Log.d("Musify", artistsList.size() + " | " + turn);
        Artist right = artistsList.get(1);

        popularityLeft = left.getPopularity();
        popularityRight = right.getPopularity();

        leftName.setText(left.getName());
        Picasso.get()
                .load(left.getImages().get(0).getUrl())
                .into(leftImage);

        rightName.setText(right.getName());
        Picasso.get()
                .load(right.getImages().get(0).getUrl())
                .into(rightImage);

        //Remove artists after displaying them to avoid repetition
        artistsList.remove(left);
        artistsList.remove(right);
    }

    public void promptAlertDialog() {
        builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Do you want to play again ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().recreate();
                        Toast.makeText(getContext(), "New Game. Good Luck!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(getContext(), MainActivity.class);
                        i.putExtra("AccessToken", accessToken);
                        startActivity(i);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("GAME OVER!");
        alert.show();
    }
}