package com.example.dcexpertsubmit3.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.adapter.ItemListAdapter;
import com.example.dcexpertsubmit3.connectivity.Connectivity;
import com.example.dcexpertsubmit3.model.Tvshow;
import com.example.dcexpertsubmit3.view_model.TvshowViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {
    private TvshowViewModel tvshowViewModel;
    private ItemListAdapter adapter;
    private ArrayList<Tvshow> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        progressBar = view.findViewById(R.id.progressBar);
        setHasOptionsMenu(true);

        loadData();
        showData(list);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                loadSearchData(query);
                showData(list);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void loadData(){
        showLoading(true);
        tvshowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvshowViewModel.class);
        tvshowViewModel.setTvshow(Connectivity.ENDPOINT_TVSHOW);
        tvshowViewModel.getTvshow().observe(this, new Observer<ArrayList<Tvshow>>() {
            @Override
            public void onChanged(ArrayList<Tvshow> tvshows) {
                adapter.setDataTvshow(tvshows);
                showLoading(false);
            }
        });
    }

    private void showData(ArrayList<Tvshow> arrayList){
        adapter = new ItemListAdapter(getContext(), null, arrayList);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void loadSearchData(String query){
        showLoading(true);
        tvshowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvshowViewModel.class);
        tvshowViewModel.setTvshow(Connectivity.ENDPOINT_SEARCH_TVSHOW + query);
        tvshowViewModel.getTvshow().observe(this, new Observer<ArrayList<Tvshow>>() {
            @Override
            public void onChanged(ArrayList<Tvshow> tvshows) {
                adapter.setDataTvshow(tvshows);
                showLoading(false);
            }
        });
    }
}
