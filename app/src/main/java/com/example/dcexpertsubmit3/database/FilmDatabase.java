package com.example.dcexpertsubmit3.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.model.Tvshow;

/**
 * Dibuat oleh petersam
 */
@Database(entities =
        {Movie.class, Tvshow.class},
        version = 1,
        exportSchema = false)
public abstract class FilmDatabase extends RoomDatabase {
    public abstract DataAccessObject dao();
}
