package com.olabode33.android.popularmoviesapp.model;

/**
 * Created by obello004 on 9/25/2018.
 */

public class MovieReview {

    String movieId;
    String author;
    String content;
    String reviewId;
    String url;

    public MovieReview(){

    }

    public MovieReview(String movie_id, String review_author, String review_content, String review_id, String review_url){
        this.movieId = movie_id;
        this.author = review_author;
        this.content = review_content;
        this.reviewId = review_id;
        this.url = review_url;
    }

    public void setMovieId(String movie_id){  this.movieId = movie_id;  }
    public String getMovieId(){ return this.movieId; }

    public void setAuthor(String review_author) {
        this.author = review_author;
    }
    public  String getAuthor(){
        return this.author;
    }

    public void setContent(String review_content){
        this.content = review_content;
    }
    public String getContent(){
        return this.content;
    }

    public void setReviewId(String review_id){
        this.reviewId = review_id;
    }
    public String getReviewId(){
        return this.reviewId;
    }

    public void setUrl(String review_url){
        this.url = review_url;
    }
    public String getUrl(){
        return this.url;
    }
}
