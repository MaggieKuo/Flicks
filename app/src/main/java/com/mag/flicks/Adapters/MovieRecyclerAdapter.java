package com.mag.flicks.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mag.flicks.R;
import com.mag.flicks.models.Movie;
import com.mag.flicks.models.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieHolder> {
    private final String TAG = "MovieRecyclerAdapter";
    Result result = new Result();
    MovieOnclick movieOnclick;

    public MovieRecyclerAdapter(MovieOnclick movieOnclick) {
        this.movieOnclick = movieOnclick;
    }

    public void setResult(Result result){
        Log.d(TAG, "MovieRecyclerAdapter " + result.getResults().size());
        this.result = result;
        result.getResults().addAll(result.getResults());
        notifyDataSetChanged();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_portrait, null);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder " + result.getResults().size());

        if (getItemCount() == 0)
            return;

        holder.setData(result.getMovieByPosition(position), movieOnclick);

    }

    @Override
    public int getItemCount() {
        return (result == null || result.getResults() == null) ? 0 : result.getResults().size();
    }

    public interface MovieOnclick{
        void onMovieImageClick(Movie movie);
    }

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MovieOnclick movieOnclick;

        @BindView(R.id.img_movie)
        ImageView imgMovie;

        @BindView(R.id.txt_title)
        TextView tvTitle;

        @BindView(R.id.txt_overview)
        TextView tvOverview;

        @BindView(R.id.layout_overview)
        LinearLayout layoutOverview;

        @BindView(R.id.layout_movie)
        RelativeLayout layoutMovie;

        @BindView(R.id.img_play)
        ImageView imgPlay;



        Movie movie;


        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(Movie movie, MovieOnclick movieOnclick){

            Log.d(TAG, "MovieHolder.setdata");

            this.movie = movie;
            this.movieOnclick = movieOnclick;

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String img_url = "https://image.tmdb.org/t/p/w342";

            imgPlay.setVisibility((movie.getVote_average()<5) ? View.GONE : View.VISIBLE);
            String img_po = img_url+ (movie.getVote_average()>=5 ? movie.getBackdrop_path() : movie.getPoster_path());

            LinearLayout.LayoutParams layoutMovieLayoutParams = (LinearLayout.LayoutParams) layoutMovie.getLayoutParams();
            LinearLayout.LayoutParams layoutOverviewLayoutParams = (LinearLayout.LayoutParams) layoutOverview.getLayoutParams();
            int orientation = itemView.getContext().getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_PORTRAIT){
                layoutOverview.setVisibility((movie.getVote_average()>=5) ? View.GONE : View.VISIBLE);

                if (movie.getVote_average()>=5){
                    layoutMovieLayoutParams.weight = 10;
//                    layoutOverviewLayoutParams.weight = 0f;
                }else{
                    layoutMovieLayoutParams.weight = 6;
                    layoutOverviewLayoutParams.weight = 4;
                }

            }else{

                if (movie.getVote_average()>=5) {

                    layoutMovieLayoutParams.weight = 7;
                    layoutOverviewLayoutParams.weight = 3;
                }else{
                    layoutMovieLayoutParams.weight = 3;
                    layoutOverviewLayoutParams.weight = 7;
                }


            }
            layoutMovie.setLayoutParams(layoutMovieLayoutParams);
            layoutMovie.setLayoutParams(layoutOverviewLayoutParams);

            Glide.with(imgMovie.getContext())
                    .load(img_po)
                    .into(imgMovie);


            if (movie.getVote_average()>=5){
                imgMovie.setOnClickListener(this);
                imgPlay.setOnClickListener(this);
            }

        }


        @Override
        public void onClick(View v) {
            if (movie!=null && movie.getVote_average()>5){
                movieOnclick.onMovieImageClick(movie);
            }

        }
    }
}
