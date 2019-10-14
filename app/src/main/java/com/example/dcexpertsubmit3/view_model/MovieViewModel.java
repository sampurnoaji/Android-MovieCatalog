package com.example.dcexpertsubmit3.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dcexpertsubmit3.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

/**
 * Dibuat oleh petersam
 */
public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public void setMovie(){
        String API_KEY = "959de899402215489c0c8210dd65d0e2";
        final ArrayList<Movie> listMovieItem = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY +"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray responseArray = responseObject.getJSONArray("results");

                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject object = responseArray.getJSONObject(i);

                        Movie movie = new Movie();
                        movie.setId(object.getInt("id"));
                        movie.setTitle(object.getString("title"));
                        movie.setRelease_date(object.getString("release_date"));
                        movie.setOverview(object.getString("overview"));
                        movie.setPoster_path("https://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
                        movie.setOriginal_language(object.getString("original_language"));
                        movie.setVote_average(object.getDouble("vote_average"));
                        movie.setPopularity(object.getDouble("popularity"));

                        listMovieItem.add(i, movie);
                    }
                    listMovie.postValue(listMovieItem);
                } catch (Exception e){
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovie(){
        return listMovie;
    }
}
