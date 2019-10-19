package com.example.dcexpertsubmit3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dcexpertsubmit3.database.FilmDatabase;
import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.model.Tvshow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.dcexpertsubmit3.database.AppController.database;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_STRING = "extra_string";

    private Movie movie;
    private Tvshow tvshow;

    private ArrayList<Movie> listMovie = new ArrayList<>();
    private ArrayList<Tvshow> listTvshow = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getString(R.string.title_detail));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.detail_title);
        TextView date = findViewById(R.id.detail_date);
        TextView language = findViewById(R.id.detail_language);
        TextView vote = findViewById(R.id.detail_vote);
        TextView popularity = findViewById(R.id.detail_popularity);
        TextView overview = findViewById(R.id.detail_overview);
        ImageView poster = findViewById(R.id.detail_poster);

        FloatingActionButton fab = findViewById(R.id.fab_favorite);
        fab.setOnClickListener(this);

        String string = getIntent().getStringExtra(EXTRA_STRING);
        if (string != null) {
            if (string.equals("movie")) {
                movie = getIntent().getParcelableExtra(EXTRA_DATA);
                if (movie != null) {
                    title.setText(movie.getTitle());
                    date.setText(movie.getRelease_date());
                    language.setText(movie.getOriginal_language());
                    vote.setText(String.valueOf(movie.getVote_average()));
                    popularity.setText(String.valueOf(movie.getPopularity()));
                    overview.setText(movie.getOverview());
                    Glide.with(this).load(movie.getPoster_path()).into(poster);
                }
            } else {
                tvshow = getIntent().getParcelableExtra(EXTRA_DATA);
                if (tvshow != null) {
                    title.setText(tvshow.getName());
                    date.setText(tvshow.getFirst_air_date());
                    language.setText(tvshow.getOriginal_language());
                    vote.setText(String.valueOf(tvshow.getVote_average()));
                    popularity.setText(String.valueOf(tvshow.getPopularity()));
                    overview.setText(tvshow.getOverview());
                    Glide.with(this).load(tvshow.getPoster_path()).into(poster);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_favorite) {
            Intent intent = new Intent(DetailActivity.this, FavoriteActivity.class);

            if (movie != null) {
                boolean flag = true;
                if (listMovie.size() == 0) {
                    database.dao().insertFavMovie(movie);
                    Toast.makeText(this, getString(R.string.message_add_movie), Toast.LENGTH_SHORT).show();
                    Log.e("tag 1:", "MOVIE INSERT");
                } else {
                    for (int i = 0; i < listMovie.size(); i++) {
                        if (movie.getId() == listMovie.get(i).getId()) {
                            Toast.makeText(this, getString(R.string.message_add_movie_exist), Toast.LENGTH_SHORT).show();
                            Log.e("tag 2:", "MOVIE EXIST");

                            flag = false;
                        }
                    }
                    if (flag){
                        database.dao().insertFavMovie(movie);
                        Toast.makeText(this, getString(R.string.message_add_movie), Toast.LENGTH_SHORT).show();
                        Log.e("tag 3:", "MOVIE INSERT");
                    }
                }
            }
            else {
                boolean flag = true;
                if (listTvshow.size() == 0) {
                    database.dao().insertFavTvshow(tvshow);
                    Toast.makeText(this, getString(R.string.message_add_tvshow), Toast.LENGTH_SHORT).show();
                    Log.e("tag 1:", "TV INSERT");
                } else {
                    for (int i = 0; i < listTvshow.size(); i++) {
                        if (tvshow.getId() == listTvshow.get(i).getId()) {
                            Toast.makeText(this, getString(R.string.message_add_tvshow_exist), Toast.LENGTH_SHORT).show();
                            Log.e("tag 2:", "TV EXIST");

                            flag = false;
                        }
                    }
                    if (flag){
                        database.dao().insertFavTvshow(tvshow);
                        Toast.makeText(this, getString(R.string.message_add_tvshow), Toast.LENGTH_SHORT).show();
                        Log.e("tag 3:", "TV INSERT");
                    }
                }
                intent.putExtra(EXTRA_STRING, EXTRA_STRING);
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = Room.databaseBuilder(this, FilmDatabase.class, "film_database")
                .allowMainThreadQueries()
                .build();
        listMovie = (ArrayList<Movie>) database.dao().getFavMovie();
        listTvshow = (ArrayList<Tvshow>) database.dao().getFavTvshow();
    }
}
