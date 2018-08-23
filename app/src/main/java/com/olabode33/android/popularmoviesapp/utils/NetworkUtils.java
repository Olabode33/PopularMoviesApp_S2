package com.olabode33.android.popularmoviesapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.olabode33.android.popularmoviesapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by obello004 on 8/20/2018.
 */

public class NetworkUtils {
    //Define base URLs
    final static String THEMOVIEDB_BASE_API_URL = "https://api.themoviedb.org/3/movie/";

    //Define url parameters
    final static String PARAM_API_KEY = "api_key";

    public static URL buildUrl(Context context, String option){
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_API_URL + option).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, context.getString(R.string.the_movie_db_api_key))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
