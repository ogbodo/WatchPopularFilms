package com.izuking.udacity.android.watchmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.izuking.udacity.android.watchmovies.utils.JSONObject;
import com.izuking.udacity.android.watchmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Izuking on 4/15/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private MovieAdapterOnClickListener mClick;
    private Context cxt;
    private List<JSONObject.JSONResponse> movieList;

    public MovieAdapter(Context cxt, MovieAdapterOnClickListener click) {
        this.cxt = cxt;
        this.mClick = click;
    }

    public void setMovieData(List<JSONObject.JSONResponse> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list, parent, false);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Picasso.with(cxt).load(NetworkUtils.IMGURL + movieList.get(position).getPoster_path()).placeholder(R.drawable.placeholder).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public interface MovieAdapterOnClickListener {
        void onclick(JSONObject.JSONResponse response);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView posterImage;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClick.onclick(movieList.get(getAdapterPosition()));
        }
    }
}
