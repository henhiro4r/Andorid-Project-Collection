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
import com.example.movies.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private Context context;
    private ArrayList<TvShow> tvShows;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv_show, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int i) {
        TvShow t = getTvShows().get(i);
        Glide.with(context)
                .load(t.getCover())
                .into(tvShowViewHolder.show_cover);
        tvShowViewHolder.show_title.setText(t.getTitle());
        tvShowViewHolder.show_creator.setText(t.getCreator());
        tvShowViewHolder.show_date.setText(t.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return getTvShows().size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {

        ImageView show_cover;
        TextView show_title, show_creator, show_date;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            show_cover = itemView.findViewById(R.id.show_cover);
            show_title = itemView.findViewById(R.id.show_title);
            show_creator = itemView.findViewById(R.id.show_creator);
            show_date = itemView.findViewById(R.id.show_date);
        }
    }
}
