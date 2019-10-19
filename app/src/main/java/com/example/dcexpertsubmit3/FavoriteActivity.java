package com.example.dcexpertsubmit3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.dcexpertsubmit3.adapter.ViewPagerAdapter;
import com.example.dcexpertsubmit3.fragment.FavMovieFragment;
import com.example.dcexpertsubmit3.fragment.FavTvshowFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(getString(R.string.title_favorite));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setupTabLayout();
    }

    private void setupTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new FavMovieFragment(), getString(R.string.movies));
        adapter.addFragment(new FavTvshowFragment(), getString(R.string.tv_shows));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        String fromTvshow = getIntent().getStringExtra(DetailActivity.EXTRA_STRING);
        if (fromTvshow != null){
            Objects.requireNonNull(tabLayout.getTabAt(1)).select();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
