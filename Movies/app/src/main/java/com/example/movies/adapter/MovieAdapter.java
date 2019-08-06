package com.example.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {

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

    @NonNull
    @Override
    public MoviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new MoviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviewViewHolder moviewViewHolder, int i) {
        Movie m = moviesData.get(i);
        Glide.with(context)
                .load(m.getCover())
                .into(moviewViewHolder.movie_cover);
        moviewViewHolder.movie_title.setText(m.getTitle());
        moviewViewHolder.movie_director.setText(m.getDirector());
        moviewViewHolder.movie_date.setText(m.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class MoviewViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_cover;
        TextView movie_title, movie_director, movie_date;

        public MoviewViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_cover = itemView.findViewById(R.id.movie_cover);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_director = itemView.findViewById(R.id.movie_director);
            movie_date = itemView.findViewById(R.id.movie_date);
        }
    }
}
