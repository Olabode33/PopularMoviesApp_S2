package com.olabode33.android.popularmoviesapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.olabode33.android.popularmoviesapp.model.Movie;
import com.olabode33.android.popularmoviesapp.model.MovieReview;
import com.olabode33.android.popularmoviesapp.model.MovieVideo;
import com.olabode33.android.popularmoviesapp.utils.JsonUtils;
import com.olabode33.android.popularmoviesapp.utils.ListViewUtils;
import com.olabode33.android.popularmoviesapp.utils.NetworkUtils;
import com.olabode33.android.popularmoviesapp.viewmodel.FavoriteMovieViewModel;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_KEY = "movie_key";
    private Movie mMovie;
    private List<MovieReview> mReviewList;
    private List<MovieVideo> mVideoList;
    private FavoriteMovieViewModel mFavoriteMovieViewModel;

    @BindView(R.id.iv_movie_backdrop) ImageView mBackDropImageView;
    @BindView(R.id.iv_movie_poster) ImageView mPosterImageView;
    @BindView(R.id.tv_movie_title) TextView mTitleTextView;
    @BindView(R.id.tv_user_rating) TextView mUserRatingTextView;
    @BindView(R.id.tv_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_overview) TextView mOverviewTextView;
    @BindView(R.id.tv_review_label) TextView mReviewLabelTextView;
    @BindView(R.id.tv_video_label) TextView mVideoLabelTextView;
    @BindView(R.id.reviews_list_view) ListView mReviewsListView;
    @BindView(R.id.videos_list_view) ListView mVideosListView;
    @BindView(R.id.btn_add_to_favorite) ImageView mAddFavoriteImageButton;
    @BindView(R.id.btn_remove_from_favorite) ImageView mRemoveFavoriteImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_MOVIE_KEY)){
            mMovie = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MOVIE_KEY));
            populateUI();
        }

        mFavoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        mAddFavoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite();
            }
        });
        mRemoveFavoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromFavorite();
            }
        });
    }

    private void populateUI(){
        setTitle(mMovie.getOriginalTitle());
        Log.i("PicassoImageBackdrop", mMovie.getBackdropPath());
        Picasso.get()
                .load(mMovie.getBackdropImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(mBackDropImageView);

        Picasso.get()
                .load(mMovie.getPosterImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(mPosterImageView);

        mTitleTextView.setText(mMovie.getTitle());
        mUserRatingTextView.setText(mMovie.getVoteAverage());
        mReleaseDateTextView.setText(mMovie.getReleaseDate());
        mOverviewTextView.setText(mMovie.getOverview());


        mReviewLabelTextView.setText(getString(R.string.detail_review_label));
        mVideoLabelTextView.setText(getString(R.string.detail_video_label));
        getReviews();
        getVideos();
    }

    private void getReviews(){
        String opt = mMovie.getMovieId() + NetworkUtils.REVIEWS_URL;
        URL url = NetworkUtils.buildUrl(this, opt);

        if(NetworkUtils.isOnline(this)){
            new MovieDetailsAPITask(MovieDetailsAPITask.REVIEW_DATA).execute(url);
        }
        else{
            Toast.makeText(this, getString(R.string.error_no_network), Toast.LENGTH_LONG).show();
        }
    }

    private void getVideos(){
        String opt = mMovie.getMovieId() + NetworkUtils.TRAILERS_URL;
        URL url = NetworkUtils.buildUrl(this, opt);

        if(NetworkUtils.isOnline(this)){
            new MovieDetailsAPITask(MovieDetailsAPITask.VIDEO_DATA).execute(url);
        }
        else{
            Toast.makeText(this, getString(R.string.error_no_network), Toast.LENGTH_LONG).show();
        }
    }

    private void addToFavorite(){
        mFavoriteMovieViewModel.addFavoriteMovie(mMovie);
        String toastMsg = "Movie added to favorite";
        Toast toast = Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void removeFromFavorite(){
        mFavoriteMovieViewModel.deleteFavoriteMovie(mMovie);
        String toastMsg = "Movie removed to favorite";
        Toast toast = Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private class MovieDetailsAPITask extends AsyncTask<URL, Void, String>{
        private int outputDataType;

        protected static final int REVIEW_DATA = 1;
        protected static final int VIDEO_DATA = 2;

        MovieDetailsAPITask(int data_type){
            outputDataType = data_type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
            switch (outputDataType){
                case REVIEW_DATA:
                    mReviewList = JsonUtils.parseMovieReviewsListJson(s);
                    //mReviewsTextView.setText(mReviewList.toString());
                    ReviewAdapter reviewAdapter = new ReviewAdapter(mReviewList, DetailActivity.this);
                    mReviewsListView.setAdapter(reviewAdapter);
                    ListViewUtils.setListViewHeightBasedOnChildren(mReviewsListView);
                    break;
                case VIDEO_DATA:
                    mVideoList = JsonUtils.parseMovieVideoListJson(s);
                    //mVideosTextView.setText(mVideoList.toString());
                    VideoAdapter videoAdapter = new VideoAdapter(mVideoList, DetailActivity.this);
                    mVideosListView.setAdapter(videoAdapter);
                    ListViewUtils.setListViewHeightBasedOnChildren(mVideosListView);
                    mVideosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MovieVideo movieVideo = mVideoList.get(position);
                            NetworkUtils.watchYoutubeVideo(DetailActivity.this, movieVideo.getKey());
                        }
                    });
                    break;
                default:
                    super.onPostExecute(s);
                    break;
            }
        }
    }
}
