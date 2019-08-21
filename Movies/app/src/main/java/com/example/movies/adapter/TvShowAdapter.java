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
    private ArrayList<TvShow> tvShow = new ArrayList<>();

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        tvShow.clear();
        tvShow.addAll(tvShows);
        notifyDataSetChanged();
    }

    public ArrayList<TvShow> getListShow() {
        return tvShow;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int i) {
        TvShow t = tvShow.get(i);
        Glide.with(context)
                .load(t.getCover())
                .into(tvShowViewHolder.show_cover);
        tvShowViewHolder.show_title.setText(t.getTitle());
        tvShowViewHolder.show_popular.setText(t.getPopularity());
        tvShowViewHolder.show_date.setText(t.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return tvShow.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        ImageView show_cover;
        TextView show_title, show_popular, show_date;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            show_cover = itemView.findViewById(R.id.item_cover);
            show_title = itemView.findViewById(R.id.item_title);
            show_popular = itemView.findViewById(R.id.item_popular);
            show_date = itemView.findViewById(R.id.item_date);
        }
    }
}
