package com.example.dcexpertsubmit3.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dcexpertsubmit3.FavoriteActivity;
import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.model.Tvshow;

import java.util.ArrayList;

import static com.example.dcexpertsubmit3.database.AppController.database;

/**
 * Dibuat oleh petersam
 */
public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private ArrayList<Tvshow> listTvshow;

    public FavListAdapter(Context context, ArrayList<Movie> listMovie, ArrayList<Tvshow> listTvshow) {
        this.context = context;
        this.listMovie = listMovie;
        this.listTvshow = listTvshow;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (listMovie != null) {
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
                    showAlertDialog(movie, null);
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
                    showAlertDialog(null, tvshow);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
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

    private void showAlertDialog(final Movie movie, final Tvshow tvshow) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.delete));
        dialog
                .setPositiveButton(context.getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listMovie != null) {
                                    database.dao().deleteFavMovie(movie.getId());
                                    context.sendBroadcast(new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE));
                                    Toast.makeText(context, context.getString(R.string.message_delete_movie), Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, FavoriteActivity.class));
                                    ((Activity) context).finish();
                                } else {
                                    database.dao().deleteFavTvshow(tvshow.getId());
                                    Toast.makeText(context, context.getString(R.string.message_delete_tvshow), Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, FavoriteActivity.class));
                                    ((Activity) context).finish();
                                }
                            }
                        })
                .setNegativeButton(context.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
        dialog.create().show();
    }
}
