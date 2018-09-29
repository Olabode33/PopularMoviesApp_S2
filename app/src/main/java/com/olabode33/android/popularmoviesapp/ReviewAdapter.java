package com.olabode33.android.popularmoviesapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.olabode33.android.popularmoviesapp.model.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 9/28/2018.
 */

public class ReviewAdapter extends ArrayAdapter {
    private Context mContext;
    private List<MovieReview> mMovieReviewList;
    private final LayoutInflater mInflater;


    public ReviewAdapter(List<MovieReview> reviewList, Context context){
        super(context, R.layout.review_list_item, reviewList);
        mMovieReviewList = reviewList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View view, ViewGroup parent){
        ViewHolder holder;
        if(view != null){
            holder = (ViewHolder) view.getTag();
        }
        else {
            view = mInflater.inflate(R.layout.review_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        MovieReview movieReview = mMovieReviewList.get(position);

        holder.reviewAuthorTextView.setText(movieReview.getAuthor());
        holder.reviewContentTextView.setText(movieReview.getContent());

        return view;
    }

    static final class ViewHolder {
        @BindView(R.id.tv_review_author) TextView reviewAuthorTextView;
        @BindView(R.id.tv_review_content) TextView reviewContentTextView;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
