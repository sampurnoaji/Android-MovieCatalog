package com.example.dcexpertsubmit3.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.adapter.ItemListAdapter;
import com.example.dcexpertsubmit3.model.Tvshow;
import com.example.dcexpertsubmit3.view_model.TvshowViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {
    private ItemListAdapter adapter;
    private ArrayList<Tvshow> list = new ArrayList<>();
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
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        progressBar = view.findViewById(R.id.progressBar);

        showLoading(true);
        TvshowViewModel tvshowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvshowViewModel.class);
        tvshowViewModel.setTvshow();
        tvshowViewModel.getTvshow().observe(this, new Observer<ArrayList<Tvshow>>() {
            @Override
            public void onChanged(ArrayList<Tvshow> tvshows) {
                adapter.setDataTvshow(tvshows);
                showLoading(false);
            }
        });

        adapter = new ItemListAdapter(getContext(), null, list);
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
}
