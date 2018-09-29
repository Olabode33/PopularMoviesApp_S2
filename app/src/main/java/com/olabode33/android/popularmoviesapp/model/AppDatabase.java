package com.olabode33.android.popularmoviesapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.olabode33.android.popularmoviesapp.dao.MovieModelDao;

/**
 * Created by obello004 on 9/28/2018.
 */

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "FavoriteMovies_Db")
                    .build();
        }

        return INSTANCE;
    }

    public abstract MovieModelDao favoriteMovieDao();
}
