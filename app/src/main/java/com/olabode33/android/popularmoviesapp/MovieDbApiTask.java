package com.olabode33.android.popularmoviesapp;

import android.os.AsyncTask;
import android.util.Log;

import com.olabode33.android.popularmoviesapp.utils.JsonUtils;
import com.olabode33.android.popularmoviesapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by obello004 on 8/22/2018.
 */

public class MovieDbApiTask extends AsyncTask<URL, Void, String> {

    public String mTaskResult = "";

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
        mTaskResult = s;
    }
}
