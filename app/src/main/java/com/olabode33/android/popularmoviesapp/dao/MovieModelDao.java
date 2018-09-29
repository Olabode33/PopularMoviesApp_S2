package com.olabode33.android.popularmoviesapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.olabode33.android.popularmoviesapp.model.Movie;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by obello004 on 9/28/2018.
 */
@Dao
public interface MovieModelDao {

    @Query("Select * from FavoriteMovies")
    LiveData<List<Movie>> getAllFavouriteMovies();

    @Query("Select * from FavoriteMovies where id = :id")
    LiveData<Movie> getMovieById(String id);

    @Insert(onConflict = REPLACE)
    void addMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
