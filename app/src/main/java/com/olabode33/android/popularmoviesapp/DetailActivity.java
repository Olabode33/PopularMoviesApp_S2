package com.olabode33.android.popularmoviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.olabode33.android.popularmoviesapp.model.Movie;
import com.olabode33.android.popularmoviesapp.utils.JsonUtils;
import com.olabode33.android.popularmoviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "movie_id";
    private static final int DEFAULT_POSITION = -1;
    private Movie mMovie;

    private String movieID = "";

    @BindView(R.id.iv_movie_backdrop) ImageView mBackDropImageView;
    @BindView(R.id.iv_movie_poster) ImageView mPosterImageView;
    @BindView(R.id.tv_movie_title) TextView mTitleTextView;
    @BindView(R.id.tv_user_rating) TextView mUserRatingTextView;
    @BindView(R.id.tv_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_overview) TextView mOverviewTextView;
    @BindView(R.id.pd_loading_movie_details) ProgressBar mLoadingMovieDetails;

    @BindView(R.id.tv_overview_label) TextView mOverviewLabelTextView;
    @BindView(R.id.tv_user_rating_label) TextView mRatingLabelTextView;
    @BindView(R.id.tv_release_date_label) TextView mReleaseDateLabelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_MOVIE_ID)){
            movieID = intent.getStringExtra(EXTRA_MOVIE_ID);
            makeApiCall();
        }
    }

    private void makeApiCall(){
        URL url = NetworkUtils.buildUrl(this, movieID);
        if(NetworkUtils.isOnline(this)){
            new MovieDetailsApiTask().execute(url);
        }
        else{
            Toast.makeText(this, getString(R.string.error_no_network), Toast.LENGTH_LONG);
            hideProgressBar();
        }

    }

    private void populateUI(){
        setTitle(mMovie.getOriginalTitle());

        Picasso.with(this)
                .load(mMovie.getBackdropPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(mBackDropImageView);

        Picasso.with(this)
                .load(mMovie.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(mPosterImageView);

        mTitleTextView.setText(mMovie.getTitle());
        mUserRatingTextView.setText(mMovie.getVoteAverage());
        mReleaseDateTextView.setText(mMovie.getReleaseDate());
        mOverviewTextView.setText(mMovie.getOverview());

        mOverviewLabelTextView.setText(getString(R.string.detail_overview_label));
        mRatingLabelTextView.setText(getString(R.string.detail_user_rating_label));
        mReleaseDateLabelTextView.setText(getString(R.string.detail_release_date_label));
        hideProgressBar();
    }

    private void hideProgressBar(){
        if(mLoadingMovieDetails != null && mLoadingMovieDetails.isShown()){
            mLoadingMovieDetails.setVisibility(View.INVISIBLE);
        }
    }

    private class MovieDetailsApiTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String apiResult = null;

            try {
                apiResult = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return apiResult;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("API_RESULT", s.toString());

            try {
                JSONObject taskResult = new JSONObject(s);
                mMovie = JsonUtils.parseMovieJson(taskResult);
                populateUI();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
