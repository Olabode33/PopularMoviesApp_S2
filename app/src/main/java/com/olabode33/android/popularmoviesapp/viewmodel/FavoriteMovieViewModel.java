package com.olabode33.android.popularmoviesapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import com.olabode33.android.popularmoviesapp.model.AppDatabase;
import com.olabode33.android.popularmoviesapp.model.Movie;

import java.util.List;

/**
 * Created by obello004 on 9/28/2018.
 */

public class FavoriteMovieViewModel extends AndroidViewModel {
    private final LiveData<List<Movie>> favoriteMovieList;
    private AppDatabase appDatabase;

    public FavoriteMovieViewModel(Application application){
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        favoriteMovieList = appDatabase.favoriteMovieDao().getAllFavouriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovieList(){
        return favoriteMovieList;
    }

    public void deleteFavoriteMovie(Movie movie){
        new deleteAsyncTask(appDatabase).execute(movie);
    }

    public void addFavoriteMovie(Movie movie){
        new addAsyncTask(appDatabase).execute(movie);
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void>{
        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase){
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            db.favoriteMovieDao().deleteMovie(movies[0]);
            return null;
        }
    }

    private static class addAsyncTask extends AsyncTask<Movie, Void, Void>{
        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase){
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            db.favoriteMovieDao().addMovie(movies[0]);
            return null;
        }
    }
}
