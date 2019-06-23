package com.example.movie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movie.Model.Movie;
import com.example.movie.R;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }


    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        Movie movie = (Movie) getItem(i);
        viewHolder.bind(movie);
        return view;
    }

    private class ViewHolder{
        ImageView ivImage;
        TextView tvTitle, tvReleaseDate, tvGenre;

        ViewHolder(View view){
            ivImage = view.findViewById(R.id.img_movie_image);
            tvTitle = view.findViewById(R.id.tv_movie_title);
            tvReleaseDate = view.findViewById(R.id.tv_movie_releaseDate);
            tvGenre = view.findViewById(R.id.tv_movie_genre);
        }

        void bind(Movie movie) {
            Glide.with(context)
                    .load(movie.getPhoto())
                    .apply(new RequestOptions().override(130,180))
                    .into(ivImage);
            tvTitle.setText(movie.getTitle());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvGenre.setText(movie.getGenres());
        }
    }
}
