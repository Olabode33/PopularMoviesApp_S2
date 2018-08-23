package com.olabode33.android.popularmoviesapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.olabode33.android.popularmoviesapp.model.Movie;
import com.olabode33.android.popularmoviesapp.utils.JsonUtils;
import com.olabode33.android.popularmoviesapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //Initialize Views
    @BindView(R.id.movies_recycler_view)
    RecyclerView mMoviesRecyclerView;

    @BindView(R.id.pd_loading_movies)
    ProgressBar mLoadingMovies;

    @BindView(R.id.tv_error_no_network)
    TextView mErrorNoNetworkTextView;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMoviesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);

        makeApiCall(getString(R.string.param_sort_by_popularity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sort_by_popularity:
                makeApiCall(getString(R.string.param_sort_by_popularity));
                setTitle(getString(R.string.title_action_sort_by_popularity) + " Movies");
                break;
            case R.id.action_sort_by_rating:
                makeApiCall(getString(R.string.param_sort_by_rating));
                setTitle(getString(R.string.title_action_sort_by_rating) + " Movies");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeApiCall(String sortBy){
        URL url = NetworkUtils.buildUrl(this, sortBy);
        mErrorNoNetworkTextView.setVisibility(View.INVISIBLE);

        if(NetworkUtils.isOnline(this)){
            new TheMovieDbApiTask().execute(url);
        }
        else{
            Toast.makeText(this, getString(R.string.error_no_network), Toast.LENGTH_LONG).show();
            hideProgressBar();
            mErrorNoNetworkTextView.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar(){
        if(mLoadingMovies != null && mLoadingMovies.isShown()){
            mLoadingMovies.setVisibility(View.INVISIBLE);
        }
    }

    private class TheMovieDbApiTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(mLoadingMovies != null && !mLoadingMovies.isShown()){
                mLoadingMovies.setVisibility(View.VISIBLE);
            }
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
            //Log.i("API_RESULT", s.toString());

            mMovieList =  JsonUtils.parseMoviesListJson(s);
            mAdapter = new MoviesAdapter(mMovieList, MainActivity.this);
            mMoviesRecyclerView.setAdapter(mAdapter);

            hideProgressBar();
        }
    }
}
