package com.example.dcexpertsubmit3.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.example.dcexpertsubmit3.R;
import com.example.dcexpertsubmit3.database.FilmDatabase;
import com.example.dcexpertsubmit3.model.Movie;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.dcexpertsubmit3.database.AppController.database;

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgetItem = new ArrayList<>();
    private final Context context;

    WidgetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        database = Room.databaseBuilder(context, FilmDatabase.class, "film_database")
                .allowMainThreadQueries().build();
        ArrayList<Movie> listMovie = (ArrayList<Movie>) database.dao().getFavMovie();

        for (int i = 0; i < listMovie.size(); i++) {
            try {
                URL url = new URL(listMovie.get(i).getPoster_path());
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                widgetItem.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItem.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.imageView, widgetItem.get(position));

        Bundle bundle = new Bundle();
        bundle.putInt(FilmWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
