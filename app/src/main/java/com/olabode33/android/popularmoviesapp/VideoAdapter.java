package com.olabode33.android.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.olabode33.android.popularmoviesapp.model.MovieReview;
import com.olabode33.android.popularmoviesapp.model.MovieVideo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 9/28/2018.
 */

public class VideoAdapter extends ArrayAdapter {
    private Context mContext;
    private List<MovieVideo> mMovieVideoList;
    private final LayoutInflater mInflater;


    public VideoAdapter(List<MovieVideo> videoList, Context context){
        super(context, R.layout.video_list_item, videoList);
        mMovieVideoList = videoList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View view, ViewGroup parent){
        ViewHolder holder;
        if(view != null){
            holder = (ViewHolder) view.getTag();
        }
        else {
            view = mInflater.inflate(R.layout.video_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        MovieVideo movieVideo = mMovieVideoList.get(position);

        holder.videoNameTextView.setText(movieVideo.getVideoName());
        holder.videoTypeTextView.setText(movieVideo.getType());

        return view;
    }

    static final class ViewHolder {
        @BindView(R.id.tv_video_name) TextView videoNameTextView;
        @BindView(R.id.tv_video_type) TextView videoTypeTextView;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
