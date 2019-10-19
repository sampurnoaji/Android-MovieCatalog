package com.example.dcexpertsubmit3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.adapter.FavListAdapter;
import com.example.dcexpertsubmit3.database.FilmDatabase;
import com.example.dcexpertsubmit3.model.Tvshow;

import java.util.ArrayList;

import static com.example.dcexpertsubmit3.database.AppController.database;

/**
 * Dibuat oleh petersam
 */
public class FavTvshowFragment extends Fragment {
    private FavListAdapter adapter;
    private ArrayList<Tvshow> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv);

        database = Room.databaseBuilder(getContext(), FilmDatabase.class, "film_database")
                .allowMainThreadQueries()
                .build();
        list = (ArrayList<Tvshow>) database.dao().getFavTvshow();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavListAdapter(getContext(), null, list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
