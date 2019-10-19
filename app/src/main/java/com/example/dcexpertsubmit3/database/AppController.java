package com.example.dcexpertsubmit3.database;

import android.app.Application;

import androidx.room.Room;

/**
 * Dibuat oleh petersam
 */
public class AppController extends Application {
    public static FilmDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), FilmDatabase.class, "film_database")
                .allowMainThreadQueries()
                .build();
    }
}
