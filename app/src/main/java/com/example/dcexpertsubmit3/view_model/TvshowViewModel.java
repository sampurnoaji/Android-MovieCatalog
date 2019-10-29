package com.example.dcexpertsubmit3.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dcexpertsubmit3.BuildConfig;
import com.example.dcexpertsubmit3.model.Tvshow;
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
public class TvshowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Tvshow>> listTvshow = new MutableLiveData<>();

    public void setTvshow(String url){
        final ArrayList<Tvshow> listTvshowItem = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray responseArray = responseObject.getJSONArray("results");

                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject object = responseArray.getJSONObject(i);

                        Tvshow tvshow = new Tvshow();
                        tvshow.setId(object.getInt("id"));
                        tvshow.setName(object.getString("name"));
                        tvshow.setFirst_air_date(object.getString("first_air_date"));
                        tvshow.setOverview(object.getString("overview"));
                        tvshow.setPoster_path("https://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
                        tvshow.setOriginal_language(object.getString("original_language"));
                        tvshow.setVote_average(object.getDouble("vote_average"));
                        tvshow.setPopularity(object.getDouble("popularity"));

                        listTvshowItem.add(i, tvshow);
                    }
                    listTvshow.postValue(listTvshowItem);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<Tvshow>> getTvshow(){
        return listTvshow;
    }
}
