package com.example.dcexpertsubmit3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dcexpertsubmit3.model.Movie;
import com.example.dcexpertsubmit3.model.Tvshow;

import java.util.List;

/**
 * Dibuat oleh petersam
 */
@Dao
public interface DataAccessObject {
    @Insert void insertFavMovie(Movie movie);
    @Query("SELECT * FROM Movie") List<Movie> getFavMovie();
    @Query("DELETE FROM Movie WHERE id LIKE :id") void deleteFavMovie(int id);

    @Insert void insertFavTvshow(Tvshow tvshow);
    @Query("SELECT * FROM Tvshow") List<Tvshow> getFavTvshow();
    @Query("DELETE FROM Tvshow WHERE id LIKE :id") void deleteFavTvshow(int id);
}
