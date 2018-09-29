package com.olabode33.android.popularmoviesapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created by obello004 on 8/21/2018.
 */

@Parcel
@Entity(tableName = "FavoriteMovies")
public class Movie {
    @PrimaryKey @NonNull
    public String id;
    String voteAverage;
    String title;
    String posterPath;
    String originalLanguage;
    String originalTitle;
    String backdropPath;
    String overview;
    String releaseDate;

    final static String THEMOVIEDB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    final static String POSTER_IMAGE_PATH = "w342";
    final static String BACKDROP_IMAGE_PATH = "w780";

    public Movie(){

    }

    public Movie(String movie_id, String vote_average, String movie_title, String poster_path, String original_language,
                 String original_title, String backdrop_path, String movie_overview, String release_date){
        this.id = movie_id;
        this.voteAverage = vote_average;
        this.title = movie_title;
        this.posterPath = poster_path;
        this.originalLanguage = original_language;
        this.originalTitle = original_title;
        this.backdropPath = backdrop_path;
        this.overview = movie_overview;
        this.releaseDate = release_date;
    }

    public void setMovieId(String movie_id){
        this.id = movie_id;
    }
    public String getMovieId(){
        return id;
    }

    public void setVoteAverage(String vote_average){
        this.voteAverage = vote_average;
    }
    public String getVoteAverage(){
        return voteAverage;
    }

    public void setTitle(String movie_title){
        this.title = movie_title;
    }
    public String getTitle(){
        return title;
    }

    public void setPosterPath(String poster_path){
        posterPath = poster_path;
    }
    public String getPosterPath(){
        return this.posterPath;
    }

    public void setOriginalLanguage(String original_language){
        originalLanguage = original_language;
    }
    public String getOriginalLanguage(){
        return originalLanguage;
    }

    public void setOriginalTitle(String original_title){
        originalTitle = original_title;
    }
    public String getOriginalTitle(){
        return originalTitle;
    }

    public void setBackdropPath(String backdrop_path){
        backdropPath = backdrop_path;
    }
    public String getBackdropPath(){
        return this.backdropPath;
    }

    public  void setOverview(String movie_overview){
        overview = movie_overview;
    }
    public String getOverview(){
        return overview;
    }

    public void setReleaseDate(String release_date){
        releaseDate = release_date;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

    @Ignore
    public String getPosterImageUrl(){
        return THEMOVIEDB_BASE_IMAGE_URL + POSTER_IMAGE_PATH + posterPath;
    }

    @Ignore
    public String getBackdropImageUrl(){
        return THEMOVIEDB_BASE_IMAGE_URL + BACKDROP_IMAGE_PATH + backdropPath;
    }
}
