package com.example.dcexpertsubmit3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dcexpertsubmit3.DetailActivity;
import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.model.Tvshow;

import java.util.ArrayList;

/**
 * Dibuat oleh petersam
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private ArrayList<Tvshow> listTvshow;

    public ItemListAdapter(Context context, ArrayList<Movie> listMovie, ArrayList<Tvshow> listTvshow) {
        this.context = context;
        this.listMovie = listMovie;
        this.listTvshow = listTvshow;
    }

    public void setDataMovie(ArrayList<Movie> movie){
        listMovie.clear();
        listMovie.addAll(movie);
        notifyDataSetChanged();
    }

    public void setDataTvshow(ArrayList<Tvshow> tvshow){
        listTvshow.clear();
        listTvshow.addAll(tvshow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (listMovie != null){
            final Movie movie = listMovie.get(position);
            holder.title.setText(movie.getTitle());
            holder.date.setText(movie.getRelease_date());
            holder.overview.setText(movie.getOverview());
            Glide.with(context)
                    .load(movie.getPoster_path())
                    .into(holder.poster);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentMovie = new Intent(context, DetailActivity.class);
                    intentMovie.putExtra(DetailActivity.EXTRA_DATA, movie);
                    intentMovie.putExtra(DetailActivity.EXTRA_STRING, "movie");
                    context.startActivity(intentMovie);
                }
            });
        } else {
            final Tvshow tvshow = listTvshow.get(position);
            holder.title.setText(tvshow.getName());
            holder.date.setText(tvshow.getFirst_air_date());
            holder.overview.setText(tvshow.getOverview());
            Glide.with(context)
                    .load(tvshow.getPoster_path())
                    .into(holder.poster);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentTvshow = new Intent(context, DetailActivity.class);
                    intentTvshow.putExtra(DetailActivity.EXTRA_DATA, tvshow);
                    intentTvshow.putExtra(DetailActivity.EXTRA_STRING, "tvshow");
                    context.startActivity(intentTvshow);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listMovie != null){
            return listMovie.size();
        } else {
            return listTvshow.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, date, overview;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.list_poster);
            title = itemView.findViewById(R.id.list_title);
            date = itemView.findViewById(R.id.list_date);
            overview = itemView.findViewById(R.id.list_overview);
        }
    }
}
