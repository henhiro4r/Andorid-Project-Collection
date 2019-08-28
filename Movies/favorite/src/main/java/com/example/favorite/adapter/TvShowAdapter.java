package com.example.favorite.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favorite.R;
import com.example.favorite.model.TvShow;

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
                .into(tvShowViewHolder.cover);
        tvShowViewHolder.title.setText(t.getTitle());
        tvShowViewHolder.popular.setText(t.getPopularity());
        tvShowViewHolder.date.setText(t.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return tvShow.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title, popular, date;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.item_cover);
            title = itemView.findViewById(R.id.item_title);
            popular = itemView.findViewById(R.id.item_popular);
            date = itemView.findViewById(R.id.item_date);
        }
    }
}
