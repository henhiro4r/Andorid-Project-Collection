package com.example.favorite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favorite.R;
import com.example.favorite.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> moviesData = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movie> movies) {
        moviesData.clear();
        moviesData.addAll(movies);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListMovie() {
        return moviesData;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie m = moviesData.get(i);
        Glide.with(context)
                .load(m.getCover())
                .into(movieViewHolder.cover);
        movieViewHolder.title.setText(m.getTitle());
        movieViewHolder.popular.setText(m.getPopularity());
        movieViewHolder.date.setText(m.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title, popular, date;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.item_cover);
            title = itemView.findViewById(R.id.item_title);
            popular = itemView.findViewById(R.id.item_popular);
            date = itemView.findViewById(R.id.item_date);
        }
    }
}
