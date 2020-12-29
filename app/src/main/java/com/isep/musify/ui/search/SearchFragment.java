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

import com.isep.musify.R;
import com.isep.musify.RetrofitAPIConnection;
import com.isep.musify.ui.DataViewModel;


public class SearchFragment extends Fragment {

    TextView textView;
    EditText searchInput;
    Button button;
    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        textView = root.findViewById(R.id.text_search);
        //Log.d("Musify" , "Access Token received in Search Fragment: " + dataViewModel.getAccessToken());
        searchInput = root.findViewById(R.id.searchInput);
        button = root.findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String input = String.valueOf(searchInput.getText());
                Toast.makeText(getContext().getApplicationContext(), "Text entered: " + input, Toast.LENGTH_LONG).show();
                searchSpotifyAPI();
            }
        });

        return root;
    }


    public void searchSpotifyAPI() {

        RetrofitAPIConnection apiConnection = new RetrofitAPIConnection();
        apiConnection.start(dataViewModel.getAccessToken());

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
    }


}