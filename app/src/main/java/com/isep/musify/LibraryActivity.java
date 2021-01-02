package com.isep.musify;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.isep.musify.ui.library.AlbumsFragment;
import com.isep.musify.ui.library.ArtistsFragment;
import com.isep.musify.ui.LibraryFragmentAdapter;
import com.isep.musify.ui.library.PlaylistsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    private String []sTitle = new String[]{"Playlists","Artists","Albums"};
    private ViewPager mViewPager;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_library);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PlaylistsFragment.newInstance());
        fragments.add(ArtistsFragment.newInstance());
        fragments.add(AlbumsFragment.newInstance());

        LibraryFragmentAdapter adapter = new LibraryFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(sTitle));
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TabActivity","select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
