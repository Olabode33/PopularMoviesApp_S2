package com.olabode33.android.popularmoviesapp.model;

/**
 * Created by obello004 on 9/25/2018.
 */

public class MovieVideo {
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVideoName() {
        return name;
    }

    public void setVideoName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String movieId;
    String videoId;
    String key;
    String name;
    String site;
    String size;
    String type;

    public MovieVideo(){

    }

    public MovieVideo(String movie_id, String video_id, String video_key, String video_name, String video_site, String video_size, String video_type){
        this.movieId = movie_id;
        this.videoId = video_id;
        this.key = video_key;
        this.name = video_name;
        this.site = video_site;
        this.size = video_size;
        this.type = video_type;
    }
}
