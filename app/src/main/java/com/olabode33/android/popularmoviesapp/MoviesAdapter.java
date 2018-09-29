package com.olabode33.android.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.olabode33.android.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 8/21/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> mMovieList;
    private Context mContext;

    public MoviesAdapter(List<Movie> movieList, Context context){
        mMovieList = movieList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                    .inflate(R.layout.movie_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Movie movie = mMovieList.get(position);

        Picasso.get()
                .load(movie.getPosterImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.mMovieItemImageView);

        holder.mMovieItemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = mMovieList.get(position);

                Intent detailsIntent = new Intent(view.getContext(), DetailActivity.class);
                detailsIntent.putExtra(DetailActivity.EXTRA_MOVIE_KEY, Parcels.wrap(movie));
                view.getContext().startActivity(detailsIntent);
            }
        });
    }

    public void addMovies(List<Movie> movieList){
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_movie_item)
        ImageView mMovieItemImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
