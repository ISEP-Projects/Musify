package com.isep.musify.ui.library;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.isep.musify.R;
import com.isep.musify.ui.LibraryFragmentAdapter;
import com.isep.musify.ui.TracksAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LibraryFragment extends Fragment {
    private String []sTitle = new String[]{"Playlists","Artists","Albums"};
    private TabLayout mTabLayout;
    //private RecyclerView recyclerView;
    private TextView textView;
    private TracksAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_library, container, false);
        //final TextView textView = root.findViewById(R.id.text_library);
        mTabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));

        //recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);
        textView = root.findViewById(R.id.textView);
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
}