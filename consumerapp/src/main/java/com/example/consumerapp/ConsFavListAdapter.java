package com.example.consumerapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ConsFavListAdapter extends RecyclerView.Adapter<ConsFavListAdapter.MyViewHolder> {
    private Cursor cursor;
    private Context context;

    ConsFavListAdapter(Context context) {
        this.context = context;
    }

    void setData(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConsFavListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsFavListAdapter.MyViewHolder holder, int position) {
        holder.bind(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
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

        void bind(boolean moveToPosition) {
            if (moveToPosition) {
                title.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                overview.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_OVERVIEW)));
                Glide.with(context).load(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER))).into(poster);
            }
        }
    }
}
