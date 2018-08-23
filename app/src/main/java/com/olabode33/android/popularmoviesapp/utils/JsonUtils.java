package com.olabode33.android.popularmoviesapp.utils;

import android.util.Log;

import com.olabode33.android.popularmoviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by obello004 on 8/21/2018.
 */

public class JsonUtils {
    public static final String JSON_MOVIE_KEY_RESULTS = "results";
    public static final String JSON_MOVIE_KEY_ID = "id";
    public static final String JSON_MOVIE_KEY_VOTE_AVERAGE = "vote_average";
    public static final String JSON_MOVIE_KEY_TITLE = "title";
    public static final String JSON_MOVIE_KEY_POPULARITY = "popularity";
    public static final String JSON_MOVIE_KEY_POSTER_PATH = "poster_path";
    public static final String JSON_MOVIE_KEY_ORIGINAL_LANGUAGE = "original_language";
    public static final String JSON_MOVIE_KEY_ORIGINAL_TITLE = "original_title";
    public static final String JSON_MOVIE_KEY_BACKDROP_PATH = "backdrop_path";
    public static final String JSON_MOVIE_KEY_OVERVIEW = "overview";
    public static final String JSON_MOVIEW_KEY_RELEASE_DATE = "release_date";

    public static Movie parseMovieJson(JSONObject jsonMovie){
        String movie_id = jsonMovie.optString(JSON_MOVIE_KEY_ID);
        String vote_average = jsonMovie.optString(JSON_MOVIE_KEY_VOTE_AVERAGE);
        String movie_title = jsonMovie.optString(JSON_MOVIE_KEY_TITLE);
        String popularity = jsonMovie.optString(JSON_MOVIE_KEY_POPULARITY);
        String poster_path = jsonMovie.optString(JSON_MOVIE_KEY_POSTER_PATH);
        String original_language = jsonMovie.optString(JSON_MOVIE_KEY_ORIGINAL_LANGUAGE);
        String original_title = jsonMovie.optString(JSON_MOVIE_KEY_ORIGINAL_TITLE);
        String backdrop_path = jsonMovie.optString(JSON_MOVIE_KEY_BACKDROP_PATH);
        String movie_overview = jsonMovie.optString(JSON_MOVIE_KEY_OVERVIEW);
        String release_date = jsonMovie.optString(JSON_MOVIEW_KEY_RELEASE_DATE);

        //Create new movie object
        Movie movie = new Movie(movie_id, vote_average, movie_title, poster_path, original_language,
                                original_title, backdrop_path, movie_overview, release_date);

        return movie;
    }

    public static List<Movie> parseMoviesListJson(String json) {
        List<Movie> movieList = new ArrayList<Movie>();

        try {
            JSONObject jsonMoviesList = new JSONObject(json);
            JSONArray jsonMoviesArray = jsonMoviesList.getJSONArray(JSON_MOVIE_KEY_RESULTS);

            if(jsonMoviesArray != null){
                for (int i = 0; i < jsonMoviesArray.length(); i++){
                    JSONObject jsonMovieObj = jsonMoviesArray.getJSONObject(i);
                    Movie movieObj = parseMovieJson(jsonMovieObj);

                    movieList.add(movieObj);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    private static List<String> jsonArrayList(JSONArray jsonArray){
        List<String> stringList = new ArrayList<String>();

        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    stringList.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringList;
    }
}
