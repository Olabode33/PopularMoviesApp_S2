package com.olabode33.android.popularmoviesapp.utils;

import android.util.Log;

import com.olabode33.android.popularmoviesapp.model.Movie;
import com.olabode33.android.popularmoviesapp.model.MovieReview;
import com.olabode33.android.popularmoviesapp.model.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by obello004 on 8/21/2018.
 */

public class JsonUtils {
    //Movie Json Key
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

    //Movie Reviews JSON Keys
    public static final String JSON_MOVIE_REVIEW_KEY_AUTHOR = "author";
    public static final String JSON_MOVIE_REVIEW_KEY_CONTENT = "content";
    public static final String JSON_MOVIE_REVIEW_KEY_URL = "url";

    //Movie Videos JSON Keys
    public static final String JSON_MOVIE_VIDEO_KEY_KEY = "key";
    public static final String JSON_MOVIE_VIDEO_KEY_NAME = "name";
    public static final String JSON_MOVIE_VIDEO_KEY_SITE = "site";
    public static final String JSON_MOVIE_VIDEO_KEY_SIZE = "size";
    public static final String JSON_MOVIE_VIDEO_KEY_TYPE = "type";


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

    public static MovieReview parseMovieReviewJson(JSONObject jsonMovieReview, String movie_id){
        String review_author = jsonMovieReview.optString(JSON_MOVIE_REVIEW_KEY_AUTHOR);
        String review_content = jsonMovieReview.optString(JSON_MOVIE_REVIEW_KEY_CONTENT);
        String review_id = jsonMovieReview.optString(JSON_MOVIE_KEY_ID);
        String review_url = jsonMovieReview.optString(JSON_MOVIE_REVIEW_KEY_URL);

        //Create new MovieReview object
        MovieReview movieReview = new MovieReview(movie_id, review_author, review_content, review_id, review_url);
        return  movieReview;
    }

    public static MovieVideo parseMovieVideoJson(JSONObject jsonMovieVideo, String movie_id){
        String video_id = jsonMovieVideo.optString(JSON_MOVIE_KEY_ID);
        String video_key = jsonMovieVideo.optString(JSON_MOVIE_VIDEO_KEY_KEY);
        String video_name = jsonMovieVideo.optString(JSON_MOVIE_VIDEO_KEY_NAME);
        String video_site = jsonMovieVideo.optString(JSON_MOVIE_VIDEO_KEY_SITE);
        String video_size = jsonMovieVideo.optString(JSON_MOVIE_VIDEO_KEY_SIZE);
        String video_type = jsonMovieVideo.optString(JSON_MOVIE_VIDEO_KEY_TYPE);

        //Create new MovieVideo object
        MovieVideo movieVideo = new MovieVideo(movie_id, video_id, video_key, video_name, video_site, video_size, video_type);
        return  movieVideo;
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

    public static List<MovieReview> parseMovieReviewsListJson(String json){
        List<MovieReview> movieReviewList = new ArrayList<MovieReview>();

        try{
            JSONObject jsonMovieReviewObject = new JSONObject(json);
            String movie_id = jsonMovieReviewObject.optString(JSON_MOVIE_KEY_ID);
            JSONArray jsonMovieReviewArray = jsonMovieReviewObject.getJSONArray(JSON_MOVIE_KEY_RESULTS);

            if(jsonMovieReviewArray != null){
                for(int i = 0; i<jsonMovieReviewArray.length(); i++){
                    JSONObject jsonMovieReviewObj = jsonMovieReviewArray.getJSONObject(i);
                    MovieReview movieReviewOjb = parseMovieReviewJson(jsonMovieReviewObj, movie_id);

                    movieReviewList.add(movieReviewOjb);
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return movieReviewList;
    }

    public static List<MovieVideo> parseMovieVideoListJson(String json){
        List<MovieVideo> movieVideoList = new ArrayList<MovieVideo>();

        try{
            JSONObject jsonMovieVideoObject = new JSONObject(json);
            String movie_id = jsonMovieVideoObject.optString(JSON_MOVIE_KEY_ID);
            JSONArray jsonMovieVideoArray = jsonMovieVideoObject.getJSONArray(JSON_MOVIE_KEY_RESULTS);

            if(jsonMovieVideoArray != null){
                for(int i = 0; i<jsonMovieVideoArray.length(); i++){
                    JSONObject jsonMovieVideoObj = jsonMovieVideoArray.getJSONObject(i);
                    MovieVideo movieVideoObj = parseMovieVideoJson(jsonMovieVideoObj, movie_id);

                    movieVideoList.add(movieVideoObj);
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return movieVideoList;
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
