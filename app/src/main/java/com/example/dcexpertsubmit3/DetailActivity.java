package com.example.dcexpertsubmit3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.model.Tvshow;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_STRING = "extra_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.detail_title);
        TextView date = findViewById(R.id.detail_date);
        TextView language = findViewById(R.id.detail_language);
        TextView vote = findViewById(R.id.detail_vote);
        TextView popularity = findViewById(R.id.detail_popularity);
        TextView overview = findViewById(R.id.detail_overview);
        ImageView poster = findViewById(R.id.detail_poster);

        String string = getIntent().getStringExtra(EXTRA_STRING);
        if (string != null) {
            if (string.equals("movie")){
                Movie movie = getIntent().getParcelableExtra(EXTRA_DATA);
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
                Tvshow tvshow = getIntent().getParcelableExtra(EXTRA_DATA);
                if (tvshow != null){
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
}
